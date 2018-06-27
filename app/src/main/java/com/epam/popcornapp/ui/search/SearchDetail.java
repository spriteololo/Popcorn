package com.epam.popcornapp.ui.search;

import android.content.Context;
import android.support.annotation.NonNull;

import com.epam.popcornapp.application.Constants;
import com.epam.popcornapp.application.Converter;
import com.epam.popcornapp.application.SettingsUtils;
import com.epam.popcornapp.pojo.actors.search.embedded.ActorsSearchResult;
import com.epam.popcornapp.pojo.actors.search.embedded.KnownFor;
import com.epam.popcornapp.pojo.movies.search.MovieSearchResult;
import com.epam.popcornapp.pojo.search.MultiSearchResult;
import com.epam.popcornapp.pojo.tv.search.TvSearchResult;
import com.epam.popcornapp.ui.tiles.credits.TileType;

import java.util.ArrayList;
import java.util.List;

public class SearchDetail {

    private int id;
    private String title;
    private String description;
    private String imagePath;
    private @TileType
    String type;

    private SearchDetail() {
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(final String imagePath) {
        this.imagePath = imagePath;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    static List<SearchDetail> convert(@NonNull final Context context,
                                      final List<MultiSearchResult> data) {
        final List<SearchDetail> res = new ArrayList<>();

        if (data == null || data.isEmpty()) {
            return res;
        }


        for (final MultiSearchResult result : data) {
            final @TileType String mediaType = result.getMediaType();
            switch (mediaType) {
                case TileType.ACTOR:
                    res.add(convertActor(context, result));
                    break;
                case TileType.MOVIE:
                    res.add(convertMovie(context, result));
                    break;
                case TileType.TV:
                    res.add(convertTv(context, result));
                    break;
                default:
                    return null;
            }
        }

        return res;
    }

    static List<SearchDetail> convertActorsResult(@NonNull final Context context,
                                                  final List<ActorsSearchResult> data) {
        final List<SearchDetail> result = new ArrayList<>();

        if (data == null || data.isEmpty()) {
            return result;
        }


        for (final ActorsSearchResult res : data) {
            final SearchDetail detail = new SearchDetail();
            detail.setType(TileType.ACTOR);
            detail.setId(res.getId());
            detail.setTitle(res.getName());
            detail.setImagePath(Constants.BASE_IMAGE_URL + SettingsUtils.getBackdropSize(context)
                    + res.getProfilePath());

            if (!res.getKnownFor().isEmpty()) {
                detail.setDescription(getActorDescription(res.getKnownFor().get(0)));
            }

            result.add(detail);
        }

        return result;
    }

    private static String getActorDescription(final KnownFor item) {
        if (item.getMediaType().equals(TileType.MOVIE)) {
            return item.getTitle();
        } else if (item.getMediaType().equals(TileType.TV)) {
            return item.getName();
        }

        return null;
    }

    static List<SearchDetail> convertMoviesResult(@NonNull final Context context,
                                                  final List<MovieSearchResult> data) {
        final List<SearchDetail> result = new ArrayList<>();

        if (data == null || data.isEmpty()) {
            return result;
        }


        for (final MovieSearchResult res : data) {
            final SearchDetail detail = new SearchDetail();
            detail.setType(TileType.MOVIE);
            detail.setId(res.getId());
            detail.setTitle(res.getTitle());
            detail.setImagePath(Constants.BASE_IMAGE_URL + SettingsUtils.getBackdropSize(context)
                    + res.getPosterPath());
            detail.setDescription(Converter.convertToFullDate(res.getReleaseDate()));
            result.add(detail);
        }

        return result;
    }

    static List<SearchDetail> convertTvResult(@NonNull final Context context,
                                              final List<TvSearchResult> data) {
        final List<SearchDetail> result = new ArrayList<>();

        if (data == null || data.isEmpty()) {
            return result;
        }


        for (final TvSearchResult res : data) {
            final SearchDetail detail = new SearchDetail();
            detail.setType(TileType.TV);
            detail.setId(res.getId());
            detail.setTitle(res.getName());
            detail.setImagePath(Constants.BASE_IMAGE_URL + SettingsUtils.getBackdropSize(context)
                    + res.getPosterPath());
            detail.setDescription(Converter.convertToFullDate(res.getFirstAirDate()));
            result.add(detail);
        }

        return result;
    }

    private static SearchDetail convertActor(@NonNull final Context context,
                                             final MultiSearchResult item) {
        final SearchDetail result = new SearchDetail();
        result.setType(TileType.ACTOR);
        result.setId(item.getId());
        result.setTitle(item.getName());
        result.setImagePath(Constants.BASE_IMAGE_URL + SettingsUtils.getBackdropSize(context)
                + item.getProfilePath());

        if (!item.getKnownFor().isEmpty()) {
            result.setDescription(getActorDescription(item.getKnownFor().get(0)));
        }

        return result;
    }

    private static SearchDetail convertTv(@NonNull final Context context,
                                          final MultiSearchResult item) {
        final SearchDetail result = new SearchDetail();
        result.setType(TileType.TV);
        result.setId(item.getId());
        result.setTitle(item.getName());
        result.setImagePath(Constants.BASE_IMAGE_URL + SettingsUtils.getBackdropSize(context)
                + item.getPosterPath());
        result.setDescription(Converter.convertToFullDate(item.getFirstAirDate()));

        return result;
    }

    private static SearchDetail convertMovie(@NonNull final Context context,
                                             final MultiSearchResult item) {
        final SearchDetail result = new SearchDetail();
        result.setType(TileType.MOVIE);
        result.setId(item.getId());
        result.setTitle(item.getTitle());
        result.setImagePath(Constants.BASE_IMAGE_URL + SettingsUtils.getBackdropSize(context)
                + item.getPosterPath());
        result.setDescription(Converter.convertToFullDate(item.getReleaseDate()));

        return result;
    }
}
