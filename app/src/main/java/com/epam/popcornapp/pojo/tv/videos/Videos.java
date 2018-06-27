package com.epam.popcornapp.pojo.tv.videos;

import com.epam.popcornapp.pojo.base.ResultVideo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Videos extends RealmObject{

    @SerializedName("id")
    @Expose
    @PrimaryKey
    private int id;

    @SerializedName("results")
    @Expose
    private RealmList<ResultVideo> resultVideos = null;

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public RealmList<ResultVideo> getResultVideos() {
        return resultVideos;
    }

    public void setResultVideos(final RealmList<ResultVideo> resultVideos) {
        this.resultVideos = resultVideos;
    }
}