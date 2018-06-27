package com.epam.popcornapp.model.movie.interfaces;

import com.epam.popcornapp.pojo.movies.categories.MoviePopular;

import io.reactivex.Observable;

public interface IPopularMoviesInteractor {

    void getData(boolean isInternetConnection, int page);

    void onDestroy();

    void setActions(Actions actions);

    interface Actions {

        void OnSuccess(Observable<MoviePopular> observable, boolean isResponseFromServer);

        void onError();
    }
}
