package com.epam.popcornapp.inject.modules;

import com.epam.popcornapp.inject.AccountInterface;
import com.epam.popcornapp.model.favorite.FavoriteMovieInteractor;
import com.epam.popcornapp.model.favorite.FavoriteTvInteractor;
import com.epam.popcornapp.model.favorite.interfaces.IFavoriteMovieInteractor;
import com.epam.popcornapp.model.favorite.interfaces.IFavoriteTvInteractor;
import com.epam.popcornapp.model.rated.RatedMovieInteractor;
import com.epam.popcornapp.model.rated.RatedTvInteractor;
import com.epam.popcornapp.model.rated.interfaces.IRatedMovieInteractor;
import com.epam.popcornapp.model.rated.interfaces.IRatedTvInteractor;
import com.epam.popcornapp.model.watchlist.WatchlistMovieInteractor;
import com.epam.popcornapp.model.watchlist.WatchlistTvInteractor;
import com.epam.popcornapp.model.watchlist.interfaces.IWatchlistMovieInteractor;
import com.epam.popcornapp.model.watchlist.interfaces.IWatchlistTvInteractor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class AccountModule {

    @Provides
    @Singleton
    AccountInterface provideAccountInterface(final Retrofit retrofit) {
        return retrofit.create(AccountInterface.class);
    }

    @Provides
    @Singleton
    IRatedMovieInteractor provideRatedMovieInteractor() {
        return new RatedMovieInteractor();
    }

    @Provides
    @Singleton
    IRatedTvInteractor provideRatedTvInteractor() {
        return new RatedTvInteractor();
    }

    @Provides
    @Singleton
    IWatchlistMovieInteractor provideWatchlistMovieInteractor() {
        return new WatchlistMovieInteractor();
    }

    @Provides
    @Singleton
    IWatchlistTvInteractor provideWatchlistTvInteractor() {
        return new WatchlistTvInteractor();
    }

    @Provides
    @Singleton
    IFavoriteMovieInteractor provideFavoriteMovieInteractor() {
        return new FavoriteMovieInteractor();
    }

    @Provides
    @Singleton
    IFavoriteTvInteractor provideFavoriteTvInteractor() {
        return new FavoriteTvInteractor();
    }
}