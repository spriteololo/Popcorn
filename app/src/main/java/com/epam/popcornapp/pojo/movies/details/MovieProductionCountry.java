package com.epam.popcornapp.pojo.movies.details;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class MovieProductionCountry extends RealmObject {

    @SerializedName("iso_3166_1")
    @Expose
    private String iso31661;

    @SerializedName("name")
    @Expose
    private String name;

    public String getIso31661() {
        return iso31661;
    }

    public void setIso31661(final String iso31661) {
        this.iso31661 = iso31661;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }
}
