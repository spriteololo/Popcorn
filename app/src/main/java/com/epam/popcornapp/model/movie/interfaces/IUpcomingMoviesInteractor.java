package com.epam.popcornapp.model.movie.interfaces;

import com.epam.popcornapp.pojo.movies.categories.MovieUpcoming;

import io.reactivex.Observable;

public interface IUpcomingMoviesInteractor {

    void getData(boolean isInternetConnection, int page);

    void onDestroy();

    void setActions(Actions actions);

    interface Actions {

        void OnSuccess(Observable<MovieUpcoming> observable, boolean isResponseFromServer);

        void onError();
    }
}
