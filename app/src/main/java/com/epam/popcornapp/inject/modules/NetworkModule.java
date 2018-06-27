package com.epam.popcornapp.inject.modules;

import android.support.annotation.NonNull;

import com.epam.popcornapp.ui.all.base.NetworkStateReceiver;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

@Module
public class NetworkModule {

    private final int timeout;

    public NetworkModule(final int timeout) {
        this.timeout = timeout;
    }

    @Provides
    @NonNull
    @Singleton
    OkHttpClient provideOkHttpClient() {
        final OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
        okHttpBuilder.connectTimeout(timeout, TimeUnit.SECONDS);
        okHttpBuilder.writeTimeout(timeout, TimeUnit.SECONDS);
        okHttpBuilder.readTimeout(timeout, TimeUnit.SECONDS);

        return okHttpBuilder.build();
    }

    @Provides
    @Singleton
    NetworkStateReceiver getNavigatorHolder() {
        return new NetworkStateReceiver();
    }
}
