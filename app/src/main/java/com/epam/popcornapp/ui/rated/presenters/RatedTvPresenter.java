package com.epam.popcornapp.ui.rated.presenters;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;
import com.epam.popcornapp.R;
import com.epam.popcornapp.application.BaseApplication;
import com.epam.popcornapp.application.Constants.DataType;
import com.epam.popcornapp.application.ErrorMessage.ErrorType;
import com.epam.popcornapp.model.rated.interfaces.IRatedTvInteractor;
import com.epam.popcornapp.pojo.rated.tv.RatedTvResult;
import com.epam.popcornapp.ui.all.base.BaseMoreMvpPresenter;
import com.epam.popcornapp.ui.rated.RatedConverter;
import com.epam.popcornapp.ui.tiles.item.BaseTileItem;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

@InjectViewState
public class RatedTvPresenter extends BaseMoreMvpPresenter implements IRatedTvInteractor.Actions {

    @Inject
    Context context;

    @Inject
    IRatedTvInteractor ratedInteractor;

    @DataType
    private final String presenterType = DataType.RATED_TV_SHOWS;

    private int currentPage = 1;

    private Disposable disposable;

    public RatedTvPresenter() {
        BaseApplication.getApplicationComponent().inject(this);

        ratedInteractor.setActions(this);
    }

    @Override
    public void refreshData() {
        this.currentPage = 1;

        ratedInteractor.getData(isInternetConnection(), currentPage);
    }

    @Override
    public void setCurrentPage(final int page) {
        this.currentPage = page;

        ratedInteractor.getData(isInternetConnection(), currentPage);
    }

    @Override
    public boolean isInternetConnection() {
        return isInternetConnection(context);
    }

    @Override
    public int getTitleId() {
        return R.string.rated_tv_shows;
    }

    @Override
    public int getCurrentPage() {
        return this.currentPage;
    }

    @Override
    public void onSuccess(final Observable<RatedTvResult> observable, final boolean isResponseFromServer) {
        if (disposable != null && !disposable.isDisposed()) {
            return;
        }

        observable
                .map(new Function<RatedTvResult, List<BaseTileItem>>() {
                    @Override
                    public List<BaseTileItem> apply(final RatedTvResult ratedResult) throws Exception {
                        return RatedConverter.convertTvToBaseTileItem(context, ratedResult.getResults());
                    }
                })
                .subscribe(new Observer<List<BaseTileItem>>() {
                    @Override
                    public void onSubscribe(final Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(final List<BaseTileItem> itemList) {
                        getViewState().setData(presenterType, itemList, R.string.tv_shows);
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
        getViewState().onError(ErrorType.INTERNET_CONNECTION_ERROR);
    }
}
