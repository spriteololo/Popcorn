package com.epam.popcornapp.model.tv.season;

import com.epam.popcornapp.pojo.tv.season.SeasonInfo;
import com.epam.popcornapp.ui.tv.season.StartParams;

import io.reactivex.Observable;

public interface ITvSeasonInfoInteractor {

    void loadSeasonInfo(StartParams startParams, boolean hasConnection);

    void setListener(InteractorActions listener);

    void closeDb();

    interface InteractorActions {

        void onSuccess(Observable<SeasonInfo> result, boolean isSaved);

        void onError();
    }
}
