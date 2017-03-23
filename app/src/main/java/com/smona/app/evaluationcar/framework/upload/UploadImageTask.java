package com.smona.app.evaluationcar.framework.upload;

import com.smona.app.evaluationcar.data.bean.CarImageBean;
import com.smona.app.evaluationcar.data.item.UploadItem;
import com.smona.app.evaluationcar.util.CarLog;

import org.xutils.common.Callback;

public class UploadImageTask {
    public String userName;
    public CarImageBean carImageBean;
    public Callback.CommonCallback callback;
}
