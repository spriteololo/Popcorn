package com.epam.popcornapp.pojo.actors.credits;

import com.epam.popcornapp.pojo.actors.credits.embedded.ActorCast;
import com.epam.popcornapp.pojo.actors.credits.embedded.ActorCrew;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Credits extends RealmObject {

    @SerializedName("cast")
    @Expose
    private RealmList<ActorCast> cast = null;
    @SerializedName("crew")
    @Expose
    private RealmList<ActorCrew> crew = null;
    @SerializedName("id")
    @Expose
    @PrimaryKey
    private int id;

    public RealmList<ActorCast> getCast() {
        return cast;
    }

    public void setCast(final RealmList<ActorCast> cast) {
        this.cast = cast;
    }

    public RealmList<ActorCrew> getCrew() {
        return crew;
    }

    public void setCrew(final RealmList<ActorCrew> crew) {
        this.crew = crew;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }
}
