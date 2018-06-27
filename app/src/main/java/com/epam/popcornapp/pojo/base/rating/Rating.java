package com.epam.popcornapp.pojo.base.rating;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Rating {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("favorite")
    @Expose
    private boolean favorite;

    @SerializedName("rated")
    @Expose
    private RatingItem rated;

    @SerializedName("watchlist")
    @Expose
    private boolean watchlist;

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(final boolean favorite) {
        this.favorite = favorite;
    }

    public RatingItem getRated() {
        return rated;
    }

    public void setRated(final RatingItem rated) {
        this.rated = rated;
    }

    public boolean isWatchlist() {
        return watchlist;
    }

    public void setWatchlist(final boolean watchlist) {
        this.watchlist = watchlist;
    }
}
