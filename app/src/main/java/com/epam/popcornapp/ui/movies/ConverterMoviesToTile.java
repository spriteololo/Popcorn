package com.epam.popcornapp.ui.movies;

import android.content.Context;
import android.support.annotation.NonNull;

import com.epam.popcornapp.application.Constants;
import com.epam.popcornapp.application.Converter;
import com.epam.popcornapp.application.SettingsUtils;
import com.epam.popcornapp.pojo.movies.details.MovieDetailResult;
import com.epam.popcornapp.ui.tiles.credits.TileType;
import com.epam.popcornapp.ui.tiles.item.BaseTileItem;
import com.epam.popcornapp.ui.tiles.item.DescriptionTileItem;

import java.util.ArrayList;
import java.util.List;

class ConverterMoviesToTile {

    static List<BaseTileItem> convertMoviesToTiles(@NonNull final Context context,
                                                   @NonNull final List<MovieDetailResult> movies) {
        final List<BaseTileItem> tileItems = new ArrayList<>();

        for (final MovieDetailResult details : movies) {
            final DescriptionTileItem tileItem = new DescriptionTileItem();
            final StringBuilder builder = new StringBuilder();

            tileItem.setId(details.getId());
            tileItem.setName(details.getTitle());
            tileItem.setType(TileType.MOVIE);
            tileItem.setImagePath(Constants.BASE_IMAGE_URL + SettingsUtils.getBackdropSize(context)
                    + details.getPosterPath());
            final String year = Converter.convertToYear(details.getReleaseDate());

            if (!year.equals("null")) {
                builder.append(year);
            }

            if (details.getGenres() != null && !details.getGenres().isEmpty()) {
                if (builder.length() > 0) {
                    builder.append(", ");
                }

                builder.append(details.getGenres().get(0).getName());
            }

            tileItem.setDescription(builder.toString());
            tileItems.add(tileItem);
        }

        return tileItems;
    }
}
