package com.epam.popcornapp.ui.actors.info;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.epam.popcornapp.application.ErrorMessage.ErrorType;

@StateStrategyType(SingleStateStrategy.class)
public interface ActorInfoView extends MvpView {

    void setBaseInfo(ActorInfoDetails result, boolean isDataFromServer);

    void onError(@ErrorType int errorType);
}
