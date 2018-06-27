package com.epam.popcornapp.ui.actionViews;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.AppCompatDrawableManager;
import android.util.AttributeSet;
import android.view.View;

import com.epam.popcornapp.ui.base.InflateFrameLayout;
import com.epam.ui.R;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

public class ListActionView extends InflateFrameLayout implements View.OnClickListener {

    private FloatingActionMenu actionMenu;
    private FloatingActionButton addMovieButton;
    private FloatingActionButton deleteListButton;

    private ActionViewListeners actionViewListeners;

    public ListActionView(final Context context) {
        super(context);
    }

    public ListActionView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public ListActionView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onCreateView(final Context pContext, final AttributeSet pAttrs) {
        actionMenu = findViewById(R.id.actions_menu);
        addMovieButton = findViewById(R.id.action_add_movie_button);
        deleteListButton = findViewById(R.id.action_delete_list_button);

        addMovieButton.setOnClickListener(this);
        deleteListButton.setOnClickListener(this);
    }

    @Override
    protected int getViewLayout() {
        return R.layout.view_action_list;
    }

    @SuppressLint("RestrictedApi")
    public void initialize(final ActionViewListeners actionViewListeners) {
        this.actionViewListeners = actionViewListeners;

        addMovieButton.setImageDrawable(
                AppCompatDrawableManager.get().getDrawable(getContext(), R.drawable.icon_plus));
        deleteListButton.setImageDrawable(
                AppCompatDrawableManager.get().getDrawable(getContext(), R.drawable.icon_menu_delete));
    }

    public void toggle(final boolean isShow) {
        if (isShow) {
            actionMenu.showMenu(true);
        } else {
            actionMenu.hideMenu(true);
        }
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
