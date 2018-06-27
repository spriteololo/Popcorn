package com.epam.popcornapp.ui.home;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Pair;

import com.epam.popcornapp.application.Constants;
import com.epam.popcornapp.ui.gallery.GalleryActivity;
import com.epam.popcornapp.ui.gallery.GalleryParams;
import com.epam.popcornapp.ui.lists.ListsActivity;
import com.epam.popcornapp.ui.lists.detail.ListDetailActivity;
import com.epam.popcornapp.ui.lists.detail.ListDetailParams;
import com.epam.popcornapp.ui.lists.media.ListAddMediaActivity;
import com.epam.popcornapp.ui.login.account_details.AccountDetailsActivity;
import com.epam.popcornapp.ui.login.sign_in.SignInActivity;
import com.epam.popcornapp.ui.more.MoreActivity;
import com.epam.popcornapp.ui.review.ReviewActivity;
import com.epam.popcornapp.ui.review.ReviewItem;
import com.epam.popcornapp.ui.search.SearchActivity;
import com.epam.popcornapp.ui.settings.SettingsActivity;
import com.epam.popcornapp.ui.titles.TitleCardActivity;
import com.epam.popcornapp.ui.tv.episode.TvEpisodeActivity;
import com.epam.popcornapp.ui.tv.episode.TvEpisodeParams;
import com.epam.popcornapp.ui.tv.season.StartParams;
import com.epam.popcornapp.ui.tv.season.TvSeasonActivity;

import java.util.List;

import ru.terrakok.cicerone.android.SupportAppNavigator;
import ru.terrakok.cicerone.commands.Command;
import ru.terrakok.cicerone.commands.Forward;

public class PopcornAppNavigator extends SupportAppNavigator {
    private Activity activity;

    public PopcornAppNavigator(final FragmentActivity activity) {
        super(activity, 0);
        this.activity = activity;
    }

    public PopcornAppNavigator(final FragmentActivity activity, final int containerId) {
        super(activity, containerId);
        this.activity = activity;
    }

    public PopcornAppNavigator(final FragmentActivity activity, final FragmentManager fragmentManager, final int containerId) {
        super(activity, fragmentManager, containerId);
    }

    @Override
    protected Intent createActivityIntent(final String screenKey, final Object data) {
        switch (screenKey) {
            case Constants.Screens.MORE_SCREEN:
                return MoreActivity.start(activity, (String) data);
            case Constants.Screens.ACTOR_SCREEN:
            case Constants.Screens.MOVIE_SCREEN:
            case Constants.Screens.TV_SHOW_SCREEN:
                return TitleCardActivity.start(screenKey, (int) data, activity);
            case Constants.Screens.LOGIN_SCREEN:
                return SignInActivity.start(activity, (boolean) data);
            case Constants.Screens.ACCOUNT_SCREEN:
                return AccountDetailsActivity.start(activity);
            case Constants.Screens.GALLERY_PHOTO_SCREEN:
                final GalleryParams param = (GalleryParams) data;
                return GalleryActivity.start(activity, param);
            case Constants.Screens.SEARCH_SCREEN:
                return SearchActivity.start(activity, String.valueOf(data));
            case Constants.Screens.REVIEWS_SCREEN:
                return ReviewActivity.start(activity, (List<ReviewItem>) data);
            case Constants.Screens.TV_SEASON_SCREEN:
                return TvSeasonActivity.start(activity, (StartParams) data);
            case Constants.Screens.TV_EPISODE_SCREEN:
                return TvEpisodeActivity.start(activity, (TvEpisodeParams) data);
            case Constants.Screens.SETTINGS_SCREEN:
                return SettingsActivity.start(activity);
            case Constants.Screens.LISTS_SCREEN:
                return ListsActivity.start(activity, (int) data);
            case Constants.Screens.LIST_TITLE_CARD_SCREEN:
                return ListDetailActivity.start(activity, (ListDetailParams) data);
            case Constants.Screens.LIST_ADD_MEDIA_SCREEN:
                return ListAddMediaActivity.start(activity, (int) data);
            default:
                return null;
        }
    }

    @Override
    protected Bundle createStartActivityOptions(final Command command, final Intent activityIntent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            final Object data;

            if (command instanceof Forward && (data = ((Forward) command).getTransitionData()) instanceof Pair) {
                return ActivityOptions.makeSceneTransitionAnimation(activity, (Pair) data).toBundle();
            }
        }

        return super.createStartActivityOptions(command, activityIntent);
    }

    @Override
    public void applyCommand(final Command command) {
        super.applyCommand(command);
    }

    @Override
    protected Fragment createFragment(final String screenKey, final Object data) {
        return null;
    }
}
