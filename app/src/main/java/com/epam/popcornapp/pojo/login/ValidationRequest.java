package com.epam.popcornapp.pojo.login;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ValidationRequest {

    @SerializedName("success")
    @Expose
    private boolean success;
    @SerializedName("request_token")
    @Expose
    private String requestToken;
    @SerializedName("status_message")
    @Expose
    private String statusMessage;
    @SerializedName("status_code")
    @Expose
    private int statusCode;

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(final String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(final int statusCode) {
        this.statusCode = statusCode;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(final boolean success) {
        this.success = success;
    }

    public String getRequestToken() {
        return requestToken;
    }

    public void setRequestToken(final String requestToken) {
        this.requestToken = requestToken;
    }

}