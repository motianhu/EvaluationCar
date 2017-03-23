package com.smona.app.evaluationcar.framework.request;

import com.smona.app.evaluationcar.business.HttpProxy;
import com.smona.app.evaluationcar.data.bean.ImageMetaBean;
import com.smona.app.evaluationcar.data.event.BannerEvent;
import com.smona.app.evaluationcar.data.event.BillTotalEvent;
import com.smona.app.evaluationcar.data.event.NewsEvent;
import com.smona.app.evaluationcar.data.item.BannerItem;
import com.smona.app.evaluationcar.data.item.BillTotalItem;
import com.smona.app.evaluationcar.data.item.NewsItem;
import com.smona.app.evaluationcar.framework.event.EventProxy;
import com.smona.app.evaluationcar.util.CarLog;

import org.xutils.common.Callback;

import java.util.ArrayList;

/**
 * Created by Moth on 2017/3/15.
 */

public class HomeDeletor extends BaseDelegator {
    private volatile static HomeDeletor sInstance;

    private HomeDeletor() {
    }

    public static HomeDeletor getInstance() {
        if (sInstance == null) {
            sInstance = new HomeDeletor();
        }
        return sInstance;
    }

    public void requestBanner() {
        BannerEvent event = createBannerTest();
        EventProxy.post(event);
    }

    public void requestNews() {
        NewsEvent news = createNewsTest();
        EventProxy.post(news);
    }

    public void requestTotallBill() {
        BillTotalEvent bean = createBillTotalTest();
        EventProxy.post(bean);
    }

    private static BannerEvent createBannerTest() {
        BannerEvent event = new BannerEvent();
        ArrayList<BannerItem> bannerList = new ArrayList<BannerItem>();
        for (int i = 0; i < 5; i++) {
            BannerItem banner = new BannerItem();
            banner.bannerId = i;
            banner.title = "" + i;
            banner.url = "http://www.baidu.com";
            banner.imgurl = "http://assetsdl.gioneemobile.net/attachs/theme/subjectImage/201701/587c2e26cf05d.jpg";
            bannerList.add(banner);
        }
        event.setContent(bannerList);
        return event;
    }

    private static NewsEvent createNewsTest() {
        NewsEvent event = new NewsEvent();
        ArrayList<NewsItem> news = new ArrayList<NewsItem>();
        for (int i = 0; i < 5; i++) {
            NewsItem item = new NewsItem();
            item.newsId = i;
            item.title = "" + i;
            item.time = "发布时间 2017-01-23 10:23:12";
            item.summary = "ddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd";
            item.url = "http://www.baidu.com";
            item.imgurl = "http://113.107.245.39:90/attachs/theme/wallpaper/hd/2016/07/995g7j41eegpfngasjs9vsi7m5/312x277/SD-G-RW-062108.jpg";
            news.add(item);
        }
        event.setContent(news);
        return event;
    }

    private static BillTotalEvent createBillTotalTest() {
        BillTotalEvent event = new BillTotalEvent();
        BillTotalItem bean = new BillTotalItem();
        bean.setAuditingCount(20);
        bean.setNotPassCount(10);
        bean.setPassCount(15);
        bean.setUnCommitCount(5);
        event.setContent(bean);
        return event;
    }
}
