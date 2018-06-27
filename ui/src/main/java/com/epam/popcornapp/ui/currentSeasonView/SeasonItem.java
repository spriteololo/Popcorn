package com.epam.popcornapp.ui.currentSeasonView;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class SeasonItem {

    public abstract int getId();

    public abstract String getPosterPath();

    public abstract int getSeasonNumber();

    public abstract String getDescription();

    public abstract float getRating();

    @AutoValue.Builder
    public abstract static class Builder {

        public abstract Builder setId(int value);

        public abstract Builder setPosterPath(String value);

        public abstract Builder setSeasonNumber(int value);

        public abstract Builder setDescription(String value);

        public abstract Builder setRating(float value);

        public abstract SeasonItem build();
    }

    public static Builder builder() {
        return new AutoValue_SeasonItem.Builder();
    }
}
