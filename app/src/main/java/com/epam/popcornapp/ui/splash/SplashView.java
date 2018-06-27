package com.epam.popcornapp.ui.splash;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.epam.popcornapp.application.ErrorMessage.ErrorType;

@StateStrategyType(OneExecutionStateStrategy.class)
public interface SplashView extends MvpView {

    void startLoginActivity();

    void startMainActivity();

    void onError(@ErrorType int errorType);
}
