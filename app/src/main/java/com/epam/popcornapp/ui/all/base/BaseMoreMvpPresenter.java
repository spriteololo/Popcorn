package com.epam.popcornapp.ui.all.base;

import com.arellomobile.mvp.InjectViewState;

@InjectViewState
public abstract class BaseMoreMvpPresenter extends BaseInternetMvpPresenter<BaseMoreView> {

    public abstract void refreshData();

    public abstract void setCurrentPage(int page);

    public abstract boolean isInternetConnection();

    public abstract int getTitleId();

    public abstract int getCurrentPage();
}
