package com.smona.app.evaluationcar.ui;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

import com.smona.app.evaluationcar.R;
import com.smona.app.evaluationcar.business.ResponseCallback;
import com.smona.app.evaluationcar.data.event.PageElementEvent;
import com.smona.app.evaluationcar.data.item.BannerItem;
import com.smona.app.evaluationcar.framework.cache.DataDelegator;
import com.smona.app.evaluationcar.framework.event.EventProxy;
import com.smona.app.evaluationcar.framework.json.JsonParse;
import com.smona.app.evaluationcar.ui.common.activity.HeaderActivity;
import com.smona.app.evaluationcar.util.CacheContants;
import com.smona.app.evaluationcar.util.CarLog;
import com.smona.app.evaluationcar.util.ViewUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by Moth on 2016/12/15.
 */

public class WebActivity extends HeaderActivity {
    private static final String TAG = WebActivity.class.getSimpleName();

    private WebView mHtmlView;
    private View mNoContent;
    private View mLoading;

    private int mPageId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initViews();
        requestData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventProxy.unregister(this);
    }

    private void initData() {
        mPageId = getIntent().getIntExtra(CacheContants.PAGE_ELEMENT_ID, -1);
        EventProxy.register(this);
    }

    private void initViews() {
        mHtmlView = (WebView) findViewById(R.id.content_web);
        mNoContent = findViewById(R.id.no_content);
        ViewUtil.setViewVisible(mNoContent, false);
        mLoading = findViewById(R.id.loading);
    }

    @Override
    protected void onDelete() {
        super.onDelete();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_web;
    }

    @Override
    protected boolean showDelete() {
        return false;
    }

    @Override
    protected int getHeaderTitle() {
        return R.string.html_title;
    }

    private void requestData() {
        DataDelegator.getInstance().queryPageElementDetail(mPageId, mBannerCallback);
    }

    private ResponseCallback<String> mBannerCallback = new ResponseCallback<String>() {
        @Override
        public void onFailed(String error) {
            CarLog.d(TAG, "mBannerCallback onFailed error=" + error);
            poseBanner(null);
        }

        @Override
        public void onSuccess(String content) {
            CarLog.d(TAG, "mBannerCallback onSuccess content=" + content);
            BannerItem item = JsonParse.parseJson(content, BannerItem.class);
            poseBanner(item);
        }
    };

    private void poseBanner(BannerItem item) {
        PageElementEvent event = new PageElementEvent();
        event.setContent(item);
        EventProxy.post(event);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void update(PageElementEvent event) {
        BannerItem item = (BannerItem) event.getContent();
        ViewUtil.setViewVisible(mLoading, false);
        if (item != null) {
            ViewUtil.setViewVisible(mNoContent, false);
            String str = "<html><head><title>欢迎你</title></head><body>"
                    + item.detailContent
                    + "</body></html>";
            mHtmlView.loadDataWithBaseURL(null, str, "text/html", "utf-8", null);
        } else {
            ViewUtil.setViewVisible(mNoContent, true);
        }
    }
}
