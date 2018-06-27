package com.epam.popcornapp.pojo.movies.keywords;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

public class MovieKeywordsResult extends RealmObject {

    @PrimaryKey
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("keywords")
    @Expose
    private RealmList<MovieKeywords> keywords = null;

    @Ignore
    @SerializedName("results")
    @Expose
    private RealmList<MovieKeywords> keywordsTv = null;

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public RealmList<MovieKeywords> getKeywords() {
        return keywords;
    }

    public void setKeywords(final RealmList<MovieKeywords> keywords) {
        this.keywords = keywords;
    }

    public RealmList<MovieKeywords> getKeywordsTv() {
        return keywordsTv;
    }

    public void setKeywordsTv(final RealmList<MovieKeywords> keywordsTv) {
        this.keywordsTv = keywordsTv;
    }
}
