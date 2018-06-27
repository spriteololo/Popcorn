package com.epam.popcornapp.inject.modules;

import com.epam.popcornapp.inject.LoginInterface;
import com.epam.popcornapp.model.login.ILoginInteractor;
import com.epam.popcornapp.model.login.LoginInteractor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class LoginModule {

    @Provides
    @Singleton
    LoginInterface provideLoginInterface(final Retrofit retrofit) {
        return retrofit.create(LoginInterface.class);
    }

    @Provides
    @Singleton
    ILoginInteractor provideLoginInteractor() {
        return new LoginInteractor();
    }
}