package com.smona.app.evaluationcar.ui.status.notpass;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.TextView;

import com.smona.app.evaluationcar.R;
import com.smona.app.evaluationcar.data.bean.CarBillBean;
import com.smona.app.evaluationcar.ui.common.refresh.PullableListView;
import com.smona.app.evaluationcar.ui.status.RequestFace;
import com.smona.app.evaluationcar.util.CarLog;
import com.smona.app.evaluationcar.util.StatusUtils;
import com.smona.app.evaluationcar.util.ViewUtil;

import java.util.List;

public class NotPassListView extends PullableListView implements
        OnScrollListener {

    private static final String TAG = NotPassListView.class.getSimpleName();
    private NotPassAdapter mListAdapter = null;
    private int mLastItem;
    private int mCurrentFirstVisibleIndex = 0;
    private int mCurrentVisibleCount = 0;

    private int mOldVisibleIndex = 0;
    private int mOldVisibleCount = 0;
    private View mFootView;
    private boolean mListPullLoading = false;
    private int mScrollState = OnScrollListener.SCROLL_STATE_IDLE;

    private RequestFace mRequestFace;

    private int mTag = StatusUtils.MESSAGE_REQUEST_PAGE_MORE;

    public NotPassListView(Context context) {
        super(context);
        init(context);
    }

    public NotPassListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public NotPassListView(Context context, AttributeSet attrs,
                           int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        mListAdapter = new NotPassAdapter(context);
        setOnScrollListener(this);
        setAdapter(mListAdapter);
        mFootView = ViewUtil.inflater(context, R.layout.refresh_foot_load);
    }

    public void setOnRequestFace(RequestFace face) {
        mRequestFace = face;
    }

    public void update(List<CarBillBean> deltaList, int tag) {
        mListAdapter.update(deltaList);
        mListPullLoading = false;
        mTag = tag;

        if (mTag == StatusUtils.MESSAGE_REQUEST_PAGE_LAST) {
            removeFooterView(mFootView);
        } else if (mTag == StatusUtils.MESSAGE_REQUEST_ERROR) {
            View progress = mFootView.findViewById(R.id.loading_icon);
            TextView tip = (TextView) mFootView.findViewById(R.id.loadstate_tv);
            progress.setVisibility(View.GONE);
            tip.setText(R.string.load_fail);
        } else {
            View progress = mFootView.findViewById(R.id.loading_icon);
            TextView tip = (TextView) mFootView.findViewById(R.id.loadstate_tv);
            progress.setVisibility(View.GONE);
            tip.setText(R.string.pullup_to_load);
        }
    }

    public int getItemCount() {
        return mListAdapter.getCount();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        addFooterView(mFootView, null, false);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }


    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        mLastItem = firstVisibleItem + visibleItemCount - 1;
        mCurrentFirstVisibleIndex = firstVisibleItem;
        mCurrentVisibleCount = visibleItemCount;
        if (!isPageLast() && !mListPullLoading && mLastItem == mListAdapter.getCount()
                && mListAdapter.getCount() > 1 && mScrollState != OnScrollListener.SCROLL_STATE_IDLE) {
            mFootView.setVisibility(View.VISIBLE);
            View progress = mFootView.findViewById(R.id.loading_icon);
            TextView tip = (TextView) mFootView.findViewById(R.id.loadstate_tv);
            progress.setVisibility(View.GONE);
            CarLog.d(TAG,
                    "onScroll mFootView.getBottom=" + mFootView.getBottom() + " listview.getHeight="
                            + view.getHeight() + " mFootView.getHeight="
                            + mFootView.getHeight());
            if ((mFootView.getBottom() - +view.getHeight()) < mFootView.getHeight() / 4) {
                tip.setText(R.string.release_to_load);
            } else {
                tip.setText(R.string.pullup_to_load);
            }
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        mScrollState = scrollState;
        mListAdapter.setScrollState(scrollState);
        if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
            requestImage();
            if (!isPageLast() && !mListPullLoading && mLastItem == mListAdapter.getCount()
                    && mListAdapter.getCount() > 1) {
                mFootView.setVisibility(View.VISIBLE);
                View progress = mFootView.findViewById(R.id.loading_icon);
                TextView tip = (TextView) mFootView.findViewById(R.id.loadstate_tv);

                CarLog.d(TAG,
                        "onScrollStateChanged mFootView.getBottom=" + mFootView.getBottom() + " listview.getHeight="
                                + view.getHeight() + " mFootView.getHeight="
                                + mFootView.getHeight());
                if ((mFootView.getBottom() - +view.getHeight()) < mFootView.getHeight() / 4) {
                    tip.setText(R.string.loading);
                    progress.setVisibility(View.VISIBLE);
                    mListPullLoading = true;
                    mRequestFace.requestNext();
                }
            }
        }
    }

    private void requestImage() {
        int deltaIndex = mCurrentFirstVisibleIndex - mOldVisibleIndex;
        int deltaCount = mCurrentVisibleCount - mOldVisibleCount;
        if (deltaIndex == 0) {
            if (deltaCount > 0) {
                mListAdapter.notifyDataSetChanged();
            }
        } else {
            mListAdapter.notifyDataSetChanged();
        }
        mOldVisibleIndex = mCurrentFirstVisibleIndex;
        mOldVisibleCount = mCurrentVisibleCount;
    }

    @Override
    protected boolean isPageLast() {
        return mTag == StatusUtils.MESSAGE_REQUEST_PAGE_LAST;
    }

    public void clear() {
        mListAdapter.clear();
    }
}
