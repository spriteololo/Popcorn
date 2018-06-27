package com.epam.popcornapp.inject.modules;

import com.epam.popcornapp.model.base.RealmUtils;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RealmModule {

    @Provides
    @Singleton
    RealmUtils providerealm() {
        return new RealmUtils();
    }
}
