package com.epam.popcornapp.model.tv.info;

import com.epam.popcornapp.pojo.base.rating.Rating;
import com.epam.popcornapp.pojo.tv.TvDetails;

import io.reactivex.Observable;

public interface ITvInfoInteractor {

    void getDetailedTvShow(boolean hasConnection, int id);

    void insertDetailesTvShow(TvDetails tvDetails);

    void onDestroy();

    void setListener(InteractorActions listener);

    void getCurrentRating(final int id);

    void rateTvShow(final int id, final float rating);

    void deleteRating(final int id);

    interface InteractorActions {

        void onSuccess(Observable<TvDetails> result, boolean isSaved);

        void onError();

        void onRatingLoaded(Observable<Rating> observable);

        void onRatingResult(boolean isSuccess);
    }
}
