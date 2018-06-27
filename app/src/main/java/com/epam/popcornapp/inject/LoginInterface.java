package com.epam.popcornapp.inject;

import com.epam.popcornapp.pojo.account.Account;
import com.epam.popcornapp.pojo.login.Session;
import com.epam.popcornapp.pojo.login.Token;
import com.epam.popcornapp.pojo.login.ValidationRequest;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LoginInterface {

    @GET("authentication/token/new")
    Flowable<Token> getToken(@Query("api_key") String apiKey);

    @GET("authentication/token/validate_with_login")
    Flowable<ValidationRequest> validateWithLogin(@Query("api_key") String apiKey,
                                                  @Query("username") String username,
                                                  @Query("password") String password,
                                                  @Query("request_token") String requestToken);

    @GET("authentication/session/new")
    Flowable<Session> getSession(@Query("api_key") String apiKey,
                                 @Query("request_token") String requestToken);

    @GET("account")
    Flowable<Account> getAccountDetails(@Query("api_key") String apiKey,
                                        @Query("session_id") String sessionId);
}
