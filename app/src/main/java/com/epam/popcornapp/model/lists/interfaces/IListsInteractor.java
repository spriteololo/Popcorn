package com.epam.popcornapp.model.lists.interfaces;

import com.epam.popcornapp.pojo.lists.ListsCreateResponse;
import com.epam.popcornapp.pojo.lists.ListsResult;
import com.epam.popcornapp.pojo.lists.bodies.ListsCreateBody;

import io.reactivex.Observable;

public interface IListsInteractor {

    void setListener(Listener listener);

    void getCreatedLists(boolean isInternetConnection, int page);

    void createList(boolean isInternetConnection, ListsCreateBody listsCreateBody);

    interface Listener {

        void onGetCreatedListsSuccess(Observable<ListsResult> observable);

        void onCreateSuccess(Observable<ListsCreateResponse> observable);

        void onError();
    }
}
