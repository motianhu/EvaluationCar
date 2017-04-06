package com.smona.app.evaluationcar.framework.upload;

import com.smona.app.evaluationcar.business.HttpProxy;
import com.smona.app.evaluationcar.business.ResonpseCallback;
import com.smona.app.evaluationcar.data.bean.CarBillBean;
import com.smona.app.evaluationcar.util.CarLog;

/**
 * Created by motianhu on 4/5/17.
 */

public class CompleteTask extends ActionTask {
    private static final String TAG = CompleteTask.class.getSimpleName();
    public CarBillBean carBill;

    public void startTask() {
        if (carBill == null || carBill.preSalePrice <= 0.0) {
            return;
        } else {
            carBill.carBillId = mCarBillId;
            HttpProxy.getInstance().submitCarBill(userName, carBill, new ResonpseCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        CarLog.d(TAG, "onSuccess result: " + result + ", carBill: " + carBill);
                        nextTask(mCarBillId);
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        CarLog.d(TAG, "onError ex: " + ex);
                        nextTask(mCarBillId);
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {
                        CarLog.d(TAG, "CancelledException  cex: " + cex);
                        nextTask(mCarBillId);
                    }

                    @Override
                    public void onFinished() {

                    }

            });
        }

    }
}
