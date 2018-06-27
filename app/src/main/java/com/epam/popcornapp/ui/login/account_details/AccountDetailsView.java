package com.epam.popcornapp.ui.login.account_details;

import com.arellomobile.mvp.MvpView;
import com.epam.popcornapp.application.ErrorMessage.ErrorType;
import com.epam.popcornapp.pojo.account.AccountSimpleData;

public interface AccountDetailsView extends MvpView {

    void setData(AccountSimpleData username);

    void onLogOutSuccess();

    void onError(@ErrorType int errorType);
}
