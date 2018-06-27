package com.epam.popcornapp.ui.tiles.credits;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.epam.popcornapp.ui.tiles.credits.TileType.ACTOR;
import static com.epam.popcornapp.ui.tiles.credits.TileType.GALLERY;
import static com.epam.popcornapp.ui.tiles.credits.TileType.MOVIE;
import static com.epam.popcornapp.ui.tiles.credits.TileType.TV;

@Retention(RetentionPolicy.SOURCE)
@StringDef({
        MOVIE,
        TV,
        ACTOR,
        GALLERY
})
public @interface TileType {
    String MOVIE = "movie";
    String TV = "tv";
    String ACTOR = "person";
    String GALLERY = "gallery";
}
