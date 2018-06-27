package com.epam.popcornapp.ui.swimming;

import com.epam.popcornapp.ui.tiles.item.BaseTileItem;

import java.util.List;

public class SwimmingLineItem {

    private String title;
    private List<BaseTileItem> tileList;

    public SwimmingLineItem() {

    }

    public SwimmingLineItem(final String title, final List<BaseTileItem> tileList) {
        this.title = title;
        this.tileList = tileList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public List<BaseTileItem> getTileList() {
        return tileList;
    }

    public void setTileList(final List<BaseTileItem> tileList) {
        this.tileList = tileList;
    }
}
