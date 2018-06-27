package com.epam.popcornapp.pojo.search;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MultiSearch {

    @SerializedName("page")
    @Expose
    private int page;
    @SerializedName("total_results")
    @Expose
    private int totalResults;
    @SerializedName("total_pages")
    @Expose
    private int totalPages;
    @SerializedName("results")
    @Expose
    private List<MultiSearchResult> results = null;

    public int getPage() {
        return page;
    }

    public void setPage(final int page) {
        this.page = page;
    }

    public MultiSearch withPage(final int page) {
        this.page = page;
        return this;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(final int totalResults) {
        this.totalResults = totalResults;
    }

    public MultiSearch withTotalResults(final int totalResults) {
        this.totalResults = totalResults;
        return this;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(final int totalPages) {
        this.totalPages = totalPages;
    }

    public MultiSearch withTotalPages(final int totalPages) {
        this.totalPages = totalPages;
        return this;
    }

    public List<MultiSearchResult> getResults() {
        return results;
    }

    public void setResults(final List<MultiSearchResult> results) {
        this.results = results;
    }

    public MultiSearch withResults(final List<MultiSearchResult> results) {
        this.results = results;
        return this;
    }

}
