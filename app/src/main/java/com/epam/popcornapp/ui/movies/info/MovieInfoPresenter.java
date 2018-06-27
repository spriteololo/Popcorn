package com.epam.popcornapp.ui.movies.info;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;
import com.epam.popcornapp.application.BaseApplication;
import com.epam.popcornapp.application.Constants;
import com.epam.popcornapp.application.ErrorMessage.ErrorType;
import com.epam.popcornapp.model.movie.interfaces.IMovieInfoInteractor;
import com.epam.popcornapp.pojo.base.rating.Rating;
import com.epam.popcornapp.pojo.base.rating.RatingResponse;
import com.epam.popcornapp.pojo.movies.details.MovieDetailResult;
import com.epam.popcornapp.ui.all.base.BaseInternetMvpPresenter;
import com.epam.popcornapp.ui.review.ReviewItem;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import ru.terrakok.cicerone.Router;

@InjectViewState
public class MovieInfoPresenter extends BaseInternetMvpPresenter<MovieInfoView> implements
        IMovieInfoInteractor.Actions {

    @Inject
    IMovieInfoInteractor movieInfoInteractor;

    @Inject
    Router router;

    private int id;
    private float rating;

    private Disposable disposable;

    MovieInfoPresenter() {
        BaseApplication.getApplicationComponent().inject(this);
        movieInfoInteractor.setActions(this);
    }

    void getData(final int id, final Context context) {
        this.id = id;

        movieInfoInteractor.getData(id, isInternetConnection(context));

        if (Constants.CURRENT_SESSION != null) {
            movieInfoInteractor.getAccountStates(id);
        }
    }

    boolean checkNetworkState(final Context context) {
        return isInternetConnection(context);
    }

    void setRating(final float rating) {
        this.rating = rating;

        movieInfoInteractor.setRating(id, rating * 2);
    }

    void deleteRating() {
        this.rating = 0.0f;

        movieInfoInteractor.deleteRating(id);
    }

    void onMorePressed(final List<ReviewItem> reviewItems) {
        reviewItems.add(ReviewItem.builder().setId(String.valueOf(id)).build());
        router.navigateTo(Constants.Screens.REVIEWS_SCREEN, reviewItems);
    }

    void retryClicked(final Context context) {
        if (isInternetConnection(context)) {
            getData(id, context);
        } else {
            getViewState().onError(ErrorType.INTERNET_CONNECTION_ERROR);
        }
    }

    @Override
    public void onSuccess(@NonNull final Observable<MovieDetailResult> observable,
                          final boolean isDataFromServer) {
        if (disposable != null && !disposable.isDisposed()) {
            return;
        }

        observable
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<MovieDetailResult, MovieInfoData>() {

                    @Override
                    public MovieInfoData apply(final MovieDetailResult detailMovies) {
                        return MovieInfoData.newBuilder()
                                .setPosterPath(detailMovies.getPosterPath())
                                .setDetail(detailMovies)
                                .setKeyWords(detailMovies.getKeywordsResult())
                                .setCredits(detailMovies.getCreditsResult())
                                .setImages(detailMovies.getImagesResult())
                                .setRecommendations(detailMovies.getMovieRecommendationsResult())
                                .setVideoPath(detailMovies.getMovieVideoResult())
                                .setReleaseDates(detailMovies.getMovieReleaseDatesResult())
                                .setMovieReview(detailMovies.getReviewResult())
                                .build();
                    }
                })
                .subscribe(new Observer<MovieInfoData>() {
                    @Override
                    public void onSubscribe(@NonNull final Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(@NonNull final MovieInfoData detailMovies) {
                        disposable.dispose();
                        getViewState().setDetail(detailMovies, isDataFromServer);
                    }

                    @Override
                    public void onError(@NonNull final Throwable e) {
                        if (isDataFromServer) {
                            movieInfoInteractor.getData(id, false);
                            disposable.dispose();
                        }

                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void onAccountStatesSuccess(final Observable<Rating> observable) {
        observable
                .subscribeOn(Schedulers.io())
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
    public void onSetRatingSuccess(final Observable<RatingResponse> observable) {
        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RatingResponse>() {
                    @Override
                    public void onSubscribe(final Disposable d) {

                    }

                    @Override
                    public void onNext(final RatingResponse ratingResponse) {
                        getViewState().ratingUpdate(true, rating);
                    }

                    @Override
                    public void onError(final Throwable e) {
                        e.printStackTrace();
                        getViewState().ratingUpdate(false, rating);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void onDeleteRatingSuccess(final Observable<RatingResponse> observable) {
        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RatingResponse>() {
                    @Override
                    public void onSubscribe(final Disposable d) {

                    }

                    @Override
                    public void onNext(final RatingResponse ratingResponse) {
                        getViewState().ratingUpdate(true, rating);
                    }

                    @Override
                    public void onError(final Throwable e) {
                        e.printStackTrace();
                        getViewState().ratingUpdate(false, rating);
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
    public void onDestroy() {
        super.onDestroy();

        movieInfoInteractor.onDestroy();
    }
}


