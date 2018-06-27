package com.epam.popcornapp.ui.tiles.item;

public class RatingTileItem extends BaseTileItem {

    private String name;
    private float rating;

    public RatingTileItem() {
    }

    public RatingTileItem(final int id, final String imagePath, final String name, final float rating) {
        super(id, imagePath);
        this.name = name;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(final float rating) {
        this.rating = rating;
    }
}
