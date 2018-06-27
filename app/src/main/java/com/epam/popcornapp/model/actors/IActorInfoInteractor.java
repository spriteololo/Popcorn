package com.epam.popcornapp.model.actors;

import com.epam.popcornapp.pojo.actors.Actor;

import io.reactivex.Observable;

public interface IActorInfoInteractor {

    void loadInfo(boolean hasConnection, int peopleId);
    void save(Actor actor);
    void onDestroy();
    void setListener(final RequestListener listener);

    interface RequestListener {
        void onSuccess(Observable<Actor> result, boolean isNetworkModule);
        void onFailure();
    }
}
