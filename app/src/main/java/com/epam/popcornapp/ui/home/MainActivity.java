package com.epam.popcornapp.ui.home;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.epam.popcornapp.R;
import com.epam.popcornapp.application.BaseApplication;
import com.epam.popcornapp.application.Constants;
import com.epam.popcornapp.application.ErrorMessage;
import com.epam.popcornapp.application.ErrorMessage.ErrorType;
import com.epam.popcornapp.application.ErrorMessage.onErrorMessageClickListener;
import com.epam.popcornapp.glide.GlideApp;
import com.epam.popcornapp.ui.actors.ActorsFragment;
import com.epam.popcornapp.ui.all.base.BaseMvpActivity;
import com.epam.popcornapp.ui.all.base.NetworkStateReceiver;
import com.epam.popcornapp.ui.all.base.NetworkStateReceiver.NetworkStateReceiverListener;
import com.epam.popcornapp.ui.favorite.FavoriteFragment;
import com.epam.popcornapp.ui.lists.ListsFragment;
import com.epam.popcornapp.ui.login.base.LoginModel;
import com.epam.popcornapp.ui.movies.MoviesFragment;
import com.epam.popcornapp.ui.rated.RatedFragment;
import com.epam.popcornapp.ui.tv.TvFragment;
import com.epam.popcornapp.ui.watchlist.WatchlistFragment;

import javax.inject.Inject;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.NavigatorHolder;

public class MainActivity extends BaseMvpActivity implements
        MainPresenterView,
        NavigationView.OnNavigationItemSelectedListener,
        SwipeRefreshLayout.OnRefreshListener,
        FragmentCallback,
        NetworkStateReceiverListener,
        LoginModel.LoginChangeListener {

    @Inject
    ErrorMessage errorMessage;

    @Inject
    NavigatorHolder navigatorHolder;

    @Inject
    NetworkStateReceiver networkStateReceiver;

    @InjectPresenter
    MainPresenter presenter;

    @BindView(R.id.main_navigation_view)
    NavigationView navigationView;

    @BindView(R.id.main_drawer_layout)
    DrawerLayout drawerLayout;

    @BindView(R.id.blur_toolbar)
    Toolbar toolbar;

    @BindView(R.id.main_container_layout)
    FrameLayout containerLayout;

    @BindView(R.id.activity_main_swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    TextView headerLogin;
    CircleImageView headerImage;

    private boolean isHideSearchButton;

    private Navigator navigator;

    public static Intent start(@NonNull final Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BaseApplication.getApplicationComponent().inject(this);

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        navigator = getNavigator();
    }

    @Override
    protected void onViewsBinded() {
        setToolbar(toolbar, getString(R.string.app_name), true);

        LoginModel.getLoginModel().setLoginChangeListener(this);

        final ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.drawer_open,
                R.string.drawer_close);

        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        final View header = navigationView.getHeaderView(0);
        headerLogin = header.findViewById(R.id.navigation_header_login);
        headerImage = header.findViewById(R.id.navigation_header_image);

        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                presenter.onHeaderClicked(headerImage);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.transparent);
        swipeRefreshLayout.setProgressViewOffset(true, 100, 200);

        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_home);
        final MenuItem item = navigationView.getMenu().findItem(R.id.nav_home);

        presenter.onNavigationItemSelected(item);
        presenter.checkLogInStatus();
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
    public boolean onCreateOptionsMenu(final Menu menu) {
        if (!isHideSearchButton) {
            getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        }

        return super.onCreateOptionsMenu(menu);
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
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull final MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);
        presenter.onNavigationItemSelected(item);

        return !(item.getItemId() == R.id.nav_settings);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        return item.getItemId() != R.id.action_search && super.onOptionsItemSelected(item);
    }

    private Navigator getNavigator() {
        return new PopcornAppNavigator(
                MainActivity.this,
                R.id.main_container_layout) {
            @Override
            protected Fragment createFragment(final String screenKey, final Object data) {
                setToolbarTitle((String) data);

                switch (screenKey) {
                    case Constants.Screens.HOME_FRAGMENT:
                        return HomeFragment.getInstance();
                    case Constants.Screens.MOVIES_FRAGMENT:
                        return MoviesFragment.getInstance();
                    case Constants.Screens.TV_FRAGMENT:
                        return TvFragment.getInstance();
                    case Constants.Screens.PEOPLE_FRAGMENT:
                        return ActorsFragment.getInstance();
                    case Constants.Screens.RATED_FRAGMENT:
                        return RatedFragment.getInstance();
                    case Constants.Screens.WATCHLIST_FRAGMENT:
                        return WatchlistFragment.getInstance();
                    case Constants.Screens.FAVORITE_FRAGMENT:
                        return FavoriteFragment.getInstance();
                    case Constants.Screens.LISTS_FRAGMENT:
                        return ListsFragment.getInstance();
                    default:
                        throw new RuntimeException("Unknown screen key!");
                }
            }
        };
    }

    @Override
    public void onRefresh() {
        final Fragment fragment = getSupportFragmentManager().
                findFragmentById(R.id.main_container_layout);

        try {
            final RefreshInterface refreshInterface = (RefreshInterface) fragment;

            refreshInterface.refresh();
        } catch (final ClassCastException e) {
            Log.e("MainActivity", "", e);
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onError(@NonNull final onErrorMessageClickListener errorMessageClickListener,
                        @ErrorType final int errorType, final View view) {
        if (view == null) {
            errorMessage.showSnackbar(this, containerLayout, errorMessageClickListener, errorType);
        } else {
            errorMessage.showSnackbar(this, view, errorMessageClickListener, errorType);
        }
    }

    @Override
    public void removeRefreshAnimation() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showSearchButton() {
        isHideSearchButton = false;

        invalidateOptionsMenu();
    }

    @Override
    public void hideSearchButton() {
        isHideSearchButton = true;

        invalidateOptionsMenu();
    }

    @Override
    public void networkAvailable() {
        final RefreshInterface refreshInterface = (RefreshInterface) getSupportFragmentManager().
                findFragmentById(R.id.main_container_layout);

        if (refreshInterface == null) {
            return;
        }

        refreshInterface.refresh();

        errorMessage.removeSnackbar();
    }

    @Override
    public void networkUnavailable() {
        final RefreshInterface refreshInterface = (RefreshInterface) getSupportFragmentManager().
                findFragmentById(R.id.main_container_layout);

        if (refreshInterface == null) {
            return;
        }

        refreshInterface.onError(ErrorType.INTERNET_CONNECTION_ERROR);
    }

    @Override
    public void signIn() {
        if (TextUtils.isEmpty(Constants.GRAVATAR_HASH)) {
            setAccountIcon();
        } else {
            GlideApp
                    .with(this)
                    .load(Constants.GRAVATAR_URL +
                            Constants.GRAVATAR_HASH +
                            "?s=" +
                            (int) getResources().getDimension(R.dimen.account_icon_size))
                    .error(R.drawable.icon_account)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .into(headerImage);
        }

        headerLogin.setText(Constants.CURRENT_USER);

        navigationView.getMenu().setGroupVisible(R.id.account_group, true);
    }

    @Override
    public void signOut() {
        setAccountIcon();
        headerLogin.setText(getString(R.string.login));

        navigationView.setCheckedItem(R.id.nav_home);
        presenter.onNavigationItemSelected(navigationView.getMenu().findItem(R.id.nav_home));

        navigationView.getMenu().setGroupVisible(R.id.account_group, false);
    }

    private void setAccountIcon() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            headerImage.setImageResource(R.drawable.icon_account);
        } else {
            final Drawable drawable = VectorDrawableCompat.create(getResources(), R.drawable.icon_account, null);

            if (drawable != null) {
                headerImage.setImageDrawable(DrawableCompat.wrap(drawable));
            }
        }
    }
}
