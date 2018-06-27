package com.epam.popcornapp.ui.actors;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.epam.popcornapp.R;
import com.epam.popcornapp.application.BaseApplication;
import com.epam.popcornapp.application.Constants.DataType;
import com.epam.popcornapp.application.ErrorMessage;
import com.epam.popcornapp.application.ErrorMessage.ErrorType;
import com.epam.popcornapp.listeners.RecyclerViewListeners;
import com.epam.popcornapp.listeners.RecyclerViewLoadMore;
import com.epam.popcornapp.ui.all.base.BaseMoreView;
import com.epam.popcornapp.ui.all.base.BaseMvpFragment;
import com.epam.popcornapp.ui.home.FragmentCallback;
import com.epam.popcornapp.ui.home.MainPresenter;
import com.epam.popcornapp.ui.home.MainPresenterView;
import com.epam.popcornapp.ui.home.RefreshInterface;
import com.epam.popcornapp.ui.more.MoreScreenAdapter;
import com.epam.popcornapp.ui.swimming.SkeletonTileType;
import com.epam.popcornapp.ui.tiles.credits.TileType;
import com.epam.popcornapp.ui.tiles.item.BaseTileItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ActorsFragment extends BaseMvpFragment
        implements MainPresenterView,
        BaseMoreView,
        MoreScreenAdapter.onClickListener,
        ErrorMessage.onErrorMessageClickListener,
        RecyclerViewListeners.OnLoadMoreListener,
        RefreshInterface {

    @InjectPresenter
    MainPresenter presenter;

    @InjectPresenter
    PopularActorsPresenter popularActorsPresenter;

    @BindView(R.id.rv_actors_list)
    RecyclerView actorsRecyclerView;

    private boolean isRefresh;

    private MoreScreenAdapter moreScreenAdapter;
    private FragmentCallback fragmentCallback;

    public static ActorsFragment getInstance() {
        return new ActorsFragment();
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        BaseApplication.getApplicationComponent().inject(this);
        setHasOptionsMenu(true);
    }

    @Override
    public void onAttach(final Context context) {
        super.onAttach(context);

        try {
            fragmentCallback = (FragmentCallback) context;
        } catch (final ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    " must implement FragmentCallback");
        }
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        if (item.getItemId() == R.id.action_search) {
            presenter.searchClicked(TileType.ACTOR);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_people, container, false);
    }

    @Override
    protected void onViewsBinded() {
        moreScreenAdapter = new MoreScreenAdapter(new ArrayList<BaseTileItem>(), getActivity());
        moreScreenAdapter.setClickListener(this);

        actorsRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        actorsRecyclerView.setAdapter(moreScreenAdapter);
        actorsRecyclerView.addOnScrollListener(new RecyclerViewLoadMore(
                this, actorsRecyclerView.getLayoutManager()));

        popularActorsPresenter.refreshData();
    }

    @Override
    public void onItemClick(final int id, @TileType final String mediaType, final int position) {
        popularActorsPresenter.onDestroy();
        presenter.onTileClicked(id, mediaType);
    }

    @Override
    public void setData(@DataType final String dataType, @NonNull final List<BaseTileItem> data, final int titleId) {
        fragmentCallback.removeRefreshAnimation();

        moreScreenAdapter.setData(data, isRefresh, SkeletonTileType.DESCRIPTION);

        isRefresh = false;
    }

    @Override
    public void onLoadMore(final int nextPage) {
        popularActorsPresenter.setCurrentPage(nextPage);
    }

    @Override
    public void onError(@ErrorType final int errorType) {
        fragmentCallback.removeRefreshAnimation();
        fragmentCallback.onError(this, errorType, actorsRecyclerView);
    }

    @Override
    public void refresh() {
        isRefresh = true;

        popularActorsPresenter.refreshData();
    }

    @Override
    public void onRetryClicked() {
        popularActorsPresenter.retryClicked();
    }
}
