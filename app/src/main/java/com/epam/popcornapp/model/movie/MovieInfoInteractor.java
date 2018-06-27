package com.epam.popcornapp.model.movie;

import android.support.annotation.NonNull;

import com.epam.popcornapp.application.BaseApplication;
import com.epam.popcornapp.application.Constants;
import com.epam.popcornapp.inject.MoviesInterface;
import com.epam.popcornapp.model.base.BaseInteractor;
import com.epam.popcornapp.model.base.RealmUtils;
import com.epam.popcornapp.model.movie.interfaces.IMovieInfoInteractor;
import com.epam.popcornapp.pojo.base.rating.Rating;
import com.epam.popcornapp.pojo.base.rating.RatingItem;
import com.epam.popcornapp.pojo.base.rating.RatingResponse;
import com.epam.popcornapp.pojo.movies.details.MovieDetailResult;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmObject;

public class MovieInfoInteractor extends BaseInteractor implements IMovieInfoInteractor {

    @Inject
    MoviesInterface moviesInterface;

    private IMovieInfoInteractor.Actions actions;

    public MovieInfoInteractor() {
        BaseApplication.getApplicationComponent().inject(this);

        openRealm();
    }

    public void setActions(@NonNull final Actions actions) {
        this.actions = actions;
    }

    @Override
    public void getData(final int id, final boolean isInternetConnection) {
        if (actions == null) {
            return;
        }

        if (isInternetConnection) {
            final Observable<MovieDetailResult> observable = moviesInterface
                    .getMoviesDetails(id, Constants.API_KEY)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .map(new Function<MovieDetailResult, MovieDetailResult>() {
                        @Override
                        public MovieDetailResult apply(final MovieDetailResult movies) {
                            insertMovies(movies);

                            return movies;
                        }
                    });

            actions.onSuccess(observable, true);
        } else {
            actions.onSuccess(getMovies(id).toObservable().cast(MovieDetailResult.class), false);
            actions.onError();
        }
    }

    @Override
    public void getAccountStates(final int id) {
        if (actions != null) {
            final Observable<Rating> observable = moviesInterface
                    .getAccountStates(id, Constants.API_KEY, Constants.CURRENT_SESSION)
                    .observeOn(AndroidSchedulers.mainThread());

            actions.onAccountStatesSuccess(observable);
        }
    }

    @Override
    public void setRating(final int id, final float rating) {
        if (actions != null) {
            final Observable<RatingResponse> observable = moviesInterface
                    .setRating(id, Constants.API_KEY, Constants.CURRENT_SESSION, new RatingItem(rating))
                    .observeOn(AndroidSchedulers.mainThread());

            actions.onSetRatingSuccess(observable);
        }

    }

    @Override
    public void deleteRating(final int id) {
        if (actions != null) {
            final Observable<RatingResponse> observable = moviesInterface
                    .deleteRating(id, Constants.API_KEY, Constants.CURRENT_SESSION)
                    .observeOn(AndroidSchedulers.mainThread());

            actions.onDeleteRatingSuccess(observable);
        }

    }

    @Override
    public void onDestroy() {
        closeRealm();
    }

    private void insertMovies(@NonNull final MovieDetailResult movies) {
        openRealm();

        getRealm().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull final Realm realm) {
                movies.getImagesResult().setId(movies.getId());
                movies.getMovieVideoResult().setId(movies.getId());
                movies.getCreditsResult().setId(movies.getId());
                movies.getReviewResult().setId(movies.getId());
                movies.getKeywordsResult().setId(movies.getId());
                movies.getMovieReleaseDatesResult().setId(movies.getId());

                movies.getCreditsResult().setCrew(null);
                movies.getImagesResult().setPosters(null);

                movies.getMovieRecommendationsResult()
                        .setResults(RealmUtils.getUpdatedMovies(realm, movies.getMovieRecommendationsResult().getResults()));

                movies.getCreditsResult()
                        .setCast(RealmUtils.getUpdatedCredits(realm, movies.getCreditsResult().getCast()));

                realm.copyToRealmOrUpdate(movies);
            }
        });
    }

    private Flowable<RealmObject> getMovies(final int id) {
        openRealm();

        return getRealm().where(MovieDetailResult.class).equalTo("id", id)
                .findFirstAsync()
                .asFlowable()
                .observeOn(AndroidSchedulers.mainThread());
    }
}
