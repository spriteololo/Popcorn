package com.epam.popcornapp.pojo.movies.video;

import com.epam.popcornapp.pojo.base.ResultVideo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class MovieVideoResult extends RealmObject{

    @PrimaryKey
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("results")
    @Expose
    private RealmList<ResultVideo> results = null;

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public RealmList<ResultVideo> getResults() {
        return results;
    }

    public void setResults(final RealmList<ResultVideo> results) {
        this.results = results;
    }
}