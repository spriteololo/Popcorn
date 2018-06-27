package com.epam.popcornapp.model.lists.interfaces;

import com.epam.popcornapp.pojo.lists.ListStatusResponse;
import com.epam.popcornapp.pojo.lists.listDetail.ListDetailResult;

import io.reactivex.Observable;

public interface IListDetailInteractor {

    void setListener(Listener listener);

    void getDetails(boolean isInternetConnection, int listId);

    void deleteList(boolean isInternetConnection, int listId);

    interface Listener {

        void onGetDetailsSuccess(Observable<ListDetailResult> observable);

        void onDeleteSuccess(Observable<ListStatusResponse> observable);

        void onError();
    }
}
