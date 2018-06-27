package com.epam.popcornapp.ui.lists.detail;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.epam.popcornapp.R;
import com.epam.popcornapp.application.BaseApplication;
import com.epam.popcornapp.application.Constants;
import com.epam.popcornapp.application.ErrorMessage;
import com.epam.popcornapp.ui.actionViews.ListActionView;
import com.epam.popcornapp.ui.all.base.BaseMvpActivity;
import com.epam.popcornapp.ui.all.base.NetworkStateReceiver;
import com.epam.popcornapp.ui.home.MainPresenter;
import com.epam.popcornapp.ui.home.MainPresenterView;
import com.epam.popcornapp.ui.home.PopcornAppNavigator;
import com.epam.popcornapp.ui.lists.base.ListsModel;
import com.epam.popcornapp.ui.lists.media.MediaModel;
import com.epam.popcornapp.ui.lists.presenters.ListDetailPresenter;
import com.epam.popcornapp.ui.lists.presenters.ListMediaPresenter;
import com.epam.popcornapp.ui.lists.presenters.views.ListDetailView;
import com.epam.popcornapp.ui.lists.presenters.views.ListMediaView;
import com.epam.popcornapp.ui.tiles.credits.TileType;
import com.epam.popcornapp.ui.tiles.item.BaseTileItem;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.NavigatorHolder;

public class ListDetailActivity extends BaseMvpActivity implements
        MainPresenterView,
        ListDetailView,
        ListMediaView,
        MediaModel.MediaChangeListener,
        ListDetailMediaAdapter.ListMediaAdapterClickListener,
        ErrorMessage.onErrorMessageClickListener,
        SwipeRefreshLayout.OnRefreshListener,
        NetworkStateReceiver.NetworkStateReceiverListener,
        ListActionView.ActionViewListeners {

    @Inject
    ErrorMessage errorMessage;

    @Inject
    NavigatorHolder navigatorHolder;

    @Inject
    NetworkStateReceiver networkStateReceiver;

    @InjectPresenter
    MainPresenter mainPresenter;

    @InjectPresenter
    ListDetailPresenter listDetailPresenter;

    @InjectPresenter
    ListMediaPresenter listMediaPresenter;

    @BindView(R.id.blur_toolbar)
    Toolbar toolbar;

    @BindView(R.id.list_activity_message_text_view)
    View messageView;

    @BindView(R.id.activity_list_swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.activity_list_recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.activity_list_action_view)
    ListActionView actionView;

    private ListDetailParams listDetailParams;
    private ListDetailMediaAdapter listDetailMediaAdapter;

    private BaseTileItem mediaItem;

    private int mediaItemSize;

    private final Navigator navigator = new PopcornAppNavigator(ListDetailActivity.this);

    public static Intent start(@NonNull final Context context,
                               @NonNull final ListDetailParams listDetailParams) {
        final Intent intent = new Intent(context, ListDetailActivity.class);

        intent.putExtra(Constants.Extras.PARAMS, listDetailParams);

        return intent;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_detail);

        BaseApplication.getApplicationComponent().inject(this);

        registerForContextMenu(recyclerView);
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

        this.navigatorHolder.removeNavigator();
        this.unregisterReceiver(networkStateReceiver);
    }

    @Override
    protected void onViewsBinded() {
        setToolbar(toolbar, Constants.EMPTY_STRING, true);

        saveParams();

        MediaModel.getModel().setMediaChangeListener(this);

        listDetailMediaAdapter = new ListDetailMediaAdapter(new ArrayList<BaseTileItem>(), this);
        listDetailMediaAdapter.setClickListener(this);
        listDetailMediaAdapter.setItemNum(listDetailParams.getItemNum());

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(listDetailMediaAdapter);
        recyclerView.setNestedScrollingEnabled(false);

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setProgressViewOffset(true, Constants.ANIM_SWIPE_START_POSITION,
                Constants.ANIM_SWIPE_END_POSITION);

        actionView.initialize(this);

        refresh();
    }

    private void saveParams() {
        this.listDetailParams = getIntent().getParcelableExtra(Constants.Extras.PARAMS);
    }

    private void refresh() {
        listDetailPresenter.getDetails(listDetailParams.getId());
    }

    private void checkMessageVisibility() {
        messageView.setVisibility(mediaItemSize == 0 ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void setDetails(final String name, final List<BaseTileItem> baseTileItems) {
        swipeRefreshLayout.setRefreshing(false);

        mediaItemSize = baseTileItems.size();

        setToolbarTitle(name);

        listDetailMediaAdapter.setData(baseTileItems);

        checkMessageVisibility();
    }

    @Override
    public void showDialog() {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.delete_list_title))
                .setMessage(getString(R.string.delete_list_message))
                .setPositiveButton(getString(R.string.delete), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialogInterface, final int i) {
                        listDetailPresenter.deleteList(listDetailParams.getId());
                    }
                })
                .setNegativeButton(getString(R.string.cancel), null)
                .create().show();
    }

    @Override
    public void listDeleted() {
        ListsModel.getModel().onDeleteList(listDetailParams.getPosition());

        finish();
    }

    @Override
    public void startAddMediaActivity() {
        mainPresenter.onAddMediaClicked(listDetailParams.getId());
    }

    @Override
    public void addMovie(final int lastClickPosition) {
        mediaItemSize++;

        recyclerView.scrollToPosition(0);

        listDetailMediaAdapter.addNewItem(mediaItem);
        ListsModel.getModel().onMediaItemsSizeChange(listDetailParams.getPosition(), mediaItemSize);

        checkMessageVisibility();
    }

    @Override
    public void onAddMediaItem(final BaseTileItem baseTileItem) {
        mediaItem = baseTileItem;

        listMediaPresenter.addMovie(listDetailParams.getId(), baseTileItem.getId(), 0);
    }

    @Override
    public void removeMovie(final int lastClickPosition) {
        mediaItemSize--;

        listDetailMediaAdapter.deleteItem(lastClickPosition);
        ListsModel.getModel().onMediaItemsSizeChange(listDetailParams.getPosition(), mediaItemSize);

        checkMessageVisibility();
    }

    @Override
    public void showMessage(final String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(final int errorType) {
        swipeRefreshLayout.setRefreshing(false);

        errorMessage.showSnackbar(this, swipeRefreshLayout, this, errorType);
    }

    @Override
    public void onItemClick(final int itemId, final int position, @TileType final String tileType) {
        mainPresenter.onTileClicked(itemId, tileType);
    }

    @Override
    public void onMenuItemClick(final int itemId, final int position, @TileType final String tileType) {
        if (tileType.equals(TileType.MOVIE)) {
            listMediaPresenter.removeMovie(listDetailParams.getId(), itemId, position);
        } else {
            showMessage(getString(R.string.tv_remove_error));
        }
    }

    @Override
    public void onRetryClicked() {
        refresh();
    }

    @Override
    public void onRefresh() {
        refresh();
    }

    @Override
    public void networkAvailable() {
        refresh();

        actionView.toggle(true);
        errorMessage.removeSnackbar();
    }

    @Override
    public void networkUnavailable() {
        actionView.toggle(false);
        onError(ErrorMessage.ErrorType.INTERNET_CONNECTION_ERROR);
    }

    @Override
    public void onActionButtonClick(final View view) {
        listDetailPresenter.onActionsButtonClicked(view);
    }
}
