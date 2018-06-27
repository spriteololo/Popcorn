package com.epam.popcornapp.pojo.base;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ProductionCompany extends RealmObject{

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("id")
    @Expose
    @PrimaryKey
    private int id;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }
}