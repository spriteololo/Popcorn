package com.epam.popcornapp.ui.lists.media;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.epam.popcornapp.R;
import com.epam.popcornapp.application.Constants;
import com.epam.popcornapp.listeners.RecyclerViewListeners;
import com.epam.popcornapp.listeners.RecyclerViewLoadMore;
import com.epam.popcornapp.ui.all.base.BaseMvpActivity;
import com.epam.popcornapp.ui.home.MainPresenterView;
import com.epam.popcornapp.ui.lists.base.ListsConverter;
import com.epam.popcornapp.ui.search.ISearchView;
import com.epam.popcornapp.ui.search.SearchAdapter;
import com.epam.popcornapp.ui.search.SearchDetail;
import com.epam.popcornapp.ui.search.SearchObservable;
import com.epam.popcornapp.ui.search.SearchPresenter;
import com.epam.popcornapp.ui.tiles.credits.TileType;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ListAddMediaActivity extends BaseMvpActivity implements
        ISearchView,
        SearchAdapter.ClickListener,
        MainPresenterView,
        RecyclerViewListeners.OnLoadMoreListener {

    @InjectPresenter
    SearchPresenter searchPresenter;

    @BindView(R.id.search_view)
    SearchView searchView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.search_result_recycler_view)
    RecyclerView resultRecyclerView;

    @BindView(R.id.no_result_view)
    View noResultView;

    private RecyclerViewLoadMore recyclerViewLoadMore;
    private SearchAdapter searchAdapter;

    private Disposable textListener;

    public static Intent start(final Context context, final int listId) {
        final Intent intent = new Intent(context, ListAddMediaActivity.class);

        intent.putExtra(Constants.Extras.LIST_ID, listId);

        return intent;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search);
    }

    @Override
    protected void onViewsBinded() {
        setToolbarNoTitle(toolbar, true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                onBackPressed();
            }
        });

        searchAdapter = new SearchAdapter(new ArrayList<SearchDetail>(), this);
        searchAdapter.setClickListener(this);

        resultRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        resultRecyclerView.setAdapter(searchAdapter);

        recyclerViewLoadMore = new RecyclerViewLoadMore(this, resultRecyclerView.getLayoutManager());
        resultRecyclerView.addOnScrollListener(recyclerViewLoadMore);
    }

    @Override
    public void setResult(final List<SearchDetail> result) {
        searchAdapter.setData(result);
        noResultView.setVisibility(View.GONE);
    }

    @Override
    public void addResult(final List<SearchDetail> result) {
        searchAdapter.addData(result);
    }

    @Override
    public void showButton() {

    }

    @Override
    public void clear() {
        searchAdapter.clear();
        noResultView.setVisibility(View.GONE);
    }

    @Override
    public void noResult() {
        searchAdapter.clear();
        noResultView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoadMore(final int nextPage) {
        searchPresenter.nextPage(nextPage);
    }

    @Override
    public void onItemClicked(final SearchDetail item) {
        MediaModel.getModel().onAddItem(ListsConverter.convertSearchDetailToBaseTileItem(item));

        finish();
    }

    @Override
    public void allTypesSearchClicked() {

    }

    @Override
    protected void onResume() {
        super.onResume();

        textListener = SearchObservable.fromView(searchView)
                .debounce(Constants.SEARCH_INPUT_TIMEOUT, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(final String s) {
                        searchPresenter.clearPage();
                        recyclerViewLoadMore.clearPage();
                        searchPresenter.search(s, TileType.MOVIE);
                    }
                });
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (textListener != null && !textListener.isDisposed()) {
            textListener.dispose();
        }
    }
}
