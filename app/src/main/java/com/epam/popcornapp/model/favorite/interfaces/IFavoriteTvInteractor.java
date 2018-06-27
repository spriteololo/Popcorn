package com.epam.popcornapp.model.favorite.interfaces;

import com.epam.popcornapp.pojo.favorite.tv.FavoriteTvResult;

import io.reactivex.Observable;

public interface IFavoriteTvInteractor {

    void setActions(Actions actions);

    void getData(boolean isInternetConnection, int page);

    interface Actions {

        void onSuccess(Observable<FavoriteTvResult> observable, boolean isResponseFromServer);

        void onError();
    }
}
