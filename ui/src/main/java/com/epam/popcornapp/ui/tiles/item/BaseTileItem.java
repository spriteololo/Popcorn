package com.epam.popcornapp.ui.tiles.item;

import com.epam.popcornapp.ui.tiles.credits.TileType;

public class BaseTileItem implements BaseTiles {

    private int id;
    private String imagePath;
    @TileType
    private String type;

    public BaseTileItem() {
    }

    public BaseTileItem(final int id, final String imagePath) {
        this.id = id;
        this.imagePath = imagePath;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public void setImagePath(final String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }

    @TileType
    public String getType() {
        return type;
    }

    public void setType(@TileType final String type) {
        this.type = type;
    }
}
