package com.epam.popcornapp.ui.review;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.epam.popcornapp.application.BaseApplication;
import com.epam.popcornapp.application.Constants;
import com.epam.popcornapp.inject.MoviesInterface;
import com.epam.popcornapp.pojo.movies.reviews.MovieReviewResult;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ru.terrakok.cicerone.Router;

@InjectViewState
public class ReviewPresenter extends MvpPresenter<ReviewPresenterView> {

    @Inject
    Router router;

    @Inject
    MoviesInterface moviesInterface;

    private int currentPage = 1;
    private int maxPage = 2;
    private int id = 1;

    public ReviewPresenter() {
        BaseApplication.getApplicationComponent().inject(this);
    }


    public void refreshData(final int id, final int currentPage) {
        this.id = id;
        if (currentPage != -1) {
            this.currentPage = currentPage;
        }

        moviesInterface.getReviews(id, Constants.API_KEY, this.currentPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MovieReviewResult>() {
                    @Override
                    public void onSubscribe(final Disposable d) {

                    }

                    @Override
                    public void onNext(final MovieReviewResult movieReviewResult) {
                        getViewState().setData(movieReviewResult.getResultRevs());
                        maxPage = movieReviewResult.getTotalPages();
                    }

                    @Override
                    public void onError(final Throwable e) {
                        getViewState().error();
                    }

                    @Override
                    public void onComplete() {
                        getViewState().completed();
                    }
                });
    }

    public void setCurrentPage(final int id, final int currentPage) {
        if (currentPage > maxPage) {
            return;
        }
        this.currentPage = currentPage;
        refreshData(id, currentPage);
    }
}
