package com.epam.popcornapp.inject;

import com.epam.popcornapp.pojo.splash_settings.Config;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ConfigInterface {

    @GET("configuration")
    Observable<Config> getConfig(@Query("api_key") String apiKey);

}
