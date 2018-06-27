package com.epam.popcornapp.inject.modules;

import com.epam.popcornapp.model.splash.ISplashInteractor;
import com.epam.popcornapp.model.splash.SplashInteractor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class SplashModule {

    @Provides
    @Singleton
    ISplashInteractor provideSplashInteractor() {
        return new SplashInteractor();
    }
}
