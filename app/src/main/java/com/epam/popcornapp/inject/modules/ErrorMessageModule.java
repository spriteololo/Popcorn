package com.epam.popcornapp.inject.modules;

import com.epam.popcornapp.application.ErrorMessage;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ErrorMessageModule {

    @Provides
    @Singleton
    ErrorMessage provideErrorMessage() {
        return new ErrorMessage();
    }
}
