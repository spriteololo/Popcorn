package com.epam.popcornapp.ui.tiles.credits;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class ActorJobItem {

    public abstract int id();

    public abstract String year();

    public abstract String title();

    public abstract String role();

    public abstract
    @TileType
    String type();


    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder id(int value);

        public abstract Builder year(String value);

        public abstract Builder title(String value);

        public abstract Builder role(String value);

        public abstract Builder type(@TileType String value);

        public abstract ActorJobItem build();
    }

    public static Builder builder() {
        return new AutoValue_ActorJobItem.Builder();
    }

}
