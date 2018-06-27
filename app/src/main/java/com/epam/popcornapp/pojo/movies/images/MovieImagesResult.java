package com.epam.popcornapp.pojo.movies.images;

import com.epam.popcornapp.pojo.base.Image;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class MovieImagesResult extends RealmObject {

    @PrimaryKey
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("backdrops")
    @Expose
    private RealmList<Image> backdrops = null;

    @SerializedName("posters")
    @Expose
    private RealmList<Image> posters = null;

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public RealmList<Image> getBackdrops() {
        return backdrops;
    }

    public void setBackdrops(final RealmList<Image> backdrops) {
        this.backdrops = backdrops;
    }

    public RealmList<Image> getPosters() {
        return posters;
    }

    public void setPosters(final RealmList<Image> posters) {
        this.posters = posters;
    }
}