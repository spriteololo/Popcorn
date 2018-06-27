package com.epam.popcornapp.pojo.lists;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListsCreateResponse {

    @SerializedName("status_message")
    @Expose
    private String statusMessage;

    @SerializedName("success")
    @Expose
    private boolean success;

    @SerializedName("status_code")
    @Expose
    private int statusCode;

    @SerializedName("list_id")
    @Expose
    private int listId;

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(final String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(final boolean success) {
        this.success = success;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(final int statusCode) {
        this.statusCode = statusCode;
    }

    public int getListId() {
        return listId;
    }

    public void setListId(final int listId) {
        this.listId = listId;
    }
}
