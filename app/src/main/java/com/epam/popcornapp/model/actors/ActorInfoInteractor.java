package com.epam.popcornapp.model.actors;

import android.support.annotation.NonNull;

import com.epam.popcornapp.application.BaseApplication;
import com.epam.popcornapp.application.Constants;
import com.epam.popcornapp.inject.ActorsInterface;
import com.epam.popcornapp.model.base.BaseInteractor;
import com.epam.popcornapp.model.base.RealmUtils;
import com.epam.popcornapp.pojo.actors.Actor;
import com.epam.popcornapp.pojo.actors.credits.Credits;
import com.epam.popcornapp.pojo.actors.credits.embedded.ActorCast;
import com.epam.popcornapp.pojo.actors.credits.embedded.ActorCrew;
import com.epam.popcornapp.pojo.actors.credits.embedded.BaseActorCredit;
import com.epam.popcornapp.pojo.movies.details.MovieDetailResult;
import com.epam.popcornapp.pojo.tv.TvDetails;
import com.epam.popcornapp.ui.tiles.credits.TileType;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmObject;

public class ActorInfoInteractor extends BaseInteractor implements IActorInfoInteractor {

    @Inject
    ActorsInterface actorsInterface;

    private IActorInfoInteractor.RequestListener listener;

    public ActorInfoInteractor() {
        BaseApplication.getApplicationComponent().inject(this);

        openRealm();
    }

    @Override
    public void setListener(final RequestListener listener) {
        this.listener = listener;
    }

    @Override
    public void loadInfo(final boolean hasConnection, final int peopleId) {
        if (hasConnection) {
            final Observable<Actor> result =
                    actorsInterface.getDetails(peopleId, Constants.API_KEY)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .map(new Function<Actor, Actor>() {
                                @Override
                                public Actor apply(@NonNull final Actor actor) throws Exception {
                                    save(actor);

                                    return actor;
                                }
                            });

            if (listener != null) {
                listener.onSuccess(result, true);
            }
        } else {
            if (listener != null) {
                listener.onSuccess(getActor(peopleId).toObservable().cast(Actor.class),
                        false);
                listener.onFailure();
            }
        }
    }

    private Flowable<RealmObject> getActor(final int id) {
        openRealm();

        return getRealm().where(Actor.class)
                .equalTo("id", id)
                .findFirstAsync()
                .asFlowable()
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void save(final Actor actor) {
        saveActor(actor);
    }

    @Override
    public void onDestroy() {
        closeRealm();
    }

    private void saveActor(final Actor actor) {
        openRealm();

        actor.getImages().setId(actor.getId());
        actor.getCredits().setId(actor.getId());

        getRealm().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull final Realm realm) {
                final Actor item = realm.where(Actor.class)
                        .equalTo("id", actor.getId())
                        .findFirst();

                if (item != null) {
                    item.setBirthday(actor.getBirthday());
                    item.setDeathday(actor.getDeathday());
                    item.setAlsoKnownAs(actor.getAlsoKnownAs());
                    item.setGender(actor.getGender());
                    item.setBiography(actor.getBiography());
                    item.setPlaceOfBirth(actor.getPlaceOfBirth());
                    item.setImdbId(actor.getImdbId());
                    item.setHomepage(actor.getHomepage());
                    item.setImages(realm.copyToRealmOrUpdate(actor.getImages()));
                    saveCredits(actor.getCredits());
                    item.setCredits(realm.copyToRealmOrUpdate(actor.getCredits()));
                } else {
                    saveCredits(actor.getCredits());
                    realm.copyToRealm(actor);
                }
            }
        });
    }

    private void saveCredits(final Credits credits) {
        for (final ActorCast cast : credits.getCast()) {
            if (cast.getMediaType().equals(TileType.TV)) {
                final TvDetails tv = updateTv(cast);
                cast.setTv(tv);
                cast.setMovie(null);
            } else if (cast.getMediaType().equals(TileType.MOVIE)) {
                final MovieDetailResult movie = updateMovie(cast);
                cast.setMovie(movie);
                cast.setTv(null);
            }
        }

        for (final ActorCrew crew : credits.getCrew()) {
            if (crew.getMediaType().equals(TileType.TV)) {
                final TvDetails tv = updateTv(crew);
                crew.setTv(tv);
                crew.setMovie(null);
            } else if (crew.getMediaType().equals(TileType.MOVIE)) {
                final MovieDetailResult movie = updateMovie(crew);
                crew.setMovie(movie);
                crew.setTv(null);
            }
        }
    }

    private <T extends BaseActorCredit> MovieDetailResult updateMovie(final T item) {
        MovieDetailResult movie = getRealm().where(MovieDetailResult.class)
                .equalTo("id", item.getId()).findFirst();

        if (movie == null) {
            movie = getRealm().createObject(MovieDetailResult.class, item.getId());
        }

        movie.setOriginalTitle(item.getOriginalTitle());
        movie.setOverview(item.getOverview());
        movie.setVoteCount(item.getVoteCount());
        movie.setVideo(item.isVideo());
        movie.setPosterPath(item.getPosterPath());
        movie.setBackdropPath(item.getBackdropPath());
        movie.setPopularity(item.getPopularity());
        movie.setTitle(item.getTitle());
        movie.setOriginalLanguage(item.getOriginalLanguage());
        movie.setGenres(RealmUtils.getGenresByIds(getRealm(), item.getGenreIds()));
        movie.setVoteAverage(item.getVoteAverage());
        movie.setAdult(item.isAdult());
        movie.setReleaseDate(item.getReleaseDate());

        return movie;
    }

    private <T extends BaseActorCredit> TvDetails updateTv(final T item) {
        TvDetails tv = getRealm().where(TvDetails.class)
                .equalTo("id", item.getId()).findFirst();

        if (tv == null) {
            tv = getRealm().createObject(TvDetails.class, item.getId());
        }

        tv.setOverview(item.getOverview());
        tv.setOriginCountry(item.getOriginCountry());
        tv.setOriginalName(item.getOriginalName());
        tv.setGenres(RealmUtils.getGenresByIds(getRealm(), item.getGenreIds()));
        tv.setName(item.getName());
        tv.setBackdropPath(item.getBackdropPath());
        tv.setPopularity(item.getPopularity());
        tv.setFirstAirDate(item.getFirstAirDate());
        tv.setOriginalLanguage(item.getOriginalLanguage());
        tv.setVoteCount(item.getVoteCount());
        tv.setVoteAverage(item.getVoteAverage());
        tv.setPosterPath(item.getPosterPath());

        return tv;
    }
}
