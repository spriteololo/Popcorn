package com.epam.popcornapp.ui.movies;

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
import com.epam.popcornapp.ui.all.base.BaseFragmentAdapter;
import com.epam.popcornapp.ui.all.base.BaseMoreView;
import com.epam.popcornapp.ui.all.base.BaseMvpFragment;
import com.epam.popcornapp.ui.home.FragmentCallback;
import com.epam.popcornapp.ui.home.MainPresenter;
import com.epam.popcornapp.ui.home.MainPresenterView;
import com.epam.popcornapp.ui.home.RefreshInterface;
import com.epam.popcornapp.ui.swimming.SwimmingLineItem;
import com.epam.popcornapp.ui.tiles.credits.TileType;
import com.epam.popcornapp.ui.tiles.item.BaseTileItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;

public class MoviesFragment extends BaseMvpFragment implements
        MainPresenterView,
        BaseMoreView,
        BaseFragmentAdapter.OnClickListener,
        ErrorMessage.onErrorMessageClickListener,
        RefreshInterface {

    private static final int POPULAR_MOVIES_POSITION = 0;
    private static final int UPCOMING_MOVIES_POSITION = 1;
    private static final int TOP_RATED_MOVIES_POSITION = 2;

    private FragmentCallback fragmentCallback;

    @InjectPresenter
    TopRatedMoviesPresenter topRatedMoviesPresenter;

    @InjectPresenter
    UpcomingMoviesPresenter upcomingMoviesPresenter;

    @InjectPresenter
    PopularMoviesPresenter popularMoviesPresenter;

    @BindView(R.id.home_recycler_view)
    RecyclerView moviesRecyclerView;

    @InjectPresenter
    MainPresenter presenter;

    private BaseFragmentAdapter baseFragmentAdapter;

    public static MoviesFragment getInstance() {
        return new MoviesFragment();
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        BaseApplication.getApplicationComponent().inject(this);
        setHasOptionsMenu(true);

        super.onCreate(savedInstanceState);
    }

    @Override
    public void onPause() {
        super.onPause();

        popularMoviesPresenter.onDestroy();
        topRatedMoviesPresenter.onDestroy();
        popularMoviesPresenter.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        if (item.getItemId() == R.id.action_search) {
            presenter.searchClicked(TileType.MOVIE);

            return true;
        }

        return super.onOptionsItemSelected(item);
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
    protected void onViewsBinded() {
        moviesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        final List<SwimmingLineItem> list = new ArrayList<>(Collections.
                <SwimmingLineItem>nCopies(3, null));

        baseFragmentAdapter = new BaseFragmentAdapter(getActivity(), list);
        baseFragmentAdapter.setOnClickListener(this);

        moviesRecyclerView.setAdapter(baseFragmentAdapter);

        refresh();
    }

    @Override
    public void onLineMoreClicked(final int linePosition) {
        switch (linePosition) {
            case POPULAR_MOVIES_POSITION:
                presenter.onMorePressed(PopularMoviesPresenter.class.getName());

                break;
            case UPCOMING_MOVIES_POSITION:
                presenter.onMorePressed(UpcomingMoviesPresenter.class.getName());

                break;
            case TOP_RATED_MOVIES_POSITION:
                presenter.onMorePressed(TopRatedMoviesPresenter.class.getName());

                break;
            default:
                Log.e("ERROR", "Unknown line position: " + linePosition);
        }
    }

    @Override
    public void setData(@DataType final String dataType, @NonNull final List<BaseTileItem> data, final int titleId) {
        fragmentCallback.removeRefreshAnimation();

        switch (dataType) {
            case DataType.MOVIES_POPULAR:
                baseFragmentAdapter.setData(data, getString(titleId), POPULAR_MOVIES_POSITION);

                break;
            case DataType.MOVIES_UPCOMING:
                baseFragmentAdapter.setData(data, getString(titleId), UPCOMING_MOVIES_POSITION);

                break;
            case DataType.MOVIES_TOP_RATED:
                baseFragmentAdapter.setData(data, getString(titleId), TOP_RATED_MOVIES_POSITION);

                break;
            default:
                Log.e("ERROR", "Unknown data type: " + dataType);
        }
    }

    public void onLineTileClicked(final int linePosition, final int id, @TileType final String type) {
        presenter.onTileClicked(id, type);
    }

    @Override
    public void onError(@ErrorType final int errorType) {
        fragmentCallback.removeRefreshAnimation();
        fragmentCallback.onError(this, errorType, moviesRecyclerView);
    }

    @Override
    public void refresh() {
        popularMoviesPresenter.refreshData();
        topRatedMoviesPresenter.refreshData();
        upcomingMoviesPresenter.refreshData();
    }

    @Override
    public void onRetryClicked() {
        popularMoviesPresenter.retryClicked();
        topRatedMoviesPresenter.retryClicked();
        upcomingMoviesPresenter.retryClicked();
    }
}
