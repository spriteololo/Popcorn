package com.epam.popcornapp.ui.account.menu;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.epam.popcornapp.ui.swimming.SwimmingLineItem;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface AccountMenuView extends MvpView {

    void setMovieSwimmingLine(SwimmingLineItem item);

    void setTvSwimmingLine(SwimmingLineItem item);

    void hideMovieMoreButton();

    void hideTvMoreButton();

    void noData();
}
