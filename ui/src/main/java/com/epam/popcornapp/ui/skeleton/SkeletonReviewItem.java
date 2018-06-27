package com.epam.popcornapp.ui.skeleton;

import android.content.Context;
import android.util.AttributeSet;

import com.epam.popcornapp.ui.base.InflateLinearLayout;
import com.epam.ui.R;

public class SkeletonReviewItem extends InflateLinearLayout {

    public SkeletonReviewItem(final Context context) {
        super(context);
    }

    public SkeletonReviewItem(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public SkeletonReviewItem(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onCreateView(final Context pContext, final AttributeSet pAttrs) {

    }

    @Override
    protected int getViewLayout() {
        return R.layout.view_skeleton_review_item;
    }
}
