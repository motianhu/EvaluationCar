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

    public void checkUser(String userName, String password, ResonpseCallback callback) {
        RequestParams params = createParams(UrlConstants.CHECK_USER);
        params.addParameter("userName", userName);
        params.addParameter("password", password);
        x.http().get(params, callback);
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

    public void queryCarbillList(String userName, String status,int curPage, int pageSize, Callback.CommonCallback callback) {
        RequestParams params = createParams(UrlConstants.QUERY_CARBILL_LIST);
        params.addParameter("userName", userName);
        params.addParameter("status", status);
        params.addParameter("curPage",curPage);
        params.addParameter("pageSize", pageSize);
        x.http().get(params, callback);
    }

    public void getCarbillImages(String userName, String carBillId, Callback.CommonCallback callback) {
        RequestParams params = createParams(UrlConstants.QUERY_CARBILL_IMAGE);
        params.addParameter("userName", userName);
        params.addParameter("carBillId", carBillId);
        x.http().get(params, callback);
    }

    public void submitCarBill(String userName, CarBillBean carBill, Callback.CommonCallback callback) {
        RequestParams params = createParams(UrlConstants.SUBMIT_CARBILL);
        params.addParameter("userName",userName);
        params.addParameter("carBillId",carBill.carBillId);
        params.addParameter("preSalePrice",carBill.preSalePrice);
        params.addParameter("mark",carBill.mark);
        x.http().get(params, callback);
    }

    public void queryOperationDesc(Callback.CommonCallback callback) {
        RequestParams params = createParams(UrlConstants.QUERY_OPERATION_DESC);
        x.http().get(params, callback);
    }

    public void queryCarbillCount(String userName, Callback.CommonCallback callback) {
        RequestParams params = createParams(UrlConstants.QUERY_CARBILL_COUNT);
        params.addParameter("userName",userName);
        x.http().get(params, callback);
    }

    public void queryLatestNews(String classType, Callback.CommonCallback callback) {
        RequestParams params = createParams(UrlConstants.QUERY_NEWS_LATEST);
        params.addParameter("classType",classType);
        x.http().get(params, callback);
    }

    public void queryMoreNews(String classType, int curPage, int pageSize, Callback.CommonCallback callback) {
        RequestParams params = createParams(UrlConstants.QUERY_NEWS_MORE);
        params.addParameter("classType",classType);
        params.addParameter("curPage",curPage);
        params.addParameter("pageSize", pageSize);
        x.http().get(params, callback);
    }

    public void queryNewsDetail(int newsId, Callback.CommonCallback callback) {
        RequestParams params = createParams(UrlConstants.QUERY_NEWS_DETAIL);
        params.addParameter("newsId",newsId);
        x.http().get(params, callback);
    }

}
