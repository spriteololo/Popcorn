package com.epam.popcornapp.pojo.splash_settings;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Config {

    @SerializedName("images")
    @Expose
    private ImagesConfig images;

    @SerializedName("change_keys")
    @Expose
    private List<String> changeKeys = null;

    public ImagesConfig getImages() {
        return images;
    }

    public void setImages(final ImagesConfig images) {
        this.images = images;
    }

    public List<String> getChangeKeys() {
        return changeKeys;
    }

    public void setChangeKeys(final List<String> changeKeys) {
        this.changeKeys = changeKeys;
    }
}