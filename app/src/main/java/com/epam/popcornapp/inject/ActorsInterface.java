package com.epam.popcornapp.inject;

import com.epam.popcornapp.pojo.actors.Actor;
import com.epam.popcornapp.pojo.actors.ActorListModel;
import com.epam.popcornapp.pojo.actors.credits.Credits;
import com.epam.popcornapp.pojo.actors.images.Images;
import com.epam.popcornapp.pojo.actors.search.ActorsSearch;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ActorsInterface {

    @GET("person/{person_id}?append_to_response=images,combined_credits")
    Observable<Actor> getDetails(@Path("person_id") int actorsId,
                                 @Query("api_key") String apiKey);

    @GET("person/popular")
    Observable<ActorListModel> getPopularActors(@Query("api_key") String apiKey,
                                                @Query("page") int page);

    @GET("person/{person_id}/images")
    Observable<Images> getImages(@Path("person_id") int actorId,
                                 @Query("api_key") String apiKey);

    @GET("person/{person_id}/combined_credits")
    Observable<Credits> getCredits(@Path("person_id") int actorId,
                                   @Query("api_key") String apiKey);

    @GET("search/person")
    Observable<ActorsSearch> searchPeople(@Query("api_key") String apiKey,
                                          @Query("query") String name);
}
