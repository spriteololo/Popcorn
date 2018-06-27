package com.epam.popcornapp.ui.review;

import android.os.Parcel;
import android.os.Parcelable;

public class ReviewItem implements Parcelable {

    private String id;
    private String author;
    private String content;
    private String url;

    public String getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public String getUrl() {
        return url;
    }

    ReviewItem() {

    }

    protected ReviewItem(final Parcel in) {
        id = in.readString();
        author = in.readString();
        content = in.readString();
        url = in.readString();
    }

    public static final Creator<ReviewItem> CREATOR = new Creator<ReviewItem>() {
        @Override
        public ReviewItem createFromParcel(final Parcel in) {
            return new ReviewItem(in);
        }

        @Override
        public ReviewItem[] newArray(final int size) {
            return new ReviewItem[size];
        }
    };

    public static Builder builder() {
        return new ReviewItem().new Builder();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel parcel, final int i) {
        parcel.writeString(id);
        parcel.writeString(author);
        parcel.writeString(content);
        parcel.writeString(url);
    }

    public class Builder {
        Builder() {
        }

        public Builder setId(final String id1) {
            id = id1;

            return this;
        }

        public Builder setAuthor(final String author1) {
            author = author1;

            return this;
        }

        public Builder setContent(final String content1) {
            content = content1.replaceFirst("\r\n\r\n", "\r\n");

            return this;
        }

        public Builder setUrl(final String url1) {
            url = url1;

            return this;
        }

        public ReviewItem build() {
            return ReviewItem.this;
        }


    }
}
