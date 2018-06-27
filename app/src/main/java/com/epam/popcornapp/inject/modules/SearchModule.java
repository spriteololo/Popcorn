package com.epam.popcornapp.inject.modules;

import com.epam.popcornapp.inject.SearchInterface;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class SearchModule {

    @Provides
    @Singleton
    SearchInterface provideSearchInterface(final Retrofit retrofit) {
        return retrofit.create(SearchInterface.class);
    }
}
