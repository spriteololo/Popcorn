package com.epam.popcornapp.ui.login.account_details;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;
import com.epam.popcornapp.application.BaseApplication;
import com.epam.popcornapp.application.Constants;
import com.epam.popcornapp.pojo.account.AccountSimpleData;
import com.epam.popcornapp.ui.all.base.BaseInternetMvpPresenter;
import com.epam.popcornapp.ui.login.base.LoginModel;
import com.epam.popcornapp.ui.login.base.QlassifiedAccount;

import javax.inject.Inject;

@InjectViewState
public class AccountDetailsPresenter extends BaseInternetMvpPresenter<AccountDetailsView> {

    @Inject
    Context context;

    @Inject
    QlassifiedAccount qlassifiedAccount;

    AccountDetailsPresenter() {
        BaseApplication.getApplicationComponent().inject(this);
    }

    void getDetails() {
        getViewState().setData(new AccountSimpleData(
                qlassifiedAccount.getUserName(),
                qlassifiedAccount.getName(),
                qlassifiedAccount.getGravatarHash()
        ));
    }

    void logOutClick() {
        qlassifiedAccount.clearAll();

        Constants.CURRENT_SESSION = null;
        Constants.CURRENT_USER = null;
        Constants.GRAVATAR_HASH = null;
        Constants.ACCOUNT_ID = -1;

        LoginModel.getLoginModel().signOut();

        getViewState().onLogOutSuccess();
    }
}
