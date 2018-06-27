package com.epam.popcornapp.pojo.movies.details;

import com.epam.popcornapp.pojo.base.Genre;
import com.epam.popcornapp.pojo.base.ProductionCompany;
import com.epam.popcornapp.pojo.movies.credits.MovieCreditsResult;
import com.epam.popcornapp.pojo.movies.images.MovieImagesResult;
import com.epam.popcornapp.pojo.movies.keywords.MovieKeywordsResult;
import com.epam.popcornapp.pojo.movies.recommendations.MovieRecommendationsResult;
import com.epam.popcornapp.pojo.movies.release_dates.MovieReleaseDatesResult;
import com.epam.popcornapp.pojo.movies.reviews.MovieReviewResult;
import com.epam.popcornapp.pojo.movies.video.MovieVideoResult;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

public class MovieDetailResult extends RealmObject {

    @PrimaryKey
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("adult")
    @Expose
    private boolean adult;

    @SerializedName("backdrop_path")
    @Expose
    private String backdropPath;

    @SerializedName("budget")
    @Expose
    private int budget;

    @SerializedName("genres")
    @Expose
    private RealmList<Genre> genres = null;

    @Ignore
    @SerializedName("genre_ids")
    @Expose
    private RealmList<Integer> genreIds = null;

    @SerializedName("homepage")
    @Expose
    private String homepage;

    @SerializedName("imdb_id")
    @Expose
    private String imdbId;

    @SerializedName("original_language")
    @Expose
    private String originalLanguage;

    @SerializedName("original_title")
    @Expose
    private String originalTitle;

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

    @SerializedName("production_countries")
    @Expose
    private RealmList<MovieProductionCountry> productionCountries = null;

    @SerializedName("release_date")
    @Expose
    private String releaseDate;

    @SerializedName("revenue")
    @Expose
    private int revenue;

    @SerializedName("runtime")
    @Expose
    private int runtime;

    @SerializedName("spoken_languages")
    @Expose
    private RealmList<MovieSpokenLanguage> spokenLanguages = null;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("tagline")
    @Expose
    private String tagline;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("video")
    @Expose
    private boolean video;

    @SerializedName("vote_average")
    @Expose
    private float voteAverage;

    @SerializedName("vote_count")
    @Expose
    private int voteCount;

    @SerializedName("keywords")
    @Expose
    private MovieKeywordsResult keywordsResult;

    @SerializedName("credits")
    @Expose
    private MovieCreditsResult creditsResult;

    @SerializedName("images")
    @Expose
    private MovieImagesResult imagesResult;

    @SerializedName("recommendations")
    @Expose
    private MovieRecommendationsResult movieRecommendationsResult;

    @SerializedName("videos")
    @Expose
    private MovieVideoResult movieVideoResult;

    @SerializedName("release_dates")
    @Expose
    private MovieReleaseDatesResult movieReleaseDatesResult;

    @SerializedName("reviews")
    @Expose
    private MovieReviewResult reviewResult;

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(final boolean adult) {
        this.adult = adult;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(final String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(final int budget) {
        this.budget = budget;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public RealmList<Integer> getGenreIds() {
        return genreIds;
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

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(final String imdbId) {
        this.imdbId = imdbId;
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

    public List<ProductionCompany> getProductionCompanies() {
        return productionCompanies;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(final String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getRevenue() {
        return revenue;
    }

    public void setRevenue(final int revenue) {
        this.revenue = revenue;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(final int runtime) {
        this.runtime = runtime;
    }

    public List<MovieSpokenLanguage> getSpokenLanguages() {
        return spokenLanguages;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(final String tagline) {
        this.tagline = tagline;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(final boolean video) {
        this.video = video;
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

    public MovieKeywordsResult getKeywordsResult() {
        return keywordsResult;
    }

    public void setKeywordsResult(final MovieKeywordsResult keywordsResult) {
        this.keywordsResult = keywordsResult;
    }

    public MovieCreditsResult getCreditsResult() {
        return creditsResult;
    }

    public void setCreditsResult(final MovieCreditsResult creditsResult) {
        this.creditsResult = creditsResult;
    }

    public MovieImagesResult getImagesResult() {
        return imagesResult;
    }

    public void setImagesResult(final MovieImagesResult imagesResult) {
        this.imagesResult = imagesResult;
    }

    public MovieVideoResult getMovieVideoResult() {
        return movieVideoResult;
    }

    public void setMovieVideoResult(final MovieVideoResult movieVideoResult) {
        this.movieVideoResult = movieVideoResult;
    }

    public MovieRecommendationsResult getMovieRecommendationsResult() {
        return movieRecommendationsResult;
    }

    public void setMovieRecommendationsResult(final MovieRecommendationsResult movieRecommendationsResult) {
        this.movieRecommendationsResult = movieRecommendationsResult;
    }

    public MovieReleaseDatesResult getMovieReleaseDatesResult() {
        return movieReleaseDatesResult;
    }

    public void setMovieReleaseDatesResult(final MovieReleaseDatesResult movieReleaseDatesResult) {
        this.movieReleaseDatesResult = movieReleaseDatesResult;
    }

    public MovieReviewResult getReviewResult() {
        return reviewResult;
    }

    public void setReviewResult(final MovieReviewResult reviewResult) {
        this.reviewResult = reviewResult;
    }

    public void setGenres(final RealmList<Genre> genres) {
        this.genres = genres;
    }

    public void setGenreIds(final RealmList<Integer> genreIds) {
        this.genreIds = genreIds;
    }

    public void setProductionCompanies(final RealmList<ProductionCompany> productionCompanies) {
        this.productionCompanies = productionCompanies;
    }

    public RealmList<MovieProductionCountry> getProductionCountries() {
        return productionCountries;
    }

    public void setProductionCountries(final RealmList<MovieProductionCountry> productionCountries) {
        this.productionCountries = productionCountries;
    }

    public void setSpokenLanguages(final RealmList<MovieSpokenLanguage> spokenLanguages) {
        this.spokenLanguages = spokenLanguages;
    }

    public MovieDetailResult() {
    }

    public MovieDetailResult(final String posterPath,
                             final boolean adult,
                             final String overview,
                             final String releaseDate,
                             final String originalTitle,
                             final RealmList<Integer> genreIds,
                             final int id,
                             final String originalLanguage,
                             final String title,
                             final String backdropPath,
                             final double popularity,
                             final int voteCount,
                             final boolean video,
                             final float voteAverage) {
        this.id = id;
        this.adult = adult;
        this.backdropPath = backdropPath;
        this.genreIds = genreIds;
        this.originalLanguage = originalLanguage;
        this.originalTitle = originalTitle;
        this.overview = overview;
        this.popularity = popularity;
        this.posterPath = posterPath;
        this.releaseDate = releaseDate;
        this.title = title;
        this.video = video;
        this.voteAverage = voteAverage;
        this.voteCount = voteCount;
    }

    public static Builder builder() {
        return new MovieDetailResult().new Builder();
    }

    public class Builder {
        private String posterPath;
        private boolean adult;
        private String overview;
        private String releaseDate;
        private String originalTitle;
        private RealmList<Integer> genreIds;
        private int id;
        private String originalLanguage;
        private String title;
        private String backdropPath;
        private double popularity;
        private int voteCount;
        private boolean video;
        private float voteAverage;

        public Builder() {
        }

        public Builder setPosterPath(final String posterPath) {
            this.posterPath = posterPath;
            return this;
        }

        public Builder setAdult(final boolean adult) {
            this.adult = adult;
            return this;
        }

        public Builder setOverview(final String overview) {
            this.overview = overview;
            return this;
        }

        public Builder setReleaseDate(final String releaseDate) {
            this.releaseDate = releaseDate;
            return this;
        }

        public Builder setOriginalTitle(final String originalTitle) {
            this.originalTitle = originalTitle;
            return this;
        }

        public Builder setGenreIds(final RealmList<Integer> genreIds) {
            this.genreIds = genreIds;
            return this;
        }

        public Builder setId(final int id) {
            this.id = id;
            return this;
        }

        public Builder setOriginalLanguage(final String originalLanguage) {
            this.originalLanguage = originalLanguage;
            return this;
        }

        public Builder setTitle(final String title) {
            this.title = title;
            return this;
        }

        public Builder setBackdropPath(final String backdropPath) {
            this.backdropPath = backdropPath;
            return this;
        }

        public Builder setPopularity(final double popularity) {
            this.popularity = popularity;
            return this;
        }

        public Builder setVoteCount(final int voteCount) {
            this.voteCount = voteCount;
            return this;
        }

        public Builder setVideo(final boolean video) {
            this.video = video;
            return this;
        }

        public Builder setVoteAverage(final float voteAverage) {
            this.voteAverage = voteAverage;
            return this;
        }

        public MovieDetailResult build() {
            return new MovieDetailResult(posterPath,
                    adult,
                    overview,
                    releaseDate,
                    originalTitle,
                    genreIds,
                    id,
                    originalLanguage,
                    title,
                    backdropPath,
                    popularity,
                    voteCount,
                    video,
                    voteAverage);
        }
    }
}
