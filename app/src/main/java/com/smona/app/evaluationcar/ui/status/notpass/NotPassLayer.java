
package com.smona.app.evaluationcar.ui.status.notpass;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.smona.app.evaluationcar.R;
import com.smona.app.evaluationcar.business.ResponseCallback;
import com.smona.app.evaluationcar.business.param.CarbillParam;
import com.smona.app.evaluationcar.data.bean.CarBillBean;
import com.smona.app.evaluationcar.data.event.AuditingStatusEvent;
import com.smona.app.evaluationcar.data.event.NotPassStatusEvent;
import com.smona.app.evaluationcar.data.model.ResCarBill;
import com.smona.app.evaluationcar.framework.cache.DataDelegator;
import com.smona.app.evaluationcar.framework.event.EventProxy;
import com.smona.app.evaluationcar.framework.json.JsonParse;
import com.smona.app.evaluationcar.ui.common.refresh.NetworkTipUtil;
import com.smona.app.evaluationcar.ui.common.refresh.PullToRefreshLayout;
import com.smona.app.evaluationcar.util.CarLog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class NotPassLayer extends PullToRefreshLayout {
    private static final String TAG = NotPassLayer.class.getSimpleName();
    private NotPassListView mNotPassListView = null;
    private View mNoDataLayout = null;
    private View mLoadingView = null;
    private View mHeadView;
    private View mFootView;
    private boolean mPullRequest = false;

    private CarbillParam mRequestParams = new CarbillParam();

    public NotPassLayer(Context context) {
        super(context);
    }

    public NotPassLayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NotPassLayer(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initRequestParams();
    }

    private void initRequestParams() {
        mRequestParams.userName = "cy";
        mRequestParams.curPage = 1;
        mRequestParams.pageSize = 10;
        mRequestParams.status = "23,33,43,53";
    }

    @Override
    public void addObserver() {
        EventProxy.register(this);
        post();
    }

    private void post() {
        NotPassStatusEvent event = new NotPassStatusEvent();
        event.setContent(createTest(25));
        EventProxy.post(event);
    }

    private Object createTest(int count) {
        ArrayList<CarBillBean> carBillList = new ArrayList<CarBillBean>();
        for (int i = 0; i < count; i++) {
            CarBillBean carbill = new CarBillBean();
            carbill.carBillId = "NS201612021100" + i;
            carbill.createTime = "2016-12-01 12:11:00";
            carbill.modifyTime = "2016-12-21 15:11:00";
            carbill.status = 1;
            carbill.thumbUrl = "http://113.107.245.39:90/attachs/theme/wallpaper/hd/2016/07/995g7j41eegpfngasjs9vsi7m5/312x277/SD-G-RW-062108.jpg";
            carBillList.add(carbill);
        }
        return carBillList;
    }

    @Override
    public void deleteObserver() {
        EventProxy.unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void update(NotPassStatusEvent event) {
        List<CarBillBean> dataList = (List<CarBillBean>) event.getContent();
        if (dataList != null) {
            CarLog.d(TAG, "update " + dataList.size());
            mNotPassListView.update(dataList);
            if (mPullRequest) {
                loadmoreFinish(PullToRefreshLayout.SUCCEED);
            }
        } else {
            if (mPullRequest) {
                loadmoreFinish(PullToRefreshLayout.FAIL);
            }
        }
        mLoadingView.setVisibility(GONE);
        if (mNotPassListView.getItemCount() == 0) {
            mNoDataLayout.setVisibility(VISIBLE);
            mFootView.setVisibility(INVISIBLE);
            mHeadView.setVisibility(INVISIBLE);
            NetworkTipUtil.showNetworkTip(NotPassLayer.this, mReloadClickListener);
        } else {
            mNoDataLayout.setVisibility(GONE);
            mFootView.setVisibility(VISIBLE);
            mHeadView.setVisibility(VISIBLE);
        }

    }

    private ResponseCallback<String> mResonponseCallBack = new ResponseCallback<String>() {
        @Override
        public void onFailed(String error) {
            CarLog.d(TAG, "error: " + error);
        }

        @Override
        public void onSuccess(String content) {
            CarLog.d(TAG, "onSuccess: " + content);
            ResCarBill dataList = JsonParse.parseJson(content, ResCarBill.class);
        }
    };

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mNotPassListView = (NotPassListView) findViewById(R.id.local_listview);
        mNoDataLayout = findViewById(R.id.no_content_layout);
        mLoadingView = findViewById(R.id.loading);
        mHeadView = findViewById(R.id.head_view);
        mFootView = findViewById(R.id.loadmore_view);
    }

    private OnClickListener mReloadClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            CarLog.d(TAG, "mReloadClickListener reload LocalPager");
            DataDelegator.getInstance().queryCarbillList(mRequestParams, mResonponseCallBack);
            mLoadingView.setVisibility(VISIBLE);
            mNoDataLayout.setVisibility(GONE);
        }
    };

    @Override
    protected void onRefresh() {
        refreshFinish(PullToRefreshLayout.SUCCEED);
    }

    @Override
    protected void onLoadMore() {
        mPullRequest = true;
        DataDelegator.getInstance().queryCarbillList(mRequestParams, mResonponseCallBack);
    }

}
