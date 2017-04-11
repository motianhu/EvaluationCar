package com.smona.app.evaluationcar.ui.home;

import android.content.Context;
import android.util.AttributeSet;

import com.smona.app.evaluationcar.R;
import com.smona.app.evaluationcar.business.ResponseCallback;
import com.smona.app.evaluationcar.business.param.BannerParam;
import com.smona.app.evaluationcar.data.event.BannerEvent;
import com.smona.app.evaluationcar.data.event.NewsEvent;
import com.smona.app.evaluationcar.data.item.BannerItem;
import com.smona.app.evaluationcar.data.item.NewsItem;
import com.smona.app.evaluationcar.framework.cache.DataDelegator;
import com.smona.app.evaluationcar.ui.common.base.BaseListView;
import com.smona.app.evaluationcar.util.CarLog;
import com.smona.app.evaluationcar.util.ViewUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * Created by Moth on 2017/2/24.
 */

public class HomeListView extends BaseListView {
    private static final String TAG = HomeListView.class.getSimpleName();
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
        List<BannerItem> list = (List<BannerItem>) event.getContent();
        if (list != null) {
            mHeader.update(list);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void update(NewsEvent event) {
        List<NewsItem> list = (List<NewsItem>) event.getContent();
        if (list != null) {
            mAdapter.update(list);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        CarLog.d(TAG, "onAttachedToWindow post");
        post();
    }


    private void post() {
        BannerParam param = new BannerParam();
        param.classType = "新闻公告";
        DataDelegator.getInstance().requestBanner(param, new ResponseCallback<String>() {

            @Override
            public void onSuccess(String result) {

            }

            @Override
            public void onFailed(String error) {

            }
        });

        param = new BannerParam();
        param.classType = "最新资讯";
        DataDelegator.getInstance().requestNews(param, new ResponseCallback<String>() {

            @Override
            public void onSuccess(String result) {

            }

            @Override
            public void onFailed(String error) {

            }
        });
    }

}
