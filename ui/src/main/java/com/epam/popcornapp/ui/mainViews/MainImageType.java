package com.epam.popcornapp.ui.mainViews;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@IntDef({
        MainImageType.OTHER,
        MainImageType.PERSON,
        MainImageType.EPISODE
})
public @interface MainImageType {
    int OTHER = 0;
    int PERSON = 1;
    int EPISODE = 2;
}