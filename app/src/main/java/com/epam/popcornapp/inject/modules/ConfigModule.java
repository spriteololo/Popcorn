package com.epam.popcornapp.inject.modules;

import com.epam.popcornapp.inject.ConfigInterface;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class ConfigModule {

    @Provides
    @Singleton
    ConfigInterface provideConfigInterface(final Retrofit retrofit) {
        return retrofit.create(ConfigInterface.class);
    }
}