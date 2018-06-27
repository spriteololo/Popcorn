package com.epam.popcornapp.inject.modules;

import android.content.Context;

import com.epam.popcornapp.ui.login.base.QlassifiedAccount;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class QlassifiedModule {

    @Provides
    @Singleton
    QlassifiedAccount provideSplashInteractor(final Context context) {
        return new QlassifiedAccount(context);
    }
}
