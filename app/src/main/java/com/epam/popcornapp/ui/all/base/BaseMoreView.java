package com.epam.popcornapp.ui.all.base;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.epam.popcornapp.application.ErrorMessage.ErrorType;
import com.epam.popcornapp.ui.tiles.item.BaseTileItem;

import java.util.List;

@StateStrategyType(SingleStateStrategy.class)
public interface BaseMoreView extends MvpView {

    void setData(String dataType, List<BaseTileItem> data, int titleId);

    void onError(@ErrorType int errorType);
}
