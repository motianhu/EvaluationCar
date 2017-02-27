package com.smona.app.evaluationcar.data;

import android.os.Parcel;

/**
 * Created by motianhu on 2/27/17.
 */

public class BannerInfo extends ItemInfo {
    private int id;
    private String title;
    private String url;
    private String imgurl;

    public BannerInfo(Parcel in) {
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
