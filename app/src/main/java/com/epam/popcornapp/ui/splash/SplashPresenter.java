package com.epam.popcornapp.ui.splash;

import android.content.Context;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Display;

import com.arellomobile.mvp.InjectViewState;
import com.epam.popcornapp.application.BaseApplication;
import com.epam.popcornapp.application.Constants;
import com.epam.popcornapp.application.ErrorMessage.ErrorType;
import com.epam.popcornapp.model.splash.ISplashInteractor;
import com.epam.popcornapp.pojo.account.Account;
import com.epam.popcornapp.pojo.genres.GenresResult;
import com.epam.popcornapp.pojo.splash_settings.Config;
import com.epam.popcornapp.ui.all.base.BaseInternetMvpPresenter;
import com.epam.popcornapp.ui.base.UiConstants;
import com.epam.popcornapp.ui.login.base.QlassifiedAccount;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function3;

@InjectViewState
public class SplashPresenter extends BaseInternetMvpPresenter<SplashView> implements
        ISplashInteractor.Actions {


    @Inject
    Context context;

    @Inject
    QlassifiedAccount qlassifiedAccount;

    @Inject
    ISplashInteractor splashInteractor;

    SplashPresenter() {
        BaseApplication.getApplicationComponent().inject(this);

        splashInteractor.setListener(this);
    }

    void refreshData() {
        Constants.CURRENT_SESSION = qlassifiedAccount.getSessionId();

        splashInteractor.getData(isInternetConnection(context));
    }

    @Override
    public void onSuccess(final Observable<GenresResult> observable,
                          final Observable<GenresResult> observable2,
                          final Observable<Config> observable3,
                          final Observable<Account> observable4) {
        Observable.combineLatest(observable, observable2, observable3,
                new Function3<GenresResult, GenresResult, Config, SplashData>() {
                    @Override
                    public SplashData apply(final GenresResult result,
                                            final GenresResult result2,
                                            final Config result3) {
                        final SplashData splashData = new SplashData();

                        splashData.setGenresResult(result);
                        splashData.setGenresTvResult(result2);
                        splashData.setConfig(result3);

                        return splashData;
                    }
                })
                .subscribe(new Observer<SplashData>() {
                    @Override
                    public void onSubscribe(final Disposable d) {

                    }

                    @Override
                    public void onNext(final SplashData splashData) {
                        splashInteractor.saveData(
                                splashData.getGenresResult(),
                                splashData.getGenresTvResult(),
                                splashData.getConfig());
                    }

                    @Override
                    public void onError(final Throwable e) {
                        e.printStackTrace();

                        getViewState().onError(ErrorType.INTERNET_CONNECTION_ERROR);
                    }

                    @Override
                    public void onComplete() {
                        if (TextUtils.isEmpty(Constants.CURRENT_SESSION)) {
                            getViewState().startLoginActivity();
                        } else {
                            getAccountDetails(observable4);
                        }
                    }
                });
    }

    private void getAccountDetails(final Observable<Account> observable) {
        observable.subscribe(new Observer<Account>() {
            @Override
            public void onSubscribe(final Disposable d) {
            }

            @Override
            public void onNext(final Account account) {
                onAccountDetailsReceived(account);
            }

            @Override
            public void onError(final Throwable e) {
                e.printStackTrace();

                getViewState().startLoginActivity();
            }

            @Override
            public void onComplete() {
            }
        });
    }

    private void onAccountDetailsReceived(final Account accountDetails) {
        if (accountDetails.getStatusCode() == Constants.SUCCESS_CODE) {
            qlassifiedAccount.saveAccountId(Constants.ACCOUNT_ID = accountDetails.getId());
            qlassifiedAccount.saveUserName(Constants.CURRENT_USER = accountDetails.getUsername());
            qlassifiedAccount.saveName(accountDetails.getName());
            qlassifiedAccount.saveGravatarHash(
                    Constants.GRAVATAR_HASH = accountDetails.getAvatar().getGravatar().getHash());

            getViewState().startMainActivity();
        } else {
            getViewState().startLoginActivity();
        }
    }

    private void setUserDara() {
        Constants.ACCOUNT_ID = qlassifiedAccount.getAccountId();
        Constants.CURRENT_USER = qlassifiedAccount.getUserName();
        Constants.GRAVATAR_HASH = qlassifiedAccount.getGravatarHash();
    }

    void setScreenSize(@NonNull final Display display) {
        final Point size = new Point();

        display.getSize(size);

        UiConstants.SCREEN_WIDTH = Math.min(size.x, size.y);
        UiConstants.SCREEN_HEIGHT = Math.max(size.x, size.y);
    }

    @Override
    public void onError(final boolean isDataEmpty) {
        if (isDataEmpty) {
            getViewState().onError(ErrorType.INTERNET_CONNECTION_ERROR);

            return;
        }

        if (TextUtils.isEmpty(Constants.CURRENT_SESSION)) {
            getViewState().startLoginActivity();
        } else {
            setUserDara();

            getViewState().startMainActivity();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        splashInteractor.onDestroy();
    }
}
