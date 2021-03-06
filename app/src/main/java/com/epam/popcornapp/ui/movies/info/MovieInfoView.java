package com.epam.popcornapp.ui.movies.info;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.epam.popcornapp.application.ErrorMessage.ErrorType;
import com.epam.popcornapp.pojo.base.rating.Rating;

@StateStrategyType(AddToEndStrategy.class)
public interface MovieInfoView extends MvpView {

    void setDetail(MovieInfoData data, boolean isDataFromServer);

    void setCurrentRating(Rating rating);

    void ratingUpdate(boolean isSuccess, float rating);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void onError(@ErrorType int errorType);
}