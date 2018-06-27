package com.epam.popcornapp.ui.login.sign_in;

import android.content.Context;
import android.text.TextUtils;

import com.arellomobile.mvp.InjectViewState;
import com.epam.popcornapp.application.BaseApplication;
import com.epam.popcornapp.application.Constants;
import com.epam.popcornapp.application.ErrorMessage.ErrorType;
import com.epam.popcornapp.model.login.ILoginInteractor;
import com.epam.popcornapp.pojo.account.Account;
import com.epam.popcornapp.pojo.login.Session;
import com.epam.popcornapp.ui.all.base.BaseInternetMvpPresenter;
import com.epam.popcornapp.ui.login.base.LoginModel;
import com.epam.popcornapp.ui.login.base.QlassifiedAccount;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

@InjectViewState
public class SignInPresenter extends BaseInternetMvpPresenter<SignInView> implements
        ILoginInteractor.Listener {

    private final int MIN_PASSWORD_LENGTH = 3;

    @Inject
    Context context;

    @Inject
    ILoginInteractor loginInteractor;

    @Inject
    QlassifiedAccount qlassifiedAccount;

    private boolean startMode;
    private boolean rememberMe;

    void setRememberMe(final boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    SignInPresenter() {
        BaseApplication.getApplicationComponent().inject(this);

        loginInteractor.setListener(this);

        qlassifiedAccount = new QlassifiedAccount(context);
    }

    void signIn(final String userName, final String password, final boolean startMode) {
        boolean isDataValid = true;

        if (!isInternetConnection(context)) {
            onError();

            return;
        }

        if (TextUtils.isEmpty(userName)) {
            isDataValid = false;

            getViewState().invalidUserName();
        } else if (TextUtils.isEmpty(password) || password.length() < MIN_PASSWORD_LENGTH) {
            isDataValid = false;

            getViewState().invalidPassword();
        }

        if (isDataValid) {
            this.startMode = startMode;

            qlassifiedAccount.saveUserName(userName);

            loginInteractor.getSessionId(userName, password, isInternetConnection(context));
        }
    }

    void initializeFields() {
        getViewState().initializeFields(qlassifiedAccount.getUserName());
    }

    @Override
    public void onGetSessionSuccess(final Flowable<Session> flowable) {
        flowable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<Session, String>() {
                    @Override
                    public String apply(final Session session) throws Exception {
                        if (session.isSuccess()) {
                            return session.getSessionId();
                        } else {
                            throw new Exception();
                        }
                    }
                })
                .subscribe(new DisposableSubscriber<String>() {

                    @Override
                    public void onNext(final String sessionId) {
                        Constants.CURRENT_SESSION = sessionId;
                    }

                    @Override
                    public void onError(final Throwable t) {
                        getViewState().onError(ErrorType.AUTHORIZATION_ERROR);
                    }

                    @Override
                    public void onComplete() {
                        loginInteractor.getAccountDetails(
                                Constants.CURRENT_SESSION, isInternetConnection(context));
                    }
                });
    }

    @Override
    public void onGetAccountDetailsSuccess(final Flowable<Account> flowable) {
        flowable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSubscriber<Account>() {
                    @Override
                    public void onNext(final Account account) {
                        onAccountDetailsReceived(account);
                    }

                    @Override
                    public void onError(final Throwable t) {
                        getViewState().onError(ErrorType.AUTHORIZATION_ERROR);
                    }

                    @Override
                    public void onComplete() {
                        getViewState().onSuccess();
                    }
                });
    }

    private void onAccountDetailsReceived(final Account accountDetails) {
        if (accountDetails.getStatusCode() == Constants.SUCCESS_CODE) {
            Constants.ACCOUNT_ID = accountDetails.getId();
            Constants.CURRENT_USER = accountDetails.getUsername();
            Constants.GRAVATAR_HASH = accountDetails.getAvatar().getGravatar().getHash();

            qlassifiedAccount.saveUserName(Constants.CURRENT_USER);
            qlassifiedAccount.saveName(accountDetails.getName());
            qlassifiedAccount.saveGravatarHash(Constants.GRAVATAR_HASH);

            if (rememberMe) {
                qlassifiedAccount.saveSessionId(Constants.CURRENT_SESSION);
                qlassifiedAccount.saveAccountId(Constants.ACCOUNT_ID);
            }

            if (!startMode) {
                LoginModel.getLoginModel().signIn();
            }
        }
    }

    @Override
    public void onError() {
        getViewState().onError(ErrorType.INTERNET_CONNECTION_ERROR);
    }
}
