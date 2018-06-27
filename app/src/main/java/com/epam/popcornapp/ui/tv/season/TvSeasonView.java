package com.epam.popcornapp.ui.tv.season;

import com.arellomobile.mvp.MvpView;
import com.epam.popcornapp.application.ErrorMessage.ErrorType;

public interface TvSeasonView extends MvpView {

    void onSuccess(TvSeasonInfo tvSeasonInfo);

    void onError(@ErrorType int errorType);
}
