package com.smona.app.evaluationcar.framework.upload;

import android.text.TextUtils;

import com.smona.app.evaluationcar.business.HttpProxy;
import com.smona.app.evaluationcar.business.ResonpseCallback;
import com.smona.app.evaluationcar.data.bean.CarImageBean;
import com.smona.app.evaluationcar.util.CarLog;

public class ImageTask extends ActionTask {
    private static final String TAG = ImageTask.class.getSimpleName();
    public CarImageBean carImageBean;

    public void startTask() {
        if(carImageBean == null || !TextUtils.isEmpty(carImageBean.imageRemoteUrl)) {
            nextTask(mCarBillId);
        } else {
            carImageBean.carBillId = mCarBillId;
            HttpProxy.getInstance().uploadImage(userName, carImageBean, new ResonpseCallback<String>(){

                @Override
                public void onSuccess(String result) {
                    CarLog.d(TAG, "onSuccess result: " + result + "; carImageBean: " + carImageBean);
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
