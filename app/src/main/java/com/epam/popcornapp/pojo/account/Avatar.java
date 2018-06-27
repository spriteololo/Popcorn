package com.epam.popcornapp.pojo.account;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Avatar {

    @SerializedName("gravatar")
    @Expose
    private Gravatar gravatar;

    public Gravatar getGravatar() {
        return gravatar;
    }

    public void setGravatar(final Gravatar gravatar) {
        this.gravatar = gravatar;
    }
}