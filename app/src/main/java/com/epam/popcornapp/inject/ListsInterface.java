package com.epam.popcornapp.inject;

import com.epam.popcornapp.pojo.lists.ListStatusResponse;
import com.epam.popcornapp.pojo.lists.ListsCreateResponse;
import com.epam.popcornapp.pojo.lists.bodies.ListMediaBody;
import com.epam.popcornapp.pojo.lists.bodies.ListsCreateBody;
import com.epam.popcornapp.pojo.lists.listDetail.ListDetailResult;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ListsInterface {

    @GET("list/{list_id}")
    Observable<ListDetailResult> getDetails(@Path("list_id") int listId,
                                            @Query("api_key") String apiKey);

    @POST("list")
    Observable<ListsCreateResponse> createList(@Query("api_key") String apiKey,
                                               @Query("session_id") String sessionId,
                                               @Body ListsCreateBody item);

    @DELETE("list/{list_id}")
    Observable<ListStatusResponse> deleteList(@Path("list_id") int listId,
                                              @Query("api_key") String apiKey,
                                              @Query("session_id") String sessionId);

    @POST("list/{list_id}/add_item")
    Observable<ListStatusResponse> addMovie(@Path("list_id") int listId,
                                            @Query("api_key") String apiKey,
                                            @Query("session_id") String sessionId,
                                            @Body ListMediaBody item);

    @POST("list/{list_id}/remove_item")
    Observable<ListStatusResponse> removeMovie(@Path("list_id") int listId,
                                               @Query("api_key") String apiKey,
                                               @Query("session_id") String sessionId,
                                               @Body ListMediaBody item);
}
