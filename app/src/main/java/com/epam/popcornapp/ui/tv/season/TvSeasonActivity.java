package com.epam.popcornapp.ui.tv.season;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.epam.popcornapp.R;
import com.epam.popcornapp.application.BaseApplication;
import com.epam.popcornapp.application.Constants;
import com.epam.popcornapp.application.ErrorMessage;
import com.epam.popcornapp.ui.all.base.BaseMvpActivity;
import com.epam.popcornapp.ui.all.base.NetworkStateReceiver;
import com.epam.popcornapp.ui.all.base.NetworkStateReceiver.NetworkStateReceiverListener;
import com.epam.popcornapp.ui.episode.EpisodeItem;
import com.epam.popcornapp.ui.home.MainPresenter;
import com.epam.popcornapp.ui.home.MainPresenterView;
import com.epam.popcornapp.ui.home.PopcornAppNavigator;
import com.epam.popcornapp.ui.informationViews.MainInfoView;
import com.epam.popcornapp.ui.informationViews.MainInfoViewType;
import com.epam.popcornapp.ui.mainViews.MainImageView;
import com.epam.popcornapp.ui.swimming.SkeletonTileType;
import com.epam.popcornapp.ui.swimming.SwimmingLineView;
import com.epam.popcornapp.ui.switcher.PageSwitcher;
import com.epam.popcornapp.ui.tiles.credits.TileType;
import com.epam.popcornapp.ui.tv.episode.TvEpisodeParams;
import com.epam.popcornapp.ui.utils.TitleCardScrollListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.NavigatorHolder;

public class TvSeasonActivity extends BaseMvpActivity implements
        TvSeasonView,
        MainPresenterView,
        ErrorMessage.onErrorMessageClickListener,
        EpisodesAdapter.ClickListener,
        SwimmingLineView.OnSwimmingLineClickListener,
        PageSwitcher.SwitcherClickListener,
        NetworkStateReceiverListener {

    @BindView(R.id.adapter_main_photo_view)
    MainImageView mainImageView;

    @BindView(R.id.adapter_main_image_view_switcher)
    ViewSwitcher mainImageSwitcher;

    @BindView(R.id.adapter_main_info_switcher)
    ViewSwitcher mainInfoSwitcher;

    @BindView(R.id.adapter_main_info_view)
    MainInfoView mainInfoView;

    @BindView(R.id.regular_actors_swimming_line)
    SwimmingLineView regularActorsSwimmingView;

    @BindView(R.id.activity_tv_season_nested_scroll_view)
    NestedScrollView nestedScrollView;

    @BindView(R.id.episodes_recycler_view)
    RecyclerView episodesView;

    @BindView(R.id.episodes_title_view)
    TextView episodesTitleView;

    @BindView(R.id.transparent_toolbar)
    Toolbar toolbar;

    @BindView(R.id.page_switcher)
    PageSwitcher pageSwitcher;

    @BindView(R.id.season_toolbar_container)
    View toolbarContainer;

    @InjectPresenter
    TvSeasonPresenter presenter;

    @InjectPresenter
    MainPresenter mainPresenter;

    @Inject
    NavigatorHolder navigatorHolder;

    @Inject
    ErrorMessage errorMessage;

    @Inject
    NetworkStateReceiver networkStateReceiver;

    private StartParams params;

    private EpisodesAdapter adapter;

    public static Intent start(final Context context, final StartParams startParams) {
        final Intent intent = new Intent(context, TvSeasonActivity.class);
        intent.putExtra(Constants.Extras.SEASON, startParams);

        return intent;
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_season_info);

        BaseApplication.getApplicationComponent().inject(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        errorMessage.removeDialog();
        presenter.closeDb();
    }

    @Override
    protected void onViewsBinded() {
        setToolbarNoTitle(toolbar, true);

        final Intent intent = getIntent();

        if (intent != null) {
            params = intent.getParcelableExtra(Constants.Extras.SEASON);
            presenter.load(params);
            pageSwitcher.setParams(
                    params.getSeasonIdList().size(), params.getNumberOfSeasons(), params.getSeasonNumber());
        }

        regularActorsSwimmingView.createSkeletonTiles(SkeletonTileType.DESCRIPTION);
        regularActorsSwimmingView.setOnSwimmingLineClickListener(this);

        nestedScrollView.setOnScrollChangeListener(new TitleCardScrollListener() {
            @Override
            public void changeTransparency(final float transparencyValue) {
                mainImageView.setTransparency(transparencyValue);
            }

            @Override
            public void changeVisibility(final boolean visible) {
            }

            @Override
            public void changeViewVisibility(final boolean isVisible) {
                toolbarContainer.animate().translationY(isVisible ?
                        Constants.START_COORDINATE : Constants.TOOLBAR_ANIMATION_TRANSLATION)
                        .setDuration(Constants.TOOLBAR_ANIMATION_DURATION);
            }
        });

        episodesView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new EpisodesAdapter(new ArrayList<EpisodeItem>(), this);
        adapter.setListener(this);
        episodesView.setAdapter(adapter);
        episodesView.setNestedScrollingEnabled(false);

        regularActorsSwimmingView.disableMoreButton();

        pageSwitcher.setListener(this);
    }

    @Override
    public void onSuccess(final TvSeasonInfo tvSeasonInfo) {
        mainImageView.setImage(tvSeasonInfo.getPosterPath());
        mainInfoView.setData(tvSeasonInfo.getMainInfoViewItem(), MainInfoViewType.NO_RATING_BIG_TITLE);
        switchViews();

        episodesTitleView.setVisibility(View.VISIBLE);

        regularActorsSwimmingView.setData(
                SkeletonTileType.DESCRIPTION, getString(R.string.regular_actors), tvSeasonInfo.getActors());

        adapter.setData(tvSeasonInfo.getEpisodes());
    }

    @Override
    public void onError(final int errorType) {
        errorMessage.showSnackbar(this, nestedScrollView, this, errorType);
    }

    private void switchViews() {
        mainInfoSwitcher.setDisplayedChild(1);
        mainImageSwitcher.setDisplayedChild(1);
    }

    @Override
    public void onItemClick(final int episodeNumber,
                            @NonNull final List<EpisodeItem> episodeItemList) {
        mainPresenter.onEpisodeClicked(new TvEpisodeParams(
                params.getTvId(), params.getSeasonNumber(), episodeNumber,
                TvEpisodeParams.convertToIdList(episodeItemList)));
    }

    @Override
    public void onMoreClicked(@TileType final String type) {

    }

    @Override
    public void onTileClicked(final int id, final String type, final View view) {
        mainPresenter.onTileClicked(id, type);
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

    private final Navigator navigator = new PopcornAppNavigator(TvSeasonActivity.this);

    @Override
    public void onPrevClicked(final int current) {
        nestedScrollView.scrollTo(Constants.START_COORDINATE, Constants.START_COORDINATE);
        params.setSeasonNumber(current);
        presenter.load(params);
    }

    @Override
    public void onNextClicked(final int current) {
        nestedScrollView.scrollTo(Constants.START_COORDINATE, Constants.START_COORDINATE);
        params.setSeasonNumber(current);
        presenter.load(params);
    }

    @Override
    public void onRetryClicked() {
        presenter.onRetryClicked();
    }

    @Override
    public void networkAvailable() {
        presenter.load(params);

        errorMessage.removeSnackbar();
    }

    @Override
    public void networkUnavailable() {
        onError(ErrorMessage.ErrorType.INTERNET_CONNECTION_ERROR);
    }
}
