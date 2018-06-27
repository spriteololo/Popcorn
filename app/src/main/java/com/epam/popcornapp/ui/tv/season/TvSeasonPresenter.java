package com.epam.popcornapp.ui.tv.season;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;
import com.epam.popcornapp.application.BaseApplication;
import com.epam.popcornapp.application.ErrorMessage.ErrorType;
import com.epam.popcornapp.inject.TvInterface;
import com.epam.popcornapp.model.tv.season.ITvSeasonInfoInteractor;
import com.epam.popcornapp.pojo.tv.season.SeasonInfo;
import com.epam.popcornapp.ui.all.base.BaseInternetMvpPresenter;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

@InjectViewState
public class TvSeasonPresenter extends BaseInternetMvpPresenter<TvSeasonView> implements
        ITvSeasonInfoInteractor.InteractorActions {

    @Inject
    Context context;

    @Inject
    TvInterface tvInterface;

    @Inject
    ITvSeasonInfoInteractor interactor;

    private Disposable disposable;

    private StartParams startParams;

    TvSeasonPresenter() {
        BaseApplication.getApplicationComponent().inject(this);

        interactor.setListener(this);
    }

    void load(final StartParams startParams) {
        if (startParams == null) {
            return;
        }

        this.startParams = startParams;

        interactor.loadSeasonInfo(startParams, isInternetConnection(context));
    }

    void onRetryClicked() {
        if (isInternetConnection(context)) {
            load(startParams);
        } else {
            onError();
        }
    }

    @Override
    public void onSuccess(final Observable<SeasonInfo> result, final boolean isSaved) {
        if (disposable != null && !disposable.isDisposed()) {
            return;
        }

        result.map(new Function<SeasonInfo, TvSeasonInfo>() {
            @Override
            public TvSeasonInfo apply(final SeasonInfo seasonInfo) throws Exception {
                return TvSeasonInfo.Builder()
                        .setPosterPath(seasonInfo.getPosterPath())
                        .setBaseInfo(seasonInfo)
                        .setActors(seasonInfo.getCredits())
                        .setEpisodes(seasonInfo.getEpisodes())
                        .build();
            }
        })
                .subscribe(new Observer<TvSeasonInfo>() {
                    @Override
                    public void onSubscribe(final Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(final TvSeasonInfo seasonInfo) {
                        disposable.dispose();
                        getViewState().onSuccess(seasonInfo);
                    }

                    @Override
                    public void onError(final Throwable e) {
                        disposable.dispose();

                        if (isSaved) {
                            interactor.loadSeasonInfo(startParams, false);
                        }

                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void onError() {
        getViewState().onError(ErrorType.INTERNET_CONNECTION_ERROR);
    }

    public void closeDb() {
        interactor.closeDb();
    }
}
