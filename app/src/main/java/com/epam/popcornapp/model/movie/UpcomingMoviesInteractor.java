package com.epam.popcornapp.model.movie;

import android.support.annotation.NonNull;

import com.epam.popcornapp.application.BaseApplication;
import com.epam.popcornapp.application.Constants;
import com.epam.popcornapp.inject.MoviesInterface;
import com.epam.popcornapp.model.base.BaseInteractor;
import com.epam.popcornapp.model.base.RealmUtils;
import com.epam.popcornapp.model.movie.interfaces.IUpcomingMoviesInteractor;
import com.epam.popcornapp.pojo.movies.categories.MovieUpcoming;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmObject;

public class UpcomingMoviesInteractor extends BaseInteractor implements IUpcomingMoviesInteractor {

    @Inject
    MoviesInterface moviesInterface;

    private UpcomingMoviesInteractor.Actions actions;

    public UpcomingMoviesInteractor() {
        BaseApplication.getApplicationComponent().inject(this);

        openRealm();
    }

    public void setActions(final Actions actions) {
        this.actions = actions;
    }

    @Override
    public void getData(final boolean isInternetConnection, final int page) {
        if (isInternetConnection) {
            final Observable<MovieUpcoming> observable = moviesInterface.getUpcomingMovies(Constants.API_KEY, page)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .map(new Function<MovieUpcoming, MovieUpcoming>() {
                        @Override
                        public MovieUpcoming apply(final MovieUpcoming movies) throws Exception {
                            insertUpcomingMovies(movies);

                            return movies;
                        }
                    });

            actions.OnSuccess(observable, true);
        } else {
            actions.OnSuccess(getUpcomingMovies(page).toObservable().cast(MovieUpcoming.class), false);
            actions.onError();
        }
    }

    @Override
    public void onDestroy() {
        closeRealm();
    }

    private void insertUpcomingMovies(@NonNull final MovieUpcoming movies) {
        openRealm();

        getRealm().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull final Realm realm) {
                movies.setResults(RealmUtils.getUpdatedMovies(realm, movies.getResults()));

                realm.copyToRealmOrUpdate(movies);
            }
        });
    }

    private Flowable<RealmObject> getUpcomingMovies(final int page) {
        openRealm();

        return getRealm().where(MovieUpcoming.class).equalTo("page", page)
                .findFirstAsync()
                .asFlowable()
                .observeOn(AndroidSchedulers.mainThread());
    }
}
