package com.epam.popcornapp.pojo.actors;

import com.epam.popcornapp.pojo.movies.details.MovieDetailResult;
import com.epam.popcornapp.pojo.tv.TvDetails;
import com.epam.popcornapp.ui.tiles.credits.TileType;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class KnownForLink extends RealmObject{

    @PrimaryKey
    private int id;

    private @TileType String mediaType;

    private MovieDetailResult knownForMovie;

    private TvDetails knownForTv;

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(final String mediaType) {
        this.mediaType = mediaType;
    }

    public MovieDetailResult getKnownForMovie() {
        return knownForMovie;
    }

    public void setKnownForMovie(final MovieDetailResult knownForMovie) {
        this.knownForMovie = knownForMovie;
    }

    public TvDetails getKnownForTv() {
        return knownForTv;
    }

    public void setKnownForTv(final TvDetails knownForTv) {
        this.knownForTv = knownForTv;
    }
}
