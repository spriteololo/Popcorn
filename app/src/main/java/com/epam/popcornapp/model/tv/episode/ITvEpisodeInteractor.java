package com.epam.popcornapp.model.tv.episode;

import com.epam.popcornapp.pojo.base.rating.Rating;
import com.epam.popcornapp.pojo.tv.episode.TvEpisodeResult;
import com.epam.popcornapp.ui.tv.episode.TvEpisodeParams;

import io.reactivex.Observable;

public interface ITvEpisodeInteractor {

    void getData(TvEpisodeParams tvEpisodeParams, boolean isInternetConnection);

    void setListener(Listener listener);

    void onDestroy();

    void getCurrentRating(TvEpisodeParams params, String session);

    void rateEpisode(float rating, TvEpisodeParams params);

    void deleteRating(TvEpisodeParams params);

    interface Listener {

        void onSuccess(Observable<TvEpisodeResult> observable, boolean isResponseFromServer);

        void onError();

        void onRatingLoaded(Observable<Rating> observable);

        void onRatingResult(boolean isSuccess);
    }
}
