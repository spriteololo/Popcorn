package com.epam.popcornapp.ui.favorite;

import android.content.Context;
import android.support.annotation.NonNull;

import com.epam.popcornapp.application.Constants;
import com.epam.popcornapp.application.Converter;
import com.epam.popcornapp.application.SettingsUtils;
import com.epam.popcornapp.pojo.favorite.movie.FavoriteMovieItem;
import com.epam.popcornapp.pojo.favorite.tv.FavoriteTvItem;
import com.epam.popcornapp.ui.tiles.credits.TileType;
import com.epam.popcornapp.ui.tiles.item.BaseTileItem;
import com.epam.popcornapp.ui.tiles.item.DescriptionTileItem;

import java.util.ArrayList;
import java.util.List;

public class FavoriteConverter {

    public static List<BaseTileItem> convertMovieToBaseTileItem(@NonNull final Context context,
                                                                @NonNull final List<FavoriteMovieItem> tiles) {
        final List<BaseTileItem> tileItems = new ArrayList<>(tiles.size());

        for (final FavoriteMovieItem movieItem : tiles) {
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
                                                             @NonNull final List<FavoriteTvItem> tiles) {
        final List<BaseTileItem> tileItems = new ArrayList<>(tiles.size());

        for (final FavoriteTvItem tvItem : tiles) {
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
