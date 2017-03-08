package com.smona.app.evaluationcar.ui.home;

import android.content.Context;
import android.util.AttributeSet;

import com.smona.app.evaluationcar.R;
import com.smona.app.evaluationcar.data.BannerInfo;
import com.smona.app.evaluationcar.data.HomeInfo;
import com.smona.app.evaluationcar.data.NewsInfo;
import com.smona.app.evaluationcar.framework.event.EventProxy;
import com.smona.app.evaluationcar.ui.common.base.BaseListView;
import com.smona.app.evaluationcar.data.event.HomeEvent;
import com.smona.app.evaluationcar.util.CarLog;
import com.smona.app.evaluationcar.util.ViewUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

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
    public void update(HomeEvent event) {
        HomeInfo info = (HomeInfo) event.getContent();
        if (info != null) {
            mHeader.update(info.getBanner());
            mAdapter.update(info.getNews());
        } else {

        }

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        CarLog.d(this, "onAttachedToWindow post");
        post();
    }


    private void post() {
        HomeEvent event = new HomeEvent();
        Object content = createTest();
        event.setContent(content);
        EventProxy.post(event);
    }


    private Object createTest() {
        HomeInfo info = new HomeInfo();
        ArrayList<BannerInfo> bannerList = new ArrayList<BannerInfo>();
        for (int i = 0; i < 5; i++) {
            BannerInfo banner = new BannerInfo(null);
            banner.setId(i);
            banner.setTitle("" + i);
            banner.setUrl("http://www.baidu.com");
            banner.setImgurl("http://assetsdl.gioneemobile.net/attachs/theme/subjectImage/201701/587c2e26cf05d.jpg");
            bannerList.add(banner);
        }
        info.setBanner(bannerList);
        ArrayList<NewsInfo> news = new ArrayList<NewsInfo>();
        for (int i = 0; i < 5; i++) {
            NewsInfo item = new NewsInfo(null);
            item.setId(i);
            item.setTitle("" + i);
            item.setSummary("ddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd");
            item.setUrl("http://www.baidu.com");
            item.setImgurl("http://113.107.245.39:90/attachs/theme/wallpaper/hd/2016/07/995g7j41eegpfngasjs9vsi7m5/312x277/SD-G-RW-062108.jpg");
            news.add(item);
        }
        info.setNews(news);
        return info;
    }


}
