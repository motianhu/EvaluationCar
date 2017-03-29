package com.smona.app.evaluationcar.ui.status;

import android.content.Context;
import android.util.AttributeSet;

import com.smona.app.evaluationcar.data.bean.CarBillBean;
import com.smona.app.evaluationcar.data.event.LocalStatusEvent;
import com.smona.app.evaluationcar.data.event.background.LocalStatusBackgroundEvent;
import com.smona.app.evaluationcar.framework.event.EventProxy;
import com.smona.app.evaluationcar.util.CarLog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
        List<CarBillBean> datas = (List<CarBillBean>) event.getContent();
        CarLog.d(TAG, "LocalStatusEvent " + datas.size());
        mAdapter.update(datas);
        mAdapter.notifyDataSetChanged();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void update(LocalStatusBackgroundEvent event) {
        List<CarBillBean> datas = (List<CarBillBean>) event.getContent();
        CarLog.d(TAG, "LocalStatusBackgroundEvent " + datas.size());
        LocalStatusEvent local = new LocalStatusEvent();
        local.setContent(datas);
        EventProxy.post(local);
    }
}
