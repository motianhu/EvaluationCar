package com.smona.app.evaluationcar.framework.upload;

import android.text.TextUtils;
import android.widget.Toast;

import com.smona.app.evaluationcar.business.ResponseCallback;
import com.smona.app.evaluationcar.data.bean.CarBillBean;
import com.smona.app.evaluationcar.data.event.ToastEvent;
import com.smona.app.evaluationcar.data.event.background.LocalStatusSubEvent;
import com.smona.app.evaluationcar.data.event.background.StatisticsStatusSubEvent;
import com.smona.app.evaluationcar.framework.cache.DataDelegator;
import com.smona.app.evaluationcar.framework.event.EventProxy;
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
        if (carBill == null || carBill.preSalePrice <= 0.0 || !TextUtils.isEmpty(mMessage)) {
            String error = "上传失败,具体原因是: " + mMessage + " 没上传成功!";
            CarLog.d(TAG, "onSuccess  上传失败,具体原因是: " + error + " 没上传成功, carBill=" + carBill);
            postMessage(error);

            UploadTaskExecutor.getInstance().nextTask();
        } else {
            carBill.carBillId = mCarBillId;
            DataDelegator.getInstance().submitCarBill(userName, carBill, new ResponseCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    CarLog.d(TAG, "onSuccess result: " + result + ", carBill: " + carBill);
                    carBill.uploadStatus = StatusUtils.BILL_UPLOAD_STATUS_NONE;
                    if(carBill.status == 0) {
                        DBDelegator.getInstance().deleteCarbill(carBill);
                        LocalStatusSubEvent event = new LocalStatusSubEvent();
                        event.setTag(LocalStatusSubEvent.TAG_ADD_CARBILL);
                        EventProxy.post(event);
                        EventProxy.post(new StatisticsStatusSubEvent());
                    } else {
                        carBill.uploadStatus = StatusUtils.BILL_UPLOAD_STATUS_NONE;
                        DBDelegator.getInstance().updateCarBill(carBill);
                    }
                    UploadTaskExecutor.getInstance().nextTask();
                }

                @Override
                public void onFailed(String error) {
                    CarLog.d(TAG, "onError ex: " + error);
                    postMessage(error);

                    UploadTaskExecutor.getInstance().nextTask();
                }
            });
        }

    }

    private  void postMessage(String error) {
        ToastEvent event = new ToastEvent();
        event.message = error;
        EventProxy.post(event);
    }
}
