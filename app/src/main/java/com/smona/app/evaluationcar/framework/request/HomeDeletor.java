package com.smona.app.evaluationcar.framework.request;

import com.smona.app.evaluationcar.data.bean.BannerBean;
import com.smona.app.evaluationcar.data.bean.BillTotalBean;
import com.smona.app.evaluationcar.data.bean.NewsBean;
import com.smona.app.evaluationcar.data.event.BannerEvent;
import com.smona.app.evaluationcar.data.event.BillTotalEvent;
import com.smona.app.evaluationcar.data.event.NewsEvent;
import com.smona.app.evaluationcar.framework.event.EventProxy;

import java.util.ArrayList;

/**
 * Created by Moth on 2017/3/15.
 */

public class HomeDeletor {
    public static void requestBanner() {
        BannerEvent event = createBannerTest();
        EventProxy.post(event);
    }

    public static void requestNews() {
        NewsEvent news = createNewsTest();
        EventProxy.post(news);
    }

    public static void requestTotallBill() {
        BillTotalEvent bean = createBillTotalTest();
        EventProxy.post(bean);
    }


    private static BannerEvent createBannerTest() {
        BannerEvent event = new BannerEvent();
        ArrayList<BannerBean> bannerList = new ArrayList<BannerBean>();
        for (int i = 0; i < 5; i++) {
            BannerBean banner = new BannerBean(null);
            banner.setId(i);
            banner.setTitle("" + i);
            banner.setUrl("http://www.baidu.com");
            banner.setImgurl("http://assetsdl.gioneemobile.net/attachs/theme/subjectImage/201701/587c2e26cf05d.jpg");
            bannerList.add(banner);
        }
        event.setContent(bannerList);
        return event;
    }

    private static NewsEvent createNewsTest() {
        NewsEvent event = new NewsEvent();
        ArrayList<NewsBean> news = new ArrayList<NewsBean>();
        for (int i = 0; i < 5; i++) {
            NewsBean item = new NewsBean(null);
            item.setId(i);
            item.setTitle("" + i);
            item.setTime("发布时间 2017-01-23 10:23:12");
            item.setSummary("ddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd");
            item.setUrl("http://www.baidu.com");
            item.setImgurl("http://113.107.245.39:90/attachs/theme/wallpaper/hd/2016/07/995g7j41eegpfngasjs9vsi7m5/312x277/SD-G-RW-062108.jpg");
            news.add(item);
        }
        event.setContent(news);
        return event;
    }

    private static BillTotalEvent createBillTotalTest() {
        BillTotalEvent event = new BillTotalEvent();
        BillTotalBean bean = new BillTotalBean(null);
        bean.setAuditingCount(20);
        bean.setNotPassCount(10);
        bean.setPassCount(15);
        bean.setUnCommitCount(5);
        event.setContent(bean);
        return event;
    }
}
