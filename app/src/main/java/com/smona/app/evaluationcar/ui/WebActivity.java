package com.smona.app.evaluationcar.ui;

import android.os.Bundle;
import android.webkit.WebView;

import com.smona.app.evaluationcar.R;
import com.smona.app.evaluationcar.ui.common.activity.HeaderActivity;

/**
 * Created by Moth on 2016/12/15.
 */

public class WebActivity extends HeaderActivity {

    private WebView mHtmlView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHtmlView = (WebView)findViewById(R.id.content_web);
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
}
