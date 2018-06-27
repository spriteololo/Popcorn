package com.epam.popcornapp.application;

import android.app.Application;
import android.support.multidex.MultiDex;

import com.bumptech.glide.request.target.ViewTarget;
import com.epam.popcornapp.R;
import com.epam.popcornapp.inject.ApplicationComponent;
import com.epam.popcornapp.inject.DaggerApplicationComponent;
import com.epam.popcornapp.inject.modules.NetworkModule;
import com.epam.popcornapp.inject.modules.RetrofitModule;
import com.epam.popcornapp.inject.modules.RootModule;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.rx.RealmObservableFactory;

public class BaseApplication extends Application {

    private static ApplicationComponent applicationComponent;

    public static ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        initApplicationComponent();
        initRealm();

        ViewTarget.setTagId(R.id.glide_tag);
    }

    private void initRealm() {
        Realm.init(this);
        final RealmConfiguration config = new RealmConfiguration.Builder()
                .name("popcorn.realm")
                .deleteRealmIfMigrationNeeded()
                .schemaVersion(1)
                .rxFactory(new RealmObservableFactory())
                .build();

        Realm.setDefaultConfiguration(config);
    }

    private void initApplicationComponent() {
        applicationComponent = DaggerApplicationComponent.builder()
                .rootModule(new RootModule(getApplicationContext()))
                .retrofitModule(new RetrofitModule(Constants.MovieDbApi.BASE_URL))
                .networkModule(new NetworkModule(Constants.NetworkingConfig.TIMEOUT))
                .build();
    }
}
