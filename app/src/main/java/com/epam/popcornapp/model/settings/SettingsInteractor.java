package com.epam.popcornapp.model.settings;

import android.support.annotation.NonNull;

import com.epam.popcornapp.model.base.BaseInteractor;
import com.epam.popcornapp.pojo.splash_settings.ImagesConfig;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.realm.RealmObject;

public class SettingsInteractor extends BaseInteractor implements ISettingsInteractor {

    private ISettingsInteractor.Actions actions;

    public SettingsInteractor(@NonNull final ISettingsInteractor.Actions actions) {
        this.actions = actions;

        openRealm();
    }

    @Override
    public void getData() {
        actions.onSuccess(getImages().toObservable().cast(ImagesConfig.class));
    }

    private Flowable<RealmObject> getImages() {
        openRealm();

        return getRealm().where(ImagesConfig.class)
                .findFirstAsync()
                .asFlowable()
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void onDestroy() {
        closeRealm();
    }
}
