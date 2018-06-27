package com.epam.popcornapp.ui.tv.season;

import android.content.Context;
import android.support.annotation.NonNull;

import com.epam.popcornapp.R;
import com.epam.popcornapp.application.BaseApplication;
import com.epam.popcornapp.application.Constants;
import com.epam.popcornapp.application.Converter;
import com.epam.popcornapp.application.SettingsUtils;
import com.epam.popcornapp.pojo.tv.credits.TvCredits;
import com.epam.popcornapp.pojo.tv.episode.TvEpisodeResult;
import com.epam.popcornapp.pojo.tv.season.SeasonInfo;
import com.epam.popcornapp.ui.episode.EpisodeItem;
import com.epam.popcornapp.ui.informationViews.MainInfoViewItem;
import com.epam.popcornapp.ui.tiles.item.BaseTileItem;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class TvSeasonInfo {

    @Inject
    Context context;

    private int number;
    private String posterPath;
    private List<BaseTileItem> actors;
    private MainInfoViewItem mainInfoViewItem;
    private List<EpisodeItem> episodes;

    private TvSeasonInfo() {
        BaseApplication.getApplicationComponent().inject(this);
    }

    static TvSeasonInfo.Builder Builder() {
        return new TvSeasonInfo().new Builder();
    }

    int getNumber() {
        return number;
    }

    String getPosterPath() {
        return posterPath;
    }

    List<BaseTileItem> getActors() {
        return actors;
    }

    MainInfoViewItem getMainInfoViewItem() {
        return mainInfoViewItem;
    }

    List<EpisodeItem> getEpisodes() {
        return episodes;
    }

    class Builder {

        public Builder() {
        }

        Builder setPosterPath(@NonNull final String path) {
            posterPath = Constants.BASE_IMAGE_URL + SettingsUtils.getPosterSize(context) + path;

            return this;
        }

        Builder setBaseInfo(final SeasonInfo seasonInfo) {
            number = seasonInfo.getSeasonNumber();

            String name = seasonInfo.getName();

            if (name == null) {
                final int number = seasonInfo.getSeasonNumber();

                name = number == 0 ? context.getString(com.epam.ui.R.string.special_season)
                        : context.getString(R.string.season_name, number);
            }

            mainInfoViewItem = MainInfoViewItem.newBuilder()
                    .setMainInfo(name)
                    .setLeftAdditionalInfo(context.getResources().getQuantityString(R.plurals.episodes,
                            seasonInfo.getEpisodeCount(), seasonInfo.getEpisodeCount()))
                    .setRightAdditionalInfo(Converter.convertToYear(seasonInfo.getAirDate()))
                    .setRating(Constants.EMPTY_STRING)
                    .setVoteCount(Constants.EMPTY_STRING)
                    .setDescription(checkDescription(seasonInfo.getOverview()))
                    .build();

            return this;
        }

        Builder setActors(final TvCredits creditsTv) {
            if (creditsTv != null) {
                final String imagePath = Constants.BASE_IMAGE_URL + SettingsUtils.getBackdropSize(context);

                actors = Converter.convertCastToTileItem(creditsTv.getCast(), imagePath);
            }

            return this;
        }

        Builder setEpisodes(final List<TvEpisodeResult> episodeList) {
            if (episodeList == null || episodeList.isEmpty()) {
                return this;
            }

            final List<EpisodeItem> result = new ArrayList<>();

            for (final TvEpisodeResult episode : episodeList) {
                final EpisodeItem item = new EpisodeItem();

                item.setId(episode.getId());
                item.setPosterPath(Constants.BASE_IMAGE_URL + SettingsUtils.getBackdropSize(context)
                        + episode.getStillPath());
                item.setTitle(episode.getName());
                item.setEpisodeNumber(episode.getEpisodeNumber());
                item.setRating(episode.getVoteAverage());
                item.setReleaseDate(Converter.convertToFullDate(episode.getAirDate()));
                item.setOverview(episode.getOverview());

                result.add(item);
            }

            episodes = result;

            return this;
        }

        TvSeasonInfo build() {
            return TvSeasonInfo.this;
        }
    }

    private String checkDescription(final String overview) {
        if (overview == null || overview.isEmpty()) {
            return context.getResources().getString(R.string.no_season_description);
        } else {
            return overview;
        }
    }
}
