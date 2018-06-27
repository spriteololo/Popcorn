package com.epam.popcornapp.ui.watchlist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.epam.popcornapp.R;
import com.epam.popcornapp.application.Constants;
import com.epam.popcornapp.application.ErrorMessage;
import com.epam.popcornapp.ui.account.menu.AccountMenuPresenter;
import com.epam.popcornapp.ui.account.menu.AccountMenuView;
import com.epam.popcornapp.ui.all.base.BaseMoreView;
import com.epam.popcornapp.ui.all.base.BaseMvpFragment;
import com.epam.popcornapp.ui.home.FragmentCallback;
import com.epam.popcornapp.ui.home.MainPresenter;
import com.epam.popcornapp.ui.home.MainPresenterView;
import com.epam.popcornapp.ui.home.RefreshInterface;
import com.epam.popcornapp.ui.swimming.SkeletonTileType;
import com.epam.popcornapp.ui.swimming.SwimmingLineItem;
import com.epam.popcornapp.ui.swimming.SwimmingLineView;
import com.epam.popcornapp.ui.tiles.credits.TileType;
import com.epam.popcornapp.ui.tiles.item.BaseTileItem;
import com.epam.popcornapp.ui.titles.TitleCardActivity;
import com.epam.popcornapp.ui.watchlist.presenters.WatchlistMoviePresenter;
import com.epam.popcornapp.ui.watchlist.presenters.WatchlistTvPresenter;

import java.util.List;

import butterknife.BindView;

public class WatchlistFragment extends BaseMvpFragment implements
        MainPresenterView,
        BaseMoreView,
        ErrorMessage.onErrorMessageClickListener,
        RefreshInterface,
        AccountMenuView,
        SwimmingLineView.OnSwimmingLineClickListener {

    @InjectPresenter
    MainPresenter mainPresenter;

    @InjectPresenter
    AccountMenuPresenter accountMenuPresenter;

    @InjectPresenter
    WatchlistMoviePresenter watchlistMoviePresenter;

    @InjectPresenter
    WatchlistTvPresenter watchlistTvPresenter;

    @BindView(R.id.home_root_layout)
    View rootLayout;

    @BindView(R.id.home_nothing_to_show_text_view)
    View nothingToShowView;

    @BindView(R.id.home_movie_swimming_line)
    SwimmingLineView movieSwimmingView;

    @BindView(R.id.home_tv_swimming_line)
    SwimmingLineView tvSwimmingView;

    private FragmentCallback fragmentCallback;

    public static WatchlistFragment getInstance() {
        return new WatchlistFragment();
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public void onAttach(final Context context) {
        super.onAttach(context);

        try {
            fragmentCallback = (FragmentCallback) context;
        } catch (final ClassCastException e) {
            throw new ClassCastException(context.toString());
        }
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_account_menu, container, false);
    }

    @Override
    protected void onViewsBinded() {
        fragmentCallback.hideSearchButton();

        initSwimmingLines();

        refresh();
    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Constants.ChangeType.WATCHLIST) {
            refresh();
        }
    }

    private void initSwimmingLines() {
        movieSwimmingView.createSkeletonTiles(SkeletonTileType.DESCRIPTION);
        tvSwimmingView.createSkeletonTiles(SkeletonTileType.DESCRIPTION);

        movieSwimmingView.setOnSwimmingLineClickListener(this);
        tvSwimmingView.setOnSwimmingLineClickListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        fragmentCallback.showSearchButton();
    }

    @Override
    public void refresh() {
        watchlistMoviePresenter.refreshData();
        watchlistTvPresenter.refreshData();
    }

    @Override
    public void setData(final String dataType, final List<BaseTileItem> data, final int titleId) {
        fragmentCallback.removeRefreshAnimation();

        accountMenuPresenter.setData(dataType, data, getString(titleId));
    }

    @Override
    public void onError(final int errorType) {
        fragmentCallback.removeRefreshAnimation();

        fragmentCallback.onError(this, errorType, rootLayout);
    }

    @Override
    public void onRetryClicked() {
        refresh();
    }

    @Override
    public void onTileClicked(final int id, final String type, final View view) {
        startActivityForResult(TitleCardActivity.start(
                accountMenuPresenter.getScreenKeyByTileType(type), id, getContext()), 1);
    }

    @Override
    public void onMoreClicked(@TileType final String type) {
        if (type.equals(TileType.MOVIE)) {
            mainPresenter.onMorePressed(WatchlistMoviePresenter.class.getName());
        } else {
            mainPresenter.onMorePressed(WatchlistTvPresenter.class.getName());
        }
    }

    @Override
    public void setMovieSwimmingLine(final SwimmingLineItem item) {
        movieSwimmingView.setVisibility(View.VISIBLE);

        movieSwimmingView.setData(
                SkeletonTileType.DESCRIPTION, item.getTitle(), item.getTileList());
    }

    @Override
    public void setTvSwimmingLine(final SwimmingLineItem item) {
        tvSwimmingView.setVisibility(View.VISIBLE);

        tvSwimmingView.setData(
                SkeletonTileType.DESCRIPTION, item.getTitle(), item.getTileList());
    }

    @Override
    public void hideMovieMoreButton() {
        movieSwimmingView.disableMoreButton();
    }

    @Override
    public void hideTvMoreButton() {
        tvSwimmingView.disableMoreButton();
    }

    @Override
    public void noData() {
        nothingToShowView.setVisibility(View.VISIBLE);
        movieSwimmingView.setVisibility(View.INVISIBLE);
        tvSwimmingView.setVisibility(View.INVISIBLE);
    }
}
