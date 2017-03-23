package com.smona.app.evaluationcar.ui.status;

import android.content.Context;
import android.util.AttributeSet;

import com.smona.app.evaluationcar.business.HttpProxy;
import com.smona.app.evaluationcar.data.bean.CarBillBean;
import com.smona.app.evaluationcar.data.event.StatusEvent;
import com.smona.app.evaluationcar.data.model.ResCarBill;
import com.smona.app.evaluationcar.data.model.ResModel;
import com.smona.app.evaluationcar.framework.event.EventProxy;
import com.smona.app.evaluationcar.framework.json.JsonParse;
import com.smona.app.evaluationcar.ui.common.base.BaseListView;
import com.smona.app.evaluationcar.util.CarLog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.common.Callback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by motianhu on 2/28/17.
 */

public class StatusListView extends BaseListView {

    private int mType;

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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void update(StatusEvent event) {
        List<CarBillBean> datas = (List<CarBillBean>) event.getContent();
        mAdapter.update(datas);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        CarLog.d(this, "onAttachedToWindow post");
        post();
    }


    private void post() {
        String status = "21,22,23,24";
        switch(mType) {
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
        }
        HttpProxy.getInstance().queryCarbillList("cy",status,1,10, new HttpProxy.ResonpseCallback<String>(){
            @Override
            public void onSuccess(String result) {
                CarLog.d(this, "StatusListView: result: " + result);
                StatusEvent event = new StatusEvent();
                Object content = createTest();
                event.setContent(content);
                EventProxy.post(event);
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

    }


    private Object createTest() {
        ArrayList<CarBillBean> carBillList = new ArrayList<CarBillBean>();
        for (int i = 0; i < 45; i++) {
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
