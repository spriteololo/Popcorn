package com.epam.popcornapp.model.splash;

import com.epam.popcornapp.pojo.account.Account;
import com.epam.popcornapp.pojo.genres.GenresResult;
import com.epam.popcornapp.pojo.splash_settings.Config;

import io.reactivex.Observable;

public interface ISplashInteractor {

    void setListener(Actions actions);

    void getData(boolean isInternetConnection);

    void saveData(GenresResult value, GenresResult genresTvResult, Config config);

    void onDestroy();

    interface Actions {

        void onSuccess(Observable<GenresResult> observable,
                       Observable<GenresResult> observable2,
                       Observable<Config> observable3,
                       Observable<Account> observable4);

        void onError(boolean isDataEmpty);
    }
}
