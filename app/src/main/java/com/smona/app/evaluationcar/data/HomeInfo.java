package com.smona.app.evaluationcar.data;

import android.os.Parcel;

import java.util.List;

/**
 * Created by motianhu on 2/27/17.
 */

public class HomeInfo {
    private List<BannerInfo> banner;
    private List<NewsInfo> news;


    public List<BannerInfo> getBanner() {
        return banner;
    }

    public void setBanner(List<BannerInfo> banner) {
        this.banner = banner;
    }

    public List<NewsInfo> getNews() {
        return news;
    }

    public void setNews(List<NewsInfo> news) {
        this.news = news;
    }
}
