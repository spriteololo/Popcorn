package com.epam.popcornapp.ui.search;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;
import com.epam.popcornapp.application.BaseApplication;
import com.epam.popcornapp.application.Constants;
import com.epam.popcornapp.inject.SearchInterface;
import com.epam.popcornapp.pojo.actors.search.ActorsSearch;
import com.epam.popcornapp.pojo.movies.search.MovieSearch;
import com.epam.popcornapp.pojo.search.MultiSearch;
import com.epam.popcornapp.pojo.tv.search.TvSearch;
import com.epam.popcornapp.ui.all.base.BaseMvpPresenter;
import com.epam.popcornapp.ui.tiles.credits.TileType;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class SearchPresenter extends BaseMvpPresenter<ISearchView> {

    @Inject
    Context context;

    @Inject
    SearchInterface searchInterface;

    private Disposable disposable;
    private int currentPage = 1;
    private int maxPage = 1;
    private String query;
    private @TileType
    String type;

    public SearchPresenter() {
        BaseApplication.getApplicationComponent().inject(this);
    }

    public void nextPage(final int page) {
        currentPage = page;
        search(query, type);
    }

    public void clearPage() {
        currentPage = 1;
    }

    public void search(final String query, final @TileType String type) {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
            disposable = null;
        }

        this.query = query;
        this.type = type;

        if (query == null || query.isEmpty()) {
            getViewState().clear();
            currentPage = 1;

            return;
        }


        if (type == null || type.equals("null")) {
            searchAll(query);
        } else {
            switch (type) {
                case TileType.ACTOR:
                    searchPeople(query);
                    break;
                case TileType.MOVIE:
                    searchMovie(query);
                    break;
                case TileType.TV:
                    searchTV(query);
                    break;
                default:
                    return;
            }
        }
    }

    private void searchAll(final String query) {
        searchInterface.search(Constants.API_KEY, query, currentPage)
                .map(new Function<MultiSearch, List<SearchDetail>>() {
                    @Override
                    public List<SearchDetail> apply(final MultiSearch searchResult) {
                        maxPage = searchResult.getTotalPages();

                        return SearchDetail.convert(context, searchResult.getResults());
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<SearchDetail>>() {
                    @Override
                    public void onSubscribe(final Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(final List<SearchDetail> results) {
                        if (results.size() == 0 && currentPage == 1) {
                            getViewState().noResult();
                        } else {
                            returnResult(results);
                        }
                    }

                    @Override
                    public void onError(final Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void searchPeople(final String query) {
        searchInterface.searchPeople(Constants.API_KEY, query, currentPage)
                .map(new Function<ActorsSearch, List<SearchDetail>>() {
                    @Override
                    public List<SearchDetail> apply(final ActorsSearch actorsSearchResult) {
                        maxPage = actorsSearchResult.getTotalPages();

                        return SearchDetail.convertActorsResult(context, actorsSearchResult.getResults());
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<SearchDetail>>() {
                    @Override
                    public void onSubscribe(final Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(final List<SearchDetail> searchDetails) {
                        if (searchDetails.size() == 0 && currentPage == 1) {
                            getViewState().noResult();
                        } else {
                            returnResult(searchDetails);
                        }
                    }

                    @Override
                    public void onError(final Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void searchTV(final String query) {
        searchInterface.searchTV(Constants.API_KEY, query, currentPage)
                .map(new Function<TvSearch, List<SearchDetail>>() {
                    @Override
                    public List<SearchDetail> apply(final TvSearch tvSearch) {
                        maxPage = tvSearch.getTotalPages();

                        return SearchDetail.convertTvResult(context, tvSearch.getResults());
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<SearchDetail>>() {
                    @Override
                    public void onSubscribe(final Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(final List<SearchDetail> searchDetails) {
                        if (searchDetails.size() == 0 && currentPage == 1) {
                            getViewState().noResult();
                        } else {
                            returnResult(searchDetails);
                        }
                    }

                    @Override
                    public void onError(final Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void searchMovie(final String query) {
        searchInterface.searchMovie(Constants.API_KEY, query, currentPage)
                .map(new Function<MovieSearch, List<SearchDetail>>() {
                    @Override
                    public List<SearchDetail> apply(final MovieSearch searchMovie) {
                        maxPage = searchMovie.getTotalPages();

                        return SearchDetail.convertMoviesResult(context, searchMovie.getResults());
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<SearchDetail>>() {
                    @Override
                    public void onSubscribe(final Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(final List<SearchDetail> searchDetails) {
                        if (searchDetails.size() == 0 && currentPage == 1) {
                            getViewState().noResult();
                        } else {
                            returnResult(searchDetails);
                        }
                    }

                    @Override
                    public void onError(final Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void returnResult(final List<SearchDetail> result) {
        if (result.size() == 0) {
            return;
        }

        if (currentPage == 1) {
            getViewState().setResult(result);

            if (maxPage == 1 && type != null && !type.equals("null")) {
                getViewState().showButton();
            }
        } else {
            getViewState().addResult(result);

            if (maxPage == currentPage && type != null && !type.equals("null")) {
                getViewState().showButton();
            }
        }
    }

    void searchForAllTypes() {
        this.type = null;
        this.currentPage = 1;
        search(query, type);
    }
}
