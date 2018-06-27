package com.epam.popcornapp.ui.tv.info;

import android.content.Context;
import android.support.annotation.NonNull;

import com.epam.popcornapp.R;
import com.epam.popcornapp.application.BaseApplication;
import com.epam.popcornapp.application.Constants;
import com.epam.popcornapp.application.Converter;
import com.epam.popcornapp.application.SettingsUtils;
import com.epam.popcornapp.pojo.base.Image;
import com.epam.popcornapp.pojo.base.MediaCast;
import com.epam.popcornapp.pojo.base.ResultVideo;
import com.epam.popcornapp.pojo.movies.keywords.MovieKeywords;
import com.epam.popcornapp.pojo.movies.keywords.MovieKeywordsResult;
import com.epam.popcornapp.pojo.tv.TvDetails;
import com.epam.popcornapp.pojo.tv.credits.TvCredits;
import com.epam.popcornapp.pojo.tv.images.ImagesTv;
import com.epam.popcornapp.pojo.tv.recomendations.RecommendationsTv;
import com.epam.popcornapp.pojo.tv.season.SeasonInfo;
import com.epam.popcornapp.pojo.tv.videos.Videos;
import com.epam.popcornapp.ui.currentSeasonView.SeasonItem;
import com.epam.popcornapp.ui.informationViews.MainInfoViewItem;
import com.epam.popcornapp.ui.tiles.credits.TileType;
import com.epam.popcornapp.ui.tiles.item.BaseTileItem;
import com.epam.popcornapp.ui.tiles.item.RatingTileItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.realm.RealmList;

public class TvShowData {

    @Inject
    Context context;

    private String posterPath;
    private String videoPath;
    private String tags;
    private int numberOfSeasons;

    private List<SeasonItem> seasons;
    private List<BaseTileItem> galleryLine;
    private List<String> galleryPhotoPaths;
    private List<BaseTileItem> castLine;
    private List<BaseTileItem> recommendationsLine;

    private MainInfoViewItem mainInfoViewItem;

    private TvShowData() {
        BaseApplication.getApplicationComponent().inject(this);
    }

    static TvShowData.Builder newBuilder() {
        return new TvShowData().new Builder();
    }

    String getPosterPath() {
        return posterPath;
    }

    String getVideoPath() {
        return videoPath;
    }

    String getTags() {
        return tags;
    }

    int getNumberOfSeasons() {
        return numberOfSeasons;
    }

    List<SeasonItem> getSeasons() {
        return seasons;
    }

    List<BaseTileItem> getGalleryLine() {
        return galleryLine;
    }

    List<String> getGalleryPhotoPaths() {
        return galleryPhotoPaths;
    }

    List<BaseTileItem> getCastLine() {
        return castLine;
    }

    List<BaseTileItem> getRecommendationsLine() {
        return recommendationsLine;
    }

    MainInfoViewItem getMainInfoViewItem() {
        return mainInfoViewItem;
    }

    public class Builder {

        @Inject
        Context context;

        Builder() {
            BaseApplication.getApplicationComponent().inject(this);
        }

        Builder setDetail(final TvDetails tvDetails) {
            numberOfSeasons = tvDetails.getNumberOfSeasons();

            mainInfoViewItem = MainInfoViewItem.newBuilder()
                    .setMainInfo(Converter.convertObjectListToString(tvDetails.getGenres()))
                    .setLeftAdditionalInfo(
                            Converter.convertNumberToStringWithSignature(getEpisodeRunTime(tvDetails.getEpisodeRunTime()), "min"))
                    .setRightAdditionalInfo(Converter.convertToYear(tvDetails.getLastAirDate()))
                    .setRating(Converter.convertNumberToString(tvDetails.getVoteAverage()))
                    .setVoteCount(Converter.convertNumberToString(tvDetails.getVoteCount()))
                    .setDescription(tvDetails.getOverview())
                    .build();

            return this;
        }

        Builder setPosterPath(@NonNull final String path) {
            posterPath = Constants.BASE_IMAGE_URL + SettingsUtils.getPosterSize(context) + path;

            return this;
        }

        Builder setSeasons(final List<SeasonInfo> val) {
            if (val != null) {
                final List<SeasonItem> seasonItemList = new ArrayList<>(val.size());

                for (final SeasonInfo season : val) {
                    final int seasonNumber = season.getSeasonNumber();
                    final String date = Converter.convertToFullDate(season.getAirDate());
                    final int episodeCount = season.getEpisodeCount();

                    seasonItemList.add(SeasonItem.builder()
                            .setId(season.getId())
                            .setPosterPath(Constants.BASE_IMAGE_URL + SettingsUtils.getPosterSize(context)
                                    + season.getPosterPath())
                            .setSeasonNumber(seasonNumber)
                            .setDescription(date + Constants.DIVIDER +
                                    context.getResources().getQuantityString(R.plurals.episodes,
                                            episodeCount, episodeCount))
                            .setRating(0)
                            .build());
                }

                Collections.reverse(seasonItemList);

                seasons = seasonItemList;
            }

            return this;
        }

        Builder setVideoPath(final Videos val) {
            if (val != null) {
                final RealmList<ResultVideo> list = val.getResultVideos();
                if (list != null) {
                    for (final ResultVideo video : list) {
                        if (video != null && video.getType().equals("Trailer")) {
                            videoPath = "https://www.youtube.com/watch?v="
                                    + video.getKey()
                                    + "&a="
                                    + video.getId();
                        }
                    }
                }
            }

            return this;
        }

        Builder setKeyWords(final MovieKeywordsResult keyWordsResult) {
            if (keyWordsResult != null) {
                final List<MovieKeywords> keyWords = keyWordsResult.getKeywords();

                if (keyWords != null && !keyWords.isEmpty()) {
                    tags = Converter.setCapitalLetter(Converter.convertObjectListToString(keyWords));
                }
            }

            return this;
        }

        Builder setGalleryLine(final ImagesTv val) {
            if (val != null) {
                final RealmList<Image> list = val.getBackdrops();

                if (list != null) {
                    final String imagePath = Constants.BASE_IMAGE_URL + SettingsUtils.getBackdropSize(context);

                    galleryLine = Converter.convertImagesToTileItem(list, imagePath);
                    galleryPhotoPaths = Converter.convertImagesToStringList(list, imagePath);
                }
            }

            return this;
        }

        Builder setCastLine(final TvCredits val) {
            if (val != null) {
                final RealmList<MediaCast> list = val.getCast();

                if (list != null) {
                    final String imagePath = Constants.BASE_IMAGE_URL + SettingsUtils.getBackdropSize(context);

                    castLine = Converter.convertCastToTileItem(list, imagePath);
                }
            }

            return this;
        }

        Builder setRecommendationsLine(final RecommendationsTv val) {
            if (val != null) {
                final RealmList<TvDetails> list = val.getResults();

                if (list != null) {
                    final String imagePath = Constants.BASE_IMAGE_URL + SettingsUtils.getBackdropSize(context);

                    recommendationsLine = convertRecommendationsToBaseTileItem(list, imagePath);
                }
            }

            return this;
        }

        TvShowData build() {
            return TvShowData.this;
        }

        private int getEpisodeRunTime(@NonNull final List<Integer> value) {
            if (!value.isEmpty()) {
                return value.get(0);
            }

            return 0;
        }

        private List<BaseTileItem> convertRecommendationsToBaseTileItem(
                @NonNull final List<TvDetails> recommendationsList, @NonNull final String imagePath) {
            final List<BaseTileItem> baseTileItemList = new ArrayList<>();

            for (final TvDetails result : recommendationsList) {
                final RatingTileItem tileItem = new RatingTileItem(
                        result.getId(),
                        imagePath + result.getPosterPath(),
                        result.getName(),
                        result.getVoteAverage() / 2.0f
                );
                tileItem.setType(TileType.TV);

                baseTileItemList.add(tileItem);
            }

            return baseTileItemList;
        }
    }
}
