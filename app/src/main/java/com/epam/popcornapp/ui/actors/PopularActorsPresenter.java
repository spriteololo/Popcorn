package com.epam.popcornapp.ui.actors;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;
import com.epam.popcornapp.R;
import com.epam.popcornapp.application.BaseApplication;
import com.epam.popcornapp.application.Constants.DataType;
import com.epam.popcornapp.application.ErrorMessage.ErrorType;
import com.epam.popcornapp.inject.ActorsInterface;
import com.epam.popcornapp.model.actors.IPopularActorsInteractor;
import com.epam.popcornapp.model.actors.PopularActorsInteractor;
import com.epam.popcornapp.pojo.actors.ActorListModel;
import com.epam.popcornapp.ui.all.base.BaseMoreMvpPresenter;
import com.epam.popcornapp.ui.tiles.item.BaseTileItem;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

@InjectViewState
public class PopularActorsPresenter extends BaseMoreMvpPresenter
        implements IPopularActorsInteractor.InteractorActions {

    @Inject
    ActorsInterface actorsInterface;

    @Inject
    Context context;

    private IPopularActorsInteractor popularActorsInteractor;

    @DataType
    private final String presenterType = DataType.PEOPLE;

    private final int titleId = R.string.people;

    private int currentPage = 1;

    private Disposable disposable;

    public PopularActorsPresenter() {
        BaseApplication.getApplicationComponent().inject(this);

        popularActorsInteractor = new PopularActorsInteractor(this);
    }

    public void refreshData() {
        this.currentPage = 1;

        popularActorsInteractor.getPopularActors(isInternetConnection(context), currentPage);
    }

    public void setCurrentPage(final int page) {
        this.currentPage = page;

        popularActorsInteractor.getPopularActors(isInternetConnection(context), currentPage);
    }

    public void retryClicked() {
        if (isInternetConnection(context)) {
            refreshData();
        } else {
            onError();
        }
    }

    @Override
    public boolean isInternetConnection() {
        return isInternetConnection(context);
    }

    @Override
    public int getTitleId() {
        return this.titleId;
    }

    @Override
    public int getCurrentPage() {
        return this.currentPage;
    }

    @Override
    public void onSuccess(@NonNull final Observable<ActorListModel> result, final boolean isNetworkModule) {
        if (disposable != null && !disposable.isDisposed()) {
            return;
        }

        result.observeOn(AndroidSchedulers.mainThread())
                .map(new Function<ActorListModel, List<BaseTileItem>>() {
                    @Override
                    public List<BaseTileItem> apply(final ActorListModel actorListModel) throws Exception {
                        return ConverterActorsToTiles.convertActorsToTile(context, actorListModel.getResults());
                    }
                })
                .subscribe(new Observer<List<BaseTileItem>>() {
                    @Override
                    public void onSubscribe(@NonNull final Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(@NonNull final List<BaseTileItem> result) {
                        disposable.dispose();
                        getViewState().setData(presenterType, result, titleId);
                    }

                    @Override
                    public void onError(@NonNull final Throwable e) {
                        if (isNetworkModule) {
                            disposable.dispose();
                            popularActorsInteractor.getPopularActors(false, currentPage);
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

    public void onDestroy() {
        popularActorsInteractor.onDestroy();
    }
}
