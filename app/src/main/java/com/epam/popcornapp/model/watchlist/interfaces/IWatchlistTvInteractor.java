package com.epam.popcornapp.model.watchlist.interfaces;

import com.epam.popcornapp.pojo.watchlist.tv.WatchlistTvResult;

import io.reactivex.Observable;

public interface IWatchlistTvInteractor {

    void setActions(Actions actions);

    void getData(boolean isInternetConnection, int page);

    interface Actions {

        void onSuccess(Observable<WatchlistTvResult> observable, boolean isResponseFromServer);

        void onError();
    }
}
