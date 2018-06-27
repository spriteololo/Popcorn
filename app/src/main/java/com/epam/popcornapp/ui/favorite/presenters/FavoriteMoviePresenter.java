package com.epam.popcornapp.ui.favorite.presenters;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;
import com.epam.popcornapp.R;
import com.epam.popcornapp.application.BaseApplication;
import com.epam.popcornapp.application.Constants.DataType;
import com.epam.popcornapp.application.ErrorMessage;
import com.epam.popcornapp.model.favorite.interfaces.IFavoriteMovieInteractor;
import com.epam.popcornapp.pojo.favorite.movie.FavoriteMovieResult;
import com.epam.popcornapp.ui.all.base.BaseMoreMvpPresenter;
import com.epam.popcornapp.ui.favorite.FavoriteConverter;
import com.epam.popcornapp.ui.tiles.item.BaseTileItem;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

@InjectViewState
public class FavoriteMoviePresenter extends BaseMoreMvpPresenter implements
        IFavoriteMovieInteractor.Actions {

    @Inject
    Context context;

    @Inject
    IFavoriteMovieInteractor favoriteInteractor;

    @DataType
    private final String presenterType = DataType.MOVIES;

    private int currentPage = 1;

    private Disposable disposable;

    public FavoriteMoviePresenter() {
        BaseApplication.getApplicationComponent().inject(this);

        favoriteInteractor.setActions(this);
    }

    @Override
    public void refreshData() {
        this.currentPage = 1;

        favoriteInteractor.getData(isInternetConnection(), currentPage);
    }

    @Override
    public void setCurrentPage(final int page) {
        this.currentPage = page;

        favoriteInteractor.getData(isInternetConnection(), currentPage);
    }

    @Override
    public boolean isInternetConnection() {
        return isInternetConnection(context);
    }

    @Override
    public int getTitleId() {
        return R.string.favorite_movies;
    }

    @Override
    public int getCurrentPage() {
        return this.currentPage;
    }

    @Override
    public void onSuccess(final Observable<FavoriteMovieResult> observable, final boolean isResponseFromServer) {
        if (disposable != null && !disposable.isDisposed()) {
            return;
        }

        observable
                .map(new Function<FavoriteMovieResult, List<BaseTileItem>>() {
                    @Override
                    public List<BaseTileItem> apply(final FavoriteMovieResult favoriteResult) throws Exception {
                        return FavoriteConverter.convertMovieToBaseTileItem(context, favoriteResult.getResults());
                    }
                })
                .subscribe(new Observer<List<BaseTileItem>>() {
                    @Override
                    public void onSubscribe(final Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(final List<BaseTileItem> itemList) {
                        getViewState().setData(presenterType, itemList, R.string.movies);
                        disposable.dispose();
                    }

                    @Override
                    public void onError(final Throwable e) {
                        e.printStackTrace();
                        disposable.dispose();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void onError() {
        getViewState().onError(ErrorMessage.ErrorType.INTERNET_CONNECTION_ERROR);
    }
}
