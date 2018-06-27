package com.epam.popcornapp.ui.more;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.PresenterType;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.epam.popcornapp.R;
import com.epam.popcornapp.application.BaseApplication;
import com.epam.popcornapp.application.Constants;
import com.epam.popcornapp.application.Constants.DataType;
import com.epam.popcornapp.application.ErrorMessage;
import com.epam.popcornapp.application.ErrorMessage.ErrorType;
import com.epam.popcornapp.listeners.RecyclerViewListeners;
import com.epam.popcornapp.listeners.RecyclerViewLoadMore;
import com.epam.popcornapp.ui.all.base.BaseMoreMvpPresenter;
import com.epam.popcornapp.ui.all.base.BaseMoreView;
import com.epam.popcornapp.ui.all.base.BaseMvpActivity;
import com.epam.popcornapp.ui.all.base.NetworkStateReceiver;
import com.epam.popcornapp.ui.all.base.NetworkStateReceiver.NetworkStateReceiverListener;
import com.epam.popcornapp.ui.home.MainPresenter;
import com.epam.popcornapp.ui.home.MainPresenterView;
import com.epam.popcornapp.ui.home.PopcornAppNavigator;
import com.epam.popcornapp.ui.home.RefreshInterface;
import com.epam.popcornapp.ui.swimming.SkeletonTileType;
import com.epam.popcornapp.ui.tiles.credits.TileType;
import com.epam.popcornapp.ui.tiles.item.BaseTileItem;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.annotations.NonNull;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.NavigatorHolder;

public class MoreActivity extends BaseMvpActivity implements
        MainPresenterView,
        BaseMoreView,
        MoreScreenAdapter.onClickListener,
        RecyclerViewListeners.OnLoadMoreListener,
        ErrorMessage.onErrorMessageClickListener,
        SwipeRefreshLayout.OnRefreshListener,
        NetworkStateReceiverListener,
        RefreshInterface {

    @Inject
    ErrorMessage errorMessage;

    @Inject
    NavigatorHolder navigatorHolder;

    @Inject
    NetworkStateReceiver networkStateReceiver;

    @InjectPresenter
    MainPresenter presenter;

    @InjectPresenter
    BaseMoreMvpPresenter baseMoreMvpPresenter;

    @BindView(R.id.blur_toolbar)
    Toolbar toolBar;

    @BindView(R.id.activity_more_recycler_view)
    RecyclerView moreItemsRecyclerView;

    @BindView(R.id.activity_more_swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    private boolean isRefresh;

    private MoreScreenAdapter moreScreenAdapter;
    private final Navigator navigator = new PopcornAppNavigator(MoreActivity.this);

    @ProvidePresenter(type = PresenterType.LOCAL)
    BaseMoreMvpPresenter provideBaseMvpPresenter() {
        BaseMoreMvpPresenter baseMoreMvpPresenter = null;

        try {
            baseMoreMvpPresenter = (BaseMoreMvpPresenter) Class
                    .forName(getIntent().getStringExtra(Constants.Extras.SCREEN_TYPE))
                    .getConstructor()
                    .newInstance();
        } catch (final Exception e) {
            Log.e("ERROR", "Unknown presenter name: " + e.getMessage());
        }

        return baseMoreMvpPresenter;
    }

    public static Intent start(final Context context, final String basePresenterName) {
        final Intent intent = new Intent(context, MoreActivity.class);
        intent.putExtra(Constants.Extras.SCREEN_TYPE, basePresenterName);

        return intent;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);

        BaseApplication.getApplicationComponent().inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        navigatorHolder.setNavigator(navigator);

        networkStateReceiver.addListener(this);
        this.registerReceiver(networkStateReceiver,
                new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    protected void onPause() {
        super.onPause();

        navigatorHolder.removeNavigator();
        this.unregisterReceiver(networkStateReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        errorMessage.removeDialog();
    }

    @Override
    protected void onViewsBinded() {
        setToolbar(toolBar, getString(baseMoreMvpPresenter.getTitleId()), true);

        moreScreenAdapter = new MoreScreenAdapter(new ArrayList<BaseTileItem>(), this);
        moreScreenAdapter.setClickListener(this);

        moreItemsRecyclerView.setLayoutManager(new GridLayoutManager(getBaseContext(), 2));
        moreItemsRecyclerView.setAdapter(moreScreenAdapter);
        moreItemsRecyclerView.addOnScrollListener(new RecyclerViewLoadMore(
                this, moreItemsRecyclerView.getLayoutManager()));

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setProgressViewOffset(true, 100, 200);

        baseMoreMvpPresenter.refreshData();
    }

    @Override
    public void onItemClick(final int id, @TileType final String mediaType, final int position) {
        presenter.onTileClicked(id, mediaType);
    }

    @Override
    public void setData(@DataType final String screenType, @NonNull final List<BaseTileItem> data, final int titleId) {
        swipeRefreshLayout.setRefreshing(false);

        final boolean isRatedType = screenType.equals(DataType.RATED_MOVIES)
                || screenType.equals(DataType.RATED_TV_SHOWS);

        moreScreenAdapter.setData(data, isRefresh, isRatedType ?
                SkeletonTileType.RATING : SkeletonTileType.DESCRIPTION);

        isRefresh = false;
    }

    @Override
    public void refresh() {
        isRefresh = true;

        baseMoreMvpPresenter.refreshData();
    }

    @Override
    public void onError(@ErrorType final int errorType) {
        swipeRefreshLayout.setRefreshing(false);

        errorMessage.showSnackbar(this, swipeRefreshLayout, this, errorType);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLoadMore(final int nextPage) {
        baseMoreMvpPresenter.setCurrentPage(nextPage);
    }

    @Override
    public void onRefresh() {
        refresh();
    }

    @Override
    public void onRetryClicked() {
        baseMoreMvpPresenter.setCurrentPage(baseMoreMvpPresenter.getCurrentPage());
    }

    @Override
    public void networkAvailable() {
        baseMoreMvpPresenter.refreshData();

        errorMessage.removeSnackbar();
    }

    @Override
    public void networkUnavailable() {
        onError(ErrorType.INTERNET_CONNECTION_ERROR);
    }
}
