package com.epam.popcornapp.model.watchlist;

import com.epam.popcornapp.application.BaseApplication;
import com.epam.popcornapp.application.Constants;
import com.epam.popcornapp.inject.AccountInterface;
import com.epam.popcornapp.model.base.BaseInteractor;
import com.epam.popcornapp.pojo.watchlist.movie.WatchlistMovieResult;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class WatchlistMovieInteractor extends BaseInteractor implements com.epam.popcornapp.model.watchlist.interfaces.IWatchlistMovieInteractor {

    @Inject
    AccountInterface accountInterface;

    private WatchlistMovieInteractor.Actions actions;

    public WatchlistMovieInteractor() {
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
            final Observable<WatchlistMovieResult> observable = accountInterface
                    .getMovieWatchlist(Constants.ACCOUNT_ID, Constants.API_KEY, Constants.CURRENT_SESSION, page)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());

            actions.onSuccess(observable, true);
        } else {
            actions.onError();
        }
    }
}
