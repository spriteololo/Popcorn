package com.epam.popcornapp.pojo.tv.episode;

import com.epam.popcornapp.pojo.tv.credits.TvCredits;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class TvEpisodeResult extends RealmObject {

    @PrimaryKey
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("overview")
    @Expose
    private String overview;

    @SerializedName("season_number")
    @Expose
    private int seasonNumber;

    @SerializedName("episode_number")
    @Expose
    private int episodeNumber;

    @SerializedName("production_code")
    @Expose
    private String productionCode;

    @SerializedName("still_path")
    @Expose
    private String stillPath;

    @SerializedName("vote_average")
    @Expose
    private float voteAverage;

    @SerializedName("vote_count")
    @Expose
    private int voteCount;

    @SerializedName("air_date")
    @Expose
    private String airDate;

    private String director;

    private String writer;

    @SerializedName("credits")
    @Expose
    private TvCredits credits = null;

    @SerializedName("images")
    @Expose
    private TvEpisodeImages images = null;

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(final String overview) {
        this.overview = overview;
    }

    public int getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(final int seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public int getEpisodeNumber() {
        return episodeNumber;
    }

    public void setEpisodeNumber(final int episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    public String getProductionCode() {
        return productionCode;
    }

    public void setProductionCode(final String productionCode) {
        this.productionCode = productionCode;
    }

    public String getStillPath() {
        return stillPath;
    }

    public void setStillPath(final String stillPath) {
        this.stillPath = stillPath;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(final float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(final int voteCount) {
        this.voteCount = voteCount;
    }

    public String getAirDate() {
        return airDate;
    }

    public void setAirDate(final String airDate) {
        this.airDate = airDate;
    }

    public TvCredits getCredits() {
        return credits;
    }

    public void setCredits(final TvCredits credits) {
        this.credits = credits;
    }

    public TvEpisodeImages getImages() {
        return images;
    }

    public void setImages(final TvEpisodeImages images) {
        this.images = images;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(final String director) {
        this.director = director;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(final String writer) {
        this.writer = writer;
    }
}
