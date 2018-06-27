package com.epam.popcornapp.model.lists;

import com.epam.popcornapp.application.BaseApplication;
import com.epam.popcornapp.application.Constants;
import com.epam.popcornapp.inject.AccountInterface;
import com.epam.popcornapp.inject.ListsInterface;
import com.epam.popcornapp.model.base.BaseInteractor;
import com.epam.popcornapp.model.lists.interfaces.IListsInteractor;
import com.epam.popcornapp.pojo.lists.ListsCreateResponse;
import com.epam.popcornapp.pojo.lists.ListsResult;
import com.epam.popcornapp.pojo.lists.bodies.ListsCreateBody;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ListsInteractor extends BaseInteractor implements IListsInteractor {

    @Inject
    AccountInterface accountInterface;

    @Inject
    ListsInterface listsInterface;

    private Listener listener;

    public ListsInteractor() {
        BaseApplication.getApplicationComponent().inject(this);
    }

    @Override
    public void setListener(final Listener listener) {
        this.listener = listener;
    }

    @Override
    public void getCreatedLists(final boolean isInternetConnection, final int page) {
        if (listener == null) {
            return;
        }

        if (isInternetConnection) {
            final Observable<ListsResult> observable = accountInterface
                    .getCreatedLists(Constants.ACCOUNT_ID, Constants.API_KEY, Constants.CURRENT_SESSION, page)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());

            listener.onGetCreatedListsSuccess(observable);
        } else {
            listener.onError();
        }
    }

    @Override
    public void createList(final boolean isInternetConnection, final ListsCreateBody listsCreateBody) {
        if (listener == null || listsCreateBody == null) {
            return;
        }

        if (isInternetConnection) {
            final Observable<ListsCreateResponse> observable = listsInterface
                    .createList(Constants.API_KEY, Constants.CURRENT_SESSION, listsCreateBody)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());

            listener.onCreateSuccess(observable);
        } else {
            listener.onError();
        }
    }
}
