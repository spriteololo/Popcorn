package com.epam.popcornapp.ui.settings;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.epam.popcornapp.application.ErrorMessage.ErrorType;

import java.util.List;

@StateStrategyType(SingleStateStrategy.class)
public interface SettingsView extends MvpView {

    void setInfo(List<String[]> data);

    void onError(@ErrorType int errorType);
}
