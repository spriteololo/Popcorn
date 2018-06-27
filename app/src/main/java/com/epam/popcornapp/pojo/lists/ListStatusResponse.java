package com.epam.popcornapp.pojo.lists;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListStatusResponse {

    @SerializedName("status_code")
    @Expose
    private int statusCode;

    @SerializedName("status_message")
    @Expose
    private String statusMessage;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(final int statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(final String statusMessage) {
        this.statusMessage = statusMessage;
    }
}
