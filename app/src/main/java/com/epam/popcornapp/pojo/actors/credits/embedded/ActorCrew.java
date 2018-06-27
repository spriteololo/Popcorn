package com.epam.popcornapp.pojo.actors.credits.embedded;

import com.epam.popcornapp.pojo.movies.details.MovieDetailResult;
import com.epam.popcornapp.pojo.tv.TvDetails;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

public class ActorCrew extends RealmObject implements BaseActorCredit   {

    @SerializedName("credit_id")
    @Expose
    @PrimaryKey
    private String creditId;

    @SerializedName("department")
    @Expose
    private String department;

    @SerializedName("job")
    @Expose
    private String job;

    @SerializedName("media_type")
    @Expose
    private String mediaType;

    private TvDetails tv;

    private MovieDetailResult movie;

    @SerializedName("id")
    @Expose
    @Ignore
    private int id;

    @SerializedName("original_language")
    @Expose
    @Ignore
    private String originalLanguage;

    @SerializedName("original_title")
    @Expose
    @Ignore
    private String originalTitle;

    @SerializedName("overview")
    @Expose
    @Ignore
    private String overview;

    @SerializedName("vote_count")
    @Expose
    @Ignore
    private int voteCount;

    @SerializedName("video")
    @Expose
    @Ignore
    private boolean video;

    @SerializedName("poster_path")
    @Expose
    @Ignore
    private String posterPath;

    @SerializedName("backdrop_path")
    @Expose
    @Ignore
    private String backdropPath;

    @SerializedName("title")
    @Expose
    @Ignore
    private String title;

    @SerializedName("popularity")
    @Expose
    @Ignore
    private double popularity;

    @SerializedName("genre_ids")
    @Expose
    @Ignore
    private RealmList<Integer> genreIds = null;

    @SerializedName("vote_average")
    @Expose
    @Ignore
    private float voteAverage;

    @SerializedName("adult")
    @Expose
    @Ignore
    private boolean adult;

    @SerializedName("release_date")
    @Expose
    @Ignore
    private String releaseDate;

    @SerializedName("episode_count")
    @Expose
    @Ignore
    private int episodeCount;

    @SerializedName("origin_country")
    @Expose
    @Ignore
    private RealmList<String> originCountry = null;

    @SerializedName("original_name")
    @Expose
    @Ignore
    private String originalName;

    @SerializedName("name")
    @Expose
    @Ignore
    private String name;

    @SerializedName("first_air_date")
    @Expose
    @Ignore
    private String firstAirDate;

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(final String department) {
        this.department = department;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(final String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(final String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getJob() {
        return job;
    }

    public void setJob(final String job) {
        this.job = job;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(final String overview) {
        this.overview = overview;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(final int voteCount) {
        this.voteCount = voteCount;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(final boolean video) {
        this.video = video;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(final String mediaType) {
        this.mediaType = mediaType;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(final String posterPath) {
        this.posterPath = posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(final String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(final double popularity) {
        this.popularity = popularity;
    }

    public RealmList<Integer> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(final RealmList<Integer> genreIds) {
        this.genreIds = genreIds;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(final float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(final boolean adult) {
        this.adult = adult;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(final String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getCreditId() {
        return creditId;
    }

    public void setCreditId(final String creditId) {
        this.creditId = creditId;
    }

    public TvDetails getTv() {
        return tv;
    }

    public void setTv(final TvDetails tv) {
        this.tv = tv;
    }

    public MovieDetailResult getMovie() {
        return movie;
    }

    public void setMovie(final MovieDetailResult movie) {
        this.movie = movie;
    }

    @Override
    public int getEpisodeCount() {
        return episodeCount;
    }

    public void setEpisodeCount(final int episodeCount) {
        this.episodeCount = episodeCount;
    }

    @Override
    public RealmList<String> getOriginCountry() {
        return originCountry;
    }

    public void setOriginCountry(final RealmList<String> originCountry) {
        this.originCountry = originCountry;
    }

    @Override
    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(final String originalName) {
        this.originalName = originalName;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public String getFirstAirDate() {
        return firstAirDate;
    }

    public void setFirstAirDate(final String firstAirDate) {
        this.firstAirDate = firstAirDate;
    }
}
