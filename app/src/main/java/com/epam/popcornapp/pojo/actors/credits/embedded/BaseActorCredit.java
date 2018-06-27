package com.epam.popcornapp.pojo.actors.credits.embedded;

import io.realm.RealmList;

public interface BaseActorCredit {

    int getId();
    String getOriginalLanguage();
    String getOriginalTitle();
    String getOverview();
    int getVoteCount();
    boolean isVideo();
    String getPosterPath();
    String getBackdropPath();
    String getTitle();
    double getPopularity();
    RealmList<Integer> getGenreIds();
    float getVoteAverage();
    boolean isAdult();
    String getReleaseDate();
    int getEpisodeCount();
    RealmList<String> getOriginCountry();
    String getOriginalName();
    String getName();
    String getFirstAirDate();
}
