/**
 * @file XListViewHeader.java
 * @create Apr 18, 2012 5:22:27 PM
 * @author Maxwin
 * @description XListView's header
 */
package com.smona.app.evaluationcar.ui.common.pullup;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.smona.app.evaluationcar.R;

public class XListViewHeader extends LinearLayout {
    public final static int STATE_NORMAL = 0;
    public final static int STATE_READY = 1;
    public final static int STATE_REFRESHING = 2;
    public final static int REFRESH_NET_DATA_SUCESS = 3;
    public final static int REFRESH_CACHE_DATA_SUCESS = 4;
    public final static int REFRESH_FAILED = 5;
    private LinearLayout mContainer;
    private LoadingImageView mProgressBar;
    private TextView mHintTextView;
    private int mState = STATE_NORMAL;
    private int mMaxPullLength;

    public XListViewHeader(Context context) {
        super(context);
        initView(context);
    }

    /**
     * @param context
     * @param attrs
     */
    public XListViewHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    @SuppressLint("InflateParams")
    private void initView(Context context) {
        // 初始情况，设置下拉刷新view高度为0
        LayoutParams lp = new LayoutParams(
                LayoutParams.MATCH_PARENT, 0);
        mContainer = (LinearLayout) LayoutInflater.from(context).inflate(
                R.layout.xlistview_header, null);
        addView(mContainer, lp);
        setGravity(Gravity.BOTTOM);

        mHintTextView = (TextView) findViewById(R.id.xlistview_header_hint_textview);
        mProgressBar = (LoadingImageView) findViewById(R.id.xlistview_header_progressbar);
        mProgressBar.setVisibility(View.VISIBLE);

        mMaxPullLength = (int) getContext().getResources().getDimension(
                R.dimen.xlistview_header_content_max_height);

    }

    public void setState(int state) {
        if (state == mState) {
            return;
        }
        mProgressBar.setVisibility(View.VISIBLE);
        switch (state) {
            case STATE_NORMAL:
                mProgressBar.stopAnimal();
                mHintTextView.setText(R.string.xlistview_header_hint_normal);
                break;
            case STATE_READY:
                mProgressBar.stopAnimal();
                if (mState != STATE_READY) {
                    mHintTextView.setText(R.string.xlistview_header_hint_ready);
                }
                break;
            case STATE_REFRESHING:
                mHintTextView.setText(R.string.xlistview_header_hint_loading);
                mProgressBar.startAnimal();
                break;
            case REFRESH_NET_DATA_SUCESS:
                mHintTextView
                        .setText(R.string.xlistview_header_hint_refresh_net_data_success);
                break;
            case REFRESH_CACHE_DATA_SUCESS:
                mHintTextView
                        .setText(R.string.xlistview_header_hint_refresh_cache_data_success);
                break;
            case REFRESH_FAILED:
                mHintTextView
                        .setText(R.string.xlistview_header_hint_refresh_failed);
                break;
            default:
        }

        mState = state;
    }

    public int getVisiableHeight() {
        return mContainer.getHeight();
    }

    public void setVisiableHeight(int height) {
        if (height < 0) {
            height = 0;
        }
        if (height >= mMaxPullLength) {
            height = mMaxPullLength;
        }
        LayoutParams lp = (LayoutParams) mContainer
                .getLayoutParams();
        lp.height = height;
        mContainer.setLayoutParams(lp);
    }

}
