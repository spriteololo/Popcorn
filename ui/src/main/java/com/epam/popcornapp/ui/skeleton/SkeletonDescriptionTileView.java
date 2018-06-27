package com.epam.popcornapp.ui.skeleton;

import android.content.Context;
import android.util.AttributeSet;

import com.epam.popcornapp.ui.base.InflateConstraintLayout;
import com.epam.ui.R;

public class SkeletonDescriptionTileView extends InflateConstraintLayout {

    public SkeletonDescriptionTileView(final Context context) {
        super(context);
    }

    public SkeletonDescriptionTileView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public SkeletonDescriptionTileView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onCreateView(final Context context, final AttributeSet attrs) {

    }

    @Override
    protected int getViewLayout() {
        return R.layout.view_skeleton_tile_description;
    }
}
