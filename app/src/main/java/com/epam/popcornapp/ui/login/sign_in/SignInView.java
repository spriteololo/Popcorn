package com.epam.popcornapp.ui.login.sign_in;

import com.arellomobile.mvp.MvpView;
import com.epam.popcornapp.application.ErrorMessage.ErrorType;

public interface SignInView extends MvpView {

    void initializeFields(String username);

    void invalidUserName();

    void invalidPassword();

    void onSuccess();

    void onError(@ErrorType int errorType);
}
