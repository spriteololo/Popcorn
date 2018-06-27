package com.epam.popcornapp.model.actors;

import com.epam.popcornapp.pojo.actors.ActorListModel;

import io.reactivex.Observable;

public interface IPopularActorsInteractor {

    void getPopularActors(boolean hasConnection, int currentPage);

    void savePopularActor(ActorListModel actorListModel);

    void onDestroy();

    interface InteractorActions {

        void onSuccess(Observable<ActorListModel> result, boolean isNetworkModule);
        void onError();
    }
}
