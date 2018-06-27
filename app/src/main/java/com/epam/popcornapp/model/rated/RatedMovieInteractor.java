package com.epam.popcornapp.model.rated;

import com.epam.popcornapp.application.BaseApplication;
import com.epam.popcornapp.application.Constants;
import com.epam.popcornapp.inject.AccountInterface;
import com.epam.popcornapp.model.base.BaseInteractor;
import com.epam.popcornapp.model.rated.interfaces.IRatedMovieInteractor;
import com.epam.popcornapp.pojo.rated.movie.RatedMovieResult;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RatedMovieInteractor extends BaseInteractor implements IRatedMovieInteractor {

    @Inject
    AccountInterface accountInterface;

    private IRatedMovieInteractor.Actions actions;

    public RatedMovieInteractor() {
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
            final Observable<RatedMovieResult> observable = accountInterface
                    .getRatedMovies(Constants.ACCOUNT_ID, Constants.API_KEY, Constants.CURRENT_SESSION, page)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());

            actions.onSuccess(observable, true);
        } else {
            actions.onError();
        }
    }
}
