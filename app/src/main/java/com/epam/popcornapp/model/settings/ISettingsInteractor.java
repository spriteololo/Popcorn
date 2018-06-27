package com.epam.popcornapp.model.settings;


import com.epam.popcornapp.pojo.splash_settings.ImagesConfig;

import io.reactivex.Observable;

public interface ISettingsInteractor {

    void getData();

    void onDestroy();

    interface Actions {

        void onSuccess(Observable<ImagesConfig> observable);

        void onError();
    }
}
