package com.epam.popcornapp.model.rated.interfaces;

import com.epam.popcornapp.pojo.rated.movie.RatedMovieResult;

import io.reactivex.Observable;

public interface IRatedMovieInteractor {

    void setActions(Actions actions);

    void getData(boolean isInternetConnection, int page);

    interface Actions {

        void onSuccess(Observable<RatedMovieResult> observable, boolean isResponseFromServer);

        void onError();
    }
}