package com.epam.popcornapp.inject;

import com.epam.popcornapp.pojo.base.rating.Rating;
import com.epam.popcornapp.pojo.base.rating.RatingItem;
import com.epam.popcornapp.pojo.base.rating.RatingResponse;
import com.epam.popcornapp.pojo.genres.GenresResult;
import com.epam.popcornapp.pojo.movies.categories.MoviePopular;
import com.epam.popcornapp.pojo.movies.categories.MovieTopRated;
import com.epam.popcornapp.pojo.movies.categories.MovieUpcoming;
import com.epam.popcornapp.pojo.movies.details.MovieDetailResult;
import com.epam.popcornapp.pojo.movies.reviews.MovieReviewResult;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MoviesInterface {

    @GET("movie/{movie_id}?append_to_response=keywords,credits,images,recommendations,videos,release_dates,reviews")
    Observable<MovieDetailResult> getMoviesDetails(@Path("movie_id") int id,
                                                   @Query("api_key") String apiKey);

    @GET("movie/popular")
    Observable<MoviePopular> getPopularMovies(@Query("api_key") String apiKey, @Query("page") int page);

    @GET("movie/upcoming")
    Observable<MovieUpcoming> getUpcomingMovies(@Query("api_key") String apiKey, @Query("page") int page);

    @GET("movie/top_rated")
    Observable<MovieTopRated> getTopRatedMovies(@Query("api_key") String apiKey, @Query("page") int page);

    @GET("movie/{movie_id}/reviews")
    Observable<MovieReviewResult> getReviews(@Path("movie_id") int id, @Query("api_key") String apiKey, @Query("page") int page);

    @GET("genre/movie/list")
    Observable<GenresResult> getMovieGenres(@Query("api_key") String apiKey);

    @GET("movie/{movie_id}/account_states")
    Observable<Rating> getAccountStates(@Path("movie_id") int id,
                                        @Query("api_key") String apiKey,
                                        @Query("session_id") String sessionId);

    @POST("movie/{movie_id}/rating")
    Observable<RatingResponse> setRating(@Path("movie_id") int id, @Query("api_key") String apiKey,
                                         @Query("session_id") String sessionId,
                                         @Body RatingItem ratingItem);

    @DELETE("movie/{movie_id}/rating")
    Observable<RatingResponse> deleteRating(@Path("movie_id") int id,
                                            @Query("api_key") String apiKey,
                                            @Query("session_id") String sessionId);
}
