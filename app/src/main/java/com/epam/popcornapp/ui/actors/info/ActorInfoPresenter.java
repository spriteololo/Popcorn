package com.epam.popcornapp.ui.actors.info;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;
import com.epam.popcornapp.application.BaseApplication;
import com.epam.popcornapp.application.ErrorMessage.ErrorType;
import com.epam.popcornapp.model.actors.IActorInfoInteractor;
import com.epam.popcornapp.pojo.actors.Actor;
import com.epam.popcornapp.ui.all.base.BaseInternetMvpPresenter;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

@InjectViewState
public class ActorInfoPresenter extends BaseInternetMvpPresenter<ActorInfoView> implements
        IActorInfoInteractor.RequestListener {

    @Inject
    Context context;

    @Inject
    IActorInfoInteractor actorInfoInteractor;

    private Disposable disposable;
    private int userid;

    ActorInfoPresenter() {
        BaseApplication.getApplicationComponent().inject(this);
        actorInfoInteractor.setListener(this);
    }

    public void refresh(final int userId) {
        this.userid = userId;
        actorInfoInteractor.loadInfo(isInternetConnection(context), userId);
    }

    public void onDestroy() {
        actorInfoInteractor.onDestroy();
    }

    void retryClicked(final Context context) {
        if (isInternetConnection(context)) {
            refresh(userid);
        } else {
            onFailure();
        }
    }

    @Override
    public void onSuccess(@NonNull final Observable<Actor> result, final boolean isDataFromServer) {
        if (disposable != null && !disposable.isDisposed()) {
            return;
        }

        result.observeOn(AndroidSchedulers.mainThread())
                .map(new Function<Actor, ActorInfoDetails>() {
                    @Override
                    public ActorInfoDetails apply(final Actor actor) throws Exception {
                        return ActorInfoDetails.newBuilder()
                                .setImagePath(actor.getProfilePath())
                                .setDetail(actor)
                                .setImages(actor.getImages())
                                .setCredits(actor.getCredits())
                                .build();
                    }
                })
                .subscribe(new Observer<ActorInfoDetails>() {
                    @Override
                    public void onSubscribe(@NonNull final Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(@NonNull final ActorInfoDetails actorInfoDetails) {
                        disposable.dispose();
                        getViewState().setBaseInfo(actorInfoDetails, isDataFromServer);
                    }

                    @Override
                    public void onError(@NonNull final Throwable e) {
                        if (isDataFromServer) {
                            disposable.dispose();
                            actorInfoInteractor.loadInfo(false, userid);
                        }

                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void onFailure() {
        getViewState().onError(ErrorType.INTERNET_CONNECTION_ERROR);
    }
}
