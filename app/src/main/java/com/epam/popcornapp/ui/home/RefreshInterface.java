package com.epam.popcornapp.ui.home;

import com.epam.popcornapp.application.ErrorMessage.ErrorType;

public interface RefreshInterface {

    void refresh();

    void onError(@ErrorType final int errorType);
}
