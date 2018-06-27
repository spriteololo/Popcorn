package com.epam.popcornapp.model.base;

import android.support.annotation.NonNull;

import com.epam.popcornapp.pojo.actors.Actor;
import com.epam.popcornapp.pojo.actors.KnownFor;
import com.epam.popcornapp.pojo.actors.KnownForLink;
import com.epam.popcornapp.pojo.base.Genre;
import com.epam.popcornapp.pojo.base.MediaCast;
import com.epam.popcornapp.pojo.movies.details.MovieDetailResult;
import com.epam.popcornapp.pojo.tv.TvDetails;
import com.epam.popcornapp.pojo.tv.episode.TvEpisodeResult;
import com.epam.popcornapp.pojo.tv.season.SeasonInfo;
import com.epam.popcornapp.ui.tiles.credits.TileType;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;

public class RealmUtils {

    public static RealmList<MovieDetailResult> getUpdatedMovies(@NonNull final Realm realm,
                                                                @NonNull final List<MovieDetailResult> serverResponse) {
        final RealmList<MovieDetailResult> updatedMovies = new RealmList<>();

        for (final MovieDetailResult movieFromServer : serverResponse) {
            updatedMovies.add(getUpdatedMovie(realm, movieFromServer));
        }

        return updatedMovies;
    }

    private static MovieDetailResult getUpdatedMovie(@NonNull final Realm realm,
                                                     @NonNull final MovieDetailResult movieFromServer) {
        final MovieDetailResult movieFromDatabase =
                realm.where(MovieDetailResult.class).equalTo("id", movieFromServer.getId()).findFirst();

        if (movieFromDatabase != null) {
            movieFromDatabase.setBackdropPath(movieFromServer.getBackdropPath());
            movieFromDatabase.setGenres(getGenresByIds(realm, movieFromServer.getGenreIds()));
            movieFromDatabase.setReleaseDate(movieFromServer.getReleaseDate());
            movieFromDatabase.setOverview(movieFromServer.getOverview());
            movieFromDatabase.setVoteAverage(movieFromServer.getVoteAverage());
            movieFromDatabase.setVoteCount(movieFromServer.getVoteCount());

            return movieFromDatabase;
        } else {
            movieFromServer.setGenres(getGenresByIds(realm, movieFromServer.getGenreIds()));

            return movieFromServer;
        }
    }

    public static RealmList<Actor> getUpdatedActors(@NonNull final Realm realm,
                                                    @NonNull final RealmList<Actor> serverResponse) {
        final RealmList<Actor> updatedActors = new RealmList<>();

        for (final Actor actorFromServer : serverResponse) {
            final Actor actorFromDatabase =
                    realm.where(Actor.class).equalTo("id", actorFromServer.getId()).findFirst();

            if (actorFromDatabase != null) {
                actorFromDatabase.setName(actorFromServer.getName());
                actorFromDatabase.setProfilePath(actorFromServer.getProfilePath());
                actorFromDatabase.setPopularity(actorFromServer.getPopularity());
                actorFromDatabase.setAdult(actorFromDatabase.isAdult());

                final RealmList<KnownForLink> knownForLinks = actorFromDatabase.getKnownForLink();

                if (knownForLinks == null || knownForLinks.isEmpty()) {
                    actorFromDatabase.setKnownForLink(new RealmList<KnownForLink>());
                    actorFromDatabase.getKnownForLink().addAll(getKnownForLinks(realm, actorFromServer.getKnownFor()));
                }

                updatedActors.add(actorFromDatabase);
            } else {
                actorFromServer.setKnownForLink(getKnownForLinks(realm, actorFromServer.getKnownFor()));

                updatedActors.add(actorFromServer);
            }
        }

        return updatedActors;
    }

    private static RealmList<KnownForLink> getKnownForLinks(@NonNull final Realm realm,
                                                            @NonNull final List<KnownFor> knownForList) {
        final RealmList<KnownForLink> knownForLinks = new RealmList<>();

        for (final KnownFor knownFor : knownForList) {
            final KnownForLink tempLink = new KnownForLink();

            if (knownFor.getMediaType().equals(TileType.MOVIE)) {
                tempLink.setKnownForMovie(insertMovieByKnownFor(realm, knownFor));
            } else {
                tempLink.setKnownForTv(insertTvShowByKnownFor(realm, knownFor));
            }

            tempLink.setMediaType(knownFor.getMediaType());
            tempLink.setId(knownFor.getId());

            knownForLinks.add(tempLink);
        }

        return knownForLinks;
    }

    private static MovieDetailResult insertMovieByKnownFor(@NonNull final Realm realm,
                                                           @NonNull final KnownFor knownFor) {
        MovieDetailResult movieFromDatabase =
                realm.where(MovieDetailResult.class).equalTo("id", knownFor.getId()).findFirst();

        if (movieFromDatabase == null) {
            movieFromDatabase = new MovieDetailResult();

            movieFromDatabase.setId(knownFor.getId());
        }

        movieFromDatabase.setTitle(knownFor.getTitle());
        movieFromDatabase.setPosterPath(knownFor.getPosterPath());
        movieFromDatabase.setBackdropPath(knownFor.getBackdropPath());
        movieFromDatabase.setGenres(getGenresByIds(realm, knownFor.getGenreIds()));
        movieFromDatabase.setReleaseDate(knownFor.getReleaseDate());
        movieFromDatabase.setOverview(knownFor.getOverview());
        movieFromDatabase.setVoteAverage(knownFor.getVoteAverage());
        movieFromDatabase.setVoteCount(knownFor.getVoteCount());
        movieFromDatabase.setVideo(knownFor.getVideo());
        movieFromDatabase.setPopularity(knownFor.getPopularity());
        movieFromDatabase.setOriginalLanguage(knownFor.getOriginalLanguage());
        movieFromDatabase.setOriginalTitle(knownFor.getOriginalTitle());
        movieFromDatabase.setAdult(knownFor.getAdult());

        realm.copyToRealmOrUpdate(movieFromDatabase);

        return movieFromDatabase;
    }

    private static TvDetails insertTvShowByKnownFor(@NonNull final Realm realm,
                                                    @NonNull final KnownFor knownFor) {
        TvDetails tvShowFromDatabase =
                realm.where(TvDetails.class).equalTo("id", knownFor.getId()).findFirst();

        if (tvShowFromDatabase == null) {
            tvShowFromDatabase = new TvDetails();

            tvShowFromDatabase.setId(knownFor.getId());
        }

        tvShowFromDatabase.setName(knownFor.getName());
        tvShowFromDatabase.setOriginalName(knownFor.getOriginalName());
        tvShowFromDatabase.setPosterPath(knownFor.getPosterPath());
        tvShowFromDatabase.setBackdropPath(knownFor.getBackdropPath());
        tvShowFromDatabase.setGenres(getGenresByIds(realm, knownFor.getGenreIds()));
        tvShowFromDatabase.setOriginalLanguage(knownFor.getOriginalLanguage());
        tvShowFromDatabase.setOverview(knownFor.getOverview());
        tvShowFromDatabase.setPopularity(knownFor.getPopularity());
        tvShowFromDatabase.setVoteAverage(knownFor.getVoteAverage());
        tvShowFromDatabase.setVoteCount(knownFor.getVoteCount());
        tvShowFromDatabase.setFirstAirDate(knownFor.getFirstAirDate());
        tvShowFromDatabase.setOriginCountry(knownFor.getOriginCountry());

        realm.copyToRealmOrUpdate(tvShowFromDatabase);

        return tvShowFromDatabase;
    }

    public static RealmList<MediaCast> getUpdatedCredits(@NonNull final Realm realm,
                                                         @NonNull final RealmList<MediaCast> castMovieList) {

        for (final MediaCast castMovie : castMovieList) {
            Actor actor =
                    realm.where(Actor.class).equalTo("id", castMovie.getId()).findFirst();

            if (actor == null) {
                actor = new Actor();

                actor.setId(castMovie.getId());
            }

            actor.setName(castMovie.getName());
            actor.setProfilePath(castMovie.getProfilePath());

            castMovie.setActor(actor);
        }

        return castMovieList;
    }

    public static RealmList<SeasonInfo> getUpdatedSeasons(@NonNull final Realm realm,
                                                          @NonNull final RealmList<SeasonInfo> seasonsList) {

        final RealmList<SeasonInfo> seasonsListResult = new RealmList<>();

        for (final SeasonInfo seasonInfo : seasonsList) {
            SeasonInfo seasonFromDb =
                    realm.where(SeasonInfo.class).equalTo("id", seasonInfo.getId()).findFirst();

            if (seasonFromDb == null) {
                seasonFromDb = new SeasonInfo();

                seasonFromDb.setId(seasonInfo.getId());
            }

            seasonFromDb.setPosterPath(seasonInfo.getPosterPath());
            seasonFromDb.setEpisodeCount(seasonInfo.getEpisodeCount());
            seasonFromDb.setSeasonNumber(seasonInfo.getSeasonNumber());
            seasonFromDb.setAirDate(seasonInfo.getAirDate());

            seasonsListResult.add(seasonFromDb);
        }

        return seasonsListResult;
    }

    public static RealmList<TvEpisodeResult> getUpdatedEpisodes(@NonNull final Realm realm,
                                                                @NonNull final RealmList<TvEpisodeResult> episodesList) {

        final RealmList<TvEpisodeResult> episodesListResult = new RealmList<>();

        for (final TvEpisodeResult episode : episodesList) {
            TvEpisodeResult episodeFromDb =
                    realm.where(TvEpisodeResult.class).equalTo("id", episode.getId()).findFirst();

            if (episodeFromDb == null) {
                episodeFromDb = new TvEpisodeResult();

                episodeFromDb.setId(episode.getId());
            }

            episodeFromDb.setName(episode.getName());
            episodeFromDb.setSeasonNumber(episode.getSeasonNumber());
            episodeFromDb.setEpisodeNumber(episode.getEpisodeNumber());
            episodeFromDb.setOverview(episode.getOverview());
            episodeFromDb.setStillPath(episode.getStillPath());
            episodeFromDb.setAirDate(episode.getAirDate());
            episodeFromDb.setVoteAverage(episode.getVoteAverage());
            episodeFromDb.setVoteCount(episode.getVoteCount());

            episodesListResult.add(episodeFromDb);
        }

        return episodesListResult;
    }

    public static RealmList<Genre> getGenresByIds(@NonNull final Realm realm,
                                                  @NonNull final RealmList<Integer> value) {

        final RealmList<Genre> result = new RealmList<>();

        for (final int id : value) {
            final Genre genre = realm.where(Genre.class).equalTo("id", id).findFirst();

            if (genre != null) {
                result.add(genre);
            }
        }

        return result;
    }
}
