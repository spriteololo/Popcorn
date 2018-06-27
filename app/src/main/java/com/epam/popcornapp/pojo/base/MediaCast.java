package com.epam.popcornapp.pojo.base;

import com.epam.popcornapp.pojo.actors.Actor;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

public class MediaCast extends RealmObject {

    @SerializedName("credit_id")
    @Expose
    @PrimaryKey
    private String creditId;

    @SerializedName("character")
    @Expose
    private String character;

    private Actor actor;

    @SerializedName("gender")
    @Expose
    @Ignore
    private int gender;

    @SerializedName("id")
    @Expose
    @Ignore
    private int id;

    @SerializedName("name")
    @Expose
    @Ignore
    private String name;

    @SerializedName("order")
    @Expose
    @Ignore
    private int order;

    @SerializedName("profile_path")
    @Expose
    @Ignore
    private String profilePath;

    public String getCharacter() {
        return character;
    }

    public void setCharacter(final String character) {
        this.character = character;
    }

    public String getCreditId() {
        return creditId;
    }

    public void setCreditId(final String creditId) {
        this.creditId = creditId;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(final int gender) {
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(final int order) {
        this.order = order;
    }

    public String getProfilePath() {
        return profilePath;
    }

    public void setProfilePath(final String profilePath) {
        this.profilePath = profilePath;
    }

    public Actor getActor() {
        return actor;
    }

    public void setActor(final Actor actor) {
        this.actor = actor;
    }
}
