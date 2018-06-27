package com.epam.popcornapp.pojo.movies.release_dates;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class MovieReleaseDate extends RealmObject {

    @SerializedName("certification")
    @Expose
    private String certification;

    @SerializedName("iso_639_1")
    @Expose
    private String iso6391;

    @SerializedName("note")
    @Expose
    private String note;

    @SerializedName("release_date")
    @Expose
    private String releaseDate;

    @SerializedName("type")
    @Expose
    private int type;

    public String getCertification() {
        return certification;
    }

    public void setCertification(final String certification) {
        this.certification = certification;
    }

    public String getIso6391() {
        return iso6391;
    }

    public void setIso6391(final String iso6391) {
        this.iso6391 = iso6391;
    }

    public String getNote() {
        return note;
    }

    public void setNote(final String note) {
        this.note = note;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(final String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getType() {
        return type;
    }

    public void setType(final int type) {
        this.type = type;
    }
}