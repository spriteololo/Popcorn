package com.epam.popcornapp.ui.utils;

import android.support.annotation.NonNull;
import android.support.v4.widget.NestedScrollView;

public abstract class TitleCardScrollListener implements NestedScrollView.OnScrollChangeListener {

    private boolean visible = true;
    private boolean viewVisibility = true;

    @Override
    public void onScrollChange(@NonNull final NestedScrollView nestedScrollView, final int scrollX,
                               final int scrollY, final int oldScrollX, final int oldScrollY) {
        float transparencyValue = (float) (scrollY + 50) / 1000;

        if (scrollY > 0 && visible) {
            visible = false;
            changeVisibility(false);
        }

        if (scrollY == 0) {
            transparencyValue = 0;
            visible = true;
            changeVisibility(true);
        }

        if (transparencyValue > 1) {
            transparencyValue = 1;
        }

        if (oldScrollY != 0 && scrollY > oldScrollY && viewVisibility) {
            viewVisibility = false;
            changeViewVisibility(false);
        } else if (scrollY < oldScrollY && !viewVisibility) {
            viewVisibility = true;
            changeViewVisibility(true);
        }

        changeTransparency(transparencyValue);
    }

    public abstract void changeTransparency(final float transparencyValue);

    public abstract void changeVisibility(final boolean visible);

    public abstract void changeViewVisibility(final boolean isVisible);
}
