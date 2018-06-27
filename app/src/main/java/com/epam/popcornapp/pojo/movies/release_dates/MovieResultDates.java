package com.epam.popcornapp.pojo.movies.release_dates;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;

public class MovieResultDates extends RealmObject {

    @SerializedName("iso_3166_1")
    @Expose
    private String iso31661;

    @SerializedName("release_dates")
    @Expose
    private RealmList<MovieReleaseDate> releaseDates = null;

    public String getIso31661() {
        return iso31661;
    }

    public void setIso31661(final String iso31661) {
        this.iso31661 = iso31661;
    }

    public RealmList<MovieReleaseDate> getReleaseDates() {
        return releaseDates;
    }

    public void setReleaseDates(final RealmList<MovieReleaseDate> releaseDates) {
        this.releaseDates = releaseDates;
    }
}