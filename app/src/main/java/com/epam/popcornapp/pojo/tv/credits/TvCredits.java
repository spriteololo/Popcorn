package com.epam.popcornapp.pojo.tv.credits;

import com.epam.popcornapp.pojo.base.MediaCast;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

public class TvCredits extends RealmObject {

    @PrimaryKey
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("cast")
    @Expose
    private RealmList<MediaCast> cast = null;

    @SerializedName("crew")
    @Expose
    private RealmList<CrewTv> crew = null;

    @Ignore
    @SerializedName("guest_stars")
    @Expose
    private RealmList<MediaCast> guestStars = null;

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

    public RealmList<CrewTv> getCrew() {
        return crew;
    }

    public void setCrew(final RealmList<CrewTv> crew) {
        this.crew = crew;
    }

    public RealmList<MediaCast> getGuestStars() {
        return guestStars;
    }

    public void setGuestStars(final RealmList<MediaCast> guestStars) {
        this.guestStars = guestStars;
    }
}
