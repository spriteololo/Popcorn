package com.epam.popcornapp.model.tv;

import com.epam.popcornapp.pojo.tv.models.TvPopularModel;

import io.reactivex.Observable;

public interface ITvPopularInteractor {

    void getPopularTvShows(boolean hasConnection, int currentPage);

    void insertPopularTvShow(TvPopularModel tvShowsModel);

    void onDestroy();

    void setListener(InteractorActions listener);

    interface InteractorActions {

        void onSuccess(Observable<TvPopularModel> result, boolean isSaved);

        void onError();
    }
}
