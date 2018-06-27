package com.epam.popcornapp.ui.tv.episode;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.epam.popcornapp.application.ErrorMessage.ErrorType;

@StateStrategyType(AddToEndStrategy.class)
public interface TvEpisodeView extends MvpView {

    void setInfo(TvEpisodeData data, boolean isDataFromServer);

    void setCurrentRating(float rating);

    void onRatingResult(boolean isSuccess, float rating);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void onError(@ErrorType int errorType);
}
