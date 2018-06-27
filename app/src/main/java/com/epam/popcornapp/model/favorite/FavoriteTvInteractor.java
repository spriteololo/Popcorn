package com.epam.popcornapp.model.favorite;

import com.epam.popcornapp.application.BaseApplication;
import com.epam.popcornapp.application.Constants;
import com.epam.popcornapp.inject.AccountInterface;
import com.epam.popcornapp.model.base.BaseInteractor;
import com.epam.popcornapp.model.favorite.interfaces.IFavoriteTvInteractor;
import com.epam.popcornapp.pojo.favorite.tv.FavoriteTvResult;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class FavoriteTvInteractor extends BaseInteractor implements IFavoriteTvInteractor {

    @Inject
    AccountInterface accountInterface;

    private IFavoriteTvInteractor.Actions actions;

    public FavoriteTvInteractor() {
        BaseApplication.getApplicationComponent().inject(this);
    }

    @Override
    public void setActions(final Actions actions) {
        this.actions = actions;
    }

    @Override
    public void getData(final boolean isInternetConnection, final int page) {
        if (actions == null) {
            return;
        }

        if (isInternetConnection) {
            final Observable<FavoriteTvResult> observable = accountInterface
                    .getFavoriteTvShows(Constants.ACCOUNT_ID, Constants.API_KEY, Constants.CURRENT_SESSION, page)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());

            actions.onSuccess(observable, true);
        } else {
            actions.onError();
        }
    }
}
