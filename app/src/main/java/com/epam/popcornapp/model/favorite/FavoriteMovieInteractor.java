package com.epam.popcornapp.model.favorite;

import com.epam.popcornapp.application.BaseApplication;
import com.epam.popcornapp.application.Constants;
import com.epam.popcornapp.inject.AccountInterface;
import com.epam.popcornapp.model.base.BaseInteractor;
import com.epam.popcornapp.model.favorite.interfaces.IFavoriteMovieInteractor;
import com.epam.popcornapp.pojo.favorite.movie.FavoriteMovieResult;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class FavoriteMovieInteractor extends BaseInteractor implements IFavoriteMovieInteractor {

    @Inject
    AccountInterface accountInterface;

    private IFavoriteMovieInteractor.Actions actions;

    public FavoriteMovieInteractor() {
        BaseApplication.getApplicationComponent().inject(this);
    }

    @Override
    public void setActions(final Actions actions) {
        this.actions = actions;
    }

    @Override
    public void getData(final boolean isInternetConnection, final int page) {
        if (actions == null) {
            return;
        }

        if (isInternetConnection) {
            final Observable<FavoriteMovieResult> observable = accountInterface
                    .getFavoriteMovie(Constants.ACCOUNT_ID, Constants.API_KEY, Constants.CURRENT_SESSION, page)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());

            actions.onSuccess(observable, true);
        } else {
            actions.onError();
        }
    }
}
