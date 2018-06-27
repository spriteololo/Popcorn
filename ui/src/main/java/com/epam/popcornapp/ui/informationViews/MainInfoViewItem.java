package com.epam.popcornapp.ui.informationViews;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class MainInfoViewItem {

    public abstract String getMainInfo();

    public abstract String getLeftAdditionalInfo();

    public abstract String getRightAdditionalInfo();

    public abstract String getRating();

    public abstract String getVoteCount();

    public abstract String getDescription();

    @AutoValue.Builder
    public abstract static class Builder {

        public abstract Builder setMainInfo(String value);

        public abstract Builder setLeftAdditionalInfo(String value);

        public abstract Builder setRightAdditionalInfo(String value);

        public abstract Builder setRating(String value);

        public abstract Builder setVoteCount(String value);

        public abstract Builder setDescription(String value);

        public abstract MainInfoViewItem build();
    }

    public static Builder newBuilder() {
        return new AutoValue_MainInfoViewItem.Builder();
    }
}
