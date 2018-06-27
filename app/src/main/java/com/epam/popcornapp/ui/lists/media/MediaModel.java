package com.epam.popcornapp.ui.lists.media;

import android.support.annotation.NonNull;

import com.epam.popcornapp.ui.tiles.item.BaseTileItem;

public class MediaModel {

    private static MediaModel mediaModel;

    private MediaChangeListener mediaChangeListener;

    private MediaModel() {
    }

    public static MediaModel getModel() {
        if (mediaModel == null) {
            mediaModel = new MediaModel();
        }

        return mediaModel;
    }

    public void setMediaChangeListener(@NonNull final MediaChangeListener mediaChangeListener) {
        this.mediaChangeListener = mediaChangeListener;
    }

    void onAddItem(@NonNull final BaseTileItem baseTileItem) {
        if (mediaChangeListener != null) {
            mediaChangeListener.onAddMediaItem(baseTileItem);
        }
    }

    public interface MediaChangeListener {

        void onAddMediaItem(BaseTileItem baseTileItem);
    }
}
