package com.smona.app.evaluationcar.ui.status;

import android.content.Context;
import android.util.AttributeSet;

import com.smona.app.evaluationcar.data.bean.CarBillBean;
import com.smona.app.evaluationcar.data.event.PassStatusEvent;
import com.smona.app.evaluationcar.util.CarLog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * Created by motianhu on 2/28/17.
 */

public class PassStatusListView extends StatusListView {
    private static final String TAG = PassStatusListView.class.getSimpleName();


    public PassStatusListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void update(PassStatusEvent event) {
        CarLog.d(TAG, "PassStatusEvent " + event);
        List<CarBillBean> datas = (List<CarBillBean>) event.getContent();
        mAdapter.update(datas);
        mAdapter.notifyDataSetChanged();
    }
}
