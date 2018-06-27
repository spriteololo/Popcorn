package com.epam.popcornapp.model.tv;

import android.support.annotation.NonNull;

import com.epam.popcornapp.application.BaseApplication;
import com.epam.popcornapp.application.Constants;
import com.epam.popcornapp.inject.TvInterface;
import com.epam.popcornapp.model.base.BaseInteractor;
import com.epam.popcornapp.pojo.tv.models.TvTopRatedModel;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmObject;

public class TvTopRatedInteractor extends BaseInteractor implements ITvTopRatedInteractor {

    @Inject
    TvInterface webTvApi;

    private ITvTopRatedInteractor.InteractorActions listener;

    public TvTopRatedInteractor() {
        BaseApplication.getApplicationComponent().inject(this);

        openRealm();
    }

    @Override
    public void setListener(final InteractorActions listener) {
        this.listener = listener;
    }

    @Override
    public void getTopRatedTvShows(final boolean hasConnection, final int currentPage) {
        if (hasConnection) {
            final Observable<TvTopRatedModel> observable =
                    webTvApi.getTopRatedTvShows(Constants.API_KEY, currentPage)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .map(new Function<TvTopRatedModel, TvTopRatedModel>() {
                                @Override
                                public TvTopRatedModel apply(final TvTopRatedModel tvTopRatedModel) throws Exception {
                                    insertTopRatedTvShows(tvTopRatedModel);

                                    return tvTopRatedModel;
                                }
                            });

            if (listener != null) {
                listener.onSuccess(observable, true);
            }
        } else {
            final Flowable<RealmObject> result = getTopRatedTvShows(currentPage);

            if (listener != null) {
                listener.onSuccess(result.toObservable().cast(TvTopRatedModel.class), false);
                listener.onError();
            }
        }
    }

    @Override
    public void insertTopRatedTvShows(final TvTopRatedModel tvTopRatedModel) {
        openRealm();

        getRealm().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull final Realm realm) {
                tvTopRatedModel.setResults(BaseTvDB.convertPage(tvTopRatedModel.getResults(), realm));
                realm.copyToRealmOrUpdate(tvTopRatedModel);
            }
        });

    }

    private Flowable<RealmObject> getTopRatedTvShows(final int position) {
        openRealm();

        return getRealm().where(TvTopRatedModel.class)
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
