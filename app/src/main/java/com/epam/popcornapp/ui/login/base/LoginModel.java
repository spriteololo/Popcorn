package com.epam.popcornapp.ui.login.base;

import android.support.annotation.NonNull;

public class LoginModel {

    private static LoginModel loginModel;

    private LoginChangeListener loginChangeListener;

    private LoginModel() {
    }

    public static LoginModel getLoginModel() {
        if (loginModel == null) {
            loginModel = new LoginModel();
        }

        return loginModel;
    }

    public void setLoginChangeListener(@NonNull final LoginChangeListener loginChangeListener) {
        this.loginChangeListener = loginChangeListener;
    }

    public void signIn() {
        if (loginChangeListener != null) {
            loginChangeListener.signIn();
        }
    }

    public void signOut() {
        if (loginChangeListener != null) {
            loginChangeListener.signOut();
        }
    }

    public interface LoginChangeListener {

        void signIn();

        void signOut();
    }
}
