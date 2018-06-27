package com.epam.popcornapp.inject;

import com.epam.popcornapp.pojo.base.rating.Rating;
import com.epam.popcornapp.pojo.base.rating.RatingItem;
import com.epam.popcornapp.pojo.base.rating.RatingResponse;
import com.epam.popcornapp.pojo.genres.GenresResult;
import com.epam.popcornapp.pojo.tv.TvDetails;
import com.epam.popcornapp.pojo.tv.episode.TvEpisodeResult;
import com.epam.popcornapp.pojo.tv.models.TvAiringTodayModel;
import com.epam.popcornapp.pojo.tv.models.TvOnTheAirModel;
import com.epam.popcornapp.pojo.tv.models.TvPopularModel;
import com.epam.popcornapp.pojo.tv.models.TvTopRatedModel;
import com.epam.popcornapp.pojo.tv.season.SeasonInfo;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TvInterface {

    @GET("tv/{tv_id}?append_to_response=images,credits,recommendations,videos,keywords")
    Observable<TvDetails> getTvDetails(@Path("tv_id") int tvId,
                                       @Query("api_key") String apiKey);

    @GET("tv/popular")
    Observable<TvPopularModel> getPopularTvShows(@Query("api_key") String apiKey,
                                                 @Query("page") int page);

    @GET("tv/top_rated")
    Observable<TvTopRatedModel> getTopRatedTvShows(@Query("api_key") String apiKey,
                                                   @Query("page") int page);

    @GET("tv/airing_today")
    Observable<TvAiringTodayModel> getAiringTodayTvShows(@Query("api_key") String apiKey,
                                                         @Query("page") int page);

    @GET("tv/on_the_air")
    Observable<TvOnTheAirModel> getOnTheAirTvShows(@Query("api_key") String apiKey,
                                                   @Query("page") int page);

    @GET("genre/tv/list")
    Observable<GenresResult> getTvGenres(@Query("api_key") String apiKey);

    @GET("tv/{tv_id}/season/{season_number}?append_to_response=credits")
    Observable<SeasonInfo> getSeasonInfo(@Path("tv_id") int id, @Path("season_number") int number,
                                         @Query("api_key") String apiKey);

    @GET("tv/{tv_id}/season/{season_number}/episode/{episode_number}?append_to_response=credits,images")
    Observable<TvEpisodeResult> getEpisode(@Path("tv_id") int tvId,
                                           @Path("season_number") int seasonNumber,
                                           @Path("episode_number") int episodeNumber,
                                           @Query("api_key") String apiKey);

    @GET("tv/{tv_id}/season/{season_number}/episode/{episode_number}/account_states")
    Observable<Rating> getCurrentRating(@Path("tv_id") int tvId,
                                        @Path("season_number") int seasonNumber,
                                        @Path("episode_number") int episodeNumber,
                                        @Query("api_key") String apiKey,
                                        @Query("session_id") String sessionID);

    @POST("tv/{tv_id}/season/{season_number}/episode/{episode_number}/rating")
    Observable<RatingResponse> rateTvEpisode(@Path("tv_id") int tvId,
                                             @Path("season_number") int seasonNumber,
                                             @Path("episode_number") int episodeNumber,
                                             @Query("api_key") String apiKey,
                                             @Query("session_id") String sessionID,
                                             @Body RatingItem rating);

    @DELETE("tv/{tv_id}/season/{season_number}/episode/{episode_number}/rating")
    Observable<RatingResponse> deleteRating(@Path("tv_id") int tvId,
                                            @Path("season_number") int seasonNumber,
                                            @Path("episode_number") int episodeNumber,
                                            @Query("api_key") String apiKey,
                                            @Query("session_id") String sessionID);

    @GET("tv/{tv_id}/account_states")
    Observable<Rating> getTvShowCurrentRating(@Path("tv_id") int tvId,
                                              @Query("api_key") String apiKey,
                                              @Query("session_id") String sessionID);

    @POST("tv/{tv_id}/rating")
    Observable<RatingResponse> rateTvShow(@Path("tv_id") int tvId,
                                          @Query("api_key") String apiKey,
                                          @Query("session_id") String sessionID,
                                          @Body RatingItem rating);

    @DELETE("tv/{tv_id}/rating")
    Observable<RatingResponse> deleteTvShowRating(@Path("tv_id") int tvId,
                                                  @Query("api_key") String apiKey,
                                                  @Query("session_id") String sessionID);
}
