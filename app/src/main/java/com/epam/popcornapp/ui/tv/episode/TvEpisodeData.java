package com.epam.popcornapp.ui.tv.episode;

import android.content.Context;
import android.support.annotation.NonNull;

import com.epam.popcornapp.R;
import com.epam.popcornapp.application.BaseApplication;
import com.epam.popcornapp.application.Constants;
import com.epam.popcornapp.application.Converter;
import com.epam.popcornapp.application.SettingsUtils;
import com.epam.popcornapp.pojo.base.MediaCast;
import com.epam.popcornapp.pojo.tv.credits.TvCredits;
import com.epam.popcornapp.pojo.tv.episode.TvEpisodeImages;
import com.epam.popcornapp.pojo.tv.episode.TvEpisodeResult;
import com.epam.popcornapp.ui.informationViews.MainInfoViewItem;
import com.epam.popcornapp.ui.tiles.item.BaseTileItem;

import java.text.DecimalFormat;
import java.util.List;

import javax.inject.Inject;

public class TvEpisodeData {

    @Inject
    Context context;

    private String photoPath;
    private String director;
    private String writer;

    private List<String> galleryPhotoPaths;

    private List<BaseTileItem> imagePathList;
    private List<BaseTileItem> billedCastList;

    private MainInfoViewItem mainInfoViewItem;

    private TvEpisodeData() {
        BaseApplication.getApplicationComponent().inject(this);
    }

    String getPhotoPath() {
        return photoPath;
    }

    String getDirector() {
        return director;
    }

    String getWriter() {
        return writer;
    }

    List<String> getGalleryPhotoPaths() {
        return galleryPhotoPaths;
    }

    List<BaseTileItem> getImagePathList() {
        return imagePathList;
    }

    List<BaseTileItem> getBilledCastList() {
        return billedCastList;
    }

    MainInfoViewItem getMainInfoViewItem() {
        return mainInfoViewItem;
    }

    static Builder newBuilder() {
        return new TvEpisodeData().new Builder();
    }

    class Builder {

        Builder() {
        }

        Builder setPhoto(final String path) {
            photoPath = Constants.BASE_IMAGE_URL + SettingsUtils.getPosterSize(context) + path;

            return this;
        }

        Builder setDetail(final TvEpisodeResult tvEpisodeResult) {
            mainInfoViewItem = MainInfoViewItem.newBuilder()
                    .setMainInfo(tvEpisodeResult.getName())
                    .setLeftAdditionalInfo(context.getString(R.string.season_and_episode,
                            tvEpisodeResult.getSeasonNumber(), tvEpisodeResult.getEpisodeNumber()))
                    .setRightAdditionalInfo(Converter.convertToFullDate(tvEpisodeResult.getAirDate()))
                    .setRating(convertVoteAverage(tvEpisodeResult.getVoteAverage()))
                    .setVoteCount(Converter.convertNumberToString(tvEpisodeResult.getVoteCount()))
                    .setDescription(tvEpisodeResult.getOverview())
                    .build();

            return this;
        }

        Builder setDirector(final String value) {
            director = value;

            return this;
        }

        Builder setWriter(final String value) {
            writer = value;

            return this;
        }

        Builder setImages(final TvEpisodeImages tvEpisodeImages) {
            if (tvEpisodeImages != null) {
                final String imagePath = Constants.BASE_IMAGE_URL + SettingsUtils.getBackdropSize(context);

                imagePathList = Converter.convertImagesToTileItem(tvEpisodeImages.getImages(), imagePath);
                galleryPhotoPaths = Converter.convertImagesToStringList(tvEpisodeImages.getImages(), imagePath);
            }

            return this;
        }

        Builder setCredits(final TvCredits creditsTv) {
            if (creditsTv == null) {
                return this;
            }

            final List<MediaCast> mediaCastList = creditsTv.getCast();

            if (mediaCastList != null) {
                final String imagePath = Constants.BASE_IMAGE_URL + SettingsUtils.getBackdropSize(context);

                billedCastList = Converter.convertCastToTileItem(mediaCastList, imagePath);
            }

            return this;
        }

        TvEpisodeData build() {
            return TvEpisodeData.this;
        }
    }

    private String convertVoteAverage(@NonNull final Number number) {
        return new DecimalFormat("#0.0").format(number);
    }
}
