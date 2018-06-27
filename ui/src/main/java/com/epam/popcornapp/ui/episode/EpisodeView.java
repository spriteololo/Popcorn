package com.epam.popcornapp.ui.episode;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.widget.TextView;

import com.epam.popcornapp.ui.base.InflateFrameLayout;
import com.epam.ui.R;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class EpisodeView extends InflateFrameLayout {

    private AppCompatImageView posterView;
    private TextView titleView;
    private MaterialRatingBar ratingBar;
    private TextView releaseDateView;
    private TextView overviewView;

    public EpisodeView(final Context context) {
        super(context);
    }

    public EpisodeView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public EpisodeView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onCreateView(final Context pContext, final AttributeSet pAttrs) {
        posterView = findViewById(R.id.photo_image_view);
        titleView = findViewById(R.id.title_text_view);
        ratingBar = findViewById(R.id.rating_bar);
        releaseDateView = findViewById(R.id.release_date_text_view);
        overviewView = findViewById(R.id.overview_text_view);
    }

    public void setData(final EpisodeItem item) {
        titleView.setText(item.getTitle());
        ratingBar.setRating(item.getRating() / 2);
        releaseDateView.setText(item.getReleaseDate());
        overviewView.setText(item.getOverview());
    }

    public AppCompatImageView getImageView() {
        return posterView;
    }

    @Override
    protected int getViewLayout() {
        return R.layout.view_episode;
    }
}
