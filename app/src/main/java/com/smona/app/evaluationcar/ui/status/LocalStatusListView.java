package com.smona.app.evaluationcar.ui.status;

import android.content.Context;
import android.util.AttributeSet;

import com.smona.app.evaluationcar.business.HttpProxy;
import com.smona.app.evaluationcar.data.bean.CarBillBean;
import com.smona.app.evaluationcar.data.event.AuditingStatusEvent;
import com.smona.app.evaluationcar.data.event.LocalStatusEvent;
import com.smona.app.evaluationcar.data.event.NotPassStatusEvent;
import com.smona.app.evaluationcar.data.event.PassStatusEvent;
import com.smona.app.evaluationcar.data.event.StatusEvent;
import com.smona.app.evaluationcar.framework.event.EventProxy;
import com.smona.app.evaluationcar.ui.common.base.BaseListView;
import com.smona.app.evaluationcar.util.CarLog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by motianhu on 2/28/17.
 */

public class LocalStatusListView extends StatusListView {
    private static final String TAG = LocalStatusListView.class.getSimpleName();

    public LocalStatusListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void update(LocalStatusEvent event) {
        CarLog.d(TAG, this + "; AuditingStatusEvent " + event);
        List<CarBillBean> datas = (List<CarBillBean>) event.getContent();
        mAdapter.update(datas);
        mAdapter.notifyDataSetChanged();
    }
}
