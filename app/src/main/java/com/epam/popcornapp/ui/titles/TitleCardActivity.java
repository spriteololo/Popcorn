package com.epam.popcornapp.ui.titles;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.SharedElementCallback;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.bumptech.glide.manager.SupportRequestManagerFragment;
import com.epam.popcornapp.R;
import com.epam.popcornapp.application.BaseApplication;
import com.epam.popcornapp.application.Constants;
import com.epam.popcornapp.application.Constants.ChangeType;
import com.epam.popcornapp.application.ErrorMessage;
import com.epam.popcornapp.application.ErrorMessage.ErrorType;
import com.epam.popcornapp.application.ErrorMessage.onErrorMessageClickListener;
import com.epam.popcornapp.ui.actors.info.ActorInfoFragment;
import com.epam.popcornapp.ui.all.base.BaseMvpActivity;
import com.epam.popcornapp.ui.all.base.NetworkStateReceiver;
import com.epam.popcornapp.ui.all.base.NetworkStateReceiver.NetworkStateReceiverListener;
import com.epam.popcornapp.ui.gallery.FragmentWithGallery;
import com.epam.popcornapp.ui.home.PopcornAppNavigator;
import com.epam.popcornapp.ui.home.RefreshInterface;
import com.epam.popcornapp.ui.movies.info.MovieInfoFragment;
import com.epam.popcornapp.ui.tiles.credits.TileType;
import com.epam.popcornapp.ui.tv.info.TvInfoFragment;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.NavigatorHolder;

public class TitleCardActivity extends BaseMvpActivity implements
        TitleView,
        TitleCardFragmentCallback,
        NetworkStateReceiverListener {

    @Inject
    ErrorMessage errorMessage;

    @Inject
    NavigatorHolder navigatorHolder;

    @Inject
    NetworkStateReceiver networkStateReceiver;

    @BindView(R.id.title_container_layout)
    FrameLayout containerLayout;

    @BindView(R.id.transparent_toolbar)
    Toolbar toolbar;

    @BindView(R.id.title_toolbar_container)
    View toolbarContainer;

    @InjectPresenter
    TitlePresenter presenter;

    private Navigator navigator;

    public static Intent start(final String type, final int id, final Context context) {
        final Intent intent = new Intent(context, TitleCardActivity.class);
        intent.putExtra(Constants.Extras.TITLE_TYPE, type);
        intent.putExtra(Constants.Extras.TITLE_ID, id);

        return intent;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title_card);

        BaseApplication.getApplicationComponent().inject(this);

        navigator = getNavigator();
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
    }

    @Override
    protected void onViewsBinded() {
        setToolbarNoTitle(toolbar, true);

        final Intent intent = getIntent();
        final String type = intent.getStringExtra(Constants.Extras.TITLE_TYPE);
        final int id = intent.getIntExtra(Constants.Extras.TITLE_ID, -1);

        presenter.init(type, id);
    }

    private Navigator getNavigator() {
        return new PopcornAppNavigator(
                TitleCardActivity.this,
                R.id.title_container_layout) {

            @Override
            protected Fragment createFragment(final String screenKey, final Object data) {
                @TileType final String type = screenKey;

                switch (type) {
                    case TileType.ACTOR:
                        return ActorInfoFragment.getInstance((int) data);
                    case TileType.MOVIE:
                        return MovieInfoFragment.getInstance((int) data);
                    case TileType.TV:
                        return TvInfoFragment.getInstance((int) data);
                    default:
                        throw new IllegalArgumentException();
                }
            }
        };
    }

    @Override
    public void onError(@NonNull final onErrorMessageClickListener errorMessageClickListener,
                        @ErrorType final int errorType) {
        errorMessage.showSnackbar(this, containerLayout, errorMessageClickListener, errorType);
    }

    @Override
    public void mediaItemChanged(@ChangeType final int changeType) {
        setResult(changeType);
    }

    @Override
    public void toggleToolbar(final boolean visible) {
        toolbarContainer.animate().translationY(visible ?
                Constants.START_COORDINATE : Constants.TOOLBAR_ANIMATION_TRANSLATION)
                .setDuration(Constants.TOOLBAR_ANIMATION_DURATION);
    }

    @Override
    public void networkAvailable() {
        final RefreshInterface refreshInterface = (RefreshInterface) getSupportFragmentManager().
                findFragmentById(R.id.title_container_layout);

        if (refreshInterface == null) {
            return;
        }

        refreshInterface.refresh();

        errorMessage.removeSnackbar();
    }

    @Override
    public void networkUnavailable() {
        final RefreshInterface refreshInterface = (RefreshInterface) getSupportFragmentManager().
                findFragmentById(R.id.title_container_layout);

        if (refreshInterface == null) {
            return;
        }

        refreshInterface.onError(ErrorType.INTERNET_CONNECTION_ERROR);
    }

    @Override
    public void startGalleryActivity(final Intent intent, final Bundle options) {

    }

    @Override
    public void onActivityReenter(final int resultCode, final Intent data) {
        super.onActivityReenter(resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            Fragment fragment = getSupportFragmentManager().getFragments().get(0);

            if (fragment instanceof SupportRequestManagerFragment) {
                fragment = getSupportFragmentManager().getFragments().get(1);
            }

            if (fragment instanceof FragmentWithGallery) {
                final int exitPosition = data.getIntExtra(Constants.Extras.EXIT_POSITION, 0);
                final View view = ((FragmentWithGallery) fragment)
                        .getSharedView(Constants.Extras.TAG + exitPosition);
                updateSharedElements(view);
            }
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
}
