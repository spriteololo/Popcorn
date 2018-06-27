package com.epam.popcornapp.ui.movies.info;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.epam.popcornapp.pojo.movies.release_dates.opt.ConvertedReleaseDate;
import com.epam.popcornapp.ui.account.AccountPresenter;
import com.epam.popcornapp.ui.account.AccountView;
import com.epam.popcornapp.ui.actionViews.MediaActionView;
import com.epam.popcornapp.ui.all.base.BaseMvpFragment;
import com.epam.popcornapp.ui.expandable.ExpandableLinearLayout;
import com.epam.popcornapp.ui.gallery.FragmentWithGallery;
import com.epam.popcornapp.ui.home.RefreshInterface;
import com.epam.popcornapp.ui.informationViews.MainInfoView;
import com.epam.popcornapp.ui.informationViews.MainInfoViewType;
import com.epam.popcornapp.ui.mainViews.MainImageView;
import com.epam.popcornapp.ui.rateView.RateView;
import com.epam.popcornapp.ui.rateView.RateView.RateViewListener;
import com.epam.popcornapp.ui.review.CurrentReviewView;
import com.epam.popcornapp.ui.swimming.SkeletonTileType;
import com.epam.popcornapp.ui.swimming.SwimmingLineView;
import com.epam.popcornapp.ui.tiles.credits.TileType;
import com.epam.popcornapp.ui.titles.TitleCardFragmentCallback;
import com.epam.popcornapp.ui.titles.TitlePresenter;
import com.epam.popcornapp.ui.titles.TitleView;
import com.epam.popcornapp.ui.utils.TitleCardScrollListener;

import java.util.ArrayList;

import butterknife.BindView;

public class MovieInfoFragment extends BaseMvpFragment implements
        MovieInfoView,
        AccountView,
        TitleView,
        ErrorMessage.onErrorMessageClickListener,
        SwimmingLineView.OnSwimmingLineClickListener,
        RefreshInterface,
        RateViewListener,
        MediaActionView.ActionViewListeners,
        FragmentWithGallery {

    @InjectPresenter
    MovieInfoPresenter movieInfoPresenter;

    @InjectPresenter
    AccountPresenter accountPresenter;

    @InjectPresenter
    TitlePresenter titlePresenter;

    @BindView(R.id.activity_movie_nested_scroll_view)
    NestedScrollView nestedScrollView;

    @BindView(R.id.adapter_main_photo_view)
    MainImageView mainPhotoView;

    @BindView(R.id.adapter_main_image_view_switcher)
    ViewSwitcher mainImageSwitcher;

    @BindView(R.id.adapter_current_review_switcher)
    ViewSwitcher currentReviewSwitcher;

    @BindView(R.id.activity_movie_play_button)
    FloatingActionButton playFButton;

    @BindView(R.id.adapter_main_info_switcher)
    ViewSwitcher mainInfoSwitcher;

    @BindView(R.id.adapter_main_info_view)
    MainInfoView mainInfoView;

    @BindView(R.id.activity_movie_show_details_button)
    Button showDetailsButton;

    @BindView(R.id.review_more_button)
    Button reviewMoreButton;

    @BindView(R.id.activity_movie_expandable_layout)
    ExpandableLinearLayout rootLayout;

    @BindView(R.id.activity_movie_tags_block)
    View tagsBlockView;

    @BindView(R.id.activity_movie_budget_block)
    View budgetBlockView;

    @BindView(R.id.activity_movie_revenue_block)
    View revenueBlockView;

    @BindView(R.id.activity_movie_release_information_block)
    View releaseInformationBlockView;

    @BindView(R.id.activity_movie_tags_text_view)
    TextView tagsView;

    @BindView(R.id.activity_movie_budget_text_view)
    TextView budgetView;

    @BindView(R.id.activity_movie_revenue_text_view)
    TextView revenueView;

    @BindView(R.id.activity_movie_gallery_swimming_line)
    SwimmingLineView gallerySwimmingView;

    @BindView(R.id.activity_movie_billed_cast_swimming_line)
    SwimmingLineView billedCastSwimmingView;

    @BindView(R.id.activity_movie_recommendations_swimming_line)
    SwimmingLineView recommendationsSwimmingView;

    @BindView(R.id.activity_movie_release_dates_recycler_view)
    RecyclerView releaseDatesRecyclerView;

    @BindView(R.id.adapter_current_review_view)
    CurrentReviewView currentReviewView;

    @BindView(R.id.activity_movie_rate_view)
    RateView rateView;

    @BindView(R.id.activity_movie_action_view)
    MediaActionView actionView;

    private TitleCardFragmentCallback fragmentCallback;

    private MovieReleaseDatesAdapter releaseDatesAdapter;

    private int movieId;
    private String videoPath;
    private Rating rating;

    private boolean isPlayFabVisible = true;

    public static Fragment getInstance(final int id) {
        final Fragment fragment = new MovieInfoFragment();
        final Bundle bundle = new Bundle();
        bundle.putInt(Constants.Extras.MOVIE_ID, id);
        fragment.setArguments(bundle);

        return fragment;
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
    public void onPause() {
        super.onPause();

        movieInfoPresenter.onDestroy();
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    protected void onViewsBinded() {
        setAnimation();
        initSwimmingLines();
        initReleaseDates();

        if (Constants.CURRENT_SESSION != null) {
            rateView.setVisibility(View.VISIBLE);
            rateView.setListener(this);

            actionView.setVisibility(View.VISIBLE);
            actionView.initialize(this, true);
        }

        nestedScrollView.setOnScrollChangeListener(new TitleCardScrollListener() {

            @Override
            public void changeTransparency(final float transparencyValue) {
                mainPhotoView.setTransparency(transparencyValue);
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
                rootLayout.expand();
                view.setVisibility(View.GONE);
            }
        });

        reviewMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                movieInfoPresenter.onMorePressed(currentReviewView.getData());
            }
        });

        refresh();
    }

    private void setAnimation() {
        final Animation inAnim = new AlphaAnimation(0, 1);
        inAnim.setDuration(300);

        mainInfoSwitcher.setInAnimation(inAnim);
        currentReviewSwitcher.setInAnimation(inAnim);
    }

    @Override
    public void refresh() {
        final Bundle bundle = getArguments();

        if (!bundle.isEmpty()) {
            this.movieId = bundle.getInt(Constants.Extras.MOVIE_ID, -1);

            movieInfoPresenter.getData(movieId, getContext());
        }

        actionView.toggleActiveState(movieInfoPresenter.checkNetworkState(getContext()));
    }

    private void initSwimmingLines() {
        gallerySwimmingView.createSkeletonTiles(SkeletonTileType.GALLERY_LANDSCAPE);
        billedCastSwimmingView.createSkeletonTiles(SkeletonTileType.DESCRIPTION);
        recommendationsSwimmingView.createSkeletonTiles(SkeletonTileType.RATING);

        gallerySwimmingView.disableMoreButton();
        billedCastSwimmingView.disableMoreButton();

        gallerySwimmingView.setOnSwimmingLineClickListener(this);
        billedCastSwimmingView.setOnSwimmingLineClickListener(this);
        recommendationsSwimmingView.setOnSwimmingLineClickListener(this);

        recommendationsSwimmingView.disableMoreButton();
    }

    private void initReleaseDates() {
        releaseDatesAdapter = new MovieReleaseDatesAdapter(new ArrayList<ConvertedReleaseDate>());

        releaseDatesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        releaseDatesRecyclerView.setAdapter(releaseDatesAdapter);

        ViewCompat.setNestedScrollingEnabled(releaseDatesRecyclerView, false);
    }

    @Override
    public void setDetail(@NonNull final MovieInfoData data, final boolean isDataFromServer) {
        videoPath = data.getVideoPath();
        mainPhotoView.setImage(data.getPosterPath());
        tagsView.setText(data.getTags());
        budgetView.setText(data.getBudget());
        revenueView.setText(data.getRevenue());
        mainInfoView.setData(data.getMainInfoViewItem(), MainInfoViewType.SMALL_TITLE);
        releaseDatesAdapter.setData(data.getConvertedReleaseDate());

        titlePresenter.onGalleryPhotosFetched(data.getGalleryPhotoPaths());

        gallerySwimmingView.setData(
                SkeletonTileType.GALLERY_LANDSCAPE, getString(R.string.gallery), data.getImagePathList());
        billedCastSwimmingView.setData(
                SkeletonTileType.DESCRIPTION, getString(R.string.top_billed_cast), data.getBilledCastList());
        recommendationsSwimmingView.setData(
                SkeletonTileType.RATING, getString(R.string.recommendations), data.getRecommendationsList());

        playFButton.setVisibility(videoPath != null && isPlayFabVisible ? View.VISIBLE : View.INVISIBLE);

        if (data.getReviewItemList() == null || data.getReviewItemList().isEmpty()) {
            currentReviewSwitcher.setVisibility(View.GONE);
        } else {
            currentReviewView.setReviews(data.getReviewItemList(), false);
        }

        showViewSwitchers();

        checkComponentsVisibility();

        if (isDataFromServer) {
            getArguments().clear();
        }
    }

    private void checkComponentsVisibility() {
        boolean isDetailInfoEmpty = true;

        if (!TextUtils.isEmpty(tagsView.getText())) {
            isDetailInfoEmpty = false;
            tagsBlockView.setVisibility(View.VISIBLE);
        }

        if (!TextUtils.isEmpty(budgetView.getText())) {
            isDetailInfoEmpty = false;
            budgetBlockView.setVisibility(View.VISIBLE);
        }

        if (!TextUtils.isEmpty(revenueView.getText())) {
            isDetailInfoEmpty = false;
            revenueBlockView.setVisibility(View.VISIBLE);
        }

        if (releaseDatesAdapter.getItemCount() != 0) {
            isDetailInfoEmpty = false;
            releaseInformationBlockView.setVisibility(View.VISIBLE);
        }

        if (isDetailInfoEmpty) {
            showDetailsButton.setVisibility(View.GONE);
        }
    }

    @Override
    public void setCurrentRating(final Rating rating) {
        this.rating = rating;

        rateView.setData(String.format(Constants.BASE_AVATAR_URL, Constants.GRAVATAR_HASH),
                getString(R.string.movie), rating.getRated().value / 2);

        actionView.changeFavoriteButton(rating.isFavorite());
        actionView.changeWatchlistButton(rating.isWatchlist());
    }

    private void showViewSwitchers() {
        mainInfoSwitcher.setDisplayedChild(1);
        mainImageSwitcher.setDisplayedChild(1);
        currentReviewSwitcher.setDisplayedChild(1);
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

        if (errorType == ErrorType.INTERNET_CONNECTION_ERROR) {
            actionView.toggleActiveState(false);
        }
    }

    @Override
    public void onRetryClicked() {
        movieInfoPresenter.retryClicked(getContext());
    }

    @Override
    public void setRating(final float rating) {
        movieInfoPresenter.setRating(rating);
    }

    @Override
    public void deleteRating() {
        movieInfoPresenter.deleteRating();
    }

    @Override
    public void ratingUpdate(final boolean isSuccess, final float rating) {
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
    public void onActionButtonClick(final View view) {
        accountPresenter.onActionsButtonClicked(movieId, rating,
                getString(R.string.movie_media_type), view);
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
        titlePresenter.startListActivity(movieId);
    }
}
