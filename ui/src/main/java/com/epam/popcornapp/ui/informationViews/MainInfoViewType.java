package com.epam.popcornapp.ui.informationViews;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@IntDef({
        MainInfoViewType.SMALL_TITLE,
        MainInfoViewType.BIG_TITLE,
        MainInfoViewType.NO_RATING_BIG_TITLE
})

public @interface MainInfoViewType {
    int SMALL_TITLE = 1;
    int BIG_TITLE = 2;
    int NO_RATING_BIG_TITLE = 3;
}