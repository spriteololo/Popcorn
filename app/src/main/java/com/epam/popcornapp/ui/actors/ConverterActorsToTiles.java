package com.epam.popcornapp.ui.actors;

import android.content.Context;
import android.support.annotation.NonNull;

import com.epam.popcornapp.application.Constants;
import com.epam.popcornapp.application.SettingsUtils;
import com.epam.popcornapp.pojo.actors.Actor;
import com.epam.popcornapp.pojo.actors.KnownFor;
import com.epam.popcornapp.pojo.actors.KnownForLink;
import com.epam.popcornapp.ui.tiles.credits.TileType;
import com.epam.popcornapp.ui.tiles.item.BaseTileItem;
import com.epam.popcornapp.ui.tiles.item.DescriptionTileItem;

import java.util.ArrayList;
import java.util.List;

class ConverterActorsToTiles {

    static List<BaseTileItem> convertActorsToTile(@NonNull final Context context,
                                                  @NonNull final List<Actor> results) {
        final List<BaseTileItem> tiles = new ArrayList<>();

        for (final Actor result : results) {
            final DescriptionTileItem tileItem = new DescriptionTileItem();
            tileItem.setId(result.getId());
            tileItem.setName(result.getName());
            tileItem.setType(TileType.ACTOR);
            tileItem.setImagePath(Constants.BASE_IMAGE_URL + SettingsUtils.getBackdropSize(context)
                    + result.getProfilePath());
            tileItem.setDescription(getMoviesTitles(result));

            tiles.add(tileItem);
        }

        return tiles;
    }

    private static String getMoviesTitles(final Actor actor) {
        final List<KnownForLink> knfList = actor.getKnownForLink();
        final StringBuilder resBuilder = new StringBuilder();
        String res = "";

        if (knfList == null || knfList.isEmpty()) {
            return getMoviesTitlesAgain(actor.getKnownFor());
        }

        for (final KnownForLink knf : knfList) {

            if (knf.getMediaType().equals(TileType.MOVIE)) {

                if (knf.getKnownForMovie().getTitle() != null)
                    resBuilder.append(knf.getKnownForMovie().getTitle()).append(", ");
            }

            if (knf.getMediaType().equals(TileType.TV)) {

                if (knf.getKnownForTv().getName() != null)
                    resBuilder.append(knf.getKnownForTv().getName()).append(", ");
            }
        }

        if (resBuilder.length() >= 2) {
            res = resBuilder.substring(0, resBuilder.length() - 3);
        }

        return res;
    }

    private static String getMoviesTitlesAgain(final List<KnownFor> knownFor) {

        String res = "";
        if (knownFor == null || knownFor.isEmpty()) {
            return res;
        }
        final StringBuilder stringBuilder = new StringBuilder();
        for (final KnownFor knf : knownFor) {

            if (knf.getMediaType().equals(TileType.MOVIE)) {

                if (knf.getTitle() != null) {
                    stringBuilder.append(knf.getTitle()).append(", ");
                }
            }

            if (knf.getMediaType().equals(TileType.TV)) {

                if (knf.getName() != null)
                    stringBuilder.append(knf.getName()).append(", ");
            }
        }

        if (stringBuilder.length() >= 2) {
            res = stringBuilder.substring(0, stringBuilder.length() - 2);
        }

        return res;
    }
}
