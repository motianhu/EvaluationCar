package com.smona.app.evaluationcar.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by motianhu on 2/27/17.
 */

public class ItemInfo implements Parcelable {

    protected ItemInfo(Parcel in) {
    }

    public static final Creator<ItemInfo> CREATOR = new Creator<ItemInfo>() {
        @Override
        public ItemInfo createFromParcel(Parcel in) {
            return new ItemInfo(in);
        }

        @Override
        public ItemInfo[] newArray(int size) {
            return new ItemInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
