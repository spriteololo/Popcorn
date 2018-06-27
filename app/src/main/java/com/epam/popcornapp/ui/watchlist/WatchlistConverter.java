package com.epam.popcornapp.ui.watchlist;

import android.content.Context;
import android.support.annotation.NonNull;

import com.epam.popcornapp.application.Constants;
import com.epam.popcornapp.application.Converter;
import com.epam.popcornapp.application.SettingsUtils;
import com.epam.popcornapp.pojo.watchlist.movie.WatchlistMovieItem;
import com.epam.popcornapp.pojo.watchlist.tv.WatchlistTvItem;
import com.epam.popcornapp.ui.tiles.credits.TileType;
import com.epam.popcornapp.ui.tiles.item.BaseTileItem;
import com.epam.popcornapp.ui.tiles.item.DescriptionTileItem;

import java.util.ArrayList;
import java.util.List;

public class WatchlistConverter {

    public static List<BaseTileItem> convertMovieToBaseTileItem(@NonNull final Context context,
                                                                @NonNull final List<WatchlistMovieItem> tiles) {
        final List<BaseTileItem> tileItems = new ArrayList<>(tiles.size());

        for (final WatchlistMovieItem movieItem : tiles) {
            final DescriptionTileItem descriptionTileItem = new DescriptionTileItem();

            descriptionTileItem.setId(movieItem.getId());
            descriptionTileItem.setImagePath(Constants.BASE_IMAGE_URL
                    + SettingsUtils.getBackdropSize(context) + movieItem.getPosterPath());
            descriptionTileItem.setDescription(Converter.convertToYear(movieItem.getReleaseDate()));
            descriptionTileItem.setName(movieItem.getTitle());
            descriptionTileItem.setType(TileType.MOVIE);

            tileItems.add(descriptionTileItem);
        }

        return tileItems;
    }

    public static List<BaseTileItem> convertTvToBaseTileItem(@NonNull final Context context,
                                                             @NonNull final List<WatchlistTvItem> tiles) {
        final List<BaseTileItem> tileItems = new ArrayList<>(tiles.size());

        for (final WatchlistTvItem tvItem : tiles) {
            final DescriptionTileItem descriptionTileItem = new DescriptionTileItem();

            descriptionTileItem.setId(tvItem.getId());
            descriptionTileItem.setImagePath(Constants.BASE_IMAGE_URL
                    + SettingsUtils.getBackdropSize(context) + tvItem.getPosterPath());
            descriptionTileItem.setDescription(Converter.convertToYear(tvItem.getFirstAirDate()));
            descriptionTileItem.setName(tvItem.getOriginalName());
            descriptionTileItem.setType(TileType.TV);

            tileItems.add(descriptionTileItem);
        }

        return tileItems;
    }
}
