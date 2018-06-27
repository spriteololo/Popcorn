package com.epam.popcornapp.ui.tiles.credits;

import com.google.auto.value.AutoValue;

import java.util.List;

@AutoValue
public abstract class BaseCreditItem {

    public abstract String category();

    public abstract List<ActorJobItem> jobs();

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder category(String value);

        public abstract Builder jobs(List<ActorJobItem> value);

        public abstract BaseCreditItem build();
    }

    public static Builder builder() {
        return new AutoValue_BaseCreditItem.Builder();
    }
}
