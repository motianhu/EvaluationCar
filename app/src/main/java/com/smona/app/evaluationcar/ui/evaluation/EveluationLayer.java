package com.smona.app.evaluationcar.ui.evaluation;

import android.content.Context;
import android.text.Html;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.smona.app.evaluationcar.R;
import com.smona.app.evaluationcar.ui.HomeActivity;
import com.smona.app.evaluationcar.ui.common.base.BaseRelativeLayout;
import com.smona.app.evaluationcar.data.event.SettingEvent;
import com.smona.app.evaluationcar.ui.evaluation.camera.CameraActivity;
import com.smona.app.evaluationcar.ui.evaluation.preview.PreviewPictureActivity;
import com.smona.app.evaluationcar.util.ActivityUtils;
import com.smona.app.evaluationcar.util.CarLog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by motianhu on 2/28/17.
 */

public class EveluationLayer extends BaseRelativeLayout implements View.OnClickListener {

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
        findViewById(R.id.uncommit).setOnClickListener(this);
        findViewById(R.id.auditing).setOnClickListener(this);
        findViewById(R.id.notpass).setOnClickListener(this);
        findViewById(R.id.pass).setOnClickListener(this);
        CarLog.d(this, "Context " + getContext());
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.search:
                ActivityUtils.jumpOnlyActivity(getContext(), SearchActivity.class);
                break;
            case R.id.uncommit:
                ((HomeActivity)getContext()).changeList(0);
                break;
            case R.id.auditing:
                ((HomeActivity)getContext()).changeList(1);
                break;
            case R.id.notpass:
                ((HomeActivity)getContext()).changeList(2);
                break;
            case R.id.pass:
                ((HomeActivity)getContext()).changeList(3);
                break;
            case R.id.preEvalution:
                ActivityUtils.jumpOnlyActivity(getContext(), PreEvaluationActivity.class);
                break;
            case R.id.evalution:
                ActivityUtils.jumpOnlyActivity(getContext(), PreviewPictureActivity.class);
                break;
            case R.id.queryVin:
                ActivityUtils.jumpOnlyActivity(getContext(), CameraActivity.class);
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void update(SettingEvent event) {
        CarLog.d(this, "update SettingEvent: " + event);
    }
}
