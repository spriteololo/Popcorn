package com.epam.popcornapp.ui.lists.detail;

import android.os.Parcel;
import android.os.Parcelable;

public class ListDetailParams implements Parcelable {

    private int id;
    private int itemNum;
    private int position;

    public ListDetailParams(final int id, final int itemNum, final int position) {
        this.id = id;
        this.itemNum = itemNum;
        this.position = position;
    }

    private ListDetailParams(final Parcel in) {
        id = in.readInt();
        itemNum = in.readInt();
        position = in.readInt();
    }

    public static final Creator<ListDetailParams> CREATOR = new Creator<ListDetailParams>() {
        @Override
        public ListDetailParams createFromParcel(final Parcel in) {
            return new ListDetailParams(in);
        }

        @Override
        public ListDetailParams[] newArray(final int size) {
            return new ListDetailParams[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(final Parcel parcel, final int i) {
        parcel.writeInt(id);
        parcel.writeInt(itemNum);
        parcel.writeInt(position);
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public int getItemNum() {
        return itemNum;
    }

    public void setItemNum(final int itemNum) {
        this.itemNum = itemNum;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(final int position) {
        this.position = position;
    }
}
