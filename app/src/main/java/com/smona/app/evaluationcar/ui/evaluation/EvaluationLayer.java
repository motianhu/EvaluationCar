package com.smona.app.evaluationcar.ui.evaluation;

import android.content.Context;
import android.text.Html;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.smona.app.evaluationcar.R;
import com.smona.app.evaluationcar.business.ResponseCallback;
import com.smona.app.evaluationcar.data.event.BillTotalEvent;
import com.smona.app.evaluationcar.data.event.LocalStatusEvent;
import com.smona.app.evaluationcar.data.event.NoticeEvent;
import com.smona.app.evaluationcar.data.event.background.LocalStatusSubEvent;
import com.smona.app.evaluationcar.data.item.BillTotalItem;
import com.smona.app.evaluationcar.data.item.UserItem;
import com.smona.app.evaluationcar.data.model.ResCountPage;
import com.smona.app.evaluationcar.data.model.ResNewsPage;
import com.smona.app.evaluationcar.framework.cache.DataDelegator;
import com.smona.app.evaluationcar.framework.event.EventProxy;
import com.smona.app.evaluationcar.framework.json.JsonParse;
import com.smona.app.evaluationcar.framework.provider.DBDelegator;
import com.smona.app.evaluationcar.ui.HomeActivity;
import com.smona.app.evaluationcar.ui.common.base.BaseRelativeLayout;
import com.smona.app.evaluationcar.ui.evaluation.preevaluation.PreEvaluationActivity;
import com.smona.app.evaluationcar.ui.evaluation.preview.PreviewPictureActivity;
import com.smona.app.evaluationcar.ui.evaluation.search.SearchActivity;
import com.smona.app.evaluationcar.util.ActivityUtils;
import com.smona.app.evaluationcar.util.CarLog;
import com.smona.app.evaluationcar.util.StatusUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by motianhu on 2/28/17.
 */

public class EvaluationLayer extends BaseRelativeLayout implements View.OnClickListener {
    private static final String TAG = EvaluationLayer.class.getSimpleName();

    private TextView mNotice;

    private TextView mUnCommitTv;
    private TextView mAuditingTv;
    private TextView mNotPassTv;
    private TextView mPassTv;

    private int mLocalCount = 0;

    private UserItem mUser;
    private ResponseCallback<String> mCallbillCountCallback = new ResponseCallback<String>() {
        @Override
        public void onFailed(String error) {
            CarLog.d(TAG, "mCallbillCountCallback onFailed error= " + error);
        }

        @Override
        public void onSuccess(String content) {
            CarLog.d(TAG, "mCallbillCountCallback onSuccess content= " + content);
            ResCountPage resCountPage = JsonParse.parseJson(content, ResCountPage.class);
            notifyUICount(resCountPage);
        }
    };

    private ResponseCallback<String> mNoticeCallback = new ResponseCallback<String>() {
        @Override
        public void onFailed(String error) {
            CarLog.d(TAG, "mNoticeCallback onFailed error= " + error);
        }

        @Override
        public void onSuccess(String content) {
            CarLog.d(TAG, "mNoticeCallback onSuccess content= " + content);
            ResNewsPage newsPage = JsonParse.parseJson(content, ResNewsPage.class);
            notifyUINotice(newsPage);
        }
    };

    public EvaluationLayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void init() {
        findViewById(R.id.search).setOnClickListener(this);
        findViewById(R.id.queryVin).setOnClickListener(this);
        findViewById(R.id.preEvalution).setOnClickListener(this);
        findViewById(R.id.evalution).setOnClickListener(this);

        findViewById(R.id.uncommit).setOnClickListener(this);
        findViewById(R.id.auditing).setOnClickListener(this);
        findViewById(R.id.notpass).setOnClickListener(this);
        findViewById(R.id.pass).setOnClickListener(this);

        mNotice = (TextView) findViewById(R.id.notice_content);
        mNotice.setText(Html.fromHtml(getContext().getString(R.string.notice_content)));

        String content = getResources().getString(R.string.home_bill_total);

        mUnCommitTv = (TextView) findViewById(R.id.tv_uncommit);
        mUnCommitTv.setText(String.format(content, mLocalCount));

        mAuditingTv = (TextView) findViewById(R.id.tv_auditing);
        mAuditingTv.setText(String.format(content, 0));

        mNotPassTv = (TextView) findViewById(R.id.tv_notpass);
        mNotPassTv.setText(String.format(content, 0));

        mPassTv = (TextView) findViewById(R.id.tv_pass);
        mPassTv.setText(String.format(content, 0));

        mUser = new UserItem();
        mUser.readSelf(getContext());
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.search:
                ActivityUtils.jumpOnlyActivity(getContext(), SearchActivity.class);
                break;
            case R.id.uncommit:
                ((HomeActivity) getContext()).changeList(0);
                break;
            case R.id.auditing:
                ((HomeActivity) getContext()).changeList(1);
                break;
            case R.id.notpass:
                ((HomeActivity) getContext()).changeList(2);
                break;
            case R.id.pass:
                ((HomeActivity) getContext()).changeList(3);
                break;
            case R.id.preEvalution:
                ActivityUtils.jumpOnlyActivity(getContext(), PreEvaluationActivity.class);
                break;
            case R.id.evalution:
                ActivityUtils.jumpEvaluation(getContext(), StatusUtils.BILL_STATUS_NONE, "", 0, EvaluationActivity.class);
                break;
            case R.id.queryVin:
                ActivityUtils.jumpOnlyActivity(getContext(), PreviewPictureActivity.class);
                break;
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        CarLog.d(TAG, "onAttachedToWindow post");
        post();
    }

    private void post() {
        DataDelegator.getInstance().requestCarbillCount(mUser.mId, mCallbillCountCallback);
        DataDelegator.getInstance().requestNotice(mNoticeCallback);
        EventProxy.post(new LocalStatusSubEvent());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void update(BillTotalEvent event) {
        String content = getResources().getString(R.string.bill_count);
        ResCountPage bean = (ResCountPage) event.getContent();
        if (bean != null && bean.total > 0) {
            mUnCommitTv.setText(String.format(content, mLocalCount));
            for (BillTotalItem item : bean.data) {
                if (BillTotalItem.FINISHCOUNT.equals(item.infoType)) {
                    mPassTv.setText(String.format(content, item.countInfo));
                } else if (BillTotalItem.PROCESSCOUNT.equals(item.infoType)) {
                    mAuditingTv.setText(String.format(content, item.countInfo));
                } else if (BillTotalItem.REFUSECOUNT.equals(item.infoType)) {
                    mNotPassTv.setText(String.format(content, item.countInfo));
                }
            }
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void update(LocalStatusEvent event) {
        String content = getResources().getString(R.string.bill_count);
        mUnCommitTv.setText(String.format(content, mLocalCount));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void update(NoticeEvent event) {
        ResNewsPage bean = (ResNewsPage) event.getContent();
        if (bean != null && bean.total > 0) {
            mNotice.setText(bean.data.get(0).summary);
        }
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void reloadDBData(LocalStatusSubEvent event) {
        mLocalCount = DBDelegator.getInstance().queryLocalBillCount();
        notifyUILocalCount();
    }

    private void notifyUICount(ResCountPage page) {
        BillTotalEvent event = new BillTotalEvent();
        event.setContent(page);
        EventProxy.post(event);
    }

    private void notifyUINotice(ResNewsPage page) {
        NoticeEvent event = new NoticeEvent();
        event.setContent(page);
        EventProxy.post(event);
    }

    private void notifyUILocalCount() {
        EventProxy.post(new LocalStatusEvent());
    }
}
