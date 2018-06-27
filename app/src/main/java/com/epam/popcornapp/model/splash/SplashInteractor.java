package com.epam.popcornapp.model.splash;

import android.support.annotation.NonNull;

import com.epam.popcornapp.application.BaseApplication;
import com.epam.popcornapp.application.Constants;
import com.epam.popcornapp.inject.AccountInterface;
import com.epam.popcornapp.inject.ConfigInterface;
import com.epam.popcornapp.inject.MoviesInterface;
import com.epam.popcornapp.inject.TvInterface;
import com.epam.popcornapp.model.base.BaseInteractor;
import com.epam.popcornapp.pojo.account.Account;
import com.epam.popcornapp.pojo.base.Genre;
import com.epam.popcornapp.pojo.genres.GenresResult;
import com.epam.popcornapp.pojo.splash_settings.Config;
import com.epam.popcornapp.pojo.splash_settings.ImagesConfig;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;

public class SplashInteractor extends BaseInteractor implements ISplashInteractor {

    @Inject
    ConfigInterface configInterface;

    @Inject
    MoviesInterface moviesInterface;

    @Inject
    AccountInterface accountInterface;

    @Inject
    TvInterface tvInterface;

    private ISplashInteractor.Actions actions;

    public SplashInteractor() {
        BaseApplication.getApplicationComponent().inject(this);

        openRealm();
    }

    public void setListener(@NonNull final ISplashInteractor.Actions actions) {
        this.actions = actions;
    }

    @Override
    public void getData(final boolean isInternetConnection) {
        if (!isInternetConnection) {
            actions.onError(isGenresEmpty());
        } else {
            final Observable<GenresResult> observable = moviesInterface.getMovieGenres(Constants.API_KEY)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());

            final Observable<GenresResult> observable2 = tvInterface.getTvGenres(Constants.API_KEY)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());

            final Observable<Config> observable3 = configInterface.getConfig(Constants.API_KEY)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());

            final Observable<Account> observable4 = accountInterface
                    .getAccountDetails(Constants.API_KEY, Constants.CURRENT_SESSION)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());

            actions.onSuccess(observable, observable2, observable3, observable4);
        }
    }

    @Override
    public void saveData(@NonNull final GenresResult value, @NonNull final GenresResult tvGenres, @NonNull final Config config) {
        openRealm();

        for (final Genre genre : value.getGenres()) {
            insertGenre(genre);
        }

        for (final Genre genre : tvGenres.getGenres()) {
            insertGenre(genre);
        }

        insertImageConfig(config.getImages());
    }

    private void insertImageConfig(@NonNull final ImagesConfig images) {
        getRealm().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull final Realm realm) {
                realm.copyToRealmOrUpdate(images);
            }
        });
    }

    private void insertGenre(@NonNull final Genre value) {
        getRealm().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull final Realm realm) {
                realm.copyToRealmOrUpdate(value);
            }
        });
    }

    private boolean isGenresEmpty() {
        openRealm();

        return getRealm().where(Genre.class).findAll().isEmpty();
    }

    @Override
    public void onDestroy() {
        closeRealm();
    }
}
