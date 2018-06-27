package com.epam.popcornapp.ui.lists.presenters;

import android.content.Context;
import android.view.View;

import com.arellomobile.mvp.InjectViewState;
import com.epam.popcornapp.R;
import com.epam.popcornapp.application.BaseApplication;
import com.epam.popcornapp.application.ErrorMessage;
import com.epam.popcornapp.model.lists.interfaces.IListDetailInteractor;
import com.epam.popcornapp.pojo.lists.ListStatusResponse;
import com.epam.popcornapp.pojo.lists.listDetail.ListDetailResult;
import com.epam.popcornapp.ui.all.base.BaseInternetMvpPresenter;
import com.epam.popcornapp.ui.lists.base.ListsConverter;
import com.epam.popcornapp.ui.lists.presenters.views.ListDetailView;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

@InjectViewState
public class ListDetailPresenter extends BaseInternetMvpPresenter<ListDetailView> implements
        IListDetailInteractor.Listener {

    @Inject
    Context context;

    @Inject
    IListDetailInteractor interactor;

    private final int STATUS_SUCCESS_CODE = 12;
    private final int SERVER_ERROR_CODE = 500;

    public ListDetailPresenter() {
        BaseApplication.getApplicationComponent().inject(this);

        interactor.setListener(this);
    }

    public void getDetails(final int listId) {
        interactor.getDetails(isInternetConnection(context), listId);
    }

    public void deleteList(final int listId) {
        interactor.deleteList(isInternetConnection(context), listId);
    }

    public void onActionsButtonClicked(final View view) {
        if (view == null) {
            return;
        }

        switch (view.getId()) {
            case R.id.action_add_movie_button:
                getViewState().startAddMediaActivity();

                break;
            case R.id.action_delete_list_button:
                getViewState().showDialog();

                break;
        }
    }

    @Override
    public void onGetDetailsSuccess(final Observable<ListDetailResult> observable) {
        observable
                .subscribe(new Observer<ListDetailResult>() {
                    @Override
                    public void onSubscribe(final Disposable d) {

                    }

                    @Override
                    public void onNext(final ListDetailResult listDetailResult) {
                        getViewState().setDetails(
                                listDetailResult.getName(),
                                ListsConverter.convertMediaItemsToBaseTileItems(listDetailResult.getItems(), context));
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
    public void onDeleteSuccess(final Observable<ListStatusResponse> observable) {
        observable
                .subscribe(new Observer<ListStatusResponse>() {
                    @Override
                    public void onSubscribe(final Disposable d) {

                    }

                    @Override
                    public void onNext(final ListStatusResponse listStatusResponse) {
                        if (listStatusResponse.getStatusCode() == STATUS_SUCCESS_CODE) {
                            getViewState().listDeleted();
                            getViewState().showMessage(context.getString(R.string.list_deleted));
                        } else {
                            getViewState().showMessage(context.getString(R.string.list_delete_error));
                        }
                    }

                    @Override
                    public void onError(final Throwable e) {
                        if (e instanceof HttpException && ((HttpException) e).code() == SERVER_ERROR_CODE) {
                            getViewState().listDeleted();
                            getViewState().showMessage(context.getString(R.string.list_deleted));
                        } else {
                            e.printStackTrace();

                            getViewState().showMessage(context.getString(R.string.list_delete_error));
                        }
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
