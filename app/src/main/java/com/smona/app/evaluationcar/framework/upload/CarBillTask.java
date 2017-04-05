package com.smona.app.evaluationcar.framework.upload;

import android.text.TextUtils;

import com.smona.app.evaluationcar.business.HttpProxy;
import com.smona.app.evaluationcar.business.ResonpseCallback;
import com.smona.app.evaluationcar.data.bean.CarBillBean;
import com.smona.app.evaluationcar.framework.provider.DBDelegator;
import com.smona.app.evaluationcar.util.CarLog;

/**
 * Created by motianhu on 4/5/17.
 */

public class CarBillTask extends ActionTask {
    private static final String TAG = CarBillTask.class.getSimpleName();
    public int imageId;

    public void startTask() {
        if(TextUtils.isEmpty(mCarBillId)) {
            HttpProxy.getInstance().createCarBillId(new ResonpseCallback<String>() {

                @Override
                public void onSuccess(String result) {
                    CarLog.d(TAG, "onSuccess result: " + result);
                    mCarBillId = result.substring(1, result.length() - 1);
                    CarBillBean carBillBean = new CarBillBean();
                    carBillBean.carBillId = mCarBillId;
                    carBillBean.imageId = imageId;
                    DBDelegator.getInstance().updateCarBill(carBillBean);
                    nextTask(mCarBillId);
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    CarLog.d(TAG, "onError ex: " + ex);
                }

                @Override
                public void onCancelled(CancelledException cex) {
                    CarLog.d(TAG, "CancelledException  cex: " + cex);
                }

                @Override
                public void onFinished() {

                }
            });
        } else {
            nextTask(mCarBillId);
        }
    }
}
