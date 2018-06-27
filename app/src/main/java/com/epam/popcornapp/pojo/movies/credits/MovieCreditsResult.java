package com.epam.popcornapp.pojo.movies.credits;

import com.epam.popcornapp.pojo.base.MediaCast;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class MovieCreditsResult extends RealmObject{

    @PrimaryKey
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("cast")
    @Expose
    private RealmList<MediaCast> cast = null;

    @SerializedName("crew")
    @Expose
    private RealmList<MovieCrew> crew = null;

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public RealmList<MediaCast> getCast() {
        return cast;
    }

    public void setCast(final RealmList<MediaCast> cast) {
        this.cast = cast;
    }

    public RealmList<MovieCrew> getCrew() {
        return crew;
    }

    public void setCrew(final RealmList<MovieCrew> crew) {
        this.crew = crew;
    }
}