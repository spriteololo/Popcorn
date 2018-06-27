package com.epam.popcornapp.ui.tv.airing_today;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;
import com.epam.popcornapp.R;
import com.epam.popcornapp.application.BaseApplication;
import com.epam.popcornapp.application.Constants.DataType;
import com.epam.popcornapp.application.ErrorMessage.ErrorType;
import com.epam.popcornapp.model.tv.ITvAiringTodayInteractor;
import com.epam.popcornapp.pojo.tv.models.TvAiringTodayModel;
import com.epam.popcornapp.ui.all.base.BaseMoreMvpPresenter;
import com.epam.popcornapp.ui.tiles.item.BaseTileItem;
import com.epam.popcornapp.ui.tv.ConvertTvToTile;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

@InjectViewState
public class TvAiringTodayPresenter extends BaseMoreMvpPresenter
        implements ITvAiringTodayInteractor.InteractorActions {

    @Inject
    Context context;

    @Inject
    ITvAiringTodayInteractor tvInteractor;

    @DataType
    private final String presenterType = DataType.TV_AIRING_TODAY;

    private final int titleId = R.string.airing_today_tv_shows;

    private int currentPage = 1;

    private Disposable disposable;

    public TvAiringTodayPresenter() {
        BaseApplication.getApplicationComponent().inject(this);

        tvInteractor.setListener(this);
    }

    public void refreshData() {
        this.currentPage = 1;

        tvInteractor.getAiringTodayTvShows(isInternetConnection(context), currentPage);
    }

    public void setCurrentPage(final int page) {
        this.currentPage = page;

        tvInteractor.getAiringTodayTvShows(isInternetConnection(context), currentPage);
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
    public void onSuccess(final Observable<TvAiringTodayModel> result, final boolean isNetworkModule) {
        if (disposable != null && !disposable.isDisposed()) {
            return;
        }
        result.map(new Function<TvAiringTodayModel, List<BaseTileItem>>() {
            @Override
            public List<BaseTileItem> apply(final TvAiringTodayModel tvAiringTodayModel) throws Exception {
                return ConvertTvToTile.convertTvToTiles(context, tvAiringTodayModel.getResults());
            }
        })
                .subscribe(new Observer<List<BaseTileItem>>() {
                    @Override
                    public void onSubscribe(final Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(final List<BaseTileItem> result) {
                        disposable.dispose();
                        getViewState().setData(presenterType, result, titleId);
                    }

                    @Override
                    public void onError(final Throwable e) {
                        if (isNetworkModule) {
                            disposable.dispose();
                            tvInteractor.getAiringTodayTvShows(false, currentPage);
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
        tvInteractor.onDestroy();
    }
}
