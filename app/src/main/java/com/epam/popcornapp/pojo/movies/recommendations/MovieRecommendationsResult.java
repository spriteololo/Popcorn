package com.epam.popcornapp.pojo.movies.recommendations;

import com.epam.popcornapp.pojo.movies.details.MovieDetailResult;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;

public class MovieRecommendationsResult extends RealmObject {

    @SerializedName("page")
    @Expose
    private int page;

    @SerializedName("results")
    @Expose
    private RealmList<MovieDetailResult> recommendations = null;

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

    public RealmList<MovieDetailResult> getResults() {
        return recommendations;
    }

    public void setResults(final RealmList<MovieDetailResult> recommendations) {
        this.recommendations = recommendations;
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
