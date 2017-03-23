package com.smona.app.evaluationcar.business;

import android.app.Application;

import com.smona.app.evaluationcar.data.bean.CarBillBean;
import com.smona.app.evaluationcar.data.bean.CarImageBean;
import com.smona.app.evaluationcar.framework.IProxy;
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

    public interface ResonpseCallback<T> extends Callback.CommonCallback<T>{}

    private RequestParams createParams(int type) {
        String url = UrlConstants.getInterface(type);
        RequestParams params = new RequestParams(url);
        return params;
    }

    public void getCarBillId(ResonpseCallback callback) {
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
        x.http().post(params, callback);
    }

    public void queryCarbillList(String userName, String status, Callback.CommonCallback callback) {
        RequestParams params = createParams(UrlConstants.QUERY_CARBILL_LIST);
        params.addParameter("userName", userName);
        params.addParameter("status", status);
        x.http().get(params, callback);
    }

    public void queryCarbillDetail(String userName, String carBillId, Callback.CommonCallback callback) {
        RequestParams params = createParams(UrlConstants.QUERY_CARBILL_DETAIL);
        params.addParameter("userName", userName);
        params.addParameter("carBillId", carBillId);
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
