package com.smona.app.evaluationcar.ui.home;

import android.content.Context;
import android.util.AttributeSet;

import com.smona.app.evaluationcar.R;
import com.smona.app.evaluationcar.data.BannerInfo;
import com.smona.app.evaluationcar.data.HomeInfo;
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
        return info;
    }


}
