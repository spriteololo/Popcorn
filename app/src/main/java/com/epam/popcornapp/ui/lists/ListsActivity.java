package com.epam.popcornapp.ui.lists;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.epam.popcornapp.R;
import com.epam.popcornapp.application.BaseApplication;
import com.epam.popcornapp.application.Constants;
import com.epam.popcornapp.application.ErrorMessage;
import com.epam.popcornapp.application.ErrorMessage.ErrorType;
import com.epam.popcornapp.pojo.lists.ListItem;
import com.epam.popcornapp.pojo.lists.ListsResult;
import com.epam.popcornapp.pojo.lists.bodies.ListsCreateBody;
import com.epam.popcornapp.ui.all.base.BaseMvpActivity;
import com.epam.popcornapp.ui.all.base.NetworkStateReceiver;
import com.epam.popcornapp.ui.createListDialog.CreateListDialog;
import com.epam.popcornapp.ui.lists.base.ListsAdapter;
import com.epam.popcornapp.ui.lists.presenters.ListMediaPresenter;
import com.epam.popcornapp.ui.lists.presenters.ListsPresenter;
import com.epam.popcornapp.ui.lists.presenters.views.ListMediaView;
import com.epam.popcornapp.ui.lists.presenters.views.ListsView;

import javax.inject.Inject;

import butterknife.BindView;

public class ListsActivity extends BaseMvpActivity implements
        ListsView,
        ListMediaView,
        ListsAdapter.ClickListener,
        ErrorMessage.onErrorMessageClickListener,
        NetworkStateReceiver.NetworkStateReceiverListener,
        CreateListDialog.DialogClickListener {

    @Inject
    ErrorMessage errorMessage;

    @Inject
    NetworkStateReceiver networkStateReceiver;

    @Inject
    CreateListDialog createListDialog;

    @InjectPresenter
    ListsPresenter listsPresenter;

    @InjectPresenter
    ListMediaPresenter listMediaPresenter;

    @BindView(R.id.blur_toolbar)
    Toolbar toolbar;

    @BindView(R.id.lists_activity_recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.lists_activity_fab)
    FloatingActionButton createListButton;

    @BindView(R.id.lists_activity_message_text_view)
    TextView messageView;

    private int movieId;

    private ListsAdapter listsAdapter;

    public static Intent start(@NonNull final Context context, final int movieId) {
        final Intent intent = new Intent(context, ListsActivity.class);

        intent.putExtra(Constants.Extras.MOVIE_ID, movieId);

        return intent;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        BaseApplication.getApplicationComponent().inject(this);
        setContentView(R.layout.activity_lists);
    }

    @Override
    protected void onResume() {
        super.onResume();

        networkStateReceiver.addListener(this);
        this.registerReceiver(networkStateReceiver,
                new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    protected void onPause() {
        super.onPause();

        this.unregisterReceiver(networkStateReceiver);
    }

    @Override
    protected void onViewsBinded() {
        this.movieId = getIntent().getIntExtra(Constants.Extras.MOVIE_ID, -1);

        setToolbar(toolbar, getString(R.string.add_to_one_of_your_lists), true);

        toolbar.setNavigationIcon(R.drawable.icon_cross_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                onBackPressed();
            }
        });

        createListDialog.setClickListener(this);

        listsAdapter = new ListsAdapter(this);
        listsAdapter.setClickListener(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listsAdapter);
        recyclerView.setNestedScrollingEnabled(false);

        createListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (listsAdapter.getItemCount() >= Constants.ONE_PAGE_ITEMS_NUM) {
                    showMessage(getString(R.string.lists_num_out_of_bounds_error));
                } else {
                    createListDialog.show(getSupportFragmentManager(), "");
                }
            }
        });

        refresh();
    }

    private void refresh() {
        listsPresenter.getCreatedLists();
    }

    @Override
    public void setData(final ListsResult listsResult) {
        messageView.setVisibility(listsResult.getTotalResults() == 0 ? View.VISIBLE : View.INVISIBLE);

        listsAdapter.setData(listsResult.getResults());
    }

    @Override
    public void setNewList(final ListItem item) {
        listsAdapter.setNewList(item);

        recyclerView.scrollToPosition(0);
    }

    @Override
    public void addMovie(final int lastClickPosition) {
        listsAdapter.increaseItemsNum(lastClickPosition);
    }

    @Override
    public void removeMovie(final int lastClickPosition) {

    }

    @Override
    public void showMessage(final String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(final int errorType) {
        errorMessage.showSnackbar(this, recyclerView, this, errorType);
    }

    @Override
    public void onItemClick(final int listId, final int position) {
        listMediaPresenter.addMovie(listId, movieId, position);
    }

    @Override
    public void onCreateClick(final String name) {
        final String language = "en";

        listsPresenter.createList(new ListsCreateBody(name, Constants.EMPTY_STRING, language));
    }

    @Override
    public void onRetryClicked() {
        refresh();
    }

    @Override
    public void networkAvailable() {
        refresh();

        errorMessage.removeSnackbar();
    }

    @Override
    public void networkUnavailable() {
        onError(ErrorType.INTERNET_CONNECTION_ERROR);
    }
}
