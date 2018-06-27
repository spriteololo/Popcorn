package com.epam.popcornapp.model.watchlist.interfaces;

import com.epam.popcornapp.pojo.watchlist.movie.WatchlistMovieResult;

import io.reactivex.Observable;

public interface IWatchlistMovieInteractor {

    void setActions(Actions actions);

    void getData(boolean isInternetConnection, int page);

    interface Actions {

        void onSuccess(Observable<WatchlistMovieResult> observable, boolean isResponseFromServer);

        void onError();
    }
}