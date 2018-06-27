package com.epam.popcornapp.ui.lists.presenters.views;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.epam.popcornapp.application.ErrorMessage.ErrorType;
import com.epam.popcornapp.ui.tiles.item.BaseTileItem;

import java.util.List;

@StateStrategyType(OneExecutionStateStrategy.class)
public interface ListDetailView extends MvpView {

    @StateStrategyType(AddToEndStrategy.class)
    void setDetails(String name, List<BaseTileItem> baseTileItems);

    @StateStrategyType(AddToEndStrategy.class)
    void listDeleted();

    void startAddMediaActivity();

    void showDialog();

    void showMessage(String message);

    void onError(@ErrorType int errorType);
}
