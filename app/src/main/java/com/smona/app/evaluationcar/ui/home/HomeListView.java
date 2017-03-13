package com.smona.app.evaluationcar.ui.home;

import android.content.Context;
import android.util.AttributeSet;

import com.smona.app.evaluationcar.R;
import com.smona.app.evaluationcar.data.bean.BannerBean;
import com.smona.app.evaluationcar.data.bean.NewsBean;
import com.smona.app.evaluationcar.data.event.NewsEvent;
import com.smona.app.evaluationcar.framework.event.EventProxy;
import com.smona.app.evaluationcar.ui.common.base.BaseListView;
import com.smona.app.evaluationcar.data.event.BannerEvent;
import com.smona.app.evaluationcar.util.CarLog;
import com.smona.app.evaluationcar.util.ViewUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Moth on 2017/2/24.
 */

public class HomeListView extends BaseListView {
    private BannerHeader mHeader;

    public HomeListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void init() {
        mHeader = (BannerHeader) ViewUtils.inflater(getContext(), R.layout.banner_header);
        this.addHeaderView(mHeader);

        mAdapter = new HomeAdapter(getContext());
        this.setAdapter(mAdapter);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void update(BannerEvent event) {
        List<BannerBean> list = (List<BannerBean>) event.getContent();
        if (list != null) {
            mHeader.update(list);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void update(NewsEvent event) {
        List<NewsBean> list = (List<NewsBean>) event.getContent();
        if (list != null) {
            mAdapter.update(list);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        CarLog.d(this, "onAttachedToWindow post");
        post();
    }



    private void post() {
        BannerEvent event = createBannerTest();
        EventProxy.post(event);
        NewsEvent news = createNewsTest();
        EventProxy.post(news);
    }


    private BannerEvent createBannerTest() {
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

    private NewsEvent createNewsTest() {
        NewsEvent event = new NewsEvent();
        ArrayList<NewsBean> news = new ArrayList<NewsBean>();
        for (int i = 0; i < 5; i++) {
            NewsBean item = new NewsBean(null);
            item.setId(i);
            item.setTitle("" + i);
            item.setSummary("ddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd");
            item.setUrl("http://www.baidu.com");
            item.setImgurl("http://113.107.245.39:90/attachs/theme/wallpaper/hd/2016/07/995g7j41eegpfngasjs9vsi7m5/312x277/SD-G-RW-062108.jpg");
            news.add(item);
        }
        event.setContent(news);
        return event;
    }


}
