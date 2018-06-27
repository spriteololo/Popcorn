package com.epam.popcornapp.ui.mainViews;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.epam.popcornapp.glide.GlideApp;
import com.epam.popcornapp.ui.base.InflateConstraintLayout;
import com.epam.ui.R;
import com.github.mmin18.widget.RealtimeBlurView;

public class MainImageView extends InflateConstraintLayout {

    private ViewGroup photoLayout;
    private ImageView photoView;
    private RealtimeBlurView realtimeBlurView;
    private View shadowTopView;
    private View shadowBottomView;

    private final int ANIM_DURATION = 650;

    private int errorIconId = 0;

    public MainImageView(final Context context) {
        super(context);
    }

    public MainImageView(final Context context, @Nullable final AttributeSet attrs) {
        super(context, attrs);
    }

    public MainImageView(final Context context, @Nullable final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onCreateView(final Context pContext, final AttributeSet pAttrs) {
        photoLayout = findViewById(R.id.photo_layout);
        photoView = findViewById(R.id.photo_image_view);
        realtimeBlurView = findViewById(R.id.real_time_blur_view);
        shadowTopView = findViewById(R.id.shadow_top_view);
        shadowBottomView = findViewById(R.id.shadow_bottom_view);
    }

    @Override
    protected int getViewLayout() {
        return R.layout.view_main_image;
    }

    public void setImage(@NonNull final String photoPath) {
        GlideApp.with(this)
                .load(photoPath)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .priority(Priority.HIGH)
                .error(errorIconId)
                .transition(DrawableTransitionOptions.withCrossFade(ANIM_DURATION))
                .into(photoView);
    }

    public void setTransparency(final float transparencyValue) {
        realtimeBlurView.setAlpha(transparencyValue);
    }

    public void setViewSetting(@MainImageType final int imageType) {
        switch (imageType) {
            case MainImageType.PERSON:
                errorIconId = R.drawable.icon_person;

                break;
            case MainImageType.EPISODE:
                ConstraintLayout.LayoutParams constraintLayoutParams =
                        (ConstraintLayout.LayoutParams) photoLayout.getLayoutParams();

                constraintLayoutParams.dimensionRatio = "9:5";
                photoLayout.setLayoutParams(constraintLayoutParams);

                constraintLayoutParams =
                        (ConstraintLayout.LayoutParams) shadowBottomView.getLayoutParams();

                constraintLayoutParams.dimensionRatio = "14:5";
                shadowBottomView.setLayoutParams(constraintLayoutParams);

                shadowTopView.setVisibility(INVISIBLE);

                break;
            case MainImageType.OTHER:
                break;
            default:
                break;
        }
    }
}
