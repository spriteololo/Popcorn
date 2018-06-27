package com.epam.popcornapp.ui.tiles;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.epam.ui.R;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class RatingTile extends BaseTile {

    private TextView nameTextView;
    private MaterialRatingBar ratingBar;

    public RatingTile(final Context context) {
        super(context);
    }

    public RatingTile(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public RatingTile(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onCreateView(final Context pContext, final AttributeSet pAttrs) {
        super.onCreateView(pContext, pAttrs);
        nameTextView = findViewById(R.id.rating_tile_name_text_view);
        ratingBar = findViewById(R.id.rating_tile_rating_bar);
    }

    @Override
    protected int getViewLayout() {
        return R.layout.view_tile_rating;
    }

    public void setName(final String name) {
        this.nameTextView.setText(name);
    }

    public void setRating(@NonNull final Float rating) {
        this.ratingBar.setRating(rating);
    }

    public float getRating() {
        return this.ratingBar.getRating();
    }

    public ImageView getImageView() {
        return super.getPhotoView();
    }
}
