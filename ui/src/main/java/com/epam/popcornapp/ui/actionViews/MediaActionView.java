package com.epam.popcornapp.ui.actionViews;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.AppCompatDrawableManager;
import android.util.AttributeSet;
import android.view.View;

import com.epam.popcornapp.ui.base.InflateFrameLayout;
import com.epam.ui.R;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

public class MediaActionView extends InflateFrameLayout implements View.OnClickListener {

    private FloatingActionMenu actionMenu;
    private FloatingActionButton addToListButton;
    private FloatingActionButton favoriteButton;
    private FloatingActionButton watchlistButton;

    private ActionViewListeners actionViewListeners;

    private boolean isEnabled = false;
    private boolean isVisible = true;

    public MediaActionView(final Context context) {
        super(context);
    }

    public MediaActionView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public MediaActionView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onCreateView(final Context pContext, final AttributeSet pAttrs) {
        actionMenu = findViewById(R.id.actions_menu);
        addToListButton = findViewById(R.id.action_add_to_list_button);
        favoriteButton = findViewById(R.id.action_favorite_button);
        watchlistButton = findViewById(R.id.action_watchlist_button);

        favoriteButton.setOnClickListener(this);
        watchlistButton.setOnClickListener(this);
    }

    @Override
    protected int getViewLayout() {
        return R.layout.view_action_media;
    }

    @SuppressLint("RestrictedApi")
    public void initialize(final ActionViewListeners actionViewListeners, final boolean isShowListButtons) {
        this.actionViewListeners = actionViewListeners;
        actionMenu.hideMenuButton(false);

        if (isShowListButtons) {
            addToListButton.setVisibility(VISIBLE);
            addToListButton.setOnClickListener(this);
            addToListButton.setImageDrawable(
                    AppCompatDrawableManager.get().getDrawable(getContext(), R.drawable.icon_menu_lists));
        }

        favoriteButton.setImageDrawable(
                AppCompatDrawableManager.get().getDrawable(getContext(), R.drawable.icon_menu_favorites));
        watchlistButton.setImageDrawable(
                AppCompatDrawableManager.get().getDrawable(getContext(), R.drawable.icon_menu_watchlist));
    }

    public void toggle(final boolean isVisible) {
        this.isVisible = isVisible;

        if (!isEnabled) {
            return;
        }

        if (isVisible) {
            actionMenu.showMenu(true);
        } else {
            actionMenu.hideMenu(true);
        }
    }

    public void toggleActiveState(final boolean isConnectionAvailable) {
        if (actionViewListeners == null) {
            return;
        }

        if (isConnectionAvailable) {
            actionMenu.showMenuButton(true);

            if (isVisible) {
                actionMenu.showMenu(false);
            } else {
                actionMenu.hideMenu(false);
            }
        } else {
            actionMenu.hideMenuButton(true);
        }

        isEnabled = isConnectionAvailable;
    }

    public void changeFavoriteButton(final boolean isAdded) {
        final Resources resources = getResources();

        favoriteButton.setLabelText(isAdded ? resources.getString(R.string.remove_favorite)
                : resources.getString(R.string.add_favorite));
    }

    public void changeWatchlistButton(final boolean isAdded) {
        final Resources resources = getResources();

        watchlistButton.setLabelText(isAdded ? resources.getString(R.string.remove_watchlist)
                : resources.getString(R.string.add_watchlist));
    }

    @Override
    public void onClick(final View view) {
        actionMenu.close(true);

        if (actionViewListeners != null) {
            actionViewListeners.onActionButtonClick(view);
        }
    }

    public interface ActionViewListeners {

        void onActionButtonClick(View view);
    }
}
