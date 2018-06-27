package com.epam.popcornapp.ui.lists.presenters.views;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.epam.popcornapp.application.ErrorMessage.ErrorType;

@StateStrategyType(AddToEndStrategy.class)
public interface ListMediaView extends MvpView {

    void addMovie(int lastClickPosition);

    void removeMovie(int lastClickPosition);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void showMessage(String message);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void onError(@ErrorType int errorType);
}
