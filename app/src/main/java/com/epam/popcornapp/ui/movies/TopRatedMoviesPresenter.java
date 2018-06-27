package com.epam.popcornapp.ui.movies;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;
import com.epam.popcornapp.R;
import com.epam.popcornapp.application.BaseApplication;
import com.epam.popcornapp.application.Constants.DataType;
import com.epam.popcornapp.application.ErrorMessage.ErrorType;
import com.epam.popcornapp.inject.MoviesInterface;
import com.epam.popcornapp.model.movie.interfaces.ITopRatedMoviesInteractor;
import com.epam.popcornapp.pojo.movies.categories.MovieTopRated;
import com.epam.popcornapp.ui.all.base.BaseMoreMvpPresenter;
import com.epam.popcornapp.ui.tiles.item.BaseTileItem;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

@InjectViewState
public class TopRatedMoviesPresenter extends BaseMoreMvpPresenter
        implements ITopRatedMoviesInteractor.Actions {

    @Inject
    MoviesInterface moviesInterface;

    @Inject
    ITopRatedMoviesInteractor interactor;

    @Inject
    Context context;

    @DataType
    private final String presenterType = DataType.MOVIES_TOP_RATED;

    private final int titleId = R.string.top_rated_movies;

    private int currentPage = 1;
    private Disposable disposable;

    TopRatedMoviesPresenter() {
        BaseApplication.getApplicationComponent().inject(this);

        interactor.setActions(this);
    }

    public void refreshData() {
        this.currentPage = 1;

        interactor.getData(isInternetConnection(context), currentPage);
    }

    public void setCurrentPage(final int page) {
        this.currentPage = page;

        interactor.getData(isInternetConnection(context), currentPage);
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
    public void OnSuccess(final Observable<MovieTopRated> observable, final boolean isResponseFromServer) {
        if (disposable != null && !disposable.isDisposed()) {
            return;
        }

        observable.observeOn(AndroidSchedulers.mainThread())
                .map(new Function<MovieTopRated, List<BaseTileItem>>() {
                    @Override
                    public List<BaseTileItem> apply(final MovieTopRated movies) throws Exception {
                        return ConverterMoviesToTile.convertMoviesToTiles(context, movies.getResults());
                    }
                })
                .subscribe(new Observer<List<BaseTileItem>>() {
                    @Override
                    public void onSubscribe(final Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(final List<BaseTileItem> movies) {
                        disposable.dispose();
                        getViewState().setData(presenterType, movies, titleId);
                    }

                    @Override
                    public void onError(final Throwable e) {
                        if (isResponseFromServer) {
                            interactor.getData(false, currentPage);
                            disposable.dispose();
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

    @Override
    public int getTitleId() {
        return this.titleId;
    }

    @Override
    public int getCurrentPage() {
        return this.currentPage;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        interactor.onDestroy();
    }
}
