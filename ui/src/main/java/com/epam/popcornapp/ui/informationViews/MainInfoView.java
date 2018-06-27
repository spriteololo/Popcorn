package com.epam.popcornapp.ui.informationViews;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.epam.popcornapp.ui.base.InflateConstraintLayout;
import com.epam.ui.R;

import at.blogc.android.views.ExpandableTextView;

public class MainInfoView extends InflateConstraintLayout {

    private TextView mainTextView;
    private TextView leftTextView;
    private TextView rightTextView;
    private TextView ratingView;
    private TextView voteCountView;
    private View mainInfoLayout;
    private View starView;
    private View dividerView;
    private ExpandableTextView overviewView;

    public MainInfoView(final Context context) {
        super(context);
    }

    public MainInfoView(final Context context, @Nullable final AttributeSet attrs) {
        super(context, attrs);
    }

    public MainInfoView(final Context context, @Nullable final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onCreateView(final Context pContext, final AttributeSet pAttrs) {
        mainTextView = findViewById(R.id.view_main_info_main_text_view);
        leftTextView = findViewById(R.id.view_main_info_left_text_view);
        rightTextView = findViewById(R.id.view_main_info_right_text_view);
        ratingView = findViewById(R.id.view_main_info_rating_text_view);
        voteCountView = findViewById(R.id.view_main_info_vote_count_text_view);
        starView = findViewById(R.id.view_main_info_star_view);
        mainInfoLayout = findViewById(R.id.view_main_info_view_linear_layout);
        dividerView = findViewById(R.id.view_main_info_divider_view);
        overviewView = findViewById(R.id.view_main_info_overview_text_view);

        overviewView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                overviewView.toggle();
            }
        });
    }

    @Override
    protected int getViewLayout() {
        return R.layout.view_main_info;
    }

    public void setData(final MainInfoViewItem item, @MainInfoViewType final int viewType) {
        setViewSetting(viewType);

        setRating(item.getRating());
        setVoteCount(item.getVoteCount());
        setOverview(item.getDescription());
        setInfo(item.getMainInfo(), item.getLeftAdditionalInfo(), item.getRightAdditionalInfo());

        if (overviewView.isExpanded()) {
            overviewView.collapse();
        }
    }

    private void setViewSetting(@MainInfoViewType final int viewType) {
        switch (viewType) {
            case MainInfoViewType.SMALL_TITLE:
                mainTextView.setIncludeFontPadding(true);

                break;
            case MainInfoViewType.BIG_TITLE:
                setBigTitle();

                break;
            case MainInfoViewType.NO_RATING_BIG_TITLE:
                setBigTitle();

                starView.setVisibility(GONE);
                ratingView.setVisibility(GONE);
                voteCountView.setVisibility(GONE);

                break;
            default:
                break;
        }
    }

    private void setBigTitle() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            mainTextView.setTextAppearance(getContext(), R.style.TextAppearance_Display0_Primary);
        } else {
            mainTextView.setTextAppearance(R.style.TextAppearance_Display0_Primary);
        }
    }

    private void setInfo(@NonNull final String mainText,
                         @NonNull final String leftValue, @NonNull final String rightValue) {
        checkInfo(leftValue.isEmpty(), rightValue.isEmpty());

        leftTextView.setText(leftValue);
        rightTextView.setText(rightValue);
        mainTextView.setText(mainText);
    }

    private void checkInfo(final boolean isLeftValueEmpty, final boolean isRightValueEmpty) {
        mainInfoLayout.setVisibility(isLeftValueEmpty && isRightValueEmpty ? voteCountView.getVisibility() : VISIBLE);
        dividerView.setVisibility(isLeftValueEmpty || isRightValueEmpty ? GONE : VISIBLE);
    }

    private void setRating(@NonNull final String value) {
        final String emptyValue = "0.0";

        ratingView.setText(!value.equals(emptyValue) ? value : "");
    }

    private void setVoteCount(@NonNull final String value) {
        starView.setVisibility(value.isEmpty() ? GONE : VISIBLE);

        voteCountView.setText(value);
    }

    private void setOverview(@NonNull final String value) {
        overviewView.setVisibility(value.isEmpty() ? GONE : VISIBLE);

        overviewView.setText(value);
    }
}