package com.epam.popcornapp.pojo.splash_settings;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ImagesConfig extends RealmObject {

    @PrimaryKey
    @SerializedName("base_url")
    @Expose
    private String baseUrl;

    @SerializedName("secure_base_url")
    @Expose
    private String secureBaseUrl;

    @SerializedName("backdrop_sizes")
    @Expose
    private RealmList<String> backdropSizes = null;

    @SerializedName("logo_sizes")
    @Expose
    private RealmList<String> logoSizes = null;

    @SerializedName("poster_sizes")
    @Expose
    private RealmList<String> posterSizes = null;

    @SerializedName("profile_sizes")
    @Expose
    private RealmList<String> profileSizes = null;

    @SerializedName("still_sizes")
    @Expose
    private RealmList<String> stillSizes = null;

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(final String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getSecureBaseUrl() {
        return secureBaseUrl;
    }

    public void setSecureBaseUrl(final String secureBaseUrl) {
        this.secureBaseUrl = secureBaseUrl;
    }

    public RealmList<String> getBackdropSizes() {
        return backdropSizes;
    }

    public void setBackdropSizes(final RealmList<String> backdropSizes) {
        this.backdropSizes = backdropSizes;
    }

    public RealmList<String> getLogoSizes() {
        return logoSizes;
    }

    public void setLogoSizes(final RealmList<String> logoSizes) {
        this.logoSizes = logoSizes;
    }

    public RealmList<String> getPosterSizes() {
        return posterSizes;
    }

    public void setPosterSizes(final RealmList<String> posterSizes) {
        this.posterSizes = posterSizes;
    }

    public RealmList<String> getProfileSizes() {
        return profileSizes;
    }

    public void setProfileSizes(final RealmList<String> profileSizes) {
        this.profileSizes = profileSizes;
    }

    public RealmList<String> getStillSizes() {
        return stillSizes;
    }

    public void setStillSizes(final RealmList<String> stillSizes) {
        this.stillSizes = stillSizes;
    }

}