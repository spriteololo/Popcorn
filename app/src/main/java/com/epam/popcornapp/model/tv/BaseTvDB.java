package com.epam.popcornapp.model.tv;

import android.support.annotation.NonNull;

import com.epam.popcornapp.pojo.base.Genre;
import com.epam.popcornapp.pojo.tv.TvDetails;
import com.epam.popcornapp.pojo.tv.credits.CrewTv;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;

public class BaseTvDB {

    static RealmList<TvDetails> convertPage(final RealmList<TvDetails> items, final Realm realm) {
        final RealmList<TvDetails> result = new RealmList<>();
        for (final TvDetails item : items) {
            TvDetails resultItem = realm.where(TvDetails.class)
                    .equalTo("id", item.getId()).findFirst();

            resultItem = saveTv(resultItem, item, realm);

            result.add(resultItem);
        }

        return result;
    }

    public static TvDetails saveTv(TvDetails localItem, final TvDetails networkItem, final Realm realm) {
        if (localItem != null) {
            if (localItem.getId() == networkItem.getId()) {
                updateExistingItem(localItem, networkItem, realm);
            } else {
                final TvDetails dbTvDetails = getItem(networkItem.getId(), realm);

                if (dbTvDetails != null) {
                    updateExistingItem(dbTvDetails, networkItem, realm);

                    return dbTvDetails;
                } else {
                    return realm.copyToRealm(networkItem);
                }
            }
        } else {
            localItem = getItem(networkItem.getId(), realm);

            if (localItem != null) {
                updateExistingItem(localItem, networkItem, realm);

                return localItem;
            } else {
                localItem = realm.copyToRealmOrUpdate(networkItem);
                localItem.setGenres(saveGenres(networkItem.getGenreIds(), realm));

                return localItem;
            }
        }

        return localItem;
    }

    public static String getNameByJobKey(@NonNull final List<CrewTv> crewList, final String jobKey) {
        for (final CrewTv crew : crewList) {
            if (crew.getJob().equals(jobKey)) {
                return crew.getName();
            }
        }

        return "";
    }

    private static TvDetails getItem(final int id, final Realm realm) {
        return realm.where(TvDetails.class)
                .equalTo("id", id)
                .findFirst();
    }

    private static void updateExistingItem(final TvDetails oldItem, final TvDetails newItem, final Realm realm) {
        oldItem.setPosterPath(newItem.getPosterPath());
        oldItem.setPopularity(newItem.getPopularity());
        oldItem.setBackdropPath(newItem.getBackdropPath());
        oldItem.setVoteAverage(newItem.getVoteAverage());
        oldItem.setOverview(newItem.getOverview());
        oldItem.setFirstAirDate(newItem.getFirstAirDate());
        oldItem.setOriginCountry(newItem.getOriginCountry());
        oldItem.setGenres(saveGenres(newItem.getGenreIds(), realm));
        oldItem.setOriginalLanguage(newItem.getOriginalLanguage());
        oldItem.setVoteCount(newItem.getVoteCount());
        oldItem.setName(newItem.getName());
        oldItem.setOriginalName(newItem.getOriginalName());
    }

    private static RealmList<Genre> saveGenres(final RealmList<Integer> genreIDs, final Realm realm) {
        final RealmList<Genre> genres = new RealmList<>();
        for (final int id : genreIDs) {
            final Genre genre = realm.where(Genre.class).equalTo("id", id).findFirst();

            if (genre != null) {
                genres.add(genre);
            }
        }

        return genres;
    }
}
