package com.epam.popcornapp.ui.lists.base;

import android.content.Context;
import android.support.annotation.NonNull;

import com.epam.popcornapp.R;
import com.epam.popcornapp.application.Constants;
import com.epam.popcornapp.application.Converter;
import com.epam.popcornapp.application.SettingsUtils;
import com.epam.popcornapp.pojo.lists.ListItem;
import com.epam.popcornapp.pojo.lists.listDetail.MediaItem;
import com.epam.popcornapp.ui.search.SearchDetail;
import com.epam.popcornapp.ui.tiles.credits.TileType;
import com.epam.popcornapp.ui.tiles.item.BaseTileItem;
import com.epam.popcornapp.ui.tiles.item.DescriptionTileItem;

import java.util.ArrayList;
import java.util.List;

public class ListsConverter {

    public static List<BaseTileItem> convertListsItemsToBaseTileItems(@NonNull final List<ListItem> itemList,
                                                                      @NonNull final Context context) {
        final List<BaseTileItem> baseTileItems = new ArrayList<>(itemList.size());

        for (final ListItem listItem : itemList) {
            baseTileItems.add(convertListItemToBaseTileItem(listItem, context));
        }

        return baseTileItems;
    }

    public static BaseTileItem convertListItemToBaseTileItem(@NonNull final ListItem listItem,
                                                             @NonNull final Context context) {
        final int itemCount = listItem.getItemCount();

        return new DescriptionTileItem(
                listItem.getId(),
                listItem.getPosterPath(),
                listItem.getName(),
                context.getResources().getQuantityString(R.plurals.item_count, itemCount, itemCount));
    }

    public static List<BaseTileItem> convertMediaItemsToBaseTileItems(@NonNull final List<MediaItem> mediaItemList,
                                                                      @NonNull final Context context) {
        final List<BaseTileItem> baseTileItems = new ArrayList<>(mediaItemList.size());

        for (final MediaItem mediaItem : mediaItemList) {
            final DescriptionTileItem descriptionTileItem = new DescriptionTileItem();

            descriptionTileItem.setId(mediaItem.getId());
            descriptionTileItem.setImagePath(Constants.BASE_IMAGE_URL + SettingsUtils.getBackdropSize(context)
                    + mediaItem.getPosterPath());

            if (mediaItem.getMediaType().equals(TileType.MOVIE)) {
                descriptionTileItem.setName(mediaItem.getTitle());
                descriptionTileItem.setDescription(Converter.convertToFullDate(mediaItem.getReleaseDate()));
                descriptionTileItem.setType(TileType.MOVIE);
            } else {
                descriptionTileItem.setName(mediaItem.getName());
                descriptionTileItem.setDescription(Converter.convertToFullDate(mediaItem.getFirstAirDate()));
                descriptionTileItem.setType(TileType.TV);
            }

            baseTileItems.add(descriptionTileItem);
        }

        return baseTileItems;
    }

    public static BaseTileItem convertSearchDetailToBaseTileItem(@NonNull final SearchDetail searchDetail) {
        final DescriptionTileItem descriptionTileItem = new DescriptionTileItem(
                searchDetail.getId(),
                searchDetail.getImagePath(),
                searchDetail.getTitle(),
                searchDetail.getDescription());

        descriptionTileItem.setType(TileType.MOVIE);

        return descriptionTileItem;
    }
}
