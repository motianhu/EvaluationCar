package com.smona.app.evaluationcar.ui.evaluation;

import android.content.Context;
import android.text.Html;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.smona.app.evaluationcar.R;
import com.smona.app.evaluationcar.ui.common.base.BaseRelativeLayout;
import com.smona.app.evaluationcar.data.event.SettingEvent;
import com.smona.app.evaluationcar.util.ActivityUtils;
import com.smona.app.evaluationcar.util.CarLog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by motianhu on 2/28/17.
 */

public class EveluationLayer extends BaseRelativeLayout implements View.OnClickListener {

    private Button mUncommit;
    private Button mAuditing;
    private Button mNotPass;
    private Button mPass;

    private TextView mNotice;

    public EveluationLayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void init() {
        findViewById(R.id.search).setOnClickListener(this);
        findViewById(R.id.queryVin).setOnClickListener(this);
        findViewById(R.id.preEvalution).setOnClickListener(this);
        findViewById(R.id.evalution).setOnClickListener(this);

        mNotice = (TextView) findViewById(R.id.evalution_gonggao);
        mNotice.setText(Html.fromHtml(getContext().getString(R.string.notice_content)));
        mUncommit = (Button) findViewById(R.id.uncommit);
        mUncommit.setOnClickListener(this);
        mAuditing = (Button) findViewById(R.id.auditing);
        mAuditing.setOnClickListener(this);
        mNotPass = (Button) findViewById(R.id.notpass);
        mNotPass.setOnClickListener(this);
        mPass = (Button) findViewById(R.id.pass);
        mPass.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.search:
                break;
            case R.id.uncommit:
                break;
            case R.id.auditing:
                break;
            case R.id.notpass:
                break;
            case R.id.pass:
                break;
            case R.id.preEvalution:
                ActivityUtils.jumpOnlyActivity(getContext(), PreEvaluationActivity.class);
                break;
            case R.id.evalution:
                ActivityUtils.jumpOnlyActivity(getContext(), EvaluationActivity.class);
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void update(SettingEvent event) {
        CarLog.d(this, "update SettingEvent: " + event);
    }
}
