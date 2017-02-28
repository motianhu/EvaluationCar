package com.smona.app.evaluationcar.ui.status;

import android.content.Context;
import android.util.AttributeSet;

import com.smona.app.evaluationcar.data.CarBillInfo;
import com.smona.app.evaluationcar.framework.event.EventProxy;
import com.smona.app.evaluationcar.ui.common.BaseListView;
import com.smona.app.evaluationcar.ui.common.event.StatusEvent;
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
            carbill.setId(i);
            carbill.setTitle("" + i);
            carbill.setUrl("http://www.baidu.com");
            carbill.setImgurl("http://assetsdl.gioneemobile.net/attachs/theme/subjectImage/201701/587c2e26cf05d.jpg");
            carBillList.add(carbill);
        }
        return carBillList;
    }
}
