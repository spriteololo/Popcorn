package com.epam.popcornapp.pojo.base;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FavoriteRequestBody {

    @SerializedName("media_type")
    @Expose
    private String mediaType;
    @SerializedName("media_id")
    @Expose
    private int mediaId;
    @SerializedName("favorite")
    @Expose
    private boolean favorite;

    public FavoriteRequestBody() {
    }

    public FavoriteRequestBody(final String mediaType, final int mediaId, final boolean favorite) {
        this.mediaType = mediaType;
        this.mediaId = mediaId;
        this.favorite = favorite;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(final String mediaType) {
        this.mediaType = mediaType;
    }

    public int getMediaId() {
        return mediaId;
    }

    public void setMediaId(final int mediaId) {
        this.mediaId = mediaId;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(final boolean favorite) {
        this.favorite = favorite;
    }
}
