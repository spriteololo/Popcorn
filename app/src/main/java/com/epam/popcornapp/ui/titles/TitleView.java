package com.epam.popcornapp.ui.titles;

import android.content.Intent;
import android.os.Bundle;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

public interface TitleView extends MvpView {

    @StateStrategyType(OneExecutionStateStrategy.class)
    void startGalleryActivity(Intent intent, Bundle options);
}
