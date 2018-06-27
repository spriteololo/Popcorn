package com.epam.popcornapp.pojo.tv.recomendations;

import com.epam.popcornapp.pojo.tv.TvDetails;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;

public class RecommendationsTv extends RealmObject{

    @SerializedName("page")
    @Expose
    private int page;

    @SerializedName("results")
    @Expose
    private RealmList<TvDetails> results = null;

    @SerializedName("total_pages")
    @Expose
    private int totalPages;

    @SerializedName("total_results")
    @Expose
    private int totalResults;

    public int getPage() {
        return page;
    }

    public void setPage(final int page) {
        this.page = page;
    }

    public RealmList<TvDetails> getResults() {
        return results;
    }

    public void setResults(final RealmList<TvDetails> results) {
        this.results = results;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(final int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(final int totalResults) {
        this.totalResults = totalResults;
    }

}