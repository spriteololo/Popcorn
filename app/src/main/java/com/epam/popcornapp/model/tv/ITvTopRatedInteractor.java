package com.epam.popcornapp.model.tv;

import com.epam.popcornapp.pojo.tv.models.TvTopRatedModel;

import io.reactivex.Observable;

public interface ITvTopRatedInteractor {

    void getTopRatedTvShows(boolean hasConnection, int currentPage);

    void insertTopRatedTvShows(TvTopRatedModel tvTopRatedModel);

    void onDestroy();

    void setListener(InteractorActions listener);

    interface InteractorActions {

        void onSuccess(Observable<TvTopRatedModel> result, boolean isSaved);

        void onError();

    }
}
