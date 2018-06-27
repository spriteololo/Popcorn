package com.epam.popcornapp.model.lists;

import com.epam.popcornapp.application.BaseApplication;
import com.epam.popcornapp.application.Constants;
import com.epam.popcornapp.inject.ListsInterface;
import com.epam.popcornapp.model.base.BaseInteractor;
import com.epam.popcornapp.model.lists.interfaces.IListMediaInteractor;
import com.epam.popcornapp.pojo.lists.ListStatusResponse;
import com.epam.popcornapp.pojo.lists.bodies.ListMediaBody;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ListMediaInteractor extends BaseInteractor implements IListMediaInteractor {

    @Inject
    ListsInterface listsInterface;

    private Listener listener;

    public ListMediaInteractor() {
        BaseApplication.getApplicationComponent().inject(this);
    }

    @Override
    public void setListener(final Listener listener) {
        this.listener = listener;
    }

    @Override
    public void addMovie(final boolean isInternetConnection, final int listId, final int mediaId,
                         final int lastClickPosition) {
        if (listener == null) {
            return;
        }

        if (isInternetConnection) {
            final Observable<ListStatusResponse> observable = listsInterface
                    .addMovie(listId, Constants.API_KEY, Constants.CURRENT_SESSION, new ListMediaBody(mediaId))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());

            listener.onAddMovieSuccess(observable, lastClickPosition);
        } else {
            listener.onError();
        }
    }

    @Override
    public void removeMovie(final boolean isInternetConnection, final int listId, final int mediaId,
                            final int lastClickPosition) {
        if (listener == null) {
            return;
        }

        if (isInternetConnection) {
            final Observable<ListStatusResponse> observable = listsInterface
                    .removeMovie(listId, Constants.API_KEY, Constants.CURRENT_SESSION, new ListMediaBody(mediaId))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());

            listener.onRemoveMovieSuccess(observable, lastClickPosition);
        } else {
            listener.onError();
        }
    }
}
