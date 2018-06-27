package com.epam.popcornapp.pojo.lists.bodies;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListMediaBody {

    @SerializedName("media_id")
    @Expose
    private int mediaId;

    public ListMediaBody(final int mediaId) {
        this.mediaId = mediaId;
    }

    public int getMediaId() {
        return mediaId;
    }

    public void setMediaId(final int mediaId) {
        this.mediaId = mediaId;
    }
}
