package com.epam.popcornapp.ui.tiles;

import android.content.Context;
import android.util.AttributeSet;

import com.epam.ui.R;

public class GalleryPortraitTile extends BaseTile {

    public GalleryPortraitTile(final Context context) {
        super(context);
    }

    public GalleryPortraitTile(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public GalleryPortraitTile(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onCreateView(final Context pContext, final AttributeSet pAttrs) {
        super.onCreateView(pContext, pAttrs);
    }

    @Override
    protected int getViewLayout() {
        return R.layout.view_tile_gallery_portrait;
    }
}
