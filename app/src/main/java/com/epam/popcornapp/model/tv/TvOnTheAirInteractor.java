package com.epam.popcornapp.model.tv;

import android.support.annotation.NonNull;

import com.epam.popcornapp.application.BaseApplication;
import com.epam.popcornapp.application.Constants;
import com.epam.popcornapp.inject.TvInterface;
import com.epam.popcornapp.model.base.BaseInteractor;
import com.epam.popcornapp.pojo.tv.models.TvOnTheAirModel;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmObject;

public class TvOnTheAirInteractor extends BaseInteractor implements ITvOnTheAirInteractor {

    @Inject
    TvInterface webTvApi;

    private ITvOnTheAirInteractor.InteractorActions listener;

    public TvOnTheAirInteractor() {
        BaseApplication.getApplicationComponent().inject(this);

        openRealm();
    }

    @Override
    public void setListener(final InteractorActions listener) {
        this.listener = listener;
    }

    @Override
    public void getOnTheAirTvShows(final boolean hasConnection, final int currentPage) {
        if (hasConnection) {
            final Observable<TvOnTheAirModel> observable =
                    webTvApi.getOnTheAirTvShows(Constants.API_KEY, currentPage)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .map(new Function<TvOnTheAirModel, TvOnTheAirModel>() {
                                @Override
                                public TvOnTheAirModel apply(final TvOnTheAirModel tvOnTheAirModel) throws Exception {
                                    insertOnTheAirTvShow(tvOnTheAirModel);

                                    return tvOnTheAirModel;
                                }
                            });

            if (listener != null) {
                listener.onSuccess(observable, true);
            }
        } else {
            final Flowable<RealmObject> result = getOnTheAirTvShows(currentPage);

            if (listener != null) {
                listener.onSuccess(result.toObservable().cast(TvOnTheAirModel.class), false);
                listener.onError();
            }

        }
    }

    @Override
    public void insertOnTheAirTvShow(final TvOnTheAirModel tvOnTheAirModel) {
        openRealm();

        getRealm().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull final Realm realm) {
                tvOnTheAirModel.setResults(BaseTvDB.convertPage(tvOnTheAirModel.getResults(), realm));
                realm.copyToRealmOrUpdate(tvOnTheAirModel);
            }
        });
    }

    private Flowable<RealmObject> getOnTheAirTvShows(final int position) {
        openRealm();

        return getRealm().where(TvOnTheAirModel.class)
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
