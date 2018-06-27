package com.epam.popcornapp.ui.tv;

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
import com.epam.popcornapp.ui.tv.airing_today.TvAiringTodayPresenter;
import com.epam.popcornapp.ui.tv.on_the_air.TvOnTheAirPresenter;
import com.epam.popcornapp.ui.tv.popular.TvPopularPresenter;
import com.epam.popcornapp.ui.tv.top_rated.TvTopRatedPresenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;

public class TvFragment extends BaseMvpFragment
        implements MainPresenterView,
        BaseMoreView,
        BaseFragmentAdapter.OnClickListener,
        ErrorMessage.onErrorMessageClickListener,
        RefreshInterface {

    private static final int POPULAR_TV_SHOWS_POSITION = 0;
    private static final int TOP_RATED_TV_SHOWS_POSITION = 1;
    private static final int AIRING_TODAY_TV_SHOWS_POSITION = 2;
    private static final int ON_THE_AIR_TV_SHOWS_POSITION = 3;

    private FragmentCallback fragmentCallback;

    @InjectPresenter
    TvPopularPresenter tvPopularPresenter;

    @InjectPresenter
    TvTopRatedPresenter tvTopRatedPresenter;

    @InjectPresenter
    TvAiringTodayPresenter tvAiringTodayPresenter;

    @InjectPresenter
    TvOnTheAirPresenter tvOnTheAirPresenter;

    @InjectPresenter
    MainPresenter presenter;

    @BindView(R.id.home_recycler_view)
    RecyclerView tvShowsRecyclerView;

    private BaseFragmentAdapter baseFragmentAdapter;

    public static TvFragment getInstance() {
        return new TvFragment();
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
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

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        if (item.getItemId() == R.id.action_search) {
            presenter.searchClicked(TileType.TV);

            return true;
        }

        return super.onOptionsItemSelected(item);
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
        tvShowsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        final List<SwimmingLineItem> list = new ArrayList<>(Collections.
                <SwimmingLineItem>nCopies(4, null));

        baseFragmentAdapter = new BaseFragmentAdapter(getActivity(), list);
        baseFragmentAdapter.setOnClickListener(this);

        tvShowsRecyclerView.setAdapter(baseFragmentAdapter);

        refresh();
    }

    @Override
    public void onLineMoreClicked(final int linePosition) {
        switch (linePosition) {
            case POPULAR_TV_SHOWS_POSITION:
                presenter.onMorePressed(TvPopularPresenter.class.getName());

                break;
            case TOP_RATED_TV_SHOWS_POSITION:
                presenter.onMorePressed(TvTopRatedPresenter.class.getName());

                break;
            case AIRING_TODAY_TV_SHOWS_POSITION:
                presenter.onMorePressed(TvAiringTodayPresenter.class.getName());

                break;
            case ON_THE_AIR_TV_SHOWS_POSITION:
                presenter.onMorePressed(TvOnTheAirPresenter.class.getName());

                break;
            default:
                Log.e("TAG", "Unknown line position: " + linePosition);
        }
    }

    @Override
    public void setData(@DataType final String dataType, @NonNull final List<BaseTileItem> data, final int titleId) {
        fragmentCallback.removeRefreshAnimation();

        switch (dataType) {
            case DataType.TV_POPULAR:
                baseFragmentAdapter.setData(data, getString(titleId), POPULAR_TV_SHOWS_POSITION);

                break;
            case DataType.TV_TOP_RATED:
                baseFragmentAdapter.setData(data, getString(titleId), TOP_RATED_TV_SHOWS_POSITION);

                break;
            case DataType.TV_AIRING_TODAY:
                baseFragmentAdapter.setData(data, getString(titleId), AIRING_TODAY_TV_SHOWS_POSITION);

                break;
            case DataType.TV_ON_THE_AIR:
                baseFragmentAdapter.setData(data, getString(titleId), ON_THE_AIR_TV_SHOWS_POSITION);

                break;
            default:
                Log.e("ERROR", "Unknown data type: " + dataType);
        }
    }

    public void onLineTileClicked(final int linePosition, final int id, @TileType final String type) {
        presenter.onTileClicked(id, type);
        tvTopRatedPresenter.closeDb();
        tvAiringTodayPresenter.closeDb();
        tvOnTheAirPresenter.closeDb();
        tvPopularPresenter.closeDb();
    }

    @Override
    public void onError(@ErrorType final int errorType) {
        fragmentCallback.removeRefreshAnimation();
        fragmentCallback.onError(this, errorType, tvShowsRecyclerView);
    }

    @Override
    public void refresh() {
        tvPopularPresenter.refreshData();
        tvAiringTodayPresenter.refreshData();
        tvOnTheAirPresenter.refreshData();
        tvTopRatedPresenter.refreshData();
    }

    @Override
    public void onRetryClicked() {
        tvPopularPresenter.retryClicked();
        tvAiringTodayPresenter.retryClicked();
        tvOnTheAirPresenter.retryClicked();
        tvTopRatedPresenter.retryClicked();
    }

    @Override
    public void onPause() {
        super.onPause();
        tvTopRatedPresenter.closeDb();
        tvAiringTodayPresenter.closeDb();
        tvOnTheAirPresenter.closeDb();
        tvPopularPresenter.closeDb();
    }
}
