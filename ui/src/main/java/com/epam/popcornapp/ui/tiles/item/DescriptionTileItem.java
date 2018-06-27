package com.epam.popcornapp.ui.tiles.item;

public class DescriptionTileItem extends BaseTileItem {

    private String name;
    private String description;

    public DescriptionTileItem() {
    }

    public DescriptionTileItem(final int id, final String imagePath, final String name, final String description) {
        super(id, imagePath);
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }
}
