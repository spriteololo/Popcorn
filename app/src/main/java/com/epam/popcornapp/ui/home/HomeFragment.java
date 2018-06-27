package com.epam.popcornapp.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.epam.popcornapp.ui.actors.PopularActorsPresenter;
import com.epam.popcornapp.ui.all.base.BaseFragmentAdapter;
import com.epam.popcornapp.ui.all.base.BaseMoreView;
import com.epam.popcornapp.ui.all.base.BaseMvpFragment;
import com.epam.popcornapp.ui.movies.PopularMoviesPresenter;
import com.epam.popcornapp.ui.swimming.SwimmingLineItem;
import com.epam.popcornapp.ui.tiles.credits.TileType;
import com.epam.popcornapp.ui.tiles.item.BaseTileItem;
import com.epam.popcornapp.ui.tv.popular.TvPopularPresenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;

public class HomeFragment extends BaseMvpFragment
        implements MainPresenterView,
        BaseMoreView,
        BaseFragmentAdapter.OnClickListener,
        ErrorMessage.onErrorMessageClickListener,
        RefreshInterface {

    private static final int MOVIES_POSITION = 0;
    private static final int ACTOR_POSITION = 1;
    private static final int TV_SHOWS_POSITION = 2;

    private FragmentCallback fragmentCallback;

    @InjectPresenter
    PopularActorsPresenter popularActorsPresenter;

    @InjectPresenter
    PopularMoviesPresenter popularMoviesPresenter;

    @InjectPresenter
    TvPopularPresenter popularTvShowsPresenter;

    @InjectPresenter
    MainPresenter presenter;

    @BindView(R.id.home_recycler_view)
    RecyclerView swimmingLinesRecyclerView;

    private BaseFragmentAdapter baseFragmentAdapter;

    public static HomeFragment getInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        BaseApplication.getApplicationComponent().inject(this);
        setHasOptionsMenu(true);

        super.onCreate(savedInstanceState);
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

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onPause() {
        super.onPause();

        popularActorsPresenter.onDestroy();
        popularMoviesPresenter.onDestroy();
        popularTvShowsPresenter.closeDb();
    }

    @Override
    protected void onViewsBinded() {
        swimmingLinesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        final List<SwimmingLineItem> list = new ArrayList<>(Collections.
                <SwimmingLineItem>nCopies(3, null));

        baseFragmentAdapter = new BaseFragmentAdapter(getActivity(), list);
        baseFragmentAdapter.setOnClickListener(this);

        swimmingLinesRecyclerView.setAdapter(baseFragmentAdapter);

        refresh();
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        if (item.getItemId() == R.id.action_search) {
            presenter.searchClicked(null);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLineTileClicked(final int linePosition, final int id, @TileType final String type) {
        popularActorsPresenter.onDestroy();
        presenter.onTileClicked(id, type);
    }

    @Override
    public void onLineMoreClicked(final int linePosition) {
        switch (linePosition) {
            case ACTOR_POSITION:
                presenter.onMorePressed(PopularActorsPresenter.class.getName());

                break;
            case MOVIES_POSITION:
                presenter.onMorePressed(PopularMoviesPresenter.class.getName());

                break;
            case TV_SHOWS_POSITION:
                presenter.onMorePressed(TvPopularPresenter.class.getName());

                break;
            default:
                Log.e("ERROR", "Unknown line position: " + linePosition);
        }
    }

    @Override
    public void setData(@DataType final String dataType, @NonNull final List<BaseTileItem> data, final int titleId) {
        fragmentCallback.removeRefreshAnimation();

        switch (dataType) {
            case DataType.PEOPLE:
                baseFragmentAdapter.setData(data, getString(titleId), ACTOR_POSITION);

                break;
            case DataType.MOVIES_POPULAR:
                baseFragmentAdapter.setData(data, getString(titleId), MOVIES_POSITION);

                break;
            case DataType.TV_POPULAR:
                baseFragmentAdapter.setData(data, getString(titleId), TV_SHOWS_POSITION);

                break;
            default:
                Log.e("ERROR", "Unknown data type: " + dataType);
        }
    }

    @Override
    public void onError(@ErrorType final int errorType) {
        fragmentCallback.removeRefreshAnimation();
        fragmentCallback.onError(this, errorType, swimmingLinesRecyclerView);
    }

    @Override
    public void refresh() {
        popularActorsPresenter.refreshData();
        popularMoviesPresenter.refreshData();
        popularTvShowsPresenter.refreshData();
    }

    @Override
    public void onRetryClicked() {
        popularActorsPresenter.retryClicked();
        popularMoviesPresenter.retryClicked();
        popularTvShowsPresenter.retryClicked();
    }
}
