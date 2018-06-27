package com.epam.popcornapp.ui.lists.base;

import android.support.annotation.NonNull;

public class ListsModel {

    private static ListsModel listsModel;

    private ListChangeListener listChangeListener;

    private ListsModel() {
    }

    public static ListsModel getModel() {
        if (listsModel == null) {
            listsModel = new ListsModel();
        }

        return listsModel;
    }

    public void setListChangeListener(@NonNull final ListChangeListener listChangeListener) {
        this.listChangeListener = listChangeListener;
    }

    public void onDeleteList(final int position) {
        if (listChangeListener != null) {
            listChangeListener.onDeleteList(position);
        }
    }

    public void onMediaItemsSizeChange(final int position, final int size) {
        if (listChangeListener != null) {
            listChangeListener.onMediaItemsSizeChange(position, size);
        }
    }

    public interface ListChangeListener {

        void onDeleteList(int position);

        void onMediaItemsSizeChange(int position, int size);
    }
}
