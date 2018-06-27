package com.epam.popcornapp.ui.lists;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.epam.popcornapp.R;
import com.epam.popcornapp.application.BaseApplication;
import com.epam.popcornapp.application.Constants;
import com.epam.popcornapp.application.ErrorMessage;
import com.epam.popcornapp.pojo.lists.ListItem;
import com.epam.popcornapp.pojo.lists.ListsResult;
import com.epam.popcornapp.pojo.lists.bodies.ListsCreateBody;
import com.epam.popcornapp.ui.all.base.BaseMvpFragment;
import com.epam.popcornapp.ui.createListDialog.CreateListDialog;
import com.epam.popcornapp.ui.home.FragmentCallback;
import com.epam.popcornapp.ui.home.MainPresenter;
import com.epam.popcornapp.ui.home.MainPresenterView;
import com.epam.popcornapp.ui.home.RefreshInterface;
import com.epam.popcornapp.ui.lists.base.ListsConverter;
import com.epam.popcornapp.ui.lists.base.ListsModel;
import com.epam.popcornapp.ui.lists.detail.ListDetailParams;
import com.epam.popcornapp.ui.lists.presenters.ListsPresenter;
import com.epam.popcornapp.ui.lists.presenters.views.ListsView;
import com.epam.popcornapp.ui.more.MoreScreenAdapter;
import com.epam.popcornapp.ui.swimming.SkeletonTileType;
import com.epam.popcornapp.ui.tiles.item.BaseTileItem;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class ListsFragment extends BaseMvpFragment implements
        MainPresenterView,
        ListsView,
        MoreScreenAdapter.onClickListener,
        ErrorMessage.onErrorMessageClickListener,
        RefreshInterface,
        CreateListDialog.DialogClickListener,
        ListsModel.ListChangeListener {

    @InjectPresenter
    MainPresenter mainPresenter;

    @InjectPresenter
    ListsPresenter listsPresenter;

    @Inject
    CreateListDialog createListDialog;

    @BindView(R.id.lists_fragment_recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.lists_fragment_fab)
    FloatingActionButton createListButton;

    @BindView(R.id.lists_fragment_message_text_view)
    TextView messageView;

    private boolean isRefresh;

    private List<ListItem> listItems;

    private MoreScreenAdapter moreScreenAdapter;
    private FragmentCallback fragmentCallback;

    public static ListsFragment getInstance() {
        return new ListsFragment();
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        BaseApplication.getApplicationComponent().inject(this);
        setHasOptionsMenu(true);
    }

    @Override
    public void onAttach(final Context context) {
        super.onAttach(context);

        try {
            fragmentCallback = (FragmentCallback) context;
        } catch (final ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    " must implement FragmentCallback");
        }
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lists, container, false);
    }

    @Override
    protected void onViewsBinded() {
        fragmentCallback.hideSearchButton();

        ListsModel.getModel().setListChangeListener(this);

        createListDialog.setClickListener(this);

        moreScreenAdapter = new MoreScreenAdapter(new ArrayList<BaseTileItem>(), getActivity());
        moreScreenAdapter.setClickListener(this);

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setAdapter(moreScreenAdapter);

        createListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (moreScreenAdapter.getItemCount() >= Constants.ONE_PAGE_ITEMS_NUM) {
                    showMessage(getString(R.string.lists_num_out_of_bounds_error));
                } else {
                    createListDialog.show(getActivity().getSupportFragmentManager(), "");
                }
            }
        });

        refresh();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        fragmentCallback.showSearchButton();
    }

    @Override
    public void refresh() {
        isRefresh = true;

        listsPresenter.getCreatedLists();
    }

    @Override
    public void setData(final ListsResult listsResult) {
        fragmentCallback.removeRefreshAnimation();

        listItems = listsResult.getResults();

        messageView.setVisibility(listsResult.getTotalResults() == 0 ? View.VISIBLE : View.INVISIBLE);

        moreScreenAdapter.setData(ListsConverter.convertListsItemsToBaseTileItems(listItems,
                getActivity()), isRefresh, SkeletonTileType.LIST);

        isRefresh = false;
    }

    @Override
    public void setNewList(final ListItem item) {
        recyclerView.scrollToPosition(0);

        moreScreenAdapter.setNewItem(ListsConverter.convertListItemToBaseTileItem(item, getActivity()));

        listItems.add(0, item);
    }

    @Override
    public void showMessage(final String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(final int errorType) {
        fragmentCallback.removeRefreshAnimation();
        fragmentCallback.onError(this, errorType, recyclerView);
    }

    @Override
    public void onRetryClicked() {
        refresh();
    }

    @Override
    public void onItemClick(final int id, final String mediaType, final int position) {
        mainPresenter.onListTitleCardClicked(
                new ListDetailParams(id, listItems.get(position).getItemCount(), position));
    }

    @Override
    public void onCreateClick(final String name) {
        final String language = "en";

        listsPresenter.createList(new ListsCreateBody(name, Constants.EMPTY_STRING, language));
    }

    @Override
    public void onDeleteList(final int position) {
        moreScreenAdapter.deleteItem(position);

        listItems.remove(position);
    }

    @Override
    public void onMediaItemsSizeChange(final int position, final int size) {
        moreScreenAdapter.setDescription(position,
                getResources().getQuantityString(R.plurals.item_count, size, size));

        listItems.get(position).setItemCount(size);
    }
}
