package com.epam.popcornapp.inject;

import com.epam.popcornapp.pojo.actors.search.ActorsSearch;
import com.epam.popcornapp.pojo.movies.search.MovieSearch;
import com.epam.popcornapp.pojo.search.MultiSearch;
import com.epam.popcornapp.pojo.tv.search.TvSearch;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SearchInterface {

    @GET("search/multi")
    Observable<MultiSearch> search(@Query("api_key") String apiKey, @Query("query") String query, @Query("page") int page);

    @GET("search/person")
    Observable<ActorsSearch> searchPeople(@Query("api_key") String apiKey, @Query("query") String query, @Query("page") int page);

    @GET("search/tv")
    Observable<TvSearch> searchTV(@Query("api_key") String apiKey, @Query("query") String query, @Query("page") int page);

    @GET("search/movie")
    Observable<MovieSearch> searchMovie(@Query("api_key") String apiKey, @Query("query") String query, @Query("page") int page);
}
