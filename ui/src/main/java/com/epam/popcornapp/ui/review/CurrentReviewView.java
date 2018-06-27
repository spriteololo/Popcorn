package com.epam.popcornapp.ui.review;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.epam.popcornapp.listeners.RecyclerViewListeners;
import com.epam.popcornapp.listeners.RecyclerViewLoadMore;
import com.epam.popcornapp.ui.base.InflateLinearLayout;
import com.epam.ui.R;

import java.util.ArrayList;
import java.util.List;

public class CurrentReviewView extends InflateLinearLayout {

    private RecyclerView reviewRecyclerView;
    private ReviewAdapter reviewAdapter;

    public CurrentReviewView(final Context context) {
        super(context);
    }

    public CurrentReviewView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public CurrentReviewView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setOnScroll(final RecyclerViewListeners.OnLoadMoreListener l) {

        reviewRecyclerView.addOnScrollListener(new RecyclerViewLoadMore(l,
                reviewRecyclerView.getLayoutManager(), 1));
    }

    @Override
    protected void onCreateView(final Context pContext, final AttributeSet pAttrs) {
        setOrientation(VERTICAL);
        reviewRecyclerView = findViewById(R.id.view_movie_review_recycler_view);
        initAdapter(pContext);
    }

    private void initAdapter(@NonNull final Context pContext) {
        final List<ReviewItem> list = new ArrayList<>();
        reviewAdapter = new ReviewAdapter(list);

        reviewRecyclerView.setAdapter(reviewAdapter);

        reviewRecyclerView.setLayoutManager(new LinearLayoutManager(
                pContext, LinearLayoutManager.VERTICAL, false) {

            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
    }

    @Override
    protected int getViewLayout() {
        return R.layout.view_current_review;
    }

    public void setReviews(@NonNull final List<ReviewItem> reviewItemList, final boolean isBig) {
        if (isBig) {
            reviewAdapter.withReviews(reviewItemList);
        } else {
            reviewAdapter.setReviews(reviewItemList);
        }
    }

    public void clearReviews() {
        reviewAdapter.clearReviews();
    }

    public List<ReviewItem> getData() {
        return reviewAdapter.getFullList();
    }
}
