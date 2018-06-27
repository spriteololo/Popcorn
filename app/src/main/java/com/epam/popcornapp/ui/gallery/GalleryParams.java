package com.epam.popcornapp.ui.gallery;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class GalleryParams implements Parcelable {
    private int currentPhotoPosition;
    private List<String> photoPaths;

    public GalleryParams(final List<String> photoPaths) {
        this.photoPaths = photoPaths;
    }

    protected GalleryParams(final Parcel in) {
        currentPhotoPosition = in.readInt();
        photoPaths = in.createStringArrayList();
    }

    public static final Creator<GalleryParams> CREATOR = new Creator<GalleryParams>() {
        @Override
        public GalleryParams createFromParcel(final Parcel in) {
            return new GalleryParams(in);
        }

        @Override
        public GalleryParams[] newArray(final int size) {
            return new GalleryParams[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeInt(currentPhotoPosition);
        dest.writeStringList(photoPaths);
    }

    List<String> getPhotoPaths() {
        return photoPaths;
    }

    int getCurrentPhotoPosition() {
        return currentPhotoPosition;
    }

    public void setCurrentPhotoPosition(final int currentPhotoPosition) {
        this.currentPhotoPosition = currentPhotoPosition;
    }

    public int getSize() {
        return photoPaths.size();
    }
}
