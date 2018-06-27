package com.epam.popcornapp.ui.home;

import android.view.View;

import com.epam.popcornapp.application.ErrorMessage.ErrorType;
import com.epam.popcornapp.application.ErrorMessage.onErrorMessageClickListener;

public interface FragmentCallback {

    void onError(onErrorMessageClickListener errorMessageClickListener, @ErrorType int errorType, View view);

    void removeRefreshAnimation();

    void showSearchButton();

    void hideSearchButton();
}
