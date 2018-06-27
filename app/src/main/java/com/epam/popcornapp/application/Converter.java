package com.epam.popcornapp.application;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.epam.popcornapp.pojo.base.Image;
import com.epam.popcornapp.pojo.base.MediaCast;
import com.epam.popcornapp.pojo.movies.reviews.MovieResultRev;
import com.epam.popcornapp.ui.review.ReviewItem;
import com.epam.popcornapp.ui.tiles.credits.TileType;
import com.epam.popcornapp.ui.tiles.item.BaseTileItem;
import com.epam.popcornapp.ui.tiles.item.DescriptionTileItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class Converter {

    public interface Word {
        String getName();
    }

    public static String convertNumberToStringWithSignature(final int value,
                                                            @NonNull final String signature) {
        final String text = convertNumberToString(value);

        return text.isEmpty() ? "" : String.format("%s %s", text, signature);
    }

    public static String convertNumberToString(@NonNull final Number number) {
        return number.floatValue() == 0 ? "" : number.toString();
    }

    public static String convertToFullDate(final String value) {
        return convertDate(value, "%1$tB %1$td, %1$tY");
    }

    public static String convertToYear(final String value) {
        return convertDate(value, "%1$tY");
    }

    public static String convertToAge(final String value) {
        final Date date = getDate(value);

        if (date == null) {
            return "";
        }

        final Calendar dob = Calendar.getInstance();
        final Calendar today = Calendar.getInstance();

        dob.setTime(date);
        dob.add(Calendar.DAY_OF_MONTH, -1);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) <= dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }

        return String.format("%s %s", age, "years");
    }

    public static <T extends Word> String convertObjectListToString(@NonNull final List<T> value) {
        if (value.isEmpty()) {
            return "";
        }

        final Iterator<T> iterator = value.iterator();
        final StringBuilder stringBuilder = new StringBuilder(iterator.next().getName());

        while (iterator.hasNext()) {
            stringBuilder.append(", ").append(iterator.next().getName());
        }

        return stringBuilder.toString();
    }

    private static Date getDate(final String value) {
        if (TextUtils.isEmpty(value)) {
            return null;
        }

        final SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        try {
            return simpleDateFormat.parse(value);
        } catch (final ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static String convertDate(final String value, @NonNull final String format) {
        final Date date = getDate(value);

        return date != null ? String.format(Locale.ENGLISH, format, date) : "";
    }

    public static List<ReviewItem> reviewsConverter(@NonNull final List<MovieResultRev> resultList) {
        final List<ReviewItem> reviewItemList = new ArrayList<>(resultList.size());

        for (final MovieResultRev review : resultList) {

            reviewItemList.add(ReviewItem.builder()
                    .setId(review.getId())
                    .setAuthor(review.getAuthor())
                    .setContent(review.getContent())
                    .setUrl(review.getUrl())
                    .build());
        }

        return reviewItemList;
    }

    public static String setCapitalLetter(@NonNull final String value) {
        if (value.isEmpty()) {
            return "";
        }

        return Character.toUpperCase(value.charAt(0)) + value.substring(1);
    }

    public static List<BaseTileItem> convertCastToTileItem(@NonNull final List<MediaCast> castList,
                                                           @NonNull final String imagePath) {
        final List<BaseTileItem> baseTileItemList = new ArrayList<>();

        for (final MediaCast cast : castList) {
            final DescriptionTileItem tileItem = new DescriptionTileItem(
                    cast.getActor().getId(),
                    imagePath + cast.getActor().getProfilePath(),
                    cast.getActor().getName(),
                    cast.getCharacter());

            tileItem.setType(TileType.ACTOR);

            baseTileItemList.add(tileItem);
        }

        return baseTileItemList;
    }

    public static List<BaseTileItem> convertImagesToTileItem(@NonNull final List<Image> imageList,
                                                             @NonNull final String imagePath) {
        final List<BaseTileItem> baseTileItemList = new ArrayList<>();
        int i = 0;

        for (final Image backdrop : imageList) {
            final BaseTileItem item = new BaseTileItem(i, imagePath + backdrop.getFilePath());

            item.setType(TileType.GALLERY);
            baseTileItemList.add(item);

            i++;
        }

        return baseTileItemList;
    }

    public static List<String> convertImagesToStringList(@NonNull final List<Image> imageList,
                                                         @NonNull final String imagePath) {
        final List<String> galleryPhotoPaths = new ArrayList<>();

        for (final Image backdrop : imageList) {
            galleryPhotoPaths.add(imagePath + backdrop.getFilePath());
        }

        return galleryPhotoPaths;
    }
}
