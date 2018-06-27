package com.epam.popcornapp.ui.titles;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;

import com.arellomobile.mvp.InjectViewState;
import com.epam.popcornapp.application.BaseApplication;
import com.epam.popcornapp.application.Constants;
import com.epam.popcornapp.ui.all.base.BaseMvpPresenter;
import com.epam.popcornapp.ui.currentSeasonView.SeasonItem;
import com.epam.popcornapp.ui.gallery.GalleryParams;
import com.epam.popcornapp.ui.gallery.GalleryActivity;
import com.epam.popcornapp.ui.tiles.credits.TileType;
import com.epam.popcornapp.ui.tv.episode.TvEpisodeActivity;
import com.epam.popcornapp.ui.tv.season.StartParams;

import java.util.List;

import javax.inject.Inject;

import ru.terrakok.cicerone.Router;

@InjectViewState
public class TitlePresenter extends BaseMvpPresenter<TitleView> {

    @Inject
    Router router;

    private GalleryParams galleryParams;

    public TitlePresenter() {
        BaseApplication.getApplicationComponent().inject(this);
    }

    void init(final String type, final int id) {
        switch (type) {
            case Constants.Screens.ACTOR_SCREEN:
                router.replaceScreen(TileType.ACTOR, id);

                break;
            case Constants.Screens.MOVIE_SCREEN:
                router.replaceScreen(TileType.MOVIE, id);

                break;
            case Constants.Screens.TV_SHOW_SCREEN:
                router.replaceScreen(TileType.TV, id);

                break;
            default:
                throw new IllegalArgumentException();
        }
    }

    public void onTileClicked(final int id, @TileType final String type, final View view,
                              final Activity context) {
        switch (type) {
            case TileType.ACTOR:
                router.navigateTo(context instanceof TvEpisodeActivity ?
                        Constants.Screens.ACTOR_SCREEN : TileType.ACTOR, id);

                break;
            case TileType.MOVIE:
                router.navigateTo(TileType.MOVIE, id);

                break;
            case TileType.TV:
                router.navigateTo(TileType.TV, id);

                break;
            case TileType.GALLERY:
                if (galleryParams != null) {
                    galleryParams.setCurrentPhotoPosition(id);

                    getViewState().startGalleryActivity(GalleryActivity.start(context, galleryParams),
                            createStartActivityOptions(context, view));
                }

                break;
            default:
                throw new IllegalArgumentException();
        }
    }

    public void onSeasonClicked(final int tvId, final int seasonNumber, final int numberOfSeasons,
                                @NonNull final List<SeasonItem> seasonItemList) {
        final StartParams startParams = new StartParams(
                tvId, seasonNumber, numberOfSeasons, StartParams.convertToIdList(seasonItemList));

        router.navigateTo(Constants.Screens.TV_SEASON_SCREEN, startParams);
    }

    public void onGalleryPhotosFetched(final List<String> galleryPhotoPaths) {
        galleryParams = new GalleryParams(galleryPhotoPaths);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private Bundle createStartActivityOptions(final Activity activity, final View view) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return null;
        }

        final ActivityOptionsCompat options = ActivityOptionsCompat
                .makeSceneTransitionAnimation(activity, view, view.getTransitionName());

        return options.toBundle();
    }

    public void startListActivity(final int movieId) {
        router.navigateTo(Constants.Screens.LISTS_SCREEN, movieId);
    }
}
