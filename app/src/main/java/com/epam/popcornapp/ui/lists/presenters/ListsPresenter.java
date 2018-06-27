package com.epam.popcornapp.ui.lists.presenters;

import android.content.Context;
import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.epam.popcornapp.R;
import com.epam.popcornapp.application.BaseApplication;
import com.epam.popcornapp.application.ErrorMessage.ErrorType;
import com.epam.popcornapp.model.lists.interfaces.IListsInteractor;
import com.epam.popcornapp.pojo.lists.ListsCreateResponse;
import com.epam.popcornapp.pojo.lists.ListItem;
import com.epam.popcornapp.pojo.lists.ListsResult;
import com.epam.popcornapp.pojo.lists.bodies.ListsCreateBody;
import com.epam.popcornapp.ui.all.base.BaseInternetMvpPresenter;
import com.epam.popcornapp.ui.lists.presenters.views.ListsView;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

@InjectViewState
public class ListsPresenter extends BaseInternetMvpPresenter<ListsView> implements
        IListsInteractor.Listener {

    @Inject
    Context context;

    @Inject
    IListsInteractor interactor;

    private int currentPage = 1;

    private ListsCreateBody listsCreateBody;

    public ListsPresenter() {
        BaseApplication.getApplicationComponent().inject(this);

        interactor.setListener(this);
    }

    public void getCreatedLists() {
        interactor.getCreatedLists(isInternetConnection(context), currentPage);
    }

    public void getCreatedListsByPage(final int page) {
        this.currentPage = page;

        getCreatedLists();
    }

    public void createList(@NonNull final ListsCreateBody listsCreateBody) {
        this.listsCreateBody = listsCreateBody;

        interactor.createList(isInternetConnection(context), listsCreateBody);
    }

    @Override
    public void onGetCreatedListsSuccess(final Observable<ListsResult> observable) {
        observable
                .subscribe(new Observer<ListsResult>() {
                    @Override
                    public void onSubscribe(final Disposable d) {

                    }

                    @Override
                    public void onNext(final ListsResult listsResult) {
                        getViewState().setData(listsResult);
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

    @Override
    public void onCreateSuccess(final Observable<ListsCreateResponse> observable) {
        observable
                .subscribe(new Observer<ListsCreateResponse>() {
                    @Override
                    public void onSubscribe(final Disposable d) {

                    }

                    @Override
                    public void onNext(final ListsCreateResponse listsCreateResponse) {
                        if (listsCreateResponse.isSuccess() && listsCreateBody != null) {
                            getViewState().setNewList(new ListItem(
                                    listsCreateResponse.getListId(),
                                    listsCreateBody.getName(),
                                    listsCreateBody.getDescription()));
                            getViewState().showMessage(context.getString(R.string.list_created));
                        } else {
                            getViewState().showMessage(context.getString(R.string.list_create_error));
                        }
                    }

                    @Override
                    public void onError(final Throwable e) {
                        e.printStackTrace();

                        getViewState().showMessage(context.getString(R.string.list_create_error));
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
