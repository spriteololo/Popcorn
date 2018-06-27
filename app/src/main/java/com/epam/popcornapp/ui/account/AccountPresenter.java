package com.epam.popcornapp.ui.account;

import android.content.Context;
import android.view.View;

import com.arellomobile.mvp.InjectViewState;
import com.epam.popcornapp.R;
import com.epam.popcornapp.application.BaseApplication;
import com.epam.popcornapp.application.Constants;
import com.epam.popcornapp.inject.AccountInterface;
import com.epam.popcornapp.pojo.base.FavoriteRequestBody;
import com.epam.popcornapp.pojo.base.WatchlistRequestBody;
import com.epam.popcornapp.pojo.base.rating.Rating;
import com.epam.popcornapp.pojo.base.rating.RatingResponse;
import com.epam.popcornapp.ui.all.base.BaseInternetMvpPresenter;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class AccountPresenter extends BaseInternetMvpPresenter<AccountView> {

    @Inject
    Context context;

    @Inject
    AccountInterface accountApi;

    public AccountPresenter() {
        BaseApplication.getApplicationComponent().inject(this);
    }

    public void onActionsButtonClicked(final int id, final Rating currentRating,
                                       final String mediaType, final View view) {
        if (currentRating == null || view == null) {
            return;
        }

        switch (view.getId()) {
            case R.id.action_favorite_button:
                markAsFavorite(id, currentRating, mediaType);

                break;
            case R.id.action_watchlist_button:
                addToWatchlist(id, currentRating, mediaType);

                break;
            case R.id.action_add_to_list_button:
                getViewState().startListActivity();
        }
    }

    private void addToWatchlist(final int id, final Rating currentRating, final String mediaType) {
        final WatchlistRequestBody item = new WatchlistRequestBody(mediaType, id,
                !currentRating.isWatchlist());

        accountApi.addToWatchlist(Constants.ACCOUNT_ID, Constants.API_KEY,
                Constants.CURRENT_SESSION, item)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RatingResponse>() {
                    @Override
                    public void onSubscribe(final Disposable d) {

                    }

                    @Override
                    public void onNext(final RatingResponse ratingResponse) {
                        final boolean isAdded = item.isWatchlist();

                        currentRating.setWatchlist(isAdded);

                        getViewState().onWatchlistResult(isAdded);
                        getViewState().showMessage(isAdded ?
                                context.getString(R.string.watchlist_added) :
                                context.getString(R.string.watchlist_removed));
                    }

                    @Override
                    public void onError(final Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void markAsFavorite(final int id, final Rating currentRating, final String mediaType) {
        final FavoriteRequestBody item = new FavoriteRequestBody(mediaType, id,
                !currentRating.isFavorite());

        accountApi.markAsFavorite(Constants.ACCOUNT_ID, Constants.API_KEY,
                Constants.CURRENT_SESSION, item)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RatingResponse>() {
                    @Override
                    public void onSubscribe(final Disposable d) {

                    }

                    @Override
                    public void onNext(final RatingResponse ratingResponse) {
                        final boolean isAdded = item.isFavorite();

                        currentRating.setFavorite(isAdded);

                        getViewState().onFavoriteResult(isAdded);
                        getViewState().showMessage(isAdded ?
                                context.getString(R.string.favorite_added) :
                                context.getString(R.string.favorite_removed));
                    }

                    @Override
                    public void onError(final Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
