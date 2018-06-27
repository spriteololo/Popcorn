package com.epam.popcornapp.pojo.movies.categories;

import com.epam.popcornapp.pojo.movies.details.MovieDetailResult;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class MovieTopRated extends RealmObject {

    @PrimaryKey
    @SerializedName("page")
    @Expose
    private int page;

    @SerializedName("results")
    @Expose
    private RealmList<MovieDetailResult> results = null;

    @SerializedName("total_results")
    @Expose
    private int totalResults;

    @SerializedName("total_pages")
    @Expose
    private int totalPages;

    public int getPage() {
        return page;
    }

    public void setPage(final int page) {
        this.page = page;
    }

    public RealmList<MovieDetailResult> getResults() {
        return results;
    }

    public void setResults(final RealmList<MovieDetailResult> results) {
        this.results = results;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(final int totalResults) {
        this.totalResults = totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(final int totalPages) {
        this.totalPages = totalPages;
    }
}