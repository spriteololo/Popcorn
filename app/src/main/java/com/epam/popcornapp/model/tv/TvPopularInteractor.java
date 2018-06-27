package com.epam.popcornapp.model.tv;


import android.support.annotation.NonNull;

import com.epam.popcornapp.application.BaseApplication;
import com.epam.popcornapp.application.Constants;
import com.epam.popcornapp.inject.TvInterface;
import com.epam.popcornapp.model.base.BaseInteractor;
import com.epam.popcornapp.pojo.tv.models.TvPopularModel;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmObject;

public class TvPopularInteractor extends BaseInteractor implements ITvPopularInteractor {

    @Inject
    TvInterface webTvApi;

    private ITvPopularInteractor.InteractorActions listener;

    public TvPopularInteractor() {
        BaseApplication.getApplicationComponent().inject(this);

        openRealm();
    }

    @Override
    public void setListener(final InteractorActions listener) {
        this.listener = listener;
    }

    @Override
    public void getPopularTvShows(final boolean hasConnection, final int currentPage) {
        if (hasConnection) {
            final Observable<TvPopularModel> observable =
                    webTvApi.getPopularTvShows(Constants.API_KEY, currentPage)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .map(new Function<TvPopularModel, TvPopularModel>() {
                                @Override
                                public TvPopularModel apply(final TvPopularModel tvPopularModel) throws Exception {
                                    insertPopularTvShow(tvPopularModel);

                                    return tvPopularModel;
                                }
                            });

            if (listener != null) {
                listener.onSuccess(observable, true);
            }
        } else {
            final Flowable<RealmObject> result = getPopularTvShows(currentPage);

            if (listener != null) {
                listener.onSuccess(result.toObservable().cast(TvPopularModel.class), false);
                listener.onError();
            }

        }
    }

    @Override
    public void insertPopularTvShow(final TvPopularModel tvPopularModel) {
        openRealm();

        getRealm().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull final Realm realm) {
                tvPopularModel.setResults(BaseTvDB.convertPage(tvPopularModel.getResults(), realm));

                realm.copyToRealmOrUpdate(tvPopularModel);
            }
        });
    }

    private Flowable<RealmObject> getPopularTvShows(final int position) {
        openRealm();

        return getRealm().where(TvPopularModel.class)
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
