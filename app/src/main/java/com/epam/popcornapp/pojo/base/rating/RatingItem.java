package com.epam.popcornapp.pojo.base.rating;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RatingItem {

    @SerializedName("value")
    @Expose
    public float value;

    public RatingItem(final float value) {
        this.value = value;
    }

    public float getValue() {
        return value;
    }

    public void setValue(final float value) {
        this.value = value;
    }
}
