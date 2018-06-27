package com.epam.popcornapp.model.movie.interfaces;

import com.epam.popcornapp.pojo.base.rating.Rating;
import com.epam.popcornapp.pojo.base.rating.RatingResponse;
import com.epam.popcornapp.pojo.movies.details.MovieDetailResult;

import io.reactivex.Observable;

public interface IMovieInfoInteractor {

    void getData(int id, boolean isInternetConnection);

    void onDestroy();

    void setActions(Actions actions);

    void getAccountStates(int id);

    void setRating(int id, float rating);

    void deleteRating(int id);

    interface Actions {

        void onSuccess(Observable<MovieDetailResult> observable, boolean isResponseFromServer);

        void onAccountStatesSuccess(Observable<Rating> observable);

        void onSetRatingSuccess(Observable<RatingResponse> observable);

        void onDeleteRatingSuccess(Observable<RatingResponse> observable);

        void onError();
    }
}

