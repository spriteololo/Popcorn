package com.epam.popcornapp.inject.modules;

import com.epam.popcornapp.inject.MoviesInterface;
import com.epam.popcornapp.model.movie.MovieInfoInteractor;
import com.epam.popcornapp.model.movie.PopularMoviesInteractor;
import com.epam.popcornapp.model.movie.TopRatedMoviesInteractor;
import com.epam.popcornapp.model.movie.UpcomingMoviesInteractor;
import com.epam.popcornapp.model.movie.interfaces.IMovieInfoInteractor;
import com.epam.popcornapp.model.movie.interfaces.IPopularMoviesInteractor;
import com.epam.popcornapp.model.movie.interfaces.ITopRatedMoviesInteractor;
import com.epam.popcornapp.model.movie.interfaces.IUpcomingMoviesInteractor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class MoviesModule {

    @Provides
    @Singleton
    MoviesInterface provideMoviesApi(final Retrofit retrofit) {
        return retrofit.create(MoviesInterface.class);
    }

    @Provides
    @Singleton
    IMovieInfoInteractor provideMoviewInfoInteractor() {
        return new MovieInfoInteractor();
    }

    @Provides
    @Singleton
    IPopularMoviesInteractor providePopularMoviesInteractor() {
        return new PopularMoviesInteractor();
    }

    @Provides
    @Singleton
    ITopRatedMoviesInteractor provideTopRatedMovieInteractor() {
        return new TopRatedMoviesInteractor();
    }

    @Provides
    @Singleton
    IUpcomingMoviesInteractor provideUpComingMovieInteractor() {
        return new UpcomingMoviesInteractor();
    }
}
