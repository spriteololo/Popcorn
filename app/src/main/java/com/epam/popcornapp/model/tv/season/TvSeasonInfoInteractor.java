package com.epam.popcornapp.model.tv.season;

import android.support.annotation.NonNull;

import com.epam.popcornapp.application.BaseApplication;
import com.epam.popcornapp.application.Constants;
import com.epam.popcornapp.inject.TvInterface;
import com.epam.popcornapp.model.base.BaseInteractor;
import com.epam.popcornapp.model.base.RealmUtils;
import com.epam.popcornapp.pojo.tv.credits.TvCredits;
import com.epam.popcornapp.pojo.tv.season.SeasonInfo;
import com.epam.popcornapp.ui.tv.season.StartParams;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmObject;

public class TvSeasonInfoInteractor extends BaseInteractor implements ITvSeasonInfoInteractor {

    @Inject
    TvInterface tvInterface;

    private ITvSeasonInfoInteractor.InteractorActions listener;

    public TvSeasonInfoInteractor() {
        BaseApplication.getApplicationComponent().inject(this);

        openRealm();
    }

    public void setListener(final InteractorActions listener) {
        this.listener = listener;
    }

    @Override
    public void loadSeasonInfo(@NonNull final StartParams startParams, final boolean hasConnection) {
        if (hasConnection) {
            final Observable<SeasonInfo> observable =
                    tvInterface.getSeasonInfo(startParams.getTvId(), startParams.getSeasonNumber(), Constants.API_KEY)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .map(new Function<SeasonInfo, SeasonInfo>() {
                                @Override
                                public SeasonInfo apply(final SeasonInfo seasonInfo) throws Exception {
                                    insertSeasonInfo(seasonInfo);

                                    return seasonInfo;
                                }
                            });

            if (listener != null) {
                listener.onSuccess(observable, true);
            }
        } else {
            if (listener != null) {
                final int idIndex = startParams.getSeasonNumber();

                listener.onSuccess(getSeasonInfo((int) startParams.getSeasonIdList().get(idIndex))
                        .toObservable().cast(SeasonInfo.class), false);

                listener.onError();
            }
        }
    }

    private void insertSeasonInfo(@NonNull final SeasonInfo seasonInfo) {
        openRealm();

        getRealm().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull final Realm realm) {
                final TvCredits tvCredits = seasonInfo.getCredits();

                tvCredits.setId(seasonInfo.getId());
                tvCredits.setCrew(null);
                tvCredits.setCast(RealmUtils.getUpdatedCredits(realm, tvCredits.getCast()));

                seasonInfo.setEpisodeCount(seasonInfo.getEpisodes().size());
                seasonInfo.setEpisodes(RealmUtils.getUpdatedEpisodes(realm, seasonInfo.getEpisodes()));
                seasonInfo.setCredits(tvCredits);

                realm.copyToRealmOrUpdate(seasonInfo);
            }
        });
    }


    private Flowable<RealmObject> getSeasonInfo(final int id) {
        openRealm();

        return getRealm().where(SeasonInfo.class)
                .equalTo("id", id)
                .findFirstAsync()
                .asFlowable()
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void closeDb() {
        closeRealm();
    }
}
