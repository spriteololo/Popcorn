package com.epam.popcornapp.model.movie;

import android.support.annotation.NonNull;

import com.epam.popcornapp.application.BaseApplication;
import com.epam.popcornapp.application.Constants;
import com.epam.popcornapp.inject.MoviesInterface;
import com.epam.popcornapp.model.base.BaseInteractor;
import com.epam.popcornapp.model.base.RealmUtils;
import com.epam.popcornapp.model.movie.interfaces.IPopularMoviesInteractor;
import com.epam.popcornapp.pojo.movies.categories.MoviePopular;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmObject;

public class PopularMoviesInteractor extends BaseInteractor implements IPopularMoviesInteractor {

    @Inject
    MoviesInterface moviesInterface;

    private PopularMoviesInteractor.Actions actions;

    public PopularMoviesInteractor() {
        BaseApplication.getApplicationComponent().inject(this);

        openRealm();
    }

    public void setActions(final Actions actions) {
        this.actions = actions;
    }

    @Override
    public void getData(final boolean isInternetConnection, final int page) {
        openRealm();

        if (isInternetConnection) {
            final Observable<MoviePopular> observable = moviesInterface.getPopularMovies(Constants.API_KEY, page)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .map(new Function<MoviePopular, MoviePopular>() {
                        @Override
                        public MoviePopular apply(final MoviePopular movies) throws Exception {
                            insertPopularMovies(movies);

                            return movies;
                        }
                    });

            actions.OnSuccess(observable, true);
        } else {
            actions.OnSuccess(getPopularMovies(page).toObservable().cast(MoviePopular.class), false);
            actions.onError();
        }
    }

    @Override
    public void onDestroy() {
        closeRealm();
    }

    private void insertPopularMovies(@NonNull final MoviePopular movies) {
        openRealm();

        getRealm().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull final Realm realm) {
                movies.setResults(RealmUtils.getUpdatedMovies(realm, movies.getResults()));

                realm.copyToRealmOrUpdate(movies);
            }
        });
    }

    private Flowable<RealmObject> getPopularMovies(final int page) {
        openRealm();

        return getRealm().where(MoviePopular.class).equalTo("page", page)
                .findFirstAsync()
                .asFlowable()
                .observeOn(AndroidSchedulers.mainThread());
    }
}
