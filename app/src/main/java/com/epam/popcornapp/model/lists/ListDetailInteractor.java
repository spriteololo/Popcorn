package com.epam.popcornapp.model.lists;

import com.epam.popcornapp.application.BaseApplication;
import com.epam.popcornapp.application.Constants;
import com.epam.popcornapp.inject.ListsInterface;
import com.epam.popcornapp.model.base.BaseInteractor;
import com.epam.popcornapp.model.lists.interfaces.IListDetailInteractor;
import com.epam.popcornapp.pojo.lists.ListStatusResponse;
import com.epam.popcornapp.pojo.lists.listDetail.ListDetailResult;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ListDetailInteractor extends BaseInteractor implements IListDetailInteractor {

    @Inject
    ListsInterface listsInterface;

    private Listener listener;

    public ListDetailInteractor() {
        BaseApplication.getApplicationComponent().inject(this);
    }

    @Override
    public void setListener(final Listener listener) {
        this.listener = listener;
    }

    @Override
    public void getDetails(final boolean isInternetConnection, final int listId) {
        if (listener == null) {
            return;
        }

        if (isInternetConnection) {
            final Observable<ListDetailResult> observable = listsInterface
                    .getDetails(listId, Constants.API_KEY)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());

            listener.onGetDetailsSuccess(observable);
        } else {
            listener.onError();
        }
    }

    @Override
    public void deleteList(final boolean isInternetConnection, final int listId) {
        if (listener == null) {
            return;
        }

        if (isInternetConnection) {
            final Observable<ListStatusResponse> observable = listsInterface
                    .deleteList(listId, Constants.API_KEY, Constants.CURRENT_SESSION)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());

            listener.onDeleteSuccess(observable);
        } else {
            listener.onError();
        }
    }
}
