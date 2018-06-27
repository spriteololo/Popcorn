package com.epam.popcornapp.ui.rateView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.epam.popcornapp.glide.GlideApp;
import com.epam.popcornapp.ui.base.InflateLinearLayout;
import com.epam.ui.R;

public class RateView extends InflateLinearLayout {

    private ImageView profileImage;
    private TextView message;
    private RatingBar ratingBar;
    private Button rateButton;

    private boolean isRated;
    private String titleWord;

    private RateViewListener listener;

    public RateView(final Context context) {
        super(context);
    }

    public RateView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public RateView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onCreateView(final Context pContext, final AttributeSet pAttrs) {
        setOrientation(VERTICAL);

        profileImage = findViewById(R.id.rate_profile_image_view);
        message = findViewById(R.id.rate_message_text_view);
        ratingBar = findViewById(R.id.rate_rating_bar);
        rateButton = findViewById(R.id.rate_button);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(final RatingBar ratingBar, final float v, final boolean b) {
                final float minValue = 0.5f;

                if (v < minValue) {
                    disableButton();
                } else if (!rateButton.isEnabled()) {
                    enableButton();
                }
            }
        });

        rateButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (listener == null) {
                    return;
                }

                if (isRated) {
                    listener.deleteRating();
                } else {
                    listener.setRating(ratingBar.getRating());
                }

                blockRateView();
            }
        });
    }

    @Override
    protected int getViewLayout() {
        return R.layout.view_rate;
    }

    public void setListener(@NonNull final RateViewListener rateViewListener) {
        listener = rateViewListener;
    }

    public void setData(final String photoPath, final String titleWord, final float ratingValue) {
        this.titleWord = titleWord;

        GlideApp.with(this)
                .load(photoPath)
                .defaultOptions()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .error(R.drawable.icon_rate_view_account)
                .dontAnimate()
                .into(profileImage);

        if (ratingValue == 0.0f) {
            setEmptyRateView();
        } else {
            setRateView(ratingValue);
        }
    }

    public void update(final boolean isSuccess, final float rating) {
        unblockRateView();

        if (!isSuccess) {
            return;
        }

        if (isRated) {
            setEmptyRateView();
        } else {
            setRateView(rating);
        }
    }

    private void setEmptyRateView() {
        isRated = false;

        message.setText(getResources().getString(R.string.rate_this, titleWord));
        rateButton.setText(getResources().getString(R.string.rate));
        ratingBar.setRating(0.0f);
        ratingBar.setIsIndicator(false);

        disableButton();
    }

    private void setRateView(final float ratingValue) {
        isRated = true;

        message.setText(getResources().getString(R.string.already_rated));
        rateButton.setText(getResources().getString(R.string.delete_rating));
        ratingBar.setRating(ratingValue);
        ratingBar.setIsIndicator(true);
    }

    private void blockRateView() {
        ratingBar.setIsIndicator(true);

        disableButton();
    }

    private void unblockRateView() {
        ratingBar.setIsIndicator(false);

        enableButton();
    }

    private void enableButton() {
        rateButton.setTextColor(getResources().getColor(R.color.color_accent));
        rateButton.setEnabled(true);
    }

    private void disableButton() {
        rateButton.setTextColor(getResources().getColor(R.color.color_text_disable));
        rateButton.setEnabled(false);
    }

    public interface RateViewListener {

        void setRating(float rating);

        void deleteRating();
    }
}
