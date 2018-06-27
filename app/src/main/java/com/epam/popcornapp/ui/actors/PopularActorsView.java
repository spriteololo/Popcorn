package com.epam.popcornapp.ui.actors;

import com.arellomobile.mvp.MvpView;
import com.epam.popcornapp.ui.tiles.item.BaseTileItem;

import java.util.List;

public interface PopularActorsView extends MvpView {

    void setActors(List<BaseTileItem> result);
}
