package com.epam.popcornapp.model.favorite.interfaces;

import com.epam.popcornapp.pojo.favorite.movie.FavoriteMovieResult;

import io.reactivex.Observable;

public interface IFavoriteMovieInteractor {

    void setActions(Actions actions);

    void getData(boolean isInternetConnection, int page);

    interface Actions {

        void onSuccess(Observable<FavoriteMovieResult> observable, boolean isResponseFromServer);

        void onError();
    }
}
