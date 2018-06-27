package com.epam.popcornapp.pojo.tv.season;

import com.epam.popcornapp.pojo.tv.credits.TvCredits;
import com.epam.popcornapp.pojo.tv.episode.TvEpisodeResult;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class SeasonInfo extends RealmObject {

    @PrimaryKey
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("_id")
    @Expose
    private String idHz;

    @SerializedName("air_date")
    @Expose
    private String airDate;

    @SerializedName("episodes")
    @Expose
    private RealmList<TvEpisodeResult> episodes = null;

    @SerializedName("episode_count")
    @Expose
    private int episodeCount;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("overview")
    @Expose
    private String overview;

    @SerializedName("poster_path")
    @Expose
    private String posterPath;

    @SerializedName("season_number")
    @Expose
    private int seasonNumber;

    @SerializedName("credits")
    @Expose
    private TvCredits credits;

    public String getIdHz() {
        return idHz;
    }

    public void setIdHz(final String idHz) {
        this.idHz = idHz;
    }

    public SeasonInfo withIdHz(final String idHz) {
        this.idHz = idHz;
        return this;
    }

    public String getAirDate() {
        return airDate;
    }

    public void setAirDate(final String airDate) {
        this.airDate = airDate;
    }

    public SeasonInfo withAirDate(final String airDate) {
        this.airDate = airDate;
        return this;
    }

    public RealmList<TvEpisodeResult> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(final RealmList<TvEpisodeResult> episodes) {
        this.episodes = episodes;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public SeasonInfo withName(final String name) {
        this.name = name;
        return this;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(final String overview) {
        this.overview = overview;
    }

    public SeasonInfo withOverview(final String overview) {
        this.overview = overview;
        return this;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public SeasonInfo withId(final int id) {
        this.id = id;
        return this;
    }

    public int getEpisodeCount() {
        return episodeCount;
    }

    public void setEpisodeCount(final int episodeCount) {
        this.episodeCount = episodeCount;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(final String posterPath) {
        this.posterPath = posterPath;
    }

    public SeasonInfo withPosterPath(final String posterPath) {
        this.posterPath = posterPath;
        return this;
    }

    public int getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(final int seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public SeasonInfo withSeasonNumber(final int seasonNumber) {
        this.seasonNumber = seasonNumber;
        return this;
    }

    public TvCredits getCredits() {
        return credits;
    }

    public void setCredits(final TvCredits credits) {
        this.credits = credits;
    }
}
