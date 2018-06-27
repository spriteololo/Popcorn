package com.epam.popcornapp.ui.splash;

import com.epam.popcornapp.pojo.genres.GenresResult;
import com.epam.popcornapp.pojo.splash_settings.Config;

class SplashData {

    private Config config;
    private GenresResult genresResult;
    private GenresResult genresTvResult;

    GenresResult getGenresTvResult() {
        return genresTvResult;
    }

    void setGenresTvResult(final GenresResult genresTvResult) {
        this.genresTvResult = genresTvResult;
    }

    Config getConfig() {
        return config;
    }

    void setConfig(final Config config) {
        this.config = config;
    }

    GenresResult getGenresResult() {
        return genresResult;
    }

    void setGenresResult(final GenresResult genresResult) {
        this.genresResult = genresResult;
    }
}
