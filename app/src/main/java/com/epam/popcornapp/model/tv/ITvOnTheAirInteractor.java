package com.epam.popcornapp.model.tv;

import com.epam.popcornapp.pojo.tv.models.TvOnTheAirModel;

import io.reactivex.Observable;

public interface ITvOnTheAirInteractor {

    void getOnTheAirTvShows(boolean hasConnection, int currentPage);

    void insertOnTheAirTvShow(TvOnTheAirModel tvShowsModel);

    void onDestroy();

    void setListener(InteractorActions listener);

    interface InteractorActions {

        void onSuccess(Observable<TvOnTheAirModel> result, boolean isSaved);

        void onError();
    }
}
