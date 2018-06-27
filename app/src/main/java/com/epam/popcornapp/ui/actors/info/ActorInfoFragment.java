package com.epam.popcornapp.ui.actors.info;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ViewSwitcher;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.epam.popcornapp.R;
import com.epam.popcornapp.application.Constants;
import com.epam.popcornapp.application.ErrorMessage;
import com.epam.popcornapp.application.ErrorMessage.ErrorType;
import com.epam.popcornapp.ui.all.base.BaseMvpFragment;
import com.epam.popcornapp.ui.credits.ActorCreditsLayout;
import com.epam.popcornapp.ui.gallery.FragmentWithGallery;
import com.epam.popcornapp.ui.home.RefreshInterface;
import com.epam.popcornapp.ui.informationViews.MainInfoView;
import com.epam.popcornapp.ui.informationViews.MainInfoViewType;
import com.epam.popcornapp.ui.mainViews.MainImageType;
import com.epam.popcornapp.ui.mainViews.MainImageView;
import com.epam.popcornapp.ui.swimming.SkeletonTileType;
import com.epam.popcornapp.ui.swimming.SwimmingLineView;
import com.epam.popcornapp.ui.tiles.credits.TileType;
import com.epam.popcornapp.ui.titles.TitleCardFragmentCallback;
import com.epam.popcornapp.ui.titles.TitlePresenter;
import com.epam.popcornapp.ui.titles.TitleView;
import com.epam.popcornapp.ui.utils.TitleCardScrollListener;

import butterknife.BindView;

public class ActorInfoFragment extends BaseMvpFragment implements
        ActorInfoView,
        TitleView,
        ActorCreditsLayout.ClickListener,
        ErrorMessage.onErrorMessageClickListener,
        SwimmingLineView.OnSwimmingLineClickListener,
        RefreshInterface, FragmentWithGallery {

    @InjectPresenter
    ActorInfoPresenter actorInfoPresenter;

    @InjectPresenter
    TitlePresenter titlePresenter;

    @BindView(R.id.adapter_main_photo_view)
    MainImageView mainImageView;

    @BindView(R.id.adapter_main_image_view_switcher)
    ViewSwitcher mainImageSwitcher;

    @BindView(R.id.adapter_main_info_view)
    MainInfoView infoView;

    @BindView(R.id.adapter_main_info_switcher)
    ViewSwitcher infoSwitcher;

    @BindView(R.id.activity_actor_gallery_swimming_line)
    SwimmingLineView gallerySwimmingView;

    @BindView(R.id.activity_actor_known_for_swimming_line)
    SwimmingLineView knownForSwimmingView;

    @BindView(R.id.activity_movie_nested_scroll_view)
    NestedScrollView nestedScrollView;

    @BindView(R.id.activity_actor_credits_layout)
    ActorCreditsLayout actorCreditsLayout;

    private TitleCardFragmentCallback fragmentCallback;

    public static Fragment getInstance(final int id) {
        final Fragment fragment = new ActorInfoFragment();
        final Bundle bundle = new Bundle();
        bundle.putInt(Constants.Extras.ACTOR_ID, id);
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

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_actor, container, false);
    }

    @Override
    protected void onViewsBinded() {
        setAnimation();
        initSwimmingLines();

        mainImageView.setViewSetting(MainImageType.PERSON);

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
                fragmentCallback.toggleToolbar(isVisible);
            }
        });

        actorCreditsLayout.setClickListener(this);
        gallerySwimmingView.setOnSwimmingLineClickListener(this);

        refresh();
    }

    private void setAnimation() {
        final Animation inAnim = new AlphaAnimation(0, 1);
        inAnim.setDuration(300);

        infoSwitcher.setInAnimation(inAnim);
    }

    @Override
    public void refresh() {
        final Bundle bundle = getArguments();

        if (!bundle.isEmpty()) {
            actorInfoPresenter.refresh(bundle.getInt(Constants.Extras.ACTOR_ID));
        }
    }


    private void initSwimmingLines() {
        gallerySwimmingView.createSkeletonTiles(SkeletonTileType.GALLERY_PORTRAIT);
        knownForSwimmingView.createSkeletonTiles(SkeletonTileType.RATING);
        gallerySwimmingView.disableMoreButton();
        knownForSwimmingView.disableMoreButton();
        knownForSwimmingView.setOnSwimmingLineClickListener(this);
    }

    @Override
    public void setBaseInfo(final ActorInfoDetails details, final boolean isDataFromServer) {
        mainImageView.setImage(details.getImagePath());
        infoView.setData(details.getMainInfoViewItem(), MainInfoViewType.NO_RATING_BIG_TITLE);

        titlePresenter.onGalleryPhotosFetched(details.getGalleryPhotoPaths());

        gallerySwimmingView.setData(
                SkeletonTileType.GALLERY_PORTRAIT, getString(R.string.gallery), details.getImagePathList());
        knownForSwimmingView.setData(
                SkeletonTileType.RATING, getString(R.string.known_for), details.getKnownFor());

        actorCreditsLayout.setData(details.getListCredits());

        showViewSwitchers();

        if (isDataFromServer) {
            getArguments().clear();
        }
    }

    private void showViewSwitchers() {
        mainImageSwitcher.setDisplayedChild(1);
        infoSwitcher.setDisplayedChild(1);
    }

    @Override
    public void onItemClick(final int id, final String mediaType) {
        titlePresenter.onTileClicked(id, mediaType, null, getActivity());
    }

    @Override
    public void onPause() {
        super.onPause();

        actorInfoPresenter.onDestroy();
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
    }

    @Override
    public void onRetryClicked() {
        actorInfoPresenter.retryClicked(getContext());
    }

    @Override
    public void startGalleryActivity(final Intent intent, final Bundle options) {
        startActivityForResult(intent, 0, options);
    }

    @Override
    public View getSharedView(final String tag) {
        return gallerySwimmingView.getViewByTag(tag);
    }
}
