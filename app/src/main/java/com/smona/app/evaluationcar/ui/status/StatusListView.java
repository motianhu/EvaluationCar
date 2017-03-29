package com.smona.app.evaluationcar.ui.status;

import android.content.Context;
import android.util.AttributeSet;

import com.smona.app.evaluationcar.business.HttpProxy;
import com.smona.app.evaluationcar.data.bean.CarBillBean;
import com.smona.app.evaluationcar.data.event.AuditingStatusEvent;
import com.smona.app.evaluationcar.data.event.NotPassStatusEvent;
import com.smona.app.evaluationcar.data.event.PassStatusEvent;
import com.smona.app.evaluationcar.data.event.background.LocalStatusBackgroundEvent;
import com.smona.app.evaluationcar.framework.event.EventProxy;
import com.smona.app.evaluationcar.ui.common.base.BaseListView;
import com.smona.app.evaluationcar.util.CarLog;

import java.util.ArrayList;

/**
 * Created by motianhu on 2/28/17.
 */

public class StatusListView extends BaseListView {
    private static final String TAG = StatusListView.class.getSimpleName();

    protected int mType;

    public StatusListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void init() {
        mAdapter = new StatusAdapter(getContext());
        this.setAdapter(mAdapter);
    }

    public void setType(int type) {
        mType = type;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        CarLog.d(TAG, "onAttachedToWindow post " + mType);
        post();
    }

    private void post() {
        String status = "21,22,23,24";
        boolean isHttp = true;
        switch (mType) {
            case 0:
                isHttp = false;
                break;
            case 1:
                AuditingStatusEvent event = new AuditingStatusEvent();
                event.setContent(createTest(4));
                EventProxy.post(event);
                break;
            case 2:
                NotPassStatusEvent event1 = new NotPassStatusEvent();
                event1.setContent(createTest(7));
                EventProxy.post(event1);
                break;
            case 3:
                PassStatusEvent event2 = new PassStatusEvent();
                event2.setContent(createTest(3));
                EventProxy.post(event2);
                break;
        }
        if (isHttp) {
            HttpProxy.getInstance().queryCarbillList("cy", status, 1, 10, new HttpProxy.ResonpseCallback<String>() {
                @Override
                public void onSuccess(String result) {
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {

                }

                @Override
                public void onCancelled(CancelledException cex) {

                }

                @Override
                public void onFinished() {

                }
            });
        } else {
            EventProxy.post(new LocalStatusBackgroundEvent());
        }
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
}
