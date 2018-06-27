package com.epam.popcornapp.model.watchlist;

import com.epam.popcornapp.application.BaseApplication;
import com.epam.popcornapp.application.Constants;
import com.epam.popcornapp.inject.AccountInterface;
import com.epam.popcornapp.model.base.BaseInteractor;
import com.epam.popcornapp.model.watchlist.interfaces.IWatchlistTvInteractor;
import com.epam.popcornapp.pojo.watchlist.tv.WatchlistTvResult;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class WatchlistTvInteractor extends BaseInteractor implements IWatchlistTvInteractor {

    @Inject
    AccountInterface accountInterface;

    private IWatchlistTvInteractor.Actions actions;

    public WatchlistTvInteractor() {
        BaseApplication.getApplicationComponent().inject(this);
    }

    @Override
    public void setActions(final IWatchlistTvInteractor.Actions actions) {
        this.actions = actions;
    }

    @Override
    public void getData(final boolean isInternetConnection, final int page) {
        if (actions == null) {
            return;
        }

        if (isInternetConnection) {
            final Observable<WatchlistTvResult> observable = accountInterface
                    .getTvShowsWatchlist(Constants.ACCOUNT_ID, Constants.API_KEY, Constants.CURRENT_SESSION, page)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());

            actions.onSuccess(observable, true);
        } else {
            actions.onError();
        }
    }
}
