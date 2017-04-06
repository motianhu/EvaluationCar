package com.smona.app.evaluationcar.framework.upload;

import android.text.TextUtils;

import com.smona.app.evaluationcar.business.HttpDelegator;
import com.smona.app.evaluationcar.business.ResponseCallback;
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
            HttpDelegator.getInstance().uploadImage(userName, carImageBean, new ResponseCallback<String>(){

                @Override
                public void onSuccess(String result) {
                    CarLog.d(TAG, "onSuccess result: " + result + "; carImageBean: " + carImageBean);
                    nextTask(mCarBillId);
                }

                @Override
                public void onFailed(String error) {
                    CarLog.d(TAG, "onError ex: " + error);
                    nextTask(mCarBillId);
                }
            });
        }
    }
}
