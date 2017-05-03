package com.smona.app.evaluationcar.ui;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

import com.smona.app.evaluationcar.R;
import com.smona.app.evaluationcar.business.ResponseCallback;
import com.smona.app.evaluationcar.data.bean.BaseBean;
import com.smona.app.evaluationcar.data.event.PageElementEvent;
import com.smona.app.evaluationcar.data.item.BannerItem;
import com.smona.app.evaluationcar.data.item.NewsItem;
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

    private int mType;
    private int mId;

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
        EventProxy.register(this);

        mType = getIntent().getIntExtra(CacheContants.WEB_ACTIVITY_TYPE, -1);
        mId = getIntent().getIntExtra(CacheContants.PAGE_ELEMENT_ID, -1);
        CarLog.d(TAG, "type=" + mType + ", id=" + mId);

    }

    private void initViews() {
        mHtmlView = (WebView) findViewById(R.id.content_web);
        mNoContent = findViewById(R.id.no_content);
        mNoContent.setOnClickListener(mReloadClick);
        mLoading = findViewById(R.id.loading);

        updateTitle();
        setLoading();
    }

    private void setHasData() {
        ViewUtil.setViewVisible(mNoContent, false);
        ViewUtil.setViewVisible(mLoading, false);
        ViewUtil.setViewVisible(mHtmlView, true);
    }

    private void setNoData() {
        ViewUtil.setViewVisible(mNoContent, true);
        ViewUtil.setViewVisible(mLoading, false);
        ViewUtil.setViewVisible(mHtmlView, false);
    }

    private void setLoading() {
        ViewUtil.setViewVisible(mNoContent, false);
        ViewUtil.setViewVisible(mLoading, true);
        ViewUtil.setViewVisible(mHtmlView, false);
    }

    private View.OnClickListener mReloadClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            setLoading();
            requestData();
        }
    };

    private void updateTitle() {
        if (isBanner()) {
            updateTitle(R.string.html_title_banner);
        } else if (isNews()) {
            updateTitle(R.string.html_title_news);
        }
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
        if (isBanner()) {
            DataDelegator.getInstance().queryPageElementDetail(mId, mCallback);
        } else if (isNews()) {
            DataDelegator.getInstance().queryNewsDetail(mId, mCallback);
        }
    }

    private boolean isBanner() {
        return CacheContants.TYPE_BANNER == mType;
    }

    private boolean isNews() {
        return CacheContants.TYPE_NEWS == mType;
    }

    private ResponseCallback<String> mCallback = new ResponseCallback<String>() {
        @Override
        public void onFailed(String error) {
            CarLog.d(TAG, "mCallback onFailed error=" + error);
            poseBanner(null);
        }

        @Override
        public void onSuccess(String content) {
            CarLog.d(TAG, "mCallback onSuccess content=" + content);
            if (isBanner()) {
                parseBanner(content);
            } else if (isNews()) {
                parseNewsItem(content);
            } else {
                poseBanner(null);
            }
        }
    };

    private void parseBanner(String content) {
        BannerItem item = JsonParse.parseJson(content, BannerItem.class);
        poseBanner(item);
    }

    private void parseNewsItem(String content) {
        NewsItem item = JsonParse.parseJson(content, NewsItem.class);
        poseBanner(item);
    }

    private void poseBanner(BaseBean item) {
        PageElementEvent event = new PageElementEvent();
        event.setContent(item);
        EventProxy.post(event);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void update(PageElementEvent event) {
        BaseBean item = (BaseBean) event.getContent();
        String content = null;
        if (item instanceof BannerItem) {
            content = ((BannerItem) item).detailContent;
        } else if (item instanceof NewsItem) {
            content = ((NewsItem) item).content;
        }

        if (item != null) {
            setHasData();

            String str = "<html><head><title>欢迎你</title></head><body>"
                    + content
                    + "</body></html>";
            mHtmlView.loadDataWithBaseURL(null, str, "text/html", "utf-8", null);
        } else {
            setNoData();
        }
    }
}
