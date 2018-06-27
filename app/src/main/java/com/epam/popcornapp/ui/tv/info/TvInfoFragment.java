package com.epam.popcornapp.ui.tv.info;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.epam.popcornapp.R;
import com.epam.popcornapp.application.Constants;
import com.epam.popcornapp.application.Constants.ChangeType;
import com.epam.popcornapp.application.ErrorMessage;
import com.epam.popcornapp.application.ErrorMessage.ErrorType;
import com.epam.popcornapp.pojo.base.rating.Rating;
import com.epam.popcornapp.ui.account.AccountPresenter;
import com.epam.popcornapp.ui.account.AccountView;
import com.epam.popcornapp.ui.actionViews.MediaActionView;
import com.epam.popcornapp.ui.all.base.BaseMvpFragment;
import com.epam.popcornapp.ui.currentSeasonView.SeasonItem;
import com.epam.popcornapp.ui.expandable.ExpandableLinearLayout;
import com.epam.popcornapp.ui.gallery.FragmentWithGallery;
import com.epam.popcornapp.ui.home.RefreshInterface;
import com.epam.popcornapp.ui.informationViews.MainInfoView;
import com.epam.popcornapp.ui.informationViews.MainInfoViewType;
import com.epam.popcornapp.ui.mainViews.MainImageView;
import com.epam.popcornapp.ui.rateView.RateView;
import com.epam.popcornapp.ui.swimming.SkeletonTileType;
import com.epam.popcornapp.ui.swimming.SwimmingLineView;
import com.epam.popcornapp.ui.tiles.credits.TileType;
import com.epam.popcornapp.ui.titles.TitleCardFragmentCallback;
import com.epam.popcornapp.ui.titles.TitlePresenter;
import com.epam.popcornapp.ui.titles.TitleView;
import com.epam.popcornapp.ui.utils.TitleCardScrollListener;

import java.util.List;

import butterknife.BindView;

public class TvInfoFragment extends BaseMvpFragment implements
        TvInfoView,
        AccountView,
        TitleView,
        ErrorMessage.onErrorMessageClickListener,
        SwimmingLineView.OnSwimmingLineClickListener,
        RefreshInterface,
        RateView.RateViewListener,
        MediaActionView.ActionViewListeners,
        FragmentWithGallery, TvSeasonsAdapter.ItemClickListener {

    @InjectPresenter
    TvInfoPresenter tvInfoPresenter;

    @InjectPresenter
    AccountPresenter accountPresenter;

    @InjectPresenter
    TitlePresenter titlePresenter;

    @BindView(R.id.activity_tv_info_scroll_view)
    NestedScrollView nestedScrollView;

    @BindView(R.id.activity_tv_play_button)
    FloatingActionButton playFButton;

    @BindView(R.id.adapter_main_photo_view)
    MainImageView mainImageView;

    @BindView(R.id.adapter_main_image_view_switcher)
    ViewSwitcher mainImageSwitcher;

    @BindView(R.id.adapter_main_info_switcher)
    ViewSwitcher mainInfoSwitcher;

    @BindView(R.id.adapter_main_info_view)
    MainInfoView mainInfoView;

    @BindView(R.id.activity_tv_show_tv_details_button)
    Button showDetailsButton;

    @BindView(R.id.activity_tv_show_all_tv_seasons_button)
    Button showSeasonsButton;

    @BindView(R.id.tv_seasons_recycler_view)
    RecyclerView seasonsRecyclerView;

    @BindView(R.id.activity_tv_gallery_swimming_line)
    SwimmingLineView gallerySwimmingView;

    @BindView(R.id.activity_tv_billed_cast_swimming_line)
    SwimmingLineView castSwimmingView;

    @BindView(R.id.activity_tv_recommended_swimming_line)
    SwimmingLineView recommendationsSwimmingView;

    @BindView(R.id.activity_tv_expandable_layout)
    ExpandableLinearLayout expandableLayout;

    @BindView(R.id.activity_tv_tags_text_view)
    TextView tagsView;

    @BindView(R.id.rate_view)
    RateView rateView;

    @BindView(R.id.activity_tv_action_view)
    MediaActionView actionView;

    private TitleCardFragmentCallback fragmentCallback;
    private TvSeasonsAdapter seasonsAdapter;

    private String videoPath;
    private Rating rating;

    private boolean isPlayFabVisible = true;
    private int tvId;
    private int numberOfSeasons;

    public static Fragment getInstance(final int id) {
        final Fragment fragment = new TvInfoFragment();
        final Bundle bundle = new Bundle();
        bundle.putInt(Constants.Extras.TV_SHOW_ID, id);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tv, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        try {
            fragmentCallback = (TitleCardFragmentCallback) getContext();
        } catch (final ClassCastException e) {
            throw new ClassCastException(getContext().toString() +
                    " must implement FragmentScrollListener");
        }
    }

    @Override
    protected void onViewsBinded() {
        setAnimation();
        initSwimmingLines();

        if (Constants.CURRENT_SESSION != null) {
            rateView.setVisibility(View.VISIBLE);
            rateView.setListener(this);

            actionView.setVisibility(View.VISIBLE);
            actionView.initialize(this, false);
        }

        seasonsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        seasonsAdapter = new TvSeasonsAdapter(this);
        seasonsRecyclerView.setAdapter(seasonsAdapter);
        ViewCompat.setNestedScrollingEnabled(seasonsRecyclerView, false);

        nestedScrollView.setOnScrollChangeListener(new TitleCardScrollListener() {

            @Override
            public void changeTransparency(final float transparencyValue) {
                mainImageView.setTransparency(transparencyValue);
            }

            @Override
            public void changeVisibility(final boolean visible) {
                isPlayFabVisible = visible;

                if (videoPath == null) {
                    return;
                }

                if (!visible) {
                    playFButton.hide();
                } else {
                    playFButton.show();
                }
            }

            @Override
            public void changeViewVisibility(final boolean isVisible) {
                actionView.toggle(isVisible);
                fragmentCallback.toggleToolbar(isVisible);
            }
        });

        playFButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(videoPath)));
            }
        });

        showDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                expandableLayout.expand();
                view.setVisibility(View.GONE);
            }
        });

        showSeasonsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                seasonsAdapter.expand();
                view.setVisibility(View.GONE);
            }
        });

        refresh();
    }

    private void setAnimation() {
        final Animation inAnim = new AlphaAnimation(0, 1);
        inAnim.setDuration(300);

        mainInfoSwitcher.setInAnimation(inAnim);
    }

    @Override
    public void refresh() {
        final Bundle bundle = getArguments();

        if (!bundle.isEmpty()) {
            this.tvId = bundle.getInt(Constants.Extras.TV_SHOW_ID);
            tvInfoPresenter.refresh(tvId, getContext());
        }

        actionView.toggleActiveState(tvInfoPresenter.checkNetworkState(getContext()));
    }

    private void initSwimmingLines() {
        gallerySwimmingView.createSkeletonTiles(SkeletonTileType.GALLERY_LANDSCAPE);
        castSwimmingView.createSkeletonTiles(SkeletonTileType.DESCRIPTION);
        recommendationsSwimmingView.createSkeletonTiles(SkeletonTileType.RATING);

        gallerySwimmingView.disableMoreButton();
        castSwimmingView.disableMoreButton();

        gallerySwimmingView.setOnSwimmingLineClickListener(this);
        castSwimmingView.setOnSwimmingLineClickListener(this);
        recommendationsSwimmingView.setOnSwimmingLineClickListener(this);

        recommendationsSwimmingView.disableMoreButton();
    }

    @Override
    public void setTvInfo(final TvShowData tvShowData, final boolean isDataFromServer) {
        videoPath = tvShowData.getVideoPath();
        mainImageView.setImage(tvShowData.getPosterPath());
        mainInfoView.setData(tvShowData.getMainInfoViewItem(), MainInfoViewType.SMALL_TITLE);

        titlePresenter.onGalleryPhotosFetched(tvShowData.getGalleryPhotoPaths());

        gallerySwimmingView.setData(
                SkeletonTileType.GALLERY_LANDSCAPE, getString(R.string.gallery), tvShowData.getGalleryLine());
        castSwimmingView.setData(
                SkeletonTileType.DESCRIPTION, getString(R.string.top_billed_cast), tvShowData.getCastLine());
        recommendationsSwimmingView.setData(
                SkeletonTileType.RATING, getString(R.string.recommendations), tvShowData.getRecommendationsLine());

        seasonsAdapter.setSeasons(tvShowData.getSeasons());
        showSeasonsButton.setVisibility(
                seasonsAdapter.isShowSeasonButton() ? View.VISIBLE : View.GONE);


        if (videoPath != null && isPlayFabVisible) {
            playFButton.setVisibility(View.VISIBLE);
        }

        final String tags = tvShowData.getTags();

        if (TextUtils.isEmpty(tags)) {
            showDetailsButton.setVisibility(View.GONE);
        } else {
            tagsView.setText(tags);
        }

        showViewSwitchers();

        if (isDataFromServer) {
            getArguments().clear();
        }

        numberOfSeasons = tvShowData.getNumberOfSeasons();
    }

    private void showViewSwitchers() {
        mainImageSwitcher.setDisplayedChild(1);
        mainInfoSwitcher.setDisplayedChild(1);
    }

    @Override
    public void onDestroy() {
        tvInfoPresenter.onDestroy();

        super.onDestroy();
    }

    @Override
    public void onMoreClicked(@TileType final String type) {

    }

    @Override
    public void onTileClicked(final int id, final String type, final View view) {
        titlePresenter.onTileClicked(id, type, view, getActivity());
    }

    @Override
    public void onError(@ErrorType final int errorType) {
        fragmentCallback.onError(this, errorType);

        if (errorType == ErrorType.INTERNET_CONNECTION_ERROR && Constants.CURRENT_SESSION != null) {
            actionView.toggleActiveState(false);
        }
    }

    @Override
    public void setCurrentRating(final Rating rating) {
        this.rating = rating;

        rateView.setData(String.format(Constants.BASE_AVATAR_URL, Constants.GRAVATAR_HASH),
                getString(R.string.tv_show), rating.getRated().getValue() / 2);

        actionView.changeFavoriteButton(rating.isFavorite());
        actionView.changeWatchlistButton(rating.isWatchlist());
    }

    @Override
    public void onRatingResult(final boolean isSuccess, final float rating) {
        rateView.update(isSuccess, rating);

        fragmentCallback.mediaItemChanged(ChangeType.RATED);
    }

    @Override
    public void onFavoriteResult(final boolean isAdded) {
        actionView.changeFavoriteButton(isAdded);

        if (!isAdded) {
            fragmentCallback.mediaItemChanged(ChangeType.FAVORITE);
        }
    }

    @Override
    public void onWatchlistResult(final boolean isAdded) {
        actionView.changeWatchlistButton(isAdded);

        if (!isAdded) {
            fragmentCallback.mediaItemChanged(ChangeType.WATCHLIST);
        }
    }

    @Override
    public void showMessage(final String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRetryClicked() {
        tvInfoPresenter.retryClicked(getContext());
    }

    @Override
    public void setRating(final float rating) {
        tvInfoPresenter.setRating(rating);
    }

    @Override
    public void deleteRating() {
        tvInfoPresenter.deleteRating();
    }

    @Override
    public void onActionButtonClick(final View view) {
        accountPresenter.onActionsButtonClicked(tvId, rating,
                getString(R.string.tv_media_type), view);
    }

    @Override
    public void startGalleryActivity(final Intent intent, final Bundle options) {
        startActivityForResult(intent, 0, options);
    }

    @Override
    public View getSharedView(final String tag) {
        return gallerySwimmingView.getViewByTag(tag);
    }

    @Override
    public void startListActivity() {

    }

    @Override
    public void onSeasonClicked(final int seasonNumber, final List<SeasonItem> seasonItemList) {
        titlePresenter.onSeasonClicked(tvId, seasonNumber, numberOfSeasons, seasonItemList);
    }
}
