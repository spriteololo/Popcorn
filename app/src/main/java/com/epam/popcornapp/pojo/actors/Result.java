package com.epam.popcornapp.pojo.actors;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.annotations.PrimaryKey;

public class Result {

    @SerializedName("id")
    @Expose
    @PrimaryKey
    private int id;
    @SerializedName("popularity")
    @Expose
    private double popularity;
    @SerializedName("profile_path")
    @Expose
    private String profilePath;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("known_for")
    @Expose
    private RealmList<KnownFor> knownFor = null;
    @SerializedName("adult")
    @Expose
    private boolean adult;

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(final double popularity) {
        this.popularity = popularity;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getProfilePath() {
        return profilePath;
    }

    public void setProfilePath(final String profilePath) {
        this.profilePath = profilePath;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public RealmList<KnownFor> getKnownFor() {
        return knownFor;
    }

    public void setKnownFor(final RealmList<KnownFor> knownFor) {
        this.knownFor = knownFor;
    }

    public boolean getAdult() {
        return adult;
    }

    public void setAdult(final boolean adult) {
        this.adult = adult;
    }
}
