package com.smona.app.evaluationcar.ui.status;

import android.content.Context;
import android.util.AttributeSet;

import com.smona.app.evaluationcar.data.CarBillInfo;
import com.smona.app.evaluationcar.framework.event.EventProxy;
import com.smona.app.evaluationcar.ui.common.base.BaseListView;
import com.smona.app.evaluationcar.data.event.StatusEvent;
import com.smona.app.evaluationcar.util.CarLog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by motianhu on 2/28/17.
 */

public class StatusListView extends BaseListView {

    public StatusListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void init() {
        mAdapter = new StatusAdapter(getContext());
        this.setAdapter(mAdapter);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void update(StatusEvent event) {
        List<CarBillInfo> datas = (List<CarBillInfo>) event.getContent();
        mAdapter.update(datas);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        CarLog.d(this, "onAttachedToWindow post");
        post();
    }


    private void post() {
        StatusEvent event = new StatusEvent();
        Object content = createTest();
        event.setContent(content);
        EventProxy.post(event);
    }


    private Object createTest() {
        ArrayList<CarBillInfo> carBillList = new ArrayList<CarBillInfo>();
        for (int i = 0; i < 45; i++) {
            CarBillInfo carbill = new CarBillInfo(null);
            carbill.setId("NS201612021100" + i);
            carbill.setCreateTime("2016-12-01 12:11:00");
            carbill.setUpdateTime("2016-12-21 15:11:00");
            carbill.setTitle("" + i);
            carbill.setUrl("http://www.baidu.com");
            carbill.setImgurl("http://113.107.245.39:90/attachs/theme/wallpaper/hd/2016/07/995g7j41eegpfngasjs9vsi7m5/312x277/SD-G-RW-062108.jpg");
            carBillList.add(carbill);
        }
        return carBillList;
    }
}
