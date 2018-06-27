package com.epam.popcornapp.ui.tv.episode;

import android.content.Context;
import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.epam.popcornapp.application.BaseApplication;
import com.epam.popcornapp.application.Constants;
import com.epam.popcornapp.application.ErrorMessage.ErrorType;
import com.epam.popcornapp.model.tv.episode.ITvEpisodeInteractor;
import com.epam.popcornapp.pojo.base.rating.Rating;
import com.epam.popcornapp.pojo.tv.episode.TvEpisodeResult;
import com.epam.popcornapp.ui.all.base.BaseInternetMvpPresenter;
import com.google.gson.JsonSyntaxException;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class TvEpisodePresenter extends BaseInternetMvpPresenter<TvEpisodeView>
        implements ITvEpisodeInteractor.Listener {

    @Inject
    Context context;

    @Inject
    ITvEpisodeInteractor interactor;

    private TvEpisodeParams tvEpisodeParams;
    private float rating;

    private Disposable disposable;

    TvEpisodePresenter() {
        BaseApplication.getApplicationComponent().inject(this);

        interactor.setListener(this);
    }

    public void getData(@NonNull final TvEpisodeParams tvEpisodeParams) {
        this.tvEpisodeParams = tvEpisodeParams;

        interactor.getData(tvEpisodeParams, isInternetConnection(context));

        if (Constants.CURRENT_SESSION != null) {
            interactor.getCurrentRating(tvEpisodeParams, Constants.CURRENT_SESSION);
        }
    }

    void retryClicked() {
        if (isInternetConnection(context)) {
            getData(tvEpisodeParams);
        } else {
            onError();
        }
    }


    void rateTvEpisode(final float rating) {
        this.rating = rating;

        interactor.rateEpisode(rating * 2, tvEpisodeParams);
    }

    void deleteRating() {
        this.rating = 0.0f;

        interactor.deleteRating(tvEpisodeParams);
    }

    @Override
    public void onSuccess(final Observable<TvEpisodeResult> observable, final boolean isResponseFromServer) {
        if (disposable != null && !disposable.isDisposed()) {
            return;
        }

        observable.map(new Function<TvEpisodeResult, TvEpisodeData>() {
            @Override
            public TvEpisodeData apply(@NonNull final TvEpisodeResult tvEpisodeResult) throws Exception {
                return TvEpisodeData.newBuilder()
                        .setPhoto(tvEpisodeResult.getStillPath())
                        .setDetail(tvEpisodeResult)
                        .setDirector(tvEpisodeResult.getDirector())
                        .setWriter(tvEpisodeResult.getWriter())
                        .setImages(tvEpisodeResult.getImages())
                        .setCredits(tvEpisodeResult.getCredits())
                        .build();
            }
        })
                .subscribe(new Observer<TvEpisodeData>() {
                    @Override
                    public void onSubscribe(final Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(final TvEpisodeData tvEpisodeData) {
                        disposable.dispose();

                        getViewState().setInfo(tvEpisodeData, isResponseFromServer);
                    }

                    @Override
                    public void onError(final Throwable e) {
                        if (isResponseFromServer) {
                            disposable.dispose();

                            interactor.getData(tvEpisodeParams, false);
                        }

                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void onError() {
        getViewState().onError(ErrorType.INTERNET_CONNECTION_ERROR);
    }

    @Override
    public void onRatingLoaded(final Observable<Rating> observable) {
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Rating>() {
                    @Override
                    public void onSubscribe(final Disposable d) {

                    }

                    @Override
                    public void onNext(final Rating rating) {
                        getViewState().setCurrentRating(rating.getRated().getValue() / 2);
                    }

                    @Override
                    public void onError(final Throwable e) {
                        if (e instanceof JsonSyntaxException) {
                            getViewState().setCurrentRating(0f);
                        } else {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void onRatingResult(final boolean isSuccess) {
        getViewState().onRatingResult(isSuccess, rating);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        interactor.onDestroy();
    }
}
