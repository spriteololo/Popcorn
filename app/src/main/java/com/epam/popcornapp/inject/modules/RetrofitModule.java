package com.epam.popcornapp.inject.modules;

import android.support.annotation.NonNull;

import com.epam.popcornapp.application.DateDeserializer;
import com.epam.popcornapp.application.RatingDeserializer;
import com.epam.popcornapp.pojo.base.rating.Rating;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Date;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class RetrofitModule {

    @NonNull
    private final String movieDbUrl;

    public RetrofitModule(@NonNull final String movieDbUrl) {
        this.movieDbUrl = movieDbUrl;
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(@NonNull final OkHttpClient okHttpClient) {
        final OkHttpClient.Builder okHttpBuilder = okHttpClient.newBuilder();

        return new Retrofit.Builder()
                .baseUrl(movieDbUrl)
                .client(okHttpBuilder.build())
                .addConverterFactory(buildGsonConverterFactory())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    private static GsonConverterFactory buildGsonConverterFactory() {

        final GsonBuilder builder = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateDeserializer())
                .registerTypeAdapter(Rating.class, new RatingDeserializer());

        final Gson gson = builder.create();

        return GsonConverterFactory.create(gson);
    }
}
