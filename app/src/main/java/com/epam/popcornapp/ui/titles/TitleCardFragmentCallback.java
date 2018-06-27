package com.epam.popcornapp.ui.titles;

import com.epam.popcornapp.application.Constants.ChangeType;
import com.epam.popcornapp.application.ErrorMessage;
import com.epam.popcornapp.application.ErrorMessage.ErrorType;

public interface TitleCardFragmentCallback {

    void onError(ErrorMessage.onErrorMessageClickListener errorMessageClickListener, @ErrorType int errorType);

    void mediaItemChanged(@ChangeType int changeType);

    void toggleToolbar(boolean visible);
}
