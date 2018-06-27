package com.epam.popcornapp.ui.tv.info;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;
import com.epam.popcornapp.application.BaseApplication;
import com.epam.popcornapp.application.Constants;
import com.epam.popcornapp.application.ErrorMessage.ErrorType;
import com.epam.popcornapp.inject.AccountInterface;
import com.epam.popcornapp.model.tv.info.ITvInfoInteractor;
import com.epam.popcornapp.pojo.base.rating.Rating;
import com.epam.popcornapp.pojo.tv.TvDetails;
import com.epam.popcornapp.ui.all.base.BaseInternetMvpPresenter;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class TvInfoPresenter extends BaseInternetMvpPresenter<TvInfoView> implements
        ITvInfoInteractor.InteractorActions {

    @Inject
    ITvInfoInteractor tvInteractor;

    @Inject
    AccountInterface accountApi;

    private int id;
    private float rating;

    private Disposable disposable;

    TvInfoPresenter() {
        BaseApplication.getApplicationComponent().inject(this);
        tvInteractor.setListener(this);
    }

    void refresh(final int id, final Context context) {
        this.id = id;
        tvInteractor.getDetailedTvShow(isInternetConnection(context), id);

        if (Constants.CURRENT_SESSION != null) {
            tvInteractor.getCurrentRating(id);
        }
    }

    boolean checkNetworkState(final Context context) {
        return isInternetConnection(context);
    }

    void setRating(final float rating) {
        this.rating = rating;

        tvInteractor.rateTvShow(id, rating * 2);
    }

    void deleteRating() {
        this.rating = 0.0f;

        tvInteractor.deleteRating(id);
    }

    void retryClicked(final Context context) {
        if (isInternetConnection(context)) {
            refresh(id, context);
        } else {
            getViewState().onError(ErrorType.INTERNET_CONNECTION_ERROR);
        }
    }

    @Override
    public void onSuccess(final Observable<TvDetails> result, final boolean isDataFromServer) {
        if (disposable != null && !disposable.isDisposed()) {

            return;
        }
        result.map(new Function<TvDetails, TvShowData>() {
            @Override
            public TvShowData apply(@NonNull final TvDetails tvDetails) {
                return TvShowData.newBuilder()
                        .setDetail(tvDetails)
                        .setPosterPath(tvDetails.getPosterPath())
                        .setSeasons(tvDetails.getSeasons())
                        .setGalleryLine(tvDetails.getImages())
                        .setCastLine(tvDetails.getCredits())
                        .setRecommendationsLine(tvDetails.getRecommendations())
                        .setVideoPath(tvDetails.getVideos())
                        .setKeyWords(tvDetails.getKeywordsResult())
                        .build();
            }
        })
                .subscribe(new Observer<TvShowData>() {
                    @Override
                    public void onSubscribe(final Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(final TvShowData tvShowData) {
                        getViewState().setTvInfo(tvShowData, isDataFromServer);
                        disposable.dispose();
                    }

                    @Override
                    public void onError(final Throwable e) {
                        if (isDataFromServer) {
                            disposable.dispose();
                            tvInteractor.getDetailedTvShow(false, id);
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
                        getViewState().setCurrentRating(rating);
                    }

                    @Override
                    public void onError(final Throwable e) {
                        e.printStackTrace();
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

    public void onDestroy() {
        tvInteractor.onDestroy();
    }
}
