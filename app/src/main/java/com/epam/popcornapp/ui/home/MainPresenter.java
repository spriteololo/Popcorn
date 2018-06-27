package com.epam.popcornapp.ui.home;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Pair;
import android.view.MenuItem;
import android.view.View;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.epam.popcornapp.R;
import com.epam.popcornapp.application.BaseApplication;
import com.epam.popcornapp.application.Constants;
import com.epam.popcornapp.ui.lists.detail.ListDetailParams;
import com.epam.popcornapp.ui.login.base.LoginModel;
import com.epam.popcornapp.ui.tiles.credits.TileType;
import com.epam.popcornapp.ui.tv.episode.TvEpisodeParams;

import javax.inject.Inject;

import ru.terrakok.cicerone.Router;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainPresenterView> {

    @Inject
    Context context;

    @Inject
    Router router;

    public MainPresenter() {
        BaseApplication.getApplicationComponent().inject(this);
    }

    void onNavigationItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                router.replaceScreen(Constants.Screens.HOME_FRAGMENT, item.getTitle());

                break;
            case R.id.nav_movies:
                router.replaceScreen(Constants.Screens.MOVIES_FRAGMENT, item.getTitle());

                break;
            case R.id.nav_TVset:
                router.replaceScreen(Constants.Screens.TV_FRAGMENT, item.getTitle());

                break;
            case R.id.nav_people:
                router.replaceScreen(Constants.Screens.PEOPLE_FRAGMENT, item.getTitle());

                break;
            case R.id.nav_rated:
                router.replaceScreen(Constants.Screens.RATED_FRAGMENT, item.getTitle());

                break;
            case R.id.nav_watchlist:
                router.replaceScreen(Constants.Screens.WATCHLIST_FRAGMENT, item.getTitle());

                break;
            case R.id.nav_favorites:
                router.replaceScreen(Constants.Screens.FAVORITE_FRAGMENT, item.getTitle());

                break;
            case R.id.nav_lists:
                router.replaceScreen(Constants.Screens.LISTS_FRAGMENT, item.getTitle());

                break;
            case R.id.nav_settings:
                router.navigateTo(Constants.Screens.SETTINGS_SCREEN);

                break;
        }
    }

    public void onHeaderClicked(final View view) {
        if (Constants.CURRENT_USER == null) {
            router.navigateTo(Constants.Screens.LOGIN_SCREEN, false);
        } else {
            router.navigateTo(Constants.Screens.ACCOUNT_SCREEN, Pair.create(view, context.getString(R.string.avatarTransition)));
        }
    }

    public void checkLogInStatus() {
        if (Constants.CURRENT_SESSION == null) {
            LoginModel.getLoginModel().signOut();
        } else {
            LoginModel.getLoginModel().signIn();
        }
    }

    public void onMorePressed(final String presenterName) {
        router.navigateTo(Constants.Screens.MORE_SCREEN, presenterName);
    }

    public void onTileClicked(final int id, @TileType final String type) {
        if (type == null) {
            return;
        }

        switch (type) {
            case TileType.MOVIE:
                router.navigateTo(Constants.Screens.MOVIE_SCREEN, id);

                break;
            case TileType.TV:
                router.navigateTo(Constants.Screens.TV_SHOW_SCREEN, id);

                break;
            case TileType.ACTOR:
                router.navigateTo(Constants.Screens.ACTOR_SCREEN, id);

                break;
            default:
        }
    }

    public void searchClicked(@TileType final String type) {
        router.navigateTo(Constants.Screens.SEARCH_SCREEN, type);
    }

    public void onEpisodeClicked(@NonNull final TvEpisodeParams tvEpisodeParams) {
        router.navigateTo(Constants.Screens.TV_EPISODE_SCREEN, tvEpisodeParams);
    }

    public void onListTitleCardClicked(@NonNull final ListDetailParams listDetailParams) {
        router.navigateTo(Constants.Screens.LIST_TITLE_CARD_SCREEN, listDetailParams);
    }

    public void onAddMediaClicked(final int listId) {
        router.navigateTo(Constants.Screens.LIST_ADD_MEDIA_SCREEN, listId);
    }
}
