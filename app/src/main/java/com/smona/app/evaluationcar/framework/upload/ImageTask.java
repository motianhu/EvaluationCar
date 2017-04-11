package com.smona.app.evaluationcar.framework.upload;

import com.smona.app.evaluationcar.business.ResponseCallback;
import com.smona.app.evaluationcar.data.bean.CarImageBean;
import com.smona.app.evaluationcar.data.model.ResNormal;
import com.smona.app.evaluationcar.framework.cache.DataDelegator;
import com.smona.app.evaluationcar.framework.json.JsonParse;
import com.smona.app.evaluationcar.framework.provider.DBDelegator;
import com.smona.app.evaluationcar.util.CarLog;
import com.smona.app.evaluationcar.util.StatusUtils;

public class ImageTask extends ActionTask {
    private static final String TAG = ImageTask.class.getSimpleName();
    public CarImageBean carImageBean;

    public void startTask() {
        if (carImageBean == null) {
            nextTask(mCarBillId);
        } else if(carImageBean.imageUpdate == StatusUtils.IMAGE_UPDATE) {
            carImageBean.carBillId = mCarBillId;
            DataDelegator.getInstance().uploadImage(userName, carImageBean, new ResponseCallback<String>() {
                @Override
                public void onSuccess(String content) {
                    CarLog.d(TAG, "onSuccess mCarBillId=" + mCarBillId + ", result: " + content + "; carImageBean: " + carImageBean);
                    ResNormal resModel = JsonParse.parseJson(content, ResNormal.class);
                    if (resModel.success) {
                        carImageBean.imagePath = resModel.object;
                        carImageBean.imageThumbPath = resModel.object;
                        carImageBean.imageUpdate = StatusUtils.IMAGE_DEFAULT;
                        DBDelegator.getInstance().updateCarImage(carImageBean);
                    }
                    nextTask(mCarBillId);
                }

                @Override
                public void onFailed(String error) {
                    CarLog.d(TAG, "onError ex: " + error);
                    nextTask(mCarBillId);
                }
            });
        } else {
            carImageBean.carBillId = mCarBillId;
            DataDelegator.getInstance().uploadImage(userName, carImageBean, new ResponseCallback<String>() {

                @Override
                public void onSuccess(String content) {
                    CarLog.d(TAG, "onSuccess mCarBillId=" + mCarBillId + ", result: " + content + "; carImageBean: " + carImageBean);
                    ResNormal resModel = JsonParse.parseJson(content, ResNormal.class);
                    if (resModel.success) {
                        carImageBean.imagePath = resModel.object;
                        carImageBean.imageThumbPath = resModel.object;
                        DBDelegator.getInstance().updateCarImage(carImageBean);
                    }
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
