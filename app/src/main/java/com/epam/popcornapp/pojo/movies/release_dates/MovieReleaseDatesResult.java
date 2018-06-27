package com.epam.popcornapp.pojo.movies.release_dates;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class MovieReleaseDatesResult extends RealmObject {

    @PrimaryKey
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("results")
    @Expose
    private RealmList<MovieResultDates> results = null;

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public RealmList<MovieResultDates> getResults() {
        return results;
    }

    public void setResults(final RealmList<MovieResultDates> results) {
        this.results = results;
    }
}