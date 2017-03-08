package com.smona.app.evaluationcar.data;

import android.os.Parcel;

/**
 * Created by motianhu on 2/27/17.
 */

public class NewsInfo extends ItemInfo {
    private int id;
    private String title;
    private String summary;
    private String url;
    private String imgurl;

    public NewsInfo(Parcel in) {
        super(in);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }
}
