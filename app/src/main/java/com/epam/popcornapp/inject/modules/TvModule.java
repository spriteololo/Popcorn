package com.epam.popcornapp.inject.modules;

import com.epam.popcornapp.inject.TvInterface;
import com.epam.popcornapp.model.tv.ITvAiringTodayInteractor;
import com.epam.popcornapp.model.tv.ITvOnTheAirInteractor;
import com.epam.popcornapp.model.tv.ITvPopularInteractor;
import com.epam.popcornapp.model.tv.ITvTopRatedInteractor;
import com.epam.popcornapp.model.tv.TvAiringTodayInteractor;
import com.epam.popcornapp.model.tv.TvOnTheAirInteractor;
import com.epam.popcornapp.model.tv.TvPopularInteractor;
import com.epam.popcornapp.model.tv.TvTopRatedInteractor;
import com.epam.popcornapp.model.tv.episode.ITvEpisodeInteractor;
import com.epam.popcornapp.model.tv.episode.TvEpisodeInteractor;
import com.epam.popcornapp.model.tv.info.ITvInfoInteractor;
import com.epam.popcornapp.model.tv.info.TvInfoInteractor;
import com.epam.popcornapp.model.tv.season.ITvSeasonInfoInteractor;
import com.epam.popcornapp.model.tv.season.TvSeasonInfoInteractor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class TvModule {

    @Provides
    @Singleton
    TvInterface provideTvInterface(final Retrofit retrofit) {
        return retrofit.create(TvInterface.class);
    }

    @Provides
    @Singleton
    ITvInfoInteractor provideTvInfoInteractor() {
        return new TvInfoInteractor();
    }

    @Provides
    @Singleton
    ITvAiringTodayInteractor provideTvAiringTodayInteractor() {
        return new TvAiringTodayInteractor();
    }

    @Provides
    @Singleton
    ITvOnTheAirInteractor provideTvOnTheAirInteractor() {
        return new TvOnTheAirInteractor();
    }

    @Provides
    @Singleton
    ITvPopularInteractor provideTvPopularInteractor() {
        return new TvPopularInteractor();
    }

    @Provides
    @Singleton
    ITvTopRatedInteractor provideTvTopRatedIntertactor() {
        return new TvTopRatedInteractor();
    }

    @Provides
    @Singleton
    ITvSeasonInfoInteractor provideTvSeasonInfoInteractor() {
        return new TvSeasonInfoInteractor();
    }

    @Provides
    @Singleton
    ITvEpisodeInteractor provideTvEpisodeInteractor() {
        return new TvEpisodeInteractor();
    }
}
