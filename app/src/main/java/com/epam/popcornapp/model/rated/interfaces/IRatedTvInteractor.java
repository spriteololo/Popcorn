package com.epam.popcornapp.model.rated.interfaces;

import com.epam.popcornapp.pojo.rated.tv.RatedTvResult;

import io.reactivex.Observable;

public interface IRatedTvInteractor {

    void setActions(Actions actions);

    void getData(boolean isInternetConnection, int page);

    interface Actions {

        void onSuccess(Observable<RatedTvResult> observable, boolean isResponseFromServer);

        void onError();
    }
}
