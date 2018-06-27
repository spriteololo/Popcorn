package com.epam.popcornapp.pojo.base;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WatchlistRequestBody {

    @SerializedName("media_type")
    @Expose
    private String mediaType;
    @SerializedName("media_id")
    @Expose
    private int mediaId;
    @SerializedName("watchlist")
    @Expose
    private boolean watchlist;

    public WatchlistRequestBody() {
    }

    public WatchlistRequestBody(final String mediaType, final int mediaId, final boolean watchlist) {
        this.mediaType = mediaType;
        this.mediaId = mediaId;
        this.watchlist = watchlist;
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

    public boolean isWatchlist() {
        return watchlist;
    }

    public void setWatchlist(final boolean watchlist) {
        this.watchlist = watchlist;
    }
}
