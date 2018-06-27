package com.epam.popcornapp.model.tv.info;


import android.support.annotation.NonNull;

import com.epam.popcornapp.application.BaseApplication;
import com.epam.popcornapp.application.Constants;
import com.epam.popcornapp.inject.TvInterface;
import com.epam.popcornapp.model.base.BaseInteractor;
import com.epam.popcornapp.model.base.RealmUtils;
import com.epam.popcornapp.model.tv.BaseTvDB;
import com.epam.popcornapp.pojo.actors.Actor;
import com.epam.popcornapp.pojo.base.Genre;
import com.epam.popcornapp.pojo.base.Image;
import com.epam.popcornapp.pojo.base.rating.RatingItem;
import com.epam.popcornapp.pojo.base.rating.RatingResponse;
import com.epam.popcornapp.pojo.movies.keywords.MovieKeywordsResult;
import com.epam.popcornapp.pojo.tv.TvDetails;
import com.epam.popcornapp.pojo.tv.credits.TvCredits;
import com.epam.popcornapp.pojo.tv.images.ImagesTv;
import com.epam.popcornapp.pojo.tv.recomendations.RecommendationsTv;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;

public class TvInfoInteractor extends BaseInteractor implements ITvInfoInteractor {

    @Inject
    TvInterface webTvApi;

    private ITvInfoInteractor.InteractorActions listener;

    public TvInfoInteractor() {
        BaseApplication.getApplicationComponent().inject(this);

        openRealm();
    }

    @Override
    public void setListener(final InteractorActions listener) {
        this.listener = listener;
    }

    @Override
    public void getCurrentRating(final int id) {
        if (listener != null) {

            listener.onRatingLoaded(webTvApi.getTvShowCurrentRating(id,
                    Constants.API_KEY, Constants.CURRENT_SESSION));
        }
    }

    @Override
    public void rateTvShow(final int id, final float rating) {
        if (listener == null) {
            return;
        }

        webTvApi.rateTvShow(id, Constants.API_KEY, Constants.CURRENT_SESSION,
                new RatingItem(rating))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RatingResponse>() {
                    @Override
                    public void onSubscribe(final Disposable d) {

                    }

                    @Override
                    public void onNext(final RatingResponse ratingResponse) {
                        listener.onRatingResult(true);
                    }

                    @Override
                    public void onError(final Throwable e) {
                        listener.onRatingResult(false);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void deleteRating(final int id) {
        if (listener == null) {
            return;
        }

        webTvApi.deleteTvShowRating(id, Constants.API_KEY, Constants.CURRENT_SESSION)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RatingResponse>() {
                    @Override
                    public void onSubscribe(final Disposable d) {

                    }

                    @Override
                    public void onNext(final RatingResponse ratingResponse) {
                        listener.onRatingResult(true);
                    }

                    @Override
                    public void onError(final Throwable e) {
                        listener.onRatingResult(false);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void getDetailedTvShow(final boolean hasConnection, final int id) {
        if (hasConnection) {
            final Observable<TvDetails> observable =
                    webTvApi.getTvDetails(id, Constants.API_KEY)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .map(new Function<TvDetails, TvDetails>() {
                                @Override
                                public TvDetails apply(final TvDetails tvDetails) throws Exception {
                                    insertDetailesTvShow(tvDetails);

                                    return tvDetails;
                                }
                            });

            if (listener != null) {
                listener.onSuccess(observable, true);
            }
        } else {
            final Flowable<RealmObject> result = getDetailedTvShow(id);

            if (listener != null) {
                listener.onSuccess(result.toObservable().cast(TvDetails.class), false);
                listener.onError();
            }

        }
    }

    @Override
    public void insertDetailesTvShow(final TvDetails tvDetails) {
        saveTvShow(tvDetails);
    }

    private void saveTvShow(final TvDetails tvDetails) {
        openRealm();

        getRealm().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull final Realm realm) {
                TvDetails item =
                        realm.where(TvDetails.class)
                                .equalTo("id", tvDetails.getId())
                                .findFirst();

                tvDetails.getCredits().setId(tvDetails.getId());

                if (item == null) {
                    item = realm.createObject(TvDetails.class, tvDetails.getId());
                }

                final RealmList<Actor> createdBy = item.getCreatedBy();
                createdBy.clear();
                saveCreatedBy(createdBy, tvDetails.getCreatedBy());
                item.setEpisodeRunTime(tvDetails.getEpisodeRunTime());
                item.getGenres().clear();
                item.getGenres().addAll(saveGenres(tvDetails.getGenres(), realm));
                item.setHomepage(tvDetails.getHomepage());
                item.setInProduction(tvDetails.isInProduction());
                item.setLanguages(tvDetails.getLanguages());
                item.setLastAirDate(tvDetails.getLastAirDate());
                item.getNetworks().clear();
                item.getNetworks().addAll(tvDetails.getNetworks());
                item.setNumberOfEpisodes(tvDetails.getNumberOfEpisodes());
                item.setNumberOfSeasons(tvDetails.getNumberOfSeasons());
                item.getProductionCompanies().clear();
                item.getProductionCompanies().addAll(tvDetails.getProductionCompanies());

                item.getSeasons().clear();
                item.getSeasons().addAll(realm.copyToRealmOrUpdate(
                        RealmUtils.getUpdatedSeasons(realm, tvDetails.getSeasons())));

                item.setStatus(tvDetails.getStatus());
                item.setType(tvDetails.getType());

                item.setKeywordsResult(realm.copyToRealmOrUpdate(
                        getUpdatedKeyWords(tvDetails.getId(), tvDetails.getKeywordsResult())));

                item.setImages(saveImages(item.getImages(), tvDetails.getImages()));

                final TvCredits tvCredits = tvDetails.getCredits();

                tvCredits.setCrew(null);
                tvCredits.setCast(RealmUtils.getUpdatedCredits(realm, tvCredits.getCast()));

                item.setCredits(realm.copyToRealmOrUpdate(tvCredits));

                final RecommendationsTv recommendations = item.getRecommendations();

                if (recommendations != null) {
                    final RealmList<TvDetails> list = recommendations.getResults();
                    list.clear();
                    saveRecommendations(list, tvDetails.getRecommendations().getResults());
                } else {
                    item.setRecommendations(createRecommendations(tvDetails, realm));
                }

                tvDetails.getVideos().setId(tvDetails.getId());
                item.setVideos(realm.copyToRealmOrUpdate(tvDetails.getVideos()));
            }
        });
    }

    @Override
    public void onDestroy() {
        closeRealm();
    }

    private MovieKeywordsResult getUpdatedKeyWords(final int id,
                                                   @NonNull final MovieKeywordsResult keywordsResult) {
        keywordsResult.setId(id);
        keywordsResult.setKeywords(keywordsResult.getKeywordsTv());

        return keywordsResult;
    }

    private RecommendationsTv createRecommendations(final TvDetails tvDetails, final Realm realm) {
        final RecommendationsTv recommendations = realm.createObject(RecommendationsTv.class);
        final RealmList<TvDetails> result = new RealmList<>();
        recommendations.setPage(tvDetails.getRecommendations().getPage());
        recommendations.setTotalPages(
                tvDetails.getRecommendations().getTotalPages());

        recommendations.setTotalResults(
                tvDetails.getRecommendations().getTotalResults());

        saveRecommendations(result, tvDetails.getRecommendations().getResults());
        recommendations.setResults(result);

        return recommendations;
    }

    private void saveRecommendations(final RealmList<TvDetails> result,
                                     final RealmList<TvDetails> data) {
        for (final TvDetails res : data) {
            final TvDetails item = BaseTvDB.saveTv(null, res, getRealm());
            result.add(item);
        }
    }

    private Flowable<RealmObject> getDetailedTvShow(final int id) {
        openRealm();

        return getRealm().where(TvDetails.class)
                .equalTo("id", id)
                .findFirstAsync()
                .asFlowable()
                .observeOn(AndroidSchedulers.mainThread());
    }

    private void saveCreatedBy(final RealmList<Actor> result, final RealmList<Actor> data) {
        for (final Actor item : data) {
            Actor actor = getRealm().where(Actor.class)
                    .equalTo("id", item.getId()).findFirst();

            if (actor != null) {
                actor.setName(item.getName());
                actor.setGender(item.getGender());
                actor.setProfilePath(item.getProfilePath());
            } else {
                actor = getRealm().createObject(Actor.class, item.getId());
                actor.setName(item.getName());
                actor.setGender(item.getGender());
                actor.setProfilePath(item.getProfilePath());

                actor = getRealm().copyToRealm(actor);
            }

            result.add(actor);
        }
    }

    private ImagesTv saveImages(ImagesTv result, final ImagesTv data) {
        if (result == null) {
            result = getRealm().createObject(ImagesTv.class);
        } else {
            result.getPosters().clear();
            result.getBackdrops().clear();
        }

        for (final Image item : data.getBackdrops()) {
            result.getBackdrops().add(getRealm().copyToRealmOrUpdate(item));
        }

        for (final Image item : data.getPosters()) {
            result.getPosters().add(getRealm().copyToRealmOrUpdate(item));
        }

        return result;
    }

    private RealmList<Genre> saveGenres(final RealmList<Genre> genres, final Realm realm) {
        final RealmList<Genre> result = new RealmList<>();
        for (final Genre genre : genres) {
            final Genre item = realm.copyToRealmOrUpdate(genre);
            result.add(item);
        }

        return result;
    }
}
