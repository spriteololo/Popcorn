package com.epam.popcornapp.model.login;

import com.epam.popcornapp.application.BaseApplication;
import com.epam.popcornapp.application.Constants;
import com.epam.popcornapp.inject.LoginInterface;
import com.epam.popcornapp.model.base.BaseInteractor;
import com.epam.popcornapp.pojo.login.Session;
import com.epam.popcornapp.pojo.login.Token;
import com.epam.popcornapp.pojo.login.ValidationRequest;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;

public class LoginInteractor extends BaseInteractor implements ILoginInteractor {

    @Inject
    LoginInterface loginInterface;

    private Listener listener;

    public LoginInteractor() {
        BaseApplication.getApplicationComponent().inject(this);
    }

    @Override
    public void setListener(final Listener listener) {
        this.listener = listener;
    }

    @Override
    public void getSessionId(final String userName, final String password,
                             final boolean isInternetConnection) {
        if (listener == null) {
            return;
        }

        if (isInternetConnection) {
            final Flowable<Token> tokenFlowable = loginInterface.getToken(Constants.API_KEY);

            final Flowable<ValidationRequest> validationFlowable = tokenFlowable
                    .flatMap(new Function<Token, Flowable<ValidationRequest>>() {

                        @Override
                        public Flowable<ValidationRequest> apply(final Token token) {
                            return loginInterface.validateWithLogin(
                                    Constants.API_KEY, userName, password, token.getRequestToken());
                        }
                    });

            final Flowable<Session> sessionFlowable = validationFlowable
                    .flatMap(new Function<ValidationRequest, Flowable<Session>>() {

                        @Override
                        public Flowable<Session> apply(final ValidationRequest validationRequest) {
                            return loginInterface.getSession(
                                    Constants.API_KEY, validationRequest.getRequestToken());
                        }
                    });


            listener.onGetSessionSuccess(sessionFlowable);
        } else {
            listener.onError();
        }
    }

    @Override
    public void getAccountDetails(final String sessionId, final boolean isInternetConnection) {
        if (isInternetConnection) {
            listener.onGetAccountDetailsSuccess(
                    loginInterface.getAccountDetails(Constants.API_KEY, sessionId));
        } else {
            listener.onError();
        }
    }
}
