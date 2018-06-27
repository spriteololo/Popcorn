package com.epam.popcornapp.ui.all.base;

import android.content.Context;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;

import com.arellomobile.mvp.MvpView;

public class BaseInternetMvpPresenter<View extends MvpView> extends BaseMvpPresenter<View> {

    protected boolean isInternetConnection(@NonNull final Context context) {
        final ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return (cm != null ? cm.getActiveNetworkInfo() : null)
                != null && cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }
}
