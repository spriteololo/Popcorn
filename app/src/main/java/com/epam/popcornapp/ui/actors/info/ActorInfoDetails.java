package com.epam.popcornapp.ui.actors.info;

import android.content.Context;
import android.support.annotation.NonNull;

import com.epam.popcornapp.application.BaseApplication;
import com.epam.popcornapp.application.Constants;
import com.epam.popcornapp.application.Converter;
import com.epam.popcornapp.application.SettingsUtils;
import com.epam.popcornapp.pojo.actors.Actor;
import com.epam.popcornapp.pojo.actors.credits.Credits;
import com.epam.popcornapp.pojo.actors.credits.embedded.ActorCast;
import com.epam.popcornapp.pojo.actors.credits.embedded.ActorCrew;
import com.epam.popcornapp.pojo.actors.images.Images;
import com.epam.popcornapp.ui.informationViews.MainInfoViewItem;
import com.epam.popcornapp.ui.tiles.credits.ActorJobItem;
import com.epam.popcornapp.ui.tiles.credits.BaseCreditItem;
import com.epam.popcornapp.ui.tiles.credits.TileType;
import com.epam.popcornapp.ui.tiles.item.BaseTileItem;
import com.epam.popcornapp.ui.tiles.item.RatingTileItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

public class ActorInfoDetails {

    @Inject
    Context context;

    private String imagePath;

    private List<String> galleryPhotoPaths;
    private List<BaseTileItem> imagePathList;
    private List<BaseTileItem> knownFor;
    private List<BaseCreditItem> listCredits;

    private MainInfoViewItem mainInfoViewItem;

    private ActorInfoDetails() {
        BaseApplication.getApplicationComponent().inject(this);
    }

    static Builder newBuilder() {
        return new ActorInfoDetails().new Builder();
    }

    String getImagePath() {
        return imagePath;
    }

    List<String> getGalleryPhotoPaths() {
        return galleryPhotoPaths;
    }

    List<BaseTileItem> getImagePathList() {
        return imagePathList;
    }

    List<BaseTileItem> getKnownFor() {
        return knownFor;
    }

    List<BaseCreditItem> getListCredits() {
        return listCredits;
    }

    MainInfoViewItem getMainInfoViewItem() {
        return mainInfoViewItem;
    }

    class Builder {

        Builder() {
        }

        Builder setImagePath(@NonNull final String path) {
            imagePath = Constants.BASE_IMAGE_URL + SettingsUtils.getPosterSize(context) + path;

            return this;
        }

        Builder setDetail(@NonNull final Actor actor) {
            mainInfoViewItem = MainInfoViewItem.newBuilder()
                    .setMainInfo(actor.getName())
                    .setLeftAdditionalInfo(Converter.convertToFullDate(actor.getBirthday()))
                    .setRightAdditionalInfo(Converter.convertToAge(actor.getBirthday()))
                    .setRating("")
                    .setVoteCount("")
                    .setDescription(checkBiography(actor.getBiography()))
                    .build();

            return this;
        }

        Builder setImages(final Images imagesResult) {
            if (imagesResult != null) {
                final String imagePath = Constants.BASE_IMAGE_URL + SettingsUtils.getBackdropSize(context);

                imagePathList = Converter.convertImagesToTileItem(imagesResult.getProfiles(), imagePath);
                galleryPhotoPaths = Converter.convertImagesToStringList(imagesResult.getProfiles(), imagePath);
            }

            return this;
        }

        Builder setCredits(final Credits credits) {
            if (credits != null) {
                final List<BaseCreditItem> cr = convertCasts(credits.getCast());
                final String imagePath = Constants.BASE_IMAGE_URL + SettingsUtils.getBackdropSize(context);

                cr.addAll(convertCrews(credits.getCrew()));
                listCredits = cr;
                knownFor = convertCreditsToTiles(credits.getCast(), imagePath);
            }

            return this;
        }

        ActorInfoDetails build() {
            return ActorInfoDetails.this;
        }

        private List<BaseCreditItem> convertCrews(@NonNull final List<ActorCrew> list) {
            final Map<String, List<ActorJobItem>> map = new HashMap<>();

            for (final ActorCrew crew : list) {
                boolean isAdded = false;
                final @TileType String type = crew.getMediaType();

                ActorJobItem item = null;

                if (crew.getMediaType().equals(TileType.MOVIE)) {
                    if (crew.getMovie().getReleaseDate() == null || crew.getMovie().getReleaseDate().length() < 4) {
                        continue;
                    }

                    item = ActorJobItem.builder()
                            .id(crew.getId())
                            .year(Converter.convertToYear(crew.getMovie().getReleaseDate()))
                            .title(crew.getMovie().getTitle())
                            .role(crew.getJob())
                            .type(type)
                            .build();
                } else if (crew.getMediaType().equals(TileType.TV)) {
                    if (crew.getTv().getFirstAirDate() == null || crew.getTv().getFirstAirDate().length() < 4) {
                        continue;
                    }

                    item = ActorJobItem.builder()
                            .id(crew.getTv().getId())
                            .year(Converter.convertToYear(crew.getTv().getFirstAirDate()))
                            .title(crew.getTv().getName())
                            .role(crew.getJob())
                            .type(type)
                            .build();
                }

                if (item == null) {
                    continue;
                }

                if (!map.containsKey(crew.getDepartment())) {
                    final List<ActorJobItem> items = new ArrayList<>();
                    items.add(item);
                    map.put(crew.getDepartment(), items);
                } else {
                    final List<ActorJobItem> items = map.get(crew.getDepartment());
                    if (items.size() > 0) {
                        for (int i = 0; i < items.size(); i++) {
                            if (!isAdded) {
                                final int itemYear = Integer.parseInt(item.year());
                                final int year = Integer.parseInt(items.get(i).year());

                                if (itemYear > year) {
                                    items.add(i, item);
                                    isAdded = true;
                                }
                            }
                        }
                    }

                    if (!isAdded) {
                        items.add(item);
                    }
                }
            }

            final List<BaseCreditItem> result = new ArrayList<>();

            for (final Map.Entry<String, List<ActorJobItem>> pair : map.entrySet()) {
                final BaseCreditItem item = BaseCreditItem.builder()
                        .category(pair.getKey())
                        .jobs(pair.getValue())
                        .build();

                result.add(item);
            }

            return result;
        }

        private List<BaseCreditItem> convertCasts(@NonNull final List<ActorCast> list) {
            final List<BaseCreditItem> result = new ArrayList<>();
            final List<ActorJobItem> acting = new ArrayList<>();

            for (final ActorCast cast : list) {
                boolean isAdded = false;
                @TileType final String type = cast.getMediaType();

                ActorJobItem item = null;

                if (cast.getMediaType().equals(TileType.MOVIE)) {
                    if (cast.getMovie().getReleaseDate() == null || cast.getMovie().getReleaseDate().length() < 4) {
                        continue;
                    }

                    item = ActorJobItem.builder()
                            .id(cast.getMovie().getId())
                            .year(Converter.convertToYear(cast.getMovie().getReleaseDate()))
                            .title(cast.getMovie().getTitle())
                            .role(cast.getCharacter())
                            .type(type)
                            .build();
                } else if (cast.getMediaType().equals(TileType.TV)) {
                    if (cast.getTv().getFirstAirDate() == null || cast.getTv().getFirstAirDate().length() < 4) {
                        continue;
                    }

                    item = ActorJobItem.builder()
                            .id(cast.getTv().getId())
                            .year(Converter.convertToYear(cast.getTv().getFirstAirDate()))
                            .title(cast.getTv().getName())
                            .role(cast.getCharacter())
                            .type(type)
                            .build();
                }

                if (item == null) {
                    continue;
                }

                if (acting.size() > 0) {
                    for (int i = 0; i < acting.size(); i++) {
                        if (!isAdded) {
                            final int itemYear = Integer.parseInt(item.year());
                            final int year = Integer.parseInt(acting.get(i).year());

                            if (itemYear > year) {
                                acting.add(i, item);
                                isAdded = true;
                            }
                        }
                    }
                }

                if (!isAdded) {
                    acting.add(item);
                }
            }

            final BaseCreditItem item = BaseCreditItem.builder()
                    .category("Acting")
                    .jobs(acting)
                    .build();
            result.add(item);

            return result;
        }

        private List<BaseTileItem> convertCreditsToTiles(@NonNull final List<ActorCast> casts,
                                                         @NonNull final String imagePath) {
            final List<BaseTileItem> result = new ArrayList<>();
            for (final ActorCast cast : casts) {
                final RatingTileItem item = new RatingTileItem();
                boolean isAdded = false;

                @TileType final String type = cast.getMediaType();
                item.setType(type);

                if (type.equals(TileType.MOVIE)) {
                    if (cast.getMovie().getPosterPath() == null) {
                        continue;
                    }

                    item.setImagePath(imagePath + cast.getMovie().getPosterPath());
                    item.setId(cast.getMovie().getId());
                    item.setName(cast.getMovie().getTitle());
                    item.setRating(cast.getMovie().getVoteAverage() / 2);
                } else if (type.equals(TileType.TV)) {
                    if (cast.getTv().getPosterPath() == null) {
                        continue;
                    }

                    item.setImagePath(imagePath + cast.getTv().getPosterPath());
                    item.setId(cast.getTv().getId());
                    item.setName(cast.getTv().getName());
                    item.setRating(cast.getTv().getVoteAverage() / 2);
                }

                if (result.size() > 0) {
                    for (int i = 0; i < result.size(); i++) {
                        if (item.getRating() > ((RatingTileItem) result.get(i)).getRating() &&
                                !isAdded) {
                            result.add(i, item);
                            isAdded = true;
                        }
                    }
                }

                if (!isAdded)
                    result.add(item);
            }

            return result.size() > 8 ? result.subList(0, 8) : result;
        }
    }

    private String checkBiography(final String biography) {
        if (biography == null) {
            return "";
        } else {
            return biography;
        }
    }
}
