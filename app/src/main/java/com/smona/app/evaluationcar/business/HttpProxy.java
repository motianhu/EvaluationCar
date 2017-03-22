package com.smona.app.evaluationcar.business;

import android.app.Application;

import com.smona.app.evaluationcar.data.bean.CarBillBean;
import com.smona.app.evaluationcar.data.bean.CarImageBean;
import com.smona.app.evaluationcar.framework.IProxy;
import com.smona.app.evaluationcar.framework.request.HomeDeletor;
import com.smona.app.evaluationcar.framework.upload1.UploadUtils;
import com.smona.app.evaluationcar.util.UrlConstants;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;

/**
 * Created by Moth on 2016/12/18.
 */

public class HttpProxy implements IProxy {

    private volatile static HttpProxy sInstance;

    private HttpProxy() {
    }

    public static HttpProxy getInstance() {
        if (sInstance == null) {
            sInstance = new HttpProxy();
        }
        return sInstance;
    }

    public void init(Application app) {
        x.Ext.init(app);
    }

    private RequestParams createParams(int type) {
        String url = UrlConstants.getInterface(type);
        RequestParams params = new RequestParams(url);
        return params;
    }

    public void getCarBillId(Callback.CommonCallback callback) {
        RequestParams params = createParams(UrlConstants.CREATE_CARBILLID);
        x.http().get(params, callback);
    }

    public void uploadImage(String createUser, CarImageBean bean, Callback.CommonCallback callback) {
        RequestParams params = createParams(UrlConstants.UPLOAD_IMAGE);
        params.addParameter("createUser",createUser);
        params.addParameter("clientName","Android");
        params.addParameter("carBillId",bean.carBillId);
        params.addParameter("imageSeqNum", bean.imageSeqNum);
        params.addParameter("imageClass", bean.imageClass);
        params.addBodyParameter("image", new File(bean.imageLocalUrl));
        UploadUtils.uploadMethod(params, callback);
    }

    public void requestImageMeta(Callback.CommonCallback callback) {
        RequestParams params = createParams(UrlConstants.QUERY_IMAGEMETA);
        x.http().get(params, callback);
    }

    public void submitCarBill(String userName, CarBillBean carBill, Callback.CommonCallback callback) {
        RequestParams params = createParams(UrlConstants.SUBMIT_CARBILL);
        params.addParameter("userName",userName);
        params.addParameter("carBillId",carBill.carBillId);
        params.addParameter("preSalePrice",carBill.price);
        params.addParameter("mark",carBill.description);
        x.http().get(params, callback);
    }
}
