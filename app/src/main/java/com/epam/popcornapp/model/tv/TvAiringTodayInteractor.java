package com.epam.popcornapp.model.tv;

import android.support.annotation.NonNull;

import com.epam.popcornapp.application.BaseApplication;
import com.epam.popcornapp.application.Constants;
import com.epam.popcornapp.inject.TvInterface;
import com.epam.popcornapp.model.base.BaseInteractor;
import com.epam.popcornapp.pojo.tv.models.TvAiringTodayModel;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmObject;

public class TvAiringTodayInteractor extends BaseInteractor implements ITvAiringTodayInteractor {

    @Inject
    TvInterface webTvApi;

    private ITvAiringTodayInteractor.InteractorActions listener;

    public TvAiringTodayInteractor() {
        BaseApplication.getApplicationComponent().inject(this);

        openRealm();
    }

    @Override
    public void setListener(final InteractorActions listener) {
        this.listener = listener;
    }

    @Override
    public void getAiringTodayTvShows(final boolean hasConnection, final int currentPage) {
        if (hasConnection) {
            final Observable<TvAiringTodayModel> observable =
                    webTvApi.getAiringTodayTvShows(Constants.API_KEY, currentPage)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .map(new Function<TvAiringTodayModel, TvAiringTodayModel>() {
                                @Override
                                public TvAiringTodayModel apply(final TvAiringTodayModel tvAiringTodayModel) throws Exception {
                                    insertAiringTodayShow(tvAiringTodayModel);

                                    return tvAiringTodayModel;
                                }
                            });

            if (listener != null) {
                listener.onSuccess(observable, true);
            }
        } else {
            final Flowable<RealmObject> result = getAiringTodayTvShows(currentPage);

            if (listener != null) {
                listener.onSuccess(result.toObservable().cast(TvAiringTodayModel.class), false);
                listener.onError();
            }
        }
    }

    @Override
    public void insertAiringTodayShow(final TvAiringTodayModel tvAiringTodayModel) {
        openRealm();

        getRealm().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull final Realm realm) {
                tvAiringTodayModel.setResults(BaseTvDB.convertPage(tvAiringTodayModel.getResults(), realm));
                realm.copyToRealmOrUpdate(tvAiringTodayModel);
            }
        });
    }

    private Flowable<RealmObject> getAiringTodayTvShows(final int position) {
        openRealm();

        return getRealm().where(TvAiringTodayModel.class)
                .equalTo("page", position)
                .findFirstAsync()
                .asFlowable()
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void onDestroy() {
        closeRealm();
    }
}
