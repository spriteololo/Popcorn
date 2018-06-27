package com.epam.popcornapp.ui.review;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.epam.popcornapp.R;
import com.epam.popcornapp.application.BaseApplication;
import com.epam.popcornapp.application.Converter;
import com.epam.popcornapp.listeners.RecyclerViewListeners;
import com.epam.popcornapp.listeners.RecyclerViewLoadMore;
import com.epam.popcornapp.pojo.movies.reviews.MovieResultRev;
import com.epam.popcornapp.ui.all.base.BaseMvpActivity;
import com.epam.popcornapp.ui.home.PopcornAppNavigator;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.NavigatorHolder;

public class ReviewActivity extends BaseMvpActivity
        implements ReviewPresenterView,
        RecyclerViewListeners.OnLoadMoreListener,
        SwipeRefreshLayout.OnRefreshListener {

    @Inject
    NavigatorHolder navigatorHolder;

    @InjectPresenter
    ReviewPresenter reviewPresenter;

    @BindView(R.id.blur_toolbar)
    Toolbar toolBar;

    @BindView(R.id.activity_review_swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.review_recycler_view)
    RecyclerView reviewRecyclerView;

    private ReviewAdapter reviewAdapter;

    private int id;

    private final Navigator navigator = new PopcornAppNavigator(ReviewActivity.this);

    public static Intent start(final Context context, final List<ReviewItem> data) {

        final int id = Integer.parseInt(data.get(data.size() - 1).getId());
        data.remove(data.size() - 1);

        final Intent intent = new Intent(context, ReviewActivity.class);
        intent.putParcelableArrayListExtra("data", new ArrayList<>(data));
        intent.putExtra("id", id);

        return intent;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        BaseApplication.getApplicationComponent().inject(this);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_review);
    }

    @Override
    protected void onResume() {
        super.onResume();

        navigatorHolder.setNavigator(navigator);
    }

    @Override
    protected void onPause() {
        navigatorHolder.removeNavigator();

        super.onPause();
    }


    @Override
    protected void onViewsBinded() {
        setToolbar(toolBar, getString(R.string.reviews), true);

        toolBar.setNavigationIcon(R.drawable.icon_cross_back);

        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                finish();
            }
        });

        reviewRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        reviewRecyclerView.addOnScrollListener(new RecyclerViewLoadMore(this,
                reviewRecyclerView.getLayoutManager(), 1));

        final ArrayList<ReviewItem> itemArrayList =
                getIntent().getParcelableArrayListExtra("data");
        final List<ReviewItem> list = new ArrayList<>(itemArrayList);

        reviewAdapter = new ReviewAdapter(list);
        reviewRecyclerView.setAdapter(reviewAdapter);

        id = getIntent().getIntExtra("id", 0);

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setProgressViewOffset(true, 100, 200);
    }


    @Override
    public void onRefresh() {
        reviewAdapter.clearReviews();
        reviewPresenter.setCurrentPage(id, 1);
    }

    @Override
    public void onLoadMore(final int nextPage) {
        reviewPresenter.setCurrentPage(id, nextPage);
    }

    @Override
    public void setData(final List<MovieResultRev> resultRevs) {
        reviewAdapter.withReviews(Converter.reviewsConverter(resultRevs));
    }

    @Override
    public void completed() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void error() {
        swipeRefreshLayout.setRefreshing(false);
    }
}
