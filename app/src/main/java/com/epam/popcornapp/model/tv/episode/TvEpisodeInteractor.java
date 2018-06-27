package com.epam.popcornapp.model.tv.episode;

import android.support.annotation.NonNull;
import android.util.Log;

import com.epam.popcornapp.application.BaseApplication;
import com.epam.popcornapp.application.Constants;
import com.epam.popcornapp.inject.TvInterface;
import com.epam.popcornapp.model.base.BaseInteractor;
import com.epam.popcornapp.model.base.RealmUtils;
import com.epam.popcornapp.model.tv.BaseTvDB;
import com.epam.popcornapp.pojo.base.rating.RatingItem;
import com.epam.popcornapp.pojo.base.rating.RatingResponse;
import com.epam.popcornapp.pojo.tv.credits.TvCredits;
import com.epam.popcornapp.pojo.tv.episode.TvEpisodeResult;
import com.epam.popcornapp.ui.tv.episode.TvEpisodeParams;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmObject;

public class TvEpisodeInteractor extends BaseInteractor implements ITvEpisodeInteractor {

    @Inject
    TvInterface tvInterface;

    private ITvEpisodeInteractor.Listener listener;

    public TvEpisodeInteractor() {
        BaseApplication.getApplicationComponent().inject(this);

        openRealm();
    }

    @Override
    public void setListener(@NonNull final ITvEpisodeInteractor.Listener listener) {
        this.listener = listener;
    }

    @Override
    public void onDestroy() {
        closeRealm();
    }

    @Override
    public void getCurrentRating(final TvEpisodeParams params, final String session) {
        if (listener != null) {
            listener.onRatingLoaded(tvInterface.getCurrentRating(params.getTvId(),
                    params.getSeasonNumber(), params.getEpisodeNumber(),
                    Constants.API_KEY, Constants.CURRENT_SESSION));
        }
    }

    @Override
    public void rateEpisode(final float rating, final TvEpisodeParams params) {
        if (listener == null) {
            return;
        }

        tvInterface.rateTvEpisode(params.getTvId(), params.getSeasonNumber(),
                params.getEpisodeNumber(),
                Constants.API_KEY, Constants.CURRENT_SESSION, new RatingItem(rating))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RatingResponse>() {
                    @Override
                    public void onSubscribe(final Disposable d) {

                    }

                    @Override
                    public void onNext(final RatingResponse ratingResponse) {
                        listener.onRatingResult(true);
                    }

                    @Override
                    public void onError(final Throwable e) {
                        Log.i("POST ERROR", e.toString());
                        listener.onRatingResult(false);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void deleteRating(final TvEpisodeParams params) {
        if (listener == null) {
            return;
        }

        tvInterface.deleteRating(params.getTvId(), params.getSeasonNumber(),
                params.getEpisodeNumber(), Constants.API_KEY, Constants.CURRENT_SESSION)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RatingResponse>() {
                    @Override
                    public void onSubscribe(final Disposable d) {

                    }

                    @Override
                    public void onNext(final RatingResponse ratingResponse) {
                        listener.onRatingResult(true);
                    }

                    @Override
                    public void onError(final Throwable e) {
                        Log.e("Delete rating error", e.toString());
                        listener.onRatingResult(false);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void getData(@NonNull final TvEpisodeParams tvEpisodeParams, final boolean isInternetConnection) {
        if (isInternetConnection) {
            final Observable<TvEpisodeResult> observable =
                    tvInterface.getEpisode(tvEpisodeParams.getTvId(),
                            tvEpisodeParams.getSeasonNumber(),
                            tvEpisodeParams.getEpisodeNumber(),
                            Constants.API_KEY)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .map(new Function<TvEpisodeResult, TvEpisodeResult>() {
                                @Override
                                public TvEpisodeResult apply(final TvEpisodeResult tvEpisode) throws Exception {
                                    insertTvEpisode(tvEpisode);

                                    return tvEpisode;
                                }
                            });

            listener.onSuccess(observable, true);
        } else {
            final int idIndex = tvEpisodeParams.getEpisodeNumber() - 1;

            listener.onSuccess(getTvEpisode((int) tvEpisodeParams.getEpisodeIdList().get(idIndex))
                    .toObservable().cast(TvEpisodeResult.class), false);

            listener.onError();
        }
    }

    private void insertTvEpisode(@NonNull final TvEpisodeResult tvEpisode) {
        openRealm();

        getRealm().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull final Realm realm) {
                final TvCredits tvCredits = tvEpisode.getCredits();

                final String directorKey = "Director";
                final String writerKey = "Writer";

                tvEpisode.getImages().setId(tvEpisode.getId());

                tvEpisode.setDirector(BaseTvDB.getNameByJobKey(tvCredits.getCrew(), directorKey));
                tvEpisode.setWriter(BaseTvDB.getNameByJobKey(tvCredits.getCrew(), writerKey));

                tvCredits.setId(tvEpisode.getId());
                tvCredits.setCrew(null);
                tvCredits.getCast().addAll(tvCredits.getGuestStars());
                tvCredits.setCast(RealmUtils.getUpdatedCredits(realm, tvCredits.getCast()));

                realm.copyToRealmOrUpdate(tvEpisode);
            }
        });
    }

    private Flowable<RealmObject> getTvEpisode(final int id) {
        openRealm();

        return getRealm().where(TvEpisodeResult.class)
                .equalTo("id", id)
                .findFirstAsync()
                .asFlowable()
                .observeOn(AndroidSchedulers.mainThread());
    }
}
