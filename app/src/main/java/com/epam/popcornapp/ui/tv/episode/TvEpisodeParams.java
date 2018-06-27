package com.epam.popcornapp.ui.tv.episode;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.epam.popcornapp.ui.episode.EpisodeItem;

import java.util.ArrayList;
import java.util.List;

public class TvEpisodeParams implements Parcelable {

    private int tvId;
    private int seasonNumber;
    private int episodeNumber;

    private List episodeIdList;

    public TvEpisodeParams(final int tvId, final int seasonNumber, final int episodeNumber,
                           @NonNull final List<Integer> episodeIdList) {
        this.tvId = tvId;
        this.seasonNumber = seasonNumber;
        this.episodeNumber = episodeNumber;
        this.episodeIdList = episodeIdList;
    }

    private TvEpisodeParams(final Parcel in) {
        tvId = in.readInt();
        seasonNumber = in.readInt();
        episodeNumber = in.readInt();
        episodeIdList = in.readArrayList(Integer.class.getClassLoader());
    }

    public static final Creator<TvEpisodeParams> CREATOR = new Creator<TvEpisodeParams>() {
        @Override
        public TvEpisodeParams createFromParcel(final Parcel in) {
            return new TvEpisodeParams(in);
        }

        @Override
        public TvEpisodeParams[] newArray(final int size) {
            return new TvEpisodeParams[size];
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
        parcel.writeInt(episodeNumber);
        parcel.writeList(episodeIdList);
    }

    public static List<Integer> convertToIdList(@NonNull final List<EpisodeItem> episodeItemList) {
        final List<Integer> idList = new ArrayList<>(episodeItemList.size());

        for (final EpisodeItem episodeItem : episodeItemList) {
            idList.add(episodeItem.getId());
        }

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

    public int getEpisodeNumber() {
        return episodeNumber;
    }

    public void setEpisodeNumber(final int episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    public List getEpisodeIdList() {
        return episodeIdList;
    }

    public void setEpisodeIdList(final List<Integer> episodeIdList) {
        this.episodeIdList = episodeIdList;
    }
}
