package com.epam.popcornapp.pojo.tv;

import com.epam.popcornapp.pojo.actors.Actor;
import com.epam.popcornapp.pojo.base.Genre;
import com.epam.popcornapp.pojo.base.ProductionCompany;
import com.epam.popcornapp.pojo.movies.keywords.MovieKeywordsResult;
import com.epam.popcornapp.pojo.tv.credits.TvCredits;
import com.epam.popcornapp.pojo.tv.images.ImagesTv;
import com.epam.popcornapp.pojo.tv.recomendations.RecommendationsTv;
import com.epam.popcornapp.pojo.tv.season.SeasonInfo;
import com.epam.popcornapp.pojo.tv.videos.Videos;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

public class TvDetails extends RealmObject {

    @PrimaryKey
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("backdrop_path")
    @Expose
    private String backdropPath;

    @SerializedName("created_by")
    @Expose
    private RealmList<Actor> createdBy = null;

    @SerializedName("episode_run_time")
    @Expose
    private RealmList<Integer> episodeRunTime = null;

    @SerializedName("first_air_date")
    @Expose
    private String firstAirDate;

    @SerializedName("genres")
    @Expose
    private RealmList<Genre> genres = null;

    @SerializedName("genre_ids")
    @Expose
    @Ignore
    private RealmList<Integer> genreIds = null;

    @SerializedName("homepage")
    @Expose
    private String homepage;

    @SerializedName("in_production")
    @Expose
    private boolean inProduction;

    @SerializedName("languages")
    @Expose
    private RealmList<String> languages = null;

    @SerializedName("last_air_date")
    @Expose
    private String lastAirDate;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("networks")
    @Expose
    private RealmList<Network> networks = null;

    @SerializedName("number_of_episodes")
    @Expose
    private int numberOfEpisodes;

    @SerializedName("number_of_seasons")
    @Expose
    private int numberOfSeasons;

    @SerializedName("origin_country")
    @Expose
    private RealmList<String> originCountry = null;

    @SerializedName("original_language")
    @Expose
    private String originalLanguage;

    @SerializedName("original_name")
    @Expose
    private String originalName;

    @SerializedName("overview")
    @Expose
    private String overview;

    @SerializedName("popularity")
    @Expose
    private double popularity;

    @SerializedName("poster_path")
    @Expose
    private String posterPath;

    @SerializedName("production_companies")
    @Expose
    private RealmList<ProductionCompany> productionCompanies = null;

    @SerializedName("seasons")
    @Expose
    private RealmList<SeasonInfo> seasons = null;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("vote_average")
    @Expose
    private float voteAverage;

    @SerializedName("vote_count")
    @Expose
    private int voteCount;

    @SerializedName("images")
    @Expose
    private ImagesTv images;

    @SerializedName("credits")
    @Expose
    private TvCredits credits;

    @SerializedName("recommendations")
    @Expose
    private RecommendationsTv recommendations;

    @SerializedName("videos")
    @Expose
    private Videos videos;

    @SerializedName("keywords")
    @Expose
    private MovieKeywordsResult keywordsResult;

    public RealmList<Integer> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(final RealmList<Integer> genreIds) {
        this.genreIds = genreIds;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(final String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public RealmList<Actor> getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(final RealmList<Actor> createdBy) {
        this.createdBy = createdBy;
    }

    public RealmList<Integer> getEpisodeRunTime() {
        return episodeRunTime;
    }

    public void setEpisodeRunTime(final RealmList<Integer> episodeRunTime) {
        this.episodeRunTime = episodeRunTime;
    }

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public void setFirstAirDate(final String firstAirDate) {
        this.firstAirDate = firstAirDate;
    }

    public RealmList<Genre> getGenres() {
        return genres;
    }

    public void setGenres(final RealmList<Genre> genres) {
        this.genres = genres;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(final String homepage) {
        this.homepage = homepage;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public boolean isInProduction() {
        return inProduction;
    }

    public void setInProduction(final boolean inProduction) {
        this.inProduction = inProduction;
    }

    public RealmList<String> getLanguages() {
        return languages;
    }

    public void setLanguages(final RealmList<String> languages) {
        this.languages = languages;
    }

    public String getLastAirDate() {
        return lastAirDate;
    }

    public void setLastAirDate(final String lastAirDate) {
        this.lastAirDate = lastAirDate;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public RealmList<Network> getNetworks() {
        return networks;
    }

    public void setNetworks(final RealmList<Network> networks) {
        this.networks = networks;
    }

    public int getNumberOfEpisodes() {
        return numberOfEpisodes;
    }

    public void setNumberOfEpisodes(final int numberOfEpisodes) {
        this.numberOfEpisodes = numberOfEpisodes;
    }

    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public void setNumberOfSeasons(final int numberOfSeasons) {
        this.numberOfSeasons = numberOfSeasons;
    }

    public RealmList<String> getOriginCountry() {
        return originCountry;
    }

    public void setOriginCountry(final RealmList<String> originCountry) {
        this.originCountry = originCountry;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(final String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(final String originalName) {
        this.originalName = originalName;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(final String overview) {
        this.overview = overview;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(final double popularity) {
        this.popularity = popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(final String posterPath) {
        this.posterPath = posterPath;
    }

    public RealmList<ProductionCompany> getProductionCompanies() {
        return productionCompanies;
    }

    public void setProductionCompanies(final RealmList<ProductionCompany> productionCompanies) {
        this.productionCompanies = productionCompanies;
    }

    public RealmList<SeasonInfo> getSeasons() {
        return seasons;
    }

    public void setSeasons(final RealmList<SeasonInfo> seasons) {
        this.seasons = seasons;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
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

    public ImagesTv getImages() {
        return images;
    }

    public void setImages(final ImagesTv images) {
        this.images = images;
    }

    public TvCredits getCredits() {
        return credits;
    }

    public void setCredits(final TvCredits credits) {
        this.credits = credits;
    }

    public RecommendationsTv getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(final RecommendationsTv recommendations) {
        this.recommendations = recommendations;
    }

    public Videos getVideos() {
        return videos;
    }

    public void setVideos(final Videos videos) {
        this.videos = videos;
    }

    public MovieKeywordsResult getKeywordsResult() {
        return keywordsResult;
    }

    public void setKeywordsResult(final MovieKeywordsResult keywordsResult) {
        this.keywordsResult = keywordsResult;
    }
}