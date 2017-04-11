package com.smona.app.evaluationcar.business;

import android.app.Application;

import com.smona.app.evaluationcar.business.param.BannerParam;
import com.smona.app.evaluationcar.business.param.CarbillParam;
import com.smona.app.evaluationcar.business.param.UserParam;
import com.smona.app.evaluationcar.data.bean.CarBillBean;
import com.smona.app.evaluationcar.data.bean.CarImageBean;
import com.smona.app.evaluationcar.framework.IProxy;
import com.smona.app.evaluationcar.util.UrlConstants;

import org.xutils.x;

import java.io.File;

/**
 * Created by Moth on 2016/12/18.
 */

public class HttpDelegator implements IProxy {

    private volatile static HttpDelegator sInstance;

    private HttpDelegator() {
    }

    public static HttpDelegator getInstance() {
        if (sInstance == null) {
            sInstance = new HttpDelegator();
        }
        return sInstance;
    }

    public void init(Application app) {
        x.Ext.init(app);
    }

    private ReqParams createParams(int type) {
        String url = UrlConstants.getInterface(type);
        ReqParams params = new ReqParams(url);
        return params;
    }

    public void checkUser(UserParam userParam, ResponseCallback callback) {
        ReqParams params = createParams(UrlConstants.CHECK_USER);
        params.addParameter(UserParam.USERNAME, userParam.userName);
        params.addParameter(UserParam.PASSWORD, userParam.password);
        x.http().get(params, callback);
    }

    public void requestLatestNews(BannerParam bannerParam, ResponseCallback callback) {
        ReqParams params = createParams(UrlConstants.QUERY_NEWS_LATEST);
        params.addParameter(BannerParam.CLASSTYPE, bannerParam.classType);
        x.http().get(params, callback);
    }

    public void createCarBillId(ResponseCallback callback) {
        ReqParams params = createParams(UrlConstants.CREATE_CARBILLID);
        x.http().get(params, callback);
    }

    public void queryCarbillList(CarbillParam param, ResponseCallback callback) {
        ReqParams params = createParams(UrlConstants.QUERY_CARBILL_LIST);
        params.addParameter(CarbillParam.USERNAME, param.userName);
        params.addParameter(CarbillParam.STATUS, param.status);
        params.addParameter(CarbillParam.CURPAGE, param.curPage);
        params.addParameter(CarbillParam.PAGESIZE, param.pageSize);
        x.http().get(params, callback);
    }

    public void uploadImage(String createUser, CarImageBean bean, ResponseCallback callback) {
        ReqParams params = createParams(UrlConstants.UPLOAD_IMAGE);
        params.addParameter("createUser", createUser);
        params.addParameter("clientName", "Android");
        params.addParameter("carBillId", bean.carBillId);
        params.addParameter("imageSeqNum", bean.imageSeqNum);
        params.addParameter("imageClass", bean.imageClass);
        params.addBodyParameter("image", new File(bean.imageLocalUrl));
        x.http().post(params, callback);
    }

    public void submitCarBill(String userName, CarBillBean carBill, ResponseCallback callback) {
        ReqParams params = createParams(UrlConstants.SUBMIT_CARBILL);
        params.addParameter("userName", userName);
        params.addParameter("carBillId", carBill.carBillId);
        params.addParameter("preSalePrice", carBill.preSalePrice);
        params.addParameter("mark", carBill.mark);
        x.http().get(params, callback);
    }


    public void getCarbillImages(String userName, String carBillId, ResponseCallback callback) {
        ReqParams params = createParams(UrlConstants.QUERY_CARBILL_IMAGE);
        params.addParameter("userName", userName);
        params.addParameter("carBillId", carBillId);
        x.http().get(params, callback);
    }

    public void queryOperationDesc(ResponseCallback callback) {
        ReqParams params = createParams(UrlConstants.QUERY_OPERATION_DESC);
        x.http().get(params, callback);
    }

    public void queryCarbillCount(String userName, ResponseCallback callback) {
        ReqParams params = createParams(UrlConstants.QUERY_CARBILL_COUNT);
        params.addParameter("userName", userName);
        x.http().get(params, callback);
    }

    //Notice
    public void requestNotice(ResponseCallback callback) {
        ReqParams params = createParams(UrlConstants.QUERY_NEWS_MORE);
        params.addParameter("classType", "新闻公告");
        params.addParameter("curPage", 1);
        params.addParameter("pageSize", 10);
        x.http().get(params, callback);
    }


    public void queryMoreNews(String classType, int curPage, int pageSize, ResponseCallback callback) {
        ReqParams params = createParams(UrlConstants.QUERY_NEWS_MORE);
        params.addParameter("classType", classType);
        params.addParameter("curPage", curPage);
        params.addParameter("pageSize", pageSize);
        x.http().get(params, callback);
    }

    public void queryNewsDetail(int newsId, ResponseCallback callback) {
        ReqParams params = createParams(UrlConstants.QUERY_NEWS_DETAIL);
        params.addParameter("newsId", newsId);
        x.http().get(params, callback);
    }

    public void requestUpgradeInfo(ResponseCallback<String> callback) {
        ReqParams params = createParams(UrlConstants.QUERY_APP_UPGRADE);
        params.addParameter("clientName", "android");
        x.http().get(params, callback);
    }


}
