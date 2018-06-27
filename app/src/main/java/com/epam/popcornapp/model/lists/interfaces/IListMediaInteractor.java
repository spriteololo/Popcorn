package com.epam.popcornapp.model.lists.interfaces;

import com.epam.popcornapp.pojo.lists.ListStatusResponse;

import io.reactivex.Observable;

public interface IListMediaInteractor {

    void setListener(Listener listener);

    void addMovie(boolean isInternetConnection, int listId, int mediaId, int lastClickPosition);

    void removeMovie(boolean isInternetConnection, int listId, int mediaId, int lastClickPosition);

    interface Listener {

        void onAddMovieSuccess(Observable<ListStatusResponse> observable, int lastClickPosition);

        void onRemoveMovieSuccess(Observable<ListStatusResponse> observable, int lastClickPosition);

        void onError();
    }
}
