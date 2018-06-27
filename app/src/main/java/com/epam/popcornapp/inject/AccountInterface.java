package com.epam.popcornapp.inject;

import com.epam.popcornapp.pojo.account.Account;
import com.epam.popcornapp.pojo.base.FavoriteRequestBody;
import com.epam.popcornapp.pojo.base.WatchlistRequestBody;
import com.epam.popcornapp.pojo.base.rating.RatingResponse;
import com.epam.popcornapp.pojo.favorite.movie.FavoriteMovieResult;
import com.epam.popcornapp.pojo.favorite.tv.FavoriteTvResult;
import com.epam.popcornapp.pojo.lists.ListsResult;
import com.epam.popcornapp.pojo.rated.movie.RatedMovieResult;
import com.epam.popcornapp.pojo.rated.tv.RatedTvResult;
import com.epam.popcornapp.pojo.watchlist.movie.WatchlistMovieResult;
import com.epam.popcornapp.pojo.watchlist.tv.WatchlistTvResult;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AccountInterface {

    @GET("account")
    Observable<Account> getAccountDetails(@Query("api_key") String apiKey,
                                          @Query("session_id") String sessionId);

    @POST("account/{account_id}/favorite")
    Observable<RatingResponse> markAsFavorite(@Path("account_id") int accountId,
                                              @Query("api_key") String apiKey,
                                              @Query("session_id") String sessionId,
                                              @Body FavoriteRequestBody item);

    @POST("account/{account_id}/watchlist")
    Observable<RatingResponse> addToWatchlist(@Path("account_id") int accountId,
                                              @Query("api_key") String apiKey,
                                              @Query("session_id") String sessionId,
                                              @Body WatchlistRequestBody item);

    @GET("account/{account_id}/rated/movies")
    Observable<RatedMovieResult> getRatedMovies(@Path("account_id") int accountId,
                                                @Query("api_key") String apiKey,
                                                @Query("session_id") String sessionId,
                                                @Query("page") int page);


    @GET("account/{account_id}/rated/tv")
    Observable<RatedTvResult> getRatedTv(@Path("account_id") int accountId,
                                         @Query("api_key") String apiKey,
                                         @Query("session_id") String sessionId,
                                         @Query("page") int page);

    @GET("account/{account_id}/watchlist/movies")
    Observable<WatchlistMovieResult> getMovieWatchlist(@Path("account_id") int accountId,
                                                       @Query("api_key") String apiKey,
                                                       @Query("session_id") String sessionId,
                                                       @Query("page") int page);

    @GET("account/{account_id}/watchlist/tv")
    Observable<WatchlistTvResult> getTvShowsWatchlist(@Path("account_id") int accountId,
                                                      @Query("api_key") String apiKey,
                                                      @Query("session_id") String sessionId,
                                                      @Query("page") int page);

    @GET("account/{account_id}/favorite/movies")
    Observable<FavoriteMovieResult> getFavoriteMovie(@Path("account_id") int accountId,
                                                     @Query("api_key") String apiKey,
                                                     @Query("session_id") String sessionId,
                                                     @Query("page") int page);

    @GET("account/{account_id}/favorite/tv")
    Observable<FavoriteTvResult> getFavoriteTvShows(@Path("account_id") int accountId,
                                                    @Query("api_key") String apiKey,
                                                    @Query("session_id") String sessionId,
                                                    @Query("page") int page);

    @GET("account/{account_id}/lists")
    Observable<ListsResult> getCreatedLists(@Path("account_id") int accountId,
                                            @Query("api_key") String apiKey,
                                            @Query("session_id") String sessionId,
                                            @Query("page") int page);
}