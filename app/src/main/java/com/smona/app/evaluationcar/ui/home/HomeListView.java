package com.smona.app.evaluationcar.ui.home;

import android.content.Context;
import android.util.AttributeSet;

import com.smona.app.evaluationcar.R;
import com.smona.app.evaluationcar.data.bean.BannerBean;
import com.smona.app.evaluationcar.data.bean.NewsBean;
import com.smona.app.evaluationcar.data.event.NewsEvent;
import com.smona.app.evaluationcar.framework.request.Deletor;
import com.smona.app.evaluationcar.ui.common.base.BaseListView;
import com.smona.app.evaluationcar.data.event.BannerEvent;
import com.smona.app.evaluationcar.util.CarLog;
import com.smona.app.evaluationcar.util.ViewUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
        mHeader = (BannerHeader) ViewUtil.inflater(getContext(), R.layout.banner_header);
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
        Deletor.requestBanner();
        Deletor.requestNews();
    }

}
