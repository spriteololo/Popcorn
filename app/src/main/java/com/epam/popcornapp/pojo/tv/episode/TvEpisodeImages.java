package com.epam.popcornapp.pojo.tv.episode;

import com.epam.popcornapp.pojo.base.Image;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class TvEpisodeImages extends RealmObject {

    @PrimaryKey
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("stills")
    @Expose
    private RealmList<Image> images = null;

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public RealmList<Image> getImages() {
        return images;
    }

    public void setImages(final RealmList<Image> images) {
        this.images = images;
    }
}
