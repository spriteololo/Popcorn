package com.epam.popcornapp.ui.tiles;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.epam.popcornapp.ui.base.InflateConstraintLayout;
import com.epam.ui.R;

public class BaseTile extends InflateConstraintLayout {

    private ImageView photoView;

    public BaseTile(final Context context) {
        super(context);
    }

    public BaseTile(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseTile(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onCreateView(final Context pContext, final AttributeSet pAttrs) {
        photoView = findViewById(R.id.base_tile_image_view);
    }

    @Override
    protected int getViewLayout() {
        return R.layout.view_tile_base;
    }

    public ImageView getPhotoView() {
        return this.photoView;
    }
}
