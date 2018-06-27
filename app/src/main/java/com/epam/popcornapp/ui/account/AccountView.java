package com.epam.popcornapp.ui.account;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(AddToEndStrategy.class)
public interface AccountView extends MvpView {

    void onFavoriteResult(boolean isAdded);

    void onWatchlistResult(boolean isAdded);

    void startListActivity();

    @StateStrategyType(OneExecutionStateStrategy.class)
    void showMessage(String message);
}
