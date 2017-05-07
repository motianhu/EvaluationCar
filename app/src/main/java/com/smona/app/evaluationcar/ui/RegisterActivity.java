package com.smona.app.evaluationcar.ui;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.smona.app.evaluationcar.R;
import com.smona.app.evaluationcar.ui.common.activity.BaseActivity;
import com.smona.app.evaluationcar.util.CarLog;

/**
 * Created by Moth on 2016/12/15.
 */

public class RegisterActivity extends BaseActivity {
    private static final String URL = "http://119.23.128.214:8080/carWeb/view/common/register.jsp";

    private WebView wvShow;
    private ProgressBar pbLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        wvShow = (WebView) findViewById(R.id.web_view);
        pbLoading = (ProgressBar) findViewById(R.id.pb_loading);

        wvShow.setWebViewClient(new WebViewClient() {

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }


            @Override
            public void onPageFinished(WebView view, String url) {
                //页面加载完毕
                CarLog.d("", "url: " + url);
                super.onPageFinished(view, url);
                if (pbLoading != null && pbLoading.getVisibility() == View.VISIBLE) {
                    pbLoading.setVisibility(View.GONE);//进度条不可见
                }
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                //页面开始加载
                super.onPageStarted(view, url, favicon);
                if (pbLoading != null && pbLoading.getVisibility() == View.INVISIBLE) {
                    pbLoading.setVisibility(View.VISIBLE);//进度条可见
                }
            }

        });


        wvShow.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        wvShow.getSettings().setJavaScriptEnabled(true);//设置支持脚本
        wvShow.getSettings().setBuiltInZoomControls(true);// 设置支持缩放
        wvShow.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);// 屏幕自适应网页,如果没有这个，在低分辨率的手机上显示可能会异常
        wvShow.loadUrl(URL);
    }
}
