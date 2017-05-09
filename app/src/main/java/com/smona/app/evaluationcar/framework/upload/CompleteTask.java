package com.smona.app.evaluationcar.framework.upload;

import com.smona.app.evaluationcar.business.ResponseCallback;
import com.smona.app.evaluationcar.data.bean.CarBillBean;
import com.smona.app.evaluationcar.framework.cache.DataDelegator;
import com.smona.app.evaluationcar.framework.provider.DBDelegator;
import com.smona.app.evaluationcar.util.CarLog;
import com.smona.app.evaluationcar.util.StatusUtils;

/**
 * Created by motianhu on 4/5/17.
 */

public class CompleteTask extends ActionTask {
    private static final String TAG = CompleteTask.class.getSimpleName();
    public CarBillBean carBill;

    public void startTask() {
        //前期如果有失败的,则不提交,同时进入下一个任务
        if (carBill == null || carBill.preSalePrice <= 0.0 || !mSuccess) {
            UploadTaskExecutor.getInstance().nextTask();
            return;
        } else {
            carBill.carBillId = mCarBillId;
            DataDelegator.getInstance().submitCarBill(userName, carBill, new ResponseCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    CarLog.d(TAG, "onSuccess result: " + result + ", carBill: " + carBill);
                    carBill.uploadStatus = StatusUtils.BILL_UPLOAD_STATUS_NONE;
                    DBDelegator.getInstance().updateCarBill(carBill);
                    UploadTaskExecutor.getInstance().nextTask();
                }

                @Override
                public void onFailed(String error) {
                    CarLog.d(TAG, "onError ex: " + error);
                    UploadTaskExecutor.getInstance().nextTask();
                }
            });
        }

    }
}
