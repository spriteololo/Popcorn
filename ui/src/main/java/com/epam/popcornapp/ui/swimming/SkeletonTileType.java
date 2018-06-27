package com.epam.popcornapp.ui.swimming;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@IntDef({
        SkeletonTileType.EMPTY,
        SkeletonTileType.GALLERY_PORTRAIT,
        SkeletonTileType.GALLERY_LANDSCAPE,
        SkeletonTileType.DESCRIPTION,
        SkeletonTileType.RATING,
        SkeletonTileType.LIST
})

public @interface SkeletonTileType {
    int EMPTY = 0;
    int GALLERY_PORTRAIT = 1;
    int GALLERY_LANDSCAPE = 2;
    int DESCRIPTION = 3;
    int RATING = 4;
    int LIST = 5;
}