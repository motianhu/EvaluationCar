/**
 * @file XFooterView.java
 * @create Mar 31, 2012 9:33:43 PM
 * @author Maxwin
 * @description XListView's footer
 */
package com.smona.app.evaluationcar.ui.common.pullup;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.smona.app.evaluationcar.R;

public class XListViewFooter extends LinearLayout {
    public final static int STATE_NORMAL = 0;
    public final static int STATE_READY = 1;
    public final static int STATE_LOADING = 2;

    private Context mContext;

    private View mContentView;
    private LoadingImageView mProgressBar;
    private TextView mHintView;
    private String mTextNormal, mTextReady;

    public XListViewFooter(Context context) {
        super(context);
        initView(context);
    }

    public XListViewFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public void setState(int state) {
        mHintView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.GONE);
        mHintView.setVisibility(View.GONE);
        if (state == STATE_READY) {
            mHintView.setVisibility(View.VISIBLE);
            mHintView.setText(mTextReady);
        } else if (state == STATE_LOADING) {
            mProgressBar.setVisibility(View.VISIBLE);
            mProgressBar.startAnimal();
        } else {
            mHintView.setVisibility(View.GONE);
            mHintView.setText(mTextNormal);
        }
    }

    public void setBottomMargin(int height) {
        if (height < 0) {
            return;
        }
        LayoutParams lp = (LayoutParams) mContentView
                .getLayoutParams();
        lp.bottomMargin = height;
        mContentView.setLayoutParams(lp);
    }

    public int getBottomMargin() {
        LayoutParams lp = (LayoutParams) mContentView
                .getLayoutParams();
        return lp.bottomMargin;
    }

    /**
     * hide footer when disable pull load more
     */
    public void hide() {
        LayoutParams lp = (LayoutParams) mContentView
                .getLayoutParams();
        lp.height = 0;
        mContentView.setLayoutParams(lp);
    }

    /**
     * show footer
     */
    public void show() {
        LayoutParams lp = (LayoutParams) mContentView
                .getLayoutParams();
        lp.height = LayoutParams.WRAP_CONTENT;
        mContentView.setLayoutParams(lp);
    }

    @SuppressLint("InflateParams")
    private void initView(Context context) {
        mContext = context;
        mTextNormal = mContext.getString(R.string.xlistview_footer_hint_normal);
        mTextReady = mContext.getString(R.string.xlistview_footer_hint_ready);
        LinearLayout moreView = (LinearLayout) LayoutInflater.from(mContext)
                .inflate(R.layout.xlistview_footer, null);
        addView(moreView);
        moreView.setLayoutParams(new LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

        mContentView = moreView.findViewById(R.id.xlistview_footer_content);
        mProgressBar = (LoadingImageView) moreView
                .findViewById(R.id.xlistview_footer_progressbar);
        mHintView = (TextView) moreView
                .findViewById(R.id.xlistview_footer_hint_textview);
    }

    public String getmTextNormal() {
        return mTextNormal;
    }

    public void setmTextNormal(String mTextNormal) {
        this.mTextNormal = mTextNormal;
    }

    public String getmTextReady() {
        return mTextReady;
    }

    public void setmTextReady(String mTextReady) {
        this.mTextReady = mTextReady;
    }


}
