package com.epam.popcornapp.ui.movies.info;

import android.content.Context;
import android.support.annotation.NonNull;

import com.epam.popcornapp.application.BaseApplication;
import com.epam.popcornapp.application.Constants;
import com.epam.popcornapp.application.Converter;
import com.epam.popcornapp.application.SettingsUtils;
import com.epam.popcornapp.pojo.base.ResultVideo;
import com.epam.popcornapp.pojo.movies.credits.MovieCreditsResult;
import com.epam.popcornapp.pojo.movies.details.MovieDetailResult;
import com.epam.popcornapp.pojo.movies.images.MovieImagesResult;
import com.epam.popcornapp.pojo.movies.keywords.MovieKeywordsResult;
import com.epam.popcornapp.pojo.movies.recommendations.MovieRecommendationsResult;
import com.epam.popcornapp.pojo.movies.release_dates.MovieReleaseDate;
import com.epam.popcornapp.pojo.movies.release_dates.MovieReleaseDatesResult;
import com.epam.popcornapp.pojo.movies.release_dates.MovieResultDates;
import com.epam.popcornapp.pojo.movies.release_dates.opt.ConvertedReleaseDate;
import com.epam.popcornapp.pojo.movies.reviews.MovieReviewResult;
import com.epam.popcornapp.pojo.movies.video.MovieVideoResult;
import com.epam.popcornapp.ui.informationViews.MainInfoViewItem;
import com.epam.popcornapp.ui.review.ReviewItem;
import com.epam.popcornapp.ui.tiles.credits.TileType;
import com.epam.popcornapp.ui.tiles.item.BaseTileItem;
import com.epam.popcornapp.ui.tiles.item.RatingTileItem;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

public class MovieInfoData {

    @Inject
    Context context;

    private String posterPath;
    private String videoPath;
    private String tags;
    private String budget;
    private String revenue;

    private List<BaseTileItem> billedCastList;
    private List<BaseTileItem> imagePathList;
    private List<BaseTileItem> recommendationsList;
    private List<ConvertedReleaseDate> convertedReleaseDate;
    private List<String> galleryPhotoPaths;
    private List<ReviewItem> reviewItemList;

    private MainInfoViewItem mainInfoViewItem;

    private MovieInfoData() {
        BaseApplication.getApplicationComponent().inject(this);
    }

    static Builder newBuilder() {
        return new MovieInfoData().new Builder();
    }


    MainInfoViewItem getMainInfoViewItem() {
        return mainInfoViewItem;
    }

    class Builder {

        Builder() {
        }

        Builder setPosterPath(@NonNull final String path) {
            posterPath = Constants.BASE_IMAGE_URL + SettingsUtils.getPosterSize(context) + path;

            return this;
        }

        Builder setDetail(final MovieDetailResult detailMovies) {
            budget = convertMoney(detailMovies.getBudget());
            revenue = convertMoney(detailMovies.getRevenue());

            mainInfoViewItem = MainInfoViewItem.newBuilder()
                    .setMainInfo(Converter.convertObjectListToString(detailMovies.getGenres()))
                    .setLeftAdditionalInfo(
                            Converter.convertNumberToStringWithSignature(detailMovies.getRuntime(), "min"))
                    .setRightAdditionalInfo(
                            Converter.convertToFullDate(detailMovies.getReleaseDate()))
                    .setRating(Converter.convertNumberToString(detailMovies.getVoteAverage()))
                    .setVoteCount(Converter.convertNumberToString(detailMovies.getVoteCount()))
                    .setDescription(detailMovies.getOverview())
                    .build();

            return this;
        }

        Builder setKeyWords(final MovieKeywordsResult keyWordsResult) {
            if (keyWordsResult != null) {
                tags = Converter.setCapitalLetter(
                        Converter.convertObjectListToString(keyWordsResult.getKeywords()));
            }

            return this;
        }

        Builder setCredits(final MovieCreditsResult creditsResult) {
            if (creditsResult != null) {
                final String imagePath = Constants.BASE_IMAGE_URL + SettingsUtils.getBackdropSize(context);

                billedCastList = Converter.convertCastToTileItem(creditsResult.getCast(), imagePath);
            }

            return this;
        }

        Builder setImages(final MovieImagesResult imagesResult) {
            if (imagesResult != null) {
                final String imagePath = Constants.BASE_IMAGE_URL + SettingsUtils.getBackdropSize(context);

                imagePathList = Converter.convertImagesToTileItem(imagesResult.getBackdrops(), imagePath);
                galleryPhotoPaths = Converter.convertImagesToStringList(imagesResult.getBackdrops(), imagePath);
            }

            return this;
        }

        Builder setRecommendations(final MovieRecommendationsResult recommendationsResult) {
            if (recommendationsResult != null) {
                final String imagePath = Constants.BASE_IMAGE_URL + SettingsUtils.getBackdropSize(context);

                recommendationsList = convertRecommendationsToTileItem(recommendationsResult.getResults(), imagePath);
            }

            return this;
        }

        Builder setVideoPath(final MovieVideoResult videoResult) {
            if (videoResult != null) {
                videoPath = createVideoPath(videoResult.getResults());
            }

            return this;
        }

        Builder setReleaseDates(final MovieReleaseDatesResult movieReleaseDatesResult) {
            if (movieReleaseDatesResult != null) {
                convertedReleaseDate = MovieInfoData.this.setReleaseDates(movieReleaseDatesResult);
            }

            return this;
        }

        Builder setMovieReview(final MovieReviewResult movieReviewResult) {
            if (movieReviewResult != null) {
                reviewItemList = Converter.reviewsConverter(movieReviewResult.getResultRevs());
            }

            return this;
        }

        MovieInfoData build() {
            return MovieInfoData.this;
        }

    }

    private String createVideoPath(@NonNull final List<ResultVideo> videoList) {
        for (final ResultVideo video : videoList) {
            if (video != null && video.getType().equals("Trailer")) {
                return "https://www.youtube.com/watch?v="
                        + video.getKey()
                        + "&setCapitalLetter="
                        + video.getId();
            }
        }

        return null;
    }

    private String convertMoney(final int money) {
        final DecimalFormat formatter = new DecimalFormat("$#,###");

        return money == 0 ? Constants.EMPTY_STRING : formatter.format(money);
    }

    private List<ConvertedReleaseDate> setReleaseDates(
            @NonNull final MovieReleaseDatesResult movieReleaseDatesResult) {
        final List<MovieResultDates> resultList =
                new ArrayList<>(movieReleaseDatesResult.getResults());
        final String[] codesStr = {"US", "BE", "GB", "RU", "UA"};
        final List<String> codes = Arrays.asList(codesStr);

        final Iterator<MovieResultDates> iterator = resultList.iterator();
        while (iterator.hasNext()) {
            if (!codes.contains(iterator.next().getIso31661())) {
                iterator.remove();
            }
        }

        final List<ConvertedReleaseDate> convertedReleaseDateList = new ArrayList<>();

        for (final MovieResultDates result : resultList) {
            ConvertedReleaseDate convertedReleaseDate = null;
            final List<MovieReleaseDate> releaseDateList = result.getReleaseDates();

            if (releaseDateList == null) {
                break;
            }

            for (final MovieReleaseDate releaseDate : releaseDateList) {
                convertedReleaseDate = new ConvertedReleaseDate();

                if (releaseDate != null) {
                    convertedReleaseDate.setConstraint(releaseDate.getCertification());
                    convertedReleaseDate.setImagePath(result.getIso31661());
                    convertedReleaseDate.setType(releaseDate.getType());
                    convertedReleaseDate.setDate(getConvertedDate(releaseDate.getReleaseDate()));
                }
            }

            convertedReleaseDateList.add(convertedReleaseDate);
        }

        return convertedReleaseDateList;
    }

    private String getConvertedDate(final String date) {
        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);

        try {
            final Date result = format.parse(date);
            final String outputFormat = "%1$tB %1$td, %1$tY";

            return String.format(Locale.ENGLISH, outputFormat, result);
        } catch (final ParseException e) {
            e.printStackTrace();

            return "-";
        }
    }

    private List<BaseTileItem> convertRecommendationsToTileItem(
            @NonNull final List<MovieDetailResult> recommendationsList, @NonNull final String imagePath) {
        final List<BaseTileItem> baseTileItemList = new ArrayList<>();

        for (final MovieDetailResult result : recommendationsList) {
            final RatingTileItem tileItem = new RatingTileItem(
                    result.getId(),
                    imagePath + result.getPosterPath(),
                    result.getTitle(),
                    result.getVoteAverage() / 2.0f);
            tileItem.setType(TileType.MOVIE);

            baseTileItemList.add(tileItem);
        }

        return baseTileItemList;
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

    String getBudget() {
        return budget;
    }

    String getRevenue() {
        return revenue;
    }

    List<BaseTileItem> getBilledCastList() {
        return billedCastList;
    }

    List<BaseTileItem> getImagePathList() {
        return imagePathList;
    }

    List<BaseTileItem> getRecommendationsList() {
        return recommendationsList;
    }

    List<ConvertedReleaseDate> getConvertedReleaseDate() {
        return convertedReleaseDate;
    }

    List<String> getGalleryPhotoPaths() {
        return galleryPhotoPaths;
    }

    List<ReviewItem> getReviewItemList() {
        return reviewItemList;
    }
}