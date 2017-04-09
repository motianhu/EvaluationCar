
package com.smona.app.evaluationcar.ui.status.local;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.smona.app.evaluationcar.R;
import com.smona.app.evaluationcar.data.bean.CarBillBean;
import com.smona.app.evaluationcar.data.event.LocalStatusEvent;
import com.smona.app.evaluationcar.data.event.background.LocalStatusBackgroundEvent;
import com.smona.app.evaluationcar.framework.cache.DataDelegator;
import com.smona.app.evaluationcar.framework.event.EventProxy;
import com.smona.app.evaluationcar.ui.common.refresh.NetworkTipUtil;
import com.smona.app.evaluationcar.ui.common.refresh.PullToRefreshLayout;
import com.smona.app.evaluationcar.ui.evaluation.EvaluationActivity;
import com.smona.app.evaluationcar.util.ActivityUtils;
import com.smona.app.evaluationcar.util.CarLog;
import com.smona.app.evaluationcar.util.StatusUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public class LocalLayer extends PullToRefreshLayout {
    private static final String TAG = LocalLayer.class.getSimpleName();
    private LocalListView mLocalListView = null;
    private View mNoDataLayout = null;
    private View mLoadingView = null;
    private View mHeadView;
    private View mFootView;
    private boolean mPullRequest = false;

    private int mCurPage = 1;

    public LocalLayer(Context context) {
        super(context);
    }

    public LocalLayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LocalLayer(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void addObserver() {
        EventProxy.register(this);
        post();
    }

    private void post() {
        EventProxy.post(new LocalStatusBackgroundEvent());
    }

    @Override
    public void deleteObserver() {
        EventProxy.unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void update(LocalStatusBackgroundEvent event) {
        List<CarBillBean> datas = DataDelegator.getInstance().queryLocalCarbill(mCurPage);
        CarLog.d(TAG, "LocalStatusBackgroundEvent " + datas.size());
        LocalStatusEvent local = new LocalStatusEvent();
        local.setContent(datas);
        EventProxy.post(local);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void update(LocalStatusEvent event) {
        List<CarBillBean> dataList = (List<CarBillBean>) event.getContent();
        if (dataList != null) {
            CarLog.d(TAG, "LocalStatusEvent " + dataList.size());
            mLocalListView.update(dataList);
            if (mPullRequest) {
                loadmoreFinish(PullToRefreshLayout.SUCCEED);
            }
        } else {
            if (mPullRequest) {
                loadmoreFinish(PullToRefreshLayout.FAIL);
            }
        }
        mLoadingView.setVisibility(GONE);
        if (mLocalListView.getItemCount() == 0) {
            mNoDataLayout.setVisibility(VISIBLE);
            mFootView.setVisibility(INVISIBLE);
            mHeadView.setVisibility(INVISIBLE);
            NetworkTipUtil.showNoDataTip(LocalLayer.this, getContext().getString(R.string.no_data_tips), mReloadClickListener);
        } else {
            mNoDataLayout.setVisibility(GONE);
            mFootView.setVisibility(VISIBLE);
            mHeadView.setVisibility(VISIBLE);
        }

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mLocalListView = (LocalListView) findViewById(R.id.local_listview);
        mNoDataLayout = findViewById(R.id.no_content_layout);
        mLoadingView = findViewById(R.id.loading);
        mHeadView = findViewById(R.id.head_view);
        mFootView = findViewById(R.id.loadmore_view);
    }

    private View.OnClickListener mReloadClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            ActivityUtils.jumpEvaluation(getContext(), StatusUtils.BILL_STATUS_NONE, "", 0, EvaluationActivity.class);
        }
    };

    @Override
    protected void onRefresh() {
        refreshFinish(PullToRefreshLayout.SUCCEED);
    }

    @Override
    protected void onLoadMore() {
        mPullRequest = true;
        DataDelegator.getInstance().queryLocalCarbill(mCurPage);
    }

}
