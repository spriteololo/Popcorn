package com.epam.popcornapp.model.login;

import com.epam.popcornapp.pojo.account.Account;
import com.epam.popcornapp.pojo.login.Session;

import io.reactivex.Flowable;

public interface ILoginInteractor {

    void setListener(Listener listener);

    void getSessionId(String userName, String password, boolean isInternetConnection);

    void getAccountDetails(String sessionId, boolean isInternetConnection);

    interface Listener {

        void onGetSessionSuccess(Flowable<Session> flowable);

        void onGetAccountDetailsSuccess(Flowable<Account> flowable);

        void onError();
    }
}
