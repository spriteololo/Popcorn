package com.epam.popcornapp.ui.search;

import com.arellomobile.mvp.MvpView;

import java.util.List;

public interface ISearchView extends MvpView {

    void setResult(final List<SearchDetail> result);

    void addResult(final List<SearchDetail> result);

    void showButton();

    void clear();

    void noResult();
}
