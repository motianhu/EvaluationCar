package com.smona.app.evaluationcar.ui.home;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.smona.app.evaluationcar.R;
import com.smona.app.evaluationcar.business.param.BannerParam;
import com.smona.app.evaluationcar.data.event.BannerEvent;
import com.smona.app.evaluationcar.data.event.BillTotalEvent;
import com.smona.app.evaluationcar.data.event.NewsEvent;
import com.smona.app.evaluationcar.data.item.BannerItem;
import com.smona.app.evaluationcar.data.item.BillTotalItem;
import com.smona.app.evaluationcar.data.item.NewsItem;
import com.smona.app.evaluationcar.data.model.ResCountPage;
import com.smona.app.evaluationcar.framework.cache.DataDelegator;
import com.smona.app.evaluationcar.framework.event.EventProxy;
import com.smona.app.evaluationcar.ui.common.base.BaseLinearLayout;
import com.smona.app.evaluationcar.util.CarLog;
import com.smona.app.evaluationcar.util.ViewUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * Created by motianhu on 4/11/17.
 */

public class HomeLayer extends BaseLinearLayout {
    private static final String TAG = HomeLayer.class.getSimpleName();

    private TextView mTvBillTotal;
    private HomeListView mHomeList;
    private View mNoContent;
    private View mLoading;

    public HomeLayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void init() {
        mTvBillTotal = (TextView) findViewById(R.id.billtotal);
        String content = getResources().getString(R.string.home_bill_total);
        mTvBillTotal.setText(String.format(content, 0));

        mHomeList = (HomeListView) findViewById(R.id.content_list);
        mNoContent = findViewById(R.id.no_content);
        mNoContent.setOnClickListener(mReloadClick);
        mLoading = findViewById(R.id.loading);

        setLoading();
    }

    private void setLoading() {
        ViewUtil.setViewVisible(mHomeList, false);
        ViewUtil.setViewVisible(mLoading, true);
        ViewUtil.setViewVisible(mNoContent, false);
    }

    private OnClickListener mReloadClick = new OnClickListener() {
        @Override
        public void onClick(View v) {
            setLoading();
            requestBanner();
            requestNews();
        }
    };

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void update(BillTotalEvent event) {
        ResCountPage bean = (ResCountPage) event.getContent();
        if (bean != null && bean.total > 0) {
            for (BillTotalItem item : bean.data) {
                if (BillTotalItem.ALLCOUNT.equals(item.infoType)) {
                    String content = getResources().getString(R.string.home_bill_total);
                    mTvBillTotal.setText(String.format(content, item.countInfo));
                    return;
                }
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void update(BannerEvent event) {
        List<BannerItem> list = (List<BannerItem>) event.getContent();
        if (list != null) {
            mHomeList.updateHeader(list);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void update(NewsEvent event) {
        List<NewsItem> list = (List<NewsItem>) event.getContent();
        if (list != null) {
            setHasData();
            mHomeList.updateAdapter(list);
        } else {
            setNoData();
        }
    }

    private void setNoData() {
        ViewUtil.setViewVisible(mLoading, false);
        ViewUtil.setViewVisible(mHomeList, false);
        ViewUtil.setViewVisible(mNoContent, true);
    }

    private void setHasData() {
        ViewUtil.setViewVisible(mNoContent, false);
        ViewUtil.setViewVisible(mLoading, false);
        ViewUtil.setViewVisible(mHomeList, true);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        CarLog.d(TAG, "onAttachedToWindow request");
        request();
    }


    private void request() {
        requestBanner();
        requestNews();
    }

    private void requestNews() {
        BannerParam param = new BannerParam();
        param.classType = "最新资讯";
        DataDelegator.getInstance().requestLatestNews(param);
    }

    private void requestBanner() {
        DataDelegator.getInstance().queryPageElementLatest();
    }




}
