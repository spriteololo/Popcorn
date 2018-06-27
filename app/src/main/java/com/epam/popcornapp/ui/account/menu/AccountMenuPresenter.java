package com.epam.popcornapp.ui.account.menu;

import com.arellomobile.mvp.InjectViewState;
import com.epam.popcornapp.application.Constants;
import com.epam.popcornapp.application.Constants.DataType;
import com.epam.popcornapp.ui.all.base.BaseMvpPresenter;
import com.epam.popcornapp.ui.swimming.SwimmingLineItem;
import com.epam.popcornapp.ui.tiles.credits.TileType;
import com.epam.popcornapp.ui.tiles.item.BaseTileItem;

import java.util.ArrayList;
import java.util.List;

@InjectViewState
public class AccountMenuPresenter extends BaseMvpPresenter<AccountMenuView> {

    private final int NUM_OF_SWIMMING_LINES = 2;
    private final List<SwimmingLineItem> items = new ArrayList<>(NUM_OF_SWIMMING_LINES);
    private boolean isDataAvailable;

    public void setData(final String dataType, final List<BaseTileItem> data, final String title) {
        if (dataType.equals(DataType.MOVIES) || dataType.equals(DataType.RATED_MOVIES)) {
            items.add(0, new SwimmingLineItem(title, data));
        } else {
            items.add(new SwimmingLineItem(title, data));
        }

        isDataAvailable = !data.isEmpty() || isDataAvailable;

        if (items.size() == NUM_OF_SWIMMING_LINES) {
            if (isDataAvailable) {
                setData();

                items.clear();
                isDataAvailable = false;
            } else {
                getViewState().noData();
            }
        }
    }

    private void setData() {
        final SwimmingLineItem movieItem = items.get(0);
        final SwimmingLineItem tvItem = items.get(1);
        final int movieTileSize = movieItem.getTileList().size();
        final int tvTileSize = tvItem.getTileList().size();

        if (movieTileSize != 0) {
            getViewState().setMovieSwimmingLine(movieItem);

            if (movieTileSize < Constants.ONE_PAGE_ITEMS_NUM) {
                getViewState().hideMovieMoreButton();
            }
        }

        if (tvTileSize != 0) {
            getViewState().setTvSwimmingLine(tvItem);

            if (tvTileSize < Constants.ONE_PAGE_ITEMS_NUM) {
                getViewState().hideTvMoreButton();
            }
        }
    }

    public String getScreenKeyByTileType(@TileType final String tileType) {
        return tileType.equals(TileType.MOVIE) ?
                Constants.Screens.MOVIE_SCREEN : Constants.Screens.TV_SHOW_SCREEN;
    }
}
