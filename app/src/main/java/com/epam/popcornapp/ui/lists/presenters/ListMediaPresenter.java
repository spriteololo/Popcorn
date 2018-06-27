package com.epam.popcornapp.ui.lists.presenters;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;
import com.epam.popcornapp.R;
import com.epam.popcornapp.application.BaseApplication;
import com.epam.popcornapp.application.ErrorMessage;
import com.epam.popcornapp.model.lists.interfaces.IListMediaInteractor;
import com.epam.popcornapp.pojo.lists.ListStatusResponse;
import com.epam.popcornapp.ui.all.base.BaseInternetMvpPresenter;
import com.epam.popcornapp.ui.lists.presenters.views.ListMediaView;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

@InjectViewState
public class ListMediaPresenter extends BaseInternetMvpPresenter<ListMediaView> implements
        IListMediaInteractor.Listener {

    @Inject
    Context context;

    @Inject
    IListMediaInteractor interactor;

    private final int STATUS_ADD_ITEM_SUCCESS_CODE = 12;
    private final int STATUS_REMOVE_ITEM_SUCCESS_CODE = 13;

    public ListMediaPresenter() {
        BaseApplication.getApplicationComponent().inject(this);

        interactor.setListener(this);
    }

    public void addMovie(final int listId, final int mediaId, final int lastClickPosition) {
        interactor.addMovie(isInternetConnection(context), listId, mediaId, lastClickPosition);
    }

    public void removeMovie(final int listId, final int mediaId, final int lastClickPosition) {
        interactor.removeMovie(isInternetConnection(context), listId, mediaId, lastClickPosition);
    }

    @Override
    public void onAddMovieSuccess(final Observable<ListStatusResponse> observable, final int lastClickPosition) {
        observable
                .subscribe(new Observer<ListStatusResponse>() {
                    @Override
                    public void onSubscribe(final Disposable d) {

                    }

                    @Override
                    public void onNext(final ListStatusResponse listStatusResponse) {
                        if (listStatusResponse.getStatusCode() == STATUS_ADD_ITEM_SUCCESS_CODE) {
                            getViewState().addMovie(lastClickPosition);
                            getViewState().showMessage(context.getString(R.string.item_added));
                        } else {
                            getViewState().showMessage(context.getString(R.string.item_add_error));
                        }
                    }

                    @Override
                    public void onError(final Throwable e) {
                        e.printStackTrace();

                        getViewState().showMessage(context.getString(R.string.item_add_error));
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void onRemoveMovieSuccess(final Observable<ListStatusResponse> observable, final int lastClickPosition) {
        observable
                .subscribe(new Observer<ListStatusResponse>() {
                    @Override
                    public void onSubscribe(final Disposable d) {

                    }

                    @Override
                    public void onNext(final ListStatusResponse listStatusResponse) {
                        if (listStatusResponse.getStatusCode() == STATUS_REMOVE_ITEM_SUCCESS_CODE) {
                            getViewState().removeMovie(lastClickPosition);
                            getViewState().showMessage(context.getString(R.string.item_removed));
                        } else {
                            getViewState().showMessage(context.getString(R.string.item_remove_error));
                        }
                    }

                    @Override
                    public void onError(final Throwable e) {
                        e.printStackTrace();

                        getViewState().showMessage(context.getString(R.string.item_remove_error));
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void onError() {
        getViewState().onError(ErrorMessage.ErrorType.INTERNET_CONNECTION_ERROR);
    }
}
