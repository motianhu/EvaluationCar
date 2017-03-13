package com.smona.app.evaluationcar.data.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by motianhu on 2/27/17.
 */

public class BaseBean implements Parcelable {

    protected BaseBean(Parcel in) {
    }

    public static final Creator<BaseBean> CREATOR = new Creator<BaseBean>() {
        @Override
        public BaseBean createFromParcel(Parcel in) {
            return new BaseBean(in);
        }

        @Override
        public BaseBean[] newArray(int size) {
            return new BaseBean[size];
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
