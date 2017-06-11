package com.smona.app.evaluationcar.ui.evaluation.preevaluation.quick;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.smona.app.evaluationcar.R;
import com.smona.app.evaluationcar.data.bean.QuickPreCarBillBean;
import com.smona.app.evaluationcar.data.event.EvaActionEvent;
import com.smona.app.evaluationcar.data.event.background.TaskSubEvent;
import com.smona.app.evaluationcar.framework.event.EventProxy;
import com.smona.app.evaluationcar.ui.common.activity.HeaderActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by motianhu on 5/23/17.
 */

public class QuickPreevaluationActivity extends HeaderActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_preevaluation_quick;
    }

    @Override
    protected boolean showDelete() {
        return false;
    }

    @Override
    protected int getHeaderTitle() {
        return R.string.evalution_pre_quick;
    }

    private QuickPreCarBillBean preCarBillBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventProxy.register(this);
        initData();
    }

    private void initData() {

    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventProxy.unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void actionSubEvent(TaskSubEvent event) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void actionMainEvent(EvaActionEvent event) {

    }
}
