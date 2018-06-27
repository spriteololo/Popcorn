package com.epam.popcornapp.model.tv;

import com.epam.popcornapp.pojo.tv.models.TvAiringTodayModel;

import io.reactivex.Observable;

public interface ITvAiringTodayInteractor {

    void getAiringTodayTvShows(boolean hasConnection, int currentPage);

    void insertAiringTodayShow(TvAiringTodayModel tvShowsModel);

    void onDestroy();

    void setListener(InteractorActions listener);

    interface InteractorActions {

        void onSuccess(Observable<TvAiringTodayModel> result, boolean isSaved);

        void onError();
    }
}
