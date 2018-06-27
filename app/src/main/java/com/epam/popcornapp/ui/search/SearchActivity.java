package com.epam.popcornapp.ui.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.epam.popcornapp.R;
import com.epam.popcornapp.application.BaseApplication;
import com.epam.popcornapp.application.Constants;
import com.epam.popcornapp.listeners.RecyclerViewListeners;
import com.epam.popcornapp.listeners.RecyclerViewLoadMore;
import com.epam.popcornapp.ui.all.base.BaseMvpActivity;
import com.epam.popcornapp.ui.home.MainPresenter;
import com.epam.popcornapp.ui.home.MainPresenterView;
import com.epam.popcornapp.ui.home.PopcornAppNavigator;
import com.epam.popcornapp.ui.tiles.credits.TileType;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.NavigatorHolder;

public class SearchActivity extends BaseMvpActivity implements ISearchView,
        SearchAdapter.ClickListener, MainPresenterView, RecyclerViewListeners.OnLoadMoreListener {

    @InjectPresenter
    SearchPresenter searchPresenter;

    @InjectPresenter
    MainPresenter mainPresenter;

    @Inject
    NavigatorHolder navigatorHolder;

    @BindView(R.id.search_view)
    SearchView searchView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.search_result_recycler_view)
    RecyclerView resultRecyclerView;

    @BindView(R.id.no_result_view)
    View noResultView;

    @BindView(R.id.global_search_button)
    Button globalSearchButton;

    private RecyclerViewLoadMore recyclerViewLoadMore;

    private SearchAdapter searchAdapter;

    private Disposable textListener;

    private @TileType
    String searchType;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        BaseApplication.getApplicationComponent().inject(this);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search);
    }

    @Override
    protected void onViewsBinded() {
        setToolbarNoTitle(toolbar, true);

        final View v = searchView.findViewById(R.id.search_plate);
        v.setBackgroundColor(getResources().getColor(R.color.color_background));

        final Intent intent = getIntent();
        searchType = intent.getExtras().getString(Constants.Extras.TITLE_TYPE);

        resultRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchAdapter = new SearchAdapter(new ArrayList<SearchDetail>(), this);
        searchAdapter.setClickListener(this);
        resultRecyclerView.setAdapter(searchAdapter);

        recyclerViewLoadMore = new RecyclerViewLoadMore(this, resultRecyclerView.getLayoutManager());
        resultRecyclerView.addOnScrollListener(recyclerViewLoadMore);

        globalSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                noResultView.setVisibility(View.GONE);
                view.setVisibility(View.GONE);
                allTypesSearchClicked();
            }
        });
    }

    public static Intent start(final Context context, final String type) {
        final Intent intent = new Intent(context, SearchActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.putExtra(Constants.Extras.TITLE_TYPE, type);

        return intent;
    }

    @Override
    public void setResult(final List<SearchDetail> result) {
        noResultView.setVisibility(View.GONE);
        globalSearchButton.setVisibility(View.GONE);
        searchAdapter.setData(result);
    }

    @Override
    public void addResult(final List<SearchDetail> result) {
        searchAdapter.addData(result);
    }

    @Override
    public void showButton() {
        searchAdapter.showButton();
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

        if (searchType != null && !searchType.equals("null")) {
            globalSearchButton.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onItemClicked(final SearchDetail item) {
        mainPresenter.onTileClicked(item.getId(), item.getType());
    }

    @Override
    public void allTypesSearchClicked() {
        recyclerViewLoadMore.clearPage();
        this.searchType = null;
        searchPresenter.searchForAllTypes();
    }

    @Override
    protected void onResume() {
        super.onResume();

        navigatorHolder.setNavigator(navigator);
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
                        searchPresenter.search(s, searchType);
                    }
                });
    }

    @Override
    protected void onPause() {
        super.onPause();

        navigatorHolder.removeNavigator();

        if (textListener != null && !textListener.isDisposed()) {
            textListener.dispose();
        }
    }

    private final Navigator navigator = new PopcornAppNavigator(SearchActivity.this);

    @Override
    public void onLoadMore(final int nextPage) {
        searchPresenter.nextPage(nextPage);
    }
}
