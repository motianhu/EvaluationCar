package com.smona.app.evaluationcar.ui.evaluation;

import android.content.Context;
import android.text.Html;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.smona.app.evaluationcar.R;
import com.smona.app.evaluationcar.data.event.BillTotalEvent;
import com.smona.app.evaluationcar.data.event.NoticeEvent;
import com.smona.app.evaluationcar.data.item.BillTotalItem;
import com.smona.app.evaluationcar.framework.request.Deletor;
import com.smona.app.evaluationcar.ui.HomeActivity;
import com.smona.app.evaluationcar.ui.common.base.BaseRelativeLayout;
import com.smona.app.evaluationcar.ui.evaluation.preview.PreviewPictureActivity;
import com.smona.app.evaluationcar.util.ActivityUtils;
import com.smona.app.evaluationcar.util.CarLog;
import com.smona.app.evaluationcar.util.StatusUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by motianhu on 2/28/17.
 */

public class EveluationLayer extends BaseRelativeLayout implements View.OnClickListener {
    private static final String TAG = EveluationLayer.class.getSimpleName();

    private TextView mNotice;

    private TextView mUnCommitTv;
    private TextView mAuditingTv;
    private TextView mNotPassTv;
    private TextView mPassTv;

    private View mUnCommit;
    private View mAuditing;
    private View mNotPass;
    private View mPass;

    public EveluationLayer(Context context, AttributeSet attrs) {
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

        mUnCommitTv = (TextView) findViewById(R.id.tv_uncommit);
        mAuditingTv = (TextView) findViewById(R.id.tv_auditing);
        mNotPassTv = (TextView) findViewById(R.id.tv_notpass);
        mPassTv = (TextView) findViewById(R.id.tv_pass);
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
                ActivityUtils.jumpEvaluation(getContext(), StatusUtils.BILL_STATUS_NONE, null, 0, EvaluationActivity.class);
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
        Deletor.getInstance().requestPerBillCount();
        Deletor.getInstance().requestNotice();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void update(BillTotalEvent event) {
        BillTotalItem bean = (BillTotalItem) event.getContent();
        if (bean != null) {
            String content = getResources().getString(R.string.bill_count);
            mUnCommitTv.setText(String.format(content, bean.getUnCommitCount()));
            mAuditingTv.setText(String.format(content, bean.getAuditingCount()));
            mNotPassTv.setText(String.format(content, bean.getNotPassCount()));
            mPassTv.setText(String.format(content, bean.getPassCount()));
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void update(NoticeEvent event) {
        NoticeEvent bean = (NoticeEvent) event.getContent();
        if (bean != null) {
            mNotice.setText(bean.getMessage());
        }
    }
}
