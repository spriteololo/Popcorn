package com.epam.popcornapp.model.movie.interfaces;

import com.epam.popcornapp.pojo.movies.categories.MovieTopRated;

import io.reactivex.Observable;

public interface ITopRatedMoviesInteractor {

    void getData(boolean isInternetConnection, int page);

    void onDestroy();

    void setActions(Actions actions);

    interface Actions {

        void OnSuccess(Observable<MovieTopRated> observable, boolean isResponseFromServer);

        void onError();
    }
}
