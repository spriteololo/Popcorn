package com.epam.popcornapp.ui.tv.season;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.epam.popcornapp.ui.currentSeasonView.SeasonItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StartParams implements Parcelable {

    private int tvId;
    private int seasonNumber;
    private int numberOfSeasons;

    private List seasonIdList;

    public StartParams(final int tvId, final int seasonNumber, final int numberOfSeasons,
                       @NonNull final List<Integer> seasonIdList) {
        this.tvId = tvId;
        this.seasonNumber = seasonNumber;
        this.numberOfSeasons = numberOfSeasons;
        this.seasonIdList = seasonIdList;
    }

    private StartParams(final Parcel in) {
        tvId = in.readInt();
        seasonNumber = in.readInt();
        numberOfSeasons = in.readInt();
        seasonIdList = in.readArrayList(Integer.class.getClassLoader());
    }

    public static final Creator<StartParams> CREATOR = new Creator<StartParams>() {
        @Override
        public StartParams createFromParcel(final Parcel in) {
            return new StartParams(in);
        }

        @Override
        public StartParams[] newArray(final int size) {
            return new StartParams[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel parcel, final int i) {
        parcel.writeInt(tvId);
        parcel.writeInt(seasonNumber);
        parcel.writeInt(numberOfSeasons);
        parcel.writeList(seasonIdList);
    }

    public static List<Integer> convertToIdList(@NonNull final List<SeasonItem> seasonItemList) {
        final List<Integer> idList = new ArrayList<>(seasonItemList.size());

        for (final SeasonItem seasonItem : seasonItemList) {
            idList.add(seasonItem.getId());
        }

        Collections.reverse(idList);

        return idList;
    }

    public int getTvId() {
        return tvId;
    }

    public void setTvId(final int tvId) {
        this.tvId = tvId;
    }

    public int getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(final int seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public void setNumberOfSeasons(final int numberOfSeasons) {
        this.numberOfSeasons = numberOfSeasons;
    }

    public List getSeasonIdList() {
        return seasonIdList;
    }

    public void setSeasonIdList(final List<Integer> seasonIdList) {
        this.seasonIdList = seasonIdList;
    }
}
