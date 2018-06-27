package com.epam.popcornapp.ui.rated;

import android.content.Context;
import android.support.annotation.NonNull;

import com.epam.popcornapp.application.Constants;
import com.epam.popcornapp.application.SettingsUtils;
import com.epam.popcornapp.pojo.rated.movie.RatedMovieItem;
import com.epam.popcornapp.pojo.rated.tv.RatedTvItem;
import com.epam.popcornapp.ui.tiles.credits.TileType;
import com.epam.popcornapp.ui.tiles.item.BaseTileItem;
import com.epam.popcornapp.ui.tiles.item.RatingTileItem;

import java.util.ArrayList;
import java.util.List;

public class RatedConverter {

    public static List<BaseTileItem> convertMovieToBaseTileItem(@NonNull final Context context,
                                                                @NonNull final List<RatedMovieItem> tiles) {
        final List<BaseTileItem> tileItems = new ArrayList<>(tiles.size());

        for (final RatedMovieItem movieItem : tiles) {
            final RatingTileItem ratingTileItem = new RatingTileItem();

            ratingTileItem.setId(movieItem.getId());
            ratingTileItem.setImagePath(Constants.BASE_IMAGE_URL
                    + SettingsUtils.getBackdropSize(context) + movieItem.getPosterPath());
            ratingTileItem.setName(movieItem.getTitle());
            ratingTileItem.setRating(movieItem.getRating() / 2.0f);
            ratingTileItem.setType(TileType.MOVIE);

            tileItems.add(ratingTileItem);
        }

        return tileItems;
    }

    public static List<BaseTileItem> convertTvToBaseTileItem(@NonNull final Context context,
                                                             @NonNull final List<RatedTvItem> tiles) {
        final List<BaseTileItem> tileItems = new ArrayList<>(tiles.size());

        for (final RatedTvItem tvItem : tiles) {
            final RatingTileItem ratingTileItem = new RatingTileItem();

            ratingTileItem.setId(tvItem.getId());
            ratingTileItem.setImagePath(Constants.BASE_IMAGE_URL
                    + SettingsUtils.getBackdropSize(context) + tvItem.getPosterPath());
            ratingTileItem.setName(tvItem.getOriginalName());
            ratingTileItem.setRating(tvItem.getRating() / 2.0f);
            ratingTileItem.setType(TileType.TV);

            tileItems.add(ratingTileItem);
        }

        return tileItems;
    }
}
