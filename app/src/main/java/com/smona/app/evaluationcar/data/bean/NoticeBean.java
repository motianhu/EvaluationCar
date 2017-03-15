package com.smona.app.evaluationcar.data.bean;

import android.os.Parcel;

/**
 * Created by Moth on 2017/3/15.
 */

public class NoticeBean extends BaseBean {
    private String message;

    public NoticeBean(Parcel in) {
        super(in);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
