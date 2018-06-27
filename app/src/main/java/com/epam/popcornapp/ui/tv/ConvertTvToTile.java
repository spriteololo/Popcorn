package com.epam.popcornapp.ui.tv;

import android.content.Context;
import android.support.annotation.NonNull;

import com.epam.popcornapp.application.Constants;
import com.epam.popcornapp.application.Converter;
import com.epam.popcornapp.application.SettingsUtils;
import com.epam.popcornapp.pojo.tv.TvDetails;
import com.epam.popcornapp.ui.tiles.credits.TileType;
import com.epam.popcornapp.ui.tiles.item.BaseTileItem;
import com.epam.popcornapp.ui.tiles.item.DescriptionTileItem;

import java.util.ArrayList;
import java.util.List;

public class ConvertTvToTile {
    public static List<BaseTileItem> convertTvToTiles(@NonNull final Context context,
                                                      @NonNull final List<TvDetails> popularTvShows) {
        final List<BaseTileItem> tileItems = new ArrayList<>();

        for (final TvDetails tvDetails : popularTvShows) {
            final DescriptionTileItem tileItem = new DescriptionTileItem();
            tileItem.setId(tvDetails.getId());
            tileItem.setName(tvDetails.getName());
            tileItem.setType(TileType.TV);
            tileItem.setImagePath(Constants.BASE_IMAGE_URL + SettingsUtils.getBackdropSize(context)
                    + tvDetails.getPosterPath());
            final String year = Converter.convertToYear(tvDetails.getFirstAirDate());
            final StringBuilder builder = new StringBuilder();

            if (!year.equals("null")) {
                builder.append(year);
            }

            if (tvDetails.getGenres() != null && !tvDetails.getGenres().isEmpty()) {
                if (builder.length() > 0) {
                    builder.append(", ");
                }

                builder.append(tvDetails.getGenres().get(0).getName());
            }

            tileItem.setDescription(builder.toString());
            tileItems.add(tileItem);
        }

        return tileItems;
    }
}
