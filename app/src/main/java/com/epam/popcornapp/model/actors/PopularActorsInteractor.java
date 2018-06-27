package com.epam.popcornapp.model.actors;

import android.support.annotation.NonNull;

import com.epam.popcornapp.application.BaseApplication;
import com.epam.popcornapp.application.Constants;
import com.epam.popcornapp.inject.ActorsInterface;
import com.epam.popcornapp.model.base.BaseInteractor;
import com.epam.popcornapp.model.base.RealmUtils;
import com.epam.popcornapp.pojo.actors.ActorListModel;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmObject;

public class PopularActorsInteractor extends BaseInteractor implements IPopularActorsInteractor {

    @Inject
    ActorsInterface webActorsApi;

    private final IPopularActorsInteractor.InteractorActions listener;

    public PopularActorsInteractor(final InteractorActions listener) {
        BaseApplication.getApplicationComponent().inject(this);

        this.listener = listener;
        openRealm();
    }

    @Override
    public void getPopularActors(final boolean hasConnection, final int currentPage) {
        if (hasConnection) {
            final Observable<ActorListModel> observable =
                    webActorsApi.getPopularActors(Constants.API_KEY, currentPage)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .map(new Function<ActorListModel, ActorListModel>() {
                                @Override
                                public ActorListModel apply(final ActorListModel actorListModel) throws Exception {
                                    savePopularActor(actorListModel);

                                    return actorListModel;
                                }
                            });

            if (listener != null) {
                listener.onSuccess(observable, true);
            }

        } else {
            final Observable<ActorListModel> observable =
                    getActors(currentPage).toObservable().cast(ActorListModel.class);

            if (listener != null) {
                listener.onSuccess(observable, false);
                listener.onError();
            }
        }
    }

    @Override
    public void savePopularActor(final ActorListModel actorListModel) {
        insertActors(actorListModel);
    }

    @Override
    public void onDestroy() {
        closeRealm();
    }

    private Flowable<RealmObject> getActors(final int position) {
        openRealm();

        return getRealm().where(ActorListModel.class)
                .equalTo("page", position)
                .findFirstAsync()
                .asFlowable()
                .observeOn(AndroidSchedulers.mainThread());
    }

    private void insertActors(final ActorListModel actors) {
        openRealm();

        getRealm().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull final Realm realm) {
                actors.setResults(RealmUtils.getUpdatedActors(realm, actors.getResults()));

                realm.copyToRealmOrUpdate(actors);
            }
        });
    }
}

