package com.epam.popcornapp.inject.modules;

import com.epam.popcornapp.inject.ActorsInterface;
import com.epam.popcornapp.model.actors.ActorInfoInteractor;
import com.epam.popcornapp.model.actors.IActorInfoInteractor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class ActorsModule {

    @Provides
    @Singleton
    ActorsInterface provideActorInterface(final Retrofit retrofit) {
        return retrofit.create(ActorsInterface.class);
    }

    @Provides
    @Singleton
    IActorInfoInteractor provideActorInfoInteractor() {
        return new ActorInfoInteractor();
    }
}
