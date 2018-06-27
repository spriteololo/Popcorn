package com.epam.popcornapp.model.movie;

import android.support.annotation.NonNull;

import com.epam.popcornapp.application.BaseApplication;
import com.epam.popcornapp.application.Constants;
import com.epam.popcornapp.inject.MoviesInterface;
import com.epam.popcornapp.model.base.BaseInteractor;
import com.epam.popcornapp.model.base.RealmUtils;
import com.epam.popcornapp.model.movie.interfaces.ITopRatedMoviesInteractor;
import com.epam.popcornapp.pojo.movies.categories.MovieTopRated;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmObject;

public class TopRatedMoviesInteractor extends BaseInteractor implements ITopRatedMoviesInteractor {

    @Inject
    MoviesInterface moviesInterface;

    private TopRatedMoviesInteractor.Actions actions;

    public TopRatedMoviesInteractor() {
        BaseApplication.getApplicationComponent().inject(this);

        openRealm();
    }

    public void setActions(final Actions actions) {
        this.actions = actions;
    }

    @Override
    public void getData(final boolean isInternetConnection, final int page) {
        if (isInternetConnection) {
            final Observable<MovieTopRated> observable = moviesInterface.getTopRatedMovies(Constants.API_KEY, page)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .map(new Function<MovieTopRated, MovieTopRated>() {
                        @Override
                        public MovieTopRated apply(final MovieTopRated movies) throws Exception {
                            insertTopRatedMovies(movies);

                            return movies;
                        }
                    });

            actions.OnSuccess(observable, true);
        } else {
            actions.OnSuccess(getTopRatedMovies(page).toObservable().cast(MovieTopRated.class), false);
            actions.onError();
        }
    }

    @Override
    public void onDestroy() {
        closeRealm();
    }

    private void insertTopRatedMovies(@NonNull final MovieTopRated movies) {
        openRealm();

        getRealm().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull final Realm realm) {
                movies.setResults(RealmUtils.getUpdatedMovies(realm, movies.getResults()));

                realm.copyToRealmOrUpdate(movies);
            }
        });
    }

    private Flowable<RealmObject> getTopRatedMovies(final int page) {
        openRealm();

        return getRealm().where(MovieTopRated.class).equalTo("page", page)
                .findFirstAsync()
                .asFlowable()
                .observeOn(AndroidSchedulers.mainThread());
    }
}
