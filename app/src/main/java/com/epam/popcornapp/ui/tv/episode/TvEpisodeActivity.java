package com.epam.popcornapp.ui.tv.episode;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.SharedElementCallback;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.epam.popcornapp.R;
import com.epam.popcornapp.application.BaseApplication;
import com.epam.popcornapp.application.Constants;
import com.epam.popcornapp.application.ErrorMessage;
import com.epam.popcornapp.application.ErrorMessage.ErrorType;
import com.epam.popcornapp.ui.all.base.BaseMvpActivity;
import com.epam.popcornapp.ui.all.base.NetworkStateReceiver;
import com.epam.popcornapp.ui.all.base.NetworkStateReceiver.NetworkStateReceiverListener;
import com.epam.popcornapp.ui.expandable.ExpandableLinearLayout;
import com.epam.popcornapp.ui.home.PopcornAppNavigator;
import com.epam.popcornapp.ui.informationViews.MainInfoView;
import com.epam.popcornapp.ui.informationViews.MainInfoViewType;
import com.epam.popcornapp.ui.mainViews.MainImageType;
import com.epam.popcornapp.ui.mainViews.MainImageView;
import com.epam.popcornapp.ui.rateView.RateView;
import com.epam.popcornapp.ui.swimming.SkeletonTileType;
import com.epam.popcornapp.ui.swimming.SwimmingLineView;
import com.epam.popcornapp.ui.switcher.PageSwitcher;
import com.epam.popcornapp.ui.tiles.credits.TileType;
import com.epam.popcornapp.ui.titles.TitlePresenter;
import com.epam.popcornapp.ui.titles.TitleView;
import com.epam.popcornapp.ui.utils.TitleCardScrollListener;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.NavigatorHolder;

public class TvEpisodeActivity extends BaseMvpActivity implements
        TvEpisodeView,
        TitleView,
        ErrorMessage.onErrorMessageClickListener,
        SwimmingLineView.OnSwimmingLineClickListener,
        PageSwitcher.SwitcherClickListener,
        NetworkStateReceiverListener,
        RateView.RateViewListener {

    @Inject
    ErrorMessage errorMessage;

    @Inject
    NavigatorHolder navigatorHolder;

    @Inject
    NetworkStateReceiver networkStateReceiver;

    @InjectPresenter
    TitlePresenter titlePresenter;

    @InjectPresenter
    TvEpisodePresenter tvEpisodePresenter;

    @BindView(R.id.transparent_toolbar)
    Toolbar toolbar;

    @BindView(R.id.adapter_main_photo_view)
    MainImageView mainImageView;

    @BindView(R.id.activity_tv_episode_scroll_view)
    NestedScrollView nestedScrollView;

    @BindView(R.id.adapter_main_info_view)
    MainInfoView mainInfoView;

    @BindView(R.id.adapter_main_image_view_switcher)
    ViewSwitcher mainImageSwitcher;

    @BindView(R.id.adapter_main_info_switcher)
    ViewSwitcher mainInfoSwitcher;

    @BindView(R.id.activity_tv_episode_show_details_button)
    Button showDetailsButton;

    @BindView(R.id.activity_tv_episode_director_block)
    View directorBlockView;

    @BindView(R.id.activity_tv_episode_director_text_view)
    TextView directorView;

    @BindView(R.id.activity_tv_episode_screenwriter_block)
    View screenwriterBlockView;

    @BindView(R.id.activity_tv_episode_screenwriter_text_view)
    TextView screenwriterView;

    @BindView(R.id.activity_tv_episode_expandable_layout)
    ExpandableLinearLayout expandableLinearLayout;

    @BindView(R.id.activity_tv_episode_gallery_swimming_view)
    SwimmingLineView gallerySwimmingView;

    @BindView(R.id.activity_tv_episode_billed_cast_swimming_view)
    SwimmingLineView billedCastSwimmingView;

    @BindView(R.id.activity_tv_episode_page_switcher)
    PageSwitcher pageSwitcher;

    @BindView(R.id.episode_toolbar_container)
    View toolbarContainer;

    @BindView(R.id.rate_view)
    RateView rateView;

    private final Navigator navigator = new PopcornAppNavigator(TvEpisodeActivity.this);

    private TvEpisodeParams params;

    public static Intent start(@NonNull final Context context, @NonNull final TvEpisodeParams tvEpisodeParams) {
        final Intent intent = new Intent(context, TvEpisodeActivity.class);

        intent.putExtra(Constants.Extras.PARAMS, tvEpisodeParams);

        return intent;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_episode);

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
        tvEpisodePresenter.onDestroy();
    }

    private void saveParams() {
        this.params = getIntent().getParcelableExtra(Constants.Extras.PARAMS);
    }

    @Override
    protected void onViewsBinded() {
        saveParams();

        setToolbarNoTitle(toolbar, true);

        initSwimmingLines();

        mainImageView.setViewSetting(MainImageType.EPISODE);

        if (Constants.CURRENT_SESSION != null) {
            rateView.setVisibility(View.VISIBLE);
            rateView.setListener(this);
        }

        showDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                expandableLinearLayout.expand();
                view.setVisibility(View.GONE);
            }
        });

        nestedScrollView.setOnScrollChangeListener(new TitleCardScrollListener() {
            @Override
            public void changeTransparency(final float transparencyValue) {
                mainImageView.setTransparency(transparencyValue * 2);
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

        final int allEpisodesNumber = params.getEpisodeIdList().size();

        pageSwitcher.setParams(allEpisodesNumber, allEpisodesNumber, params.getEpisodeNumber());
        pageSwitcher.setListener(this);

        refresh();
    }

    private void initSwimmingLines() {
        gallerySwimmingView.createSkeletonTiles(SkeletonTileType.GALLERY_LANDSCAPE);
        billedCastSwimmingView.createSkeletonTiles(SkeletonTileType.DESCRIPTION);

        gallerySwimmingView.disableMoreButton();
        billedCastSwimmingView.disableMoreButton();

        gallerySwimmingView.setOnSwimmingLineClickListener(this);
        billedCastSwimmingView.setOnSwimmingLineClickListener(this);
    }

    private void refresh() {
        tvEpisodePresenter.getData(params);
    }

    @Override
    public void setInfo(final TvEpisodeData data, final boolean isDataFromServer) {
        mainImageView.setImage(data.getPhotoPath());
        mainInfoView.setData(data.getMainInfoViewItem(), MainInfoViewType.BIG_TITLE);
        directorView.setText(data.getDirector());
        screenwriterView.setText(data.getWriter());

        titlePresenter.onGalleryPhotosFetched(data.getGalleryPhotoPaths());

        gallerySwimmingView.setData(
                SkeletonTileType.GALLERY_LANDSCAPE, getString(R.string.gallery), data.getImagePathList());
        billedCastSwimmingView.setData(
                SkeletonTileType.DESCRIPTION, getString(R.string.guest_stars), data.getBilledCastList());

        showViewSwitchers();

        checkComponentsVisibility();
    }

    private void checkComponentsVisibility() {
        boolean isDetailInfoEmpty = true;

        if (!TextUtils.isEmpty(directorView.getText())) {
            isDetailInfoEmpty = false;
            directorBlockView.setVisibility(View.VISIBLE);
        } else {
            directorBlockView.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(screenwriterView.getText())) {
            isDetailInfoEmpty = false;
            screenwriterBlockView.setVisibility(View.VISIBLE);
        } else {
            screenwriterBlockView.setVisibility(View.GONE);
        }

        if (isDetailInfoEmpty) {
            showDetailsButton.setVisibility(View.GONE);
        }
    }

    private void showViewSwitchers() {
        mainImageSwitcher.setDisplayedChild(1);
        mainInfoSwitcher.setDisplayedChild(1);
    }

    @Override
    public void onError(@ErrorType final int errorType) {
        errorMessage.showSnackbar(this, nestedScrollView, this, errorType);
    }

    @Override
    public void onTileClicked(final int id, final String type, final View view) {
        tvEpisodePresenter.onDestroy();
        titlePresenter.onTileClicked(id, type, view, this);
    }

    @Override
    public void onMoreClicked(@TileType final String type) {

    }

    @Override
    public void onRetryClicked() {
        tvEpisodePresenter.retryClicked();
    }

    @Override
    public void onPrevClicked(final int current) {
        restart(current);
    }

    @Override
    public void onNextClicked(final int current) {
        restart(current);
    }

    private void restart(final int current) {
        nestedScrollView.scrollTo(Constants.START_COORDINATE, Constants.START_COORDINATE);

        expandableLinearLayout.setVisibility(View.GONE);
        showDetailsButton.setVisibility(View.VISIBLE);

        params.setEpisodeNumber(current);
        refresh();
    }

    @Override
    public void networkAvailable() {
        refresh();

        errorMessage.removeSnackbar();
    }

    @Override
    public void networkUnavailable() {
        onError(ErrorType.INTERNET_CONNECTION_ERROR);
    }

    @Override
    public void setCurrentRating(final float rating) {
        rateView.setData(String.format(Constants.BASE_AVATAR_URL, Constants.GRAVATAR_HASH),
                getString(R.string.tv_episode), rating);
    }

    @Override
    public void onRatingResult(final boolean isSuccess, final float rating) {
        rateView.update(isSuccess, rating);
    }

    @Override
    public void setRating(final float rating) {
        tvEpisodePresenter.rateTvEpisode(rating);
    }

    @Override
    public void deleteRating() {
        tvEpisodePresenter.deleteRating();
    }

    @Override
    public void onActivityReenter(final int resultCode, final Intent data) {
        super.onActivityReenter(resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            final int exitPosition = data.getIntExtra(Constants.Extras.EXIT_POSITION, 0);
            final View view = gallerySwimmingView.getViewByTag(Constants.Extras.TAG + exitPosition);
            updateSharedElements(view);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void updateSharedElements(final View view) {
        setExitSharedElementCallback(new SharedElementCallback() {
            @Override
            public void onMapSharedElements(final List<String> names, final Map<String, View> sharedElements) {
                if (view != null) {
                    names.clear();
                    sharedElements.clear();
                    names.add(view.getTransitionName());
                    sharedElements.put(view.getTransitionName(), view);
                }

                setExitSharedElementCallback((SharedElementCallback) null);
            }
        });
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void startGalleryActivity(final Intent intent, final Bundle options) {
        startActivityForResult(intent, 0, options);
    }
}
