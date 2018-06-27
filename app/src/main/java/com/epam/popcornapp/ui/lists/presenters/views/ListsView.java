package com.epam.popcornapp.ui.lists.presenters.views;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.epam.popcornapp.application.ErrorMessage.ErrorType;
import com.epam.popcornapp.pojo.lists.ListItem;
import com.epam.popcornapp.pojo.lists.ListsResult;

@StateStrategyType(AddToEndStrategy.class)
public interface ListsView extends MvpView {

    void setData(ListsResult listsResult);

    void setNewList(ListItem item);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void showMessage(String message);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void onError(@ErrorType int errorType);
}
