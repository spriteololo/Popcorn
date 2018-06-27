package com.epam.popcornapp.pojo.actors.images;

import com.epam.popcornapp.pojo.base.Image;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Images extends RealmObject {

    @SerializedName("profiles")
    @Expose
    private RealmList<Image> profiles = null;
    @SerializedName("id")
    @Expose
    @PrimaryKey
    private int id;

    public RealmList<Image> getProfiles() {
        return profiles;
    }

    public void setProfiles(final RealmList<Image> profiles) {
        this.profiles = profiles;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }
}
