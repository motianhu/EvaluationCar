package com.smona.app.evaluationcar.framework.cache;

import android.text.TextUtils;

import com.smona.app.evaluationcar.business.HttpDelegator;
import com.smona.app.evaluationcar.business.ResponseCallback;
import com.smona.app.evaluationcar.business.param.BannerParam;
import com.smona.app.evaluationcar.business.param.CarbillParam;
import com.smona.app.evaluationcar.business.param.PageParam;
import com.smona.app.evaluationcar.business.param.Params;
import com.smona.app.evaluationcar.business.param.UserParam;
import com.smona.app.evaluationcar.data.bean.CarBillBean;
import com.smona.app.evaluationcar.data.bean.CarImageBean;
import com.smona.app.evaluationcar.data.bean.ImageMetaBean;
import com.smona.app.evaluationcar.data.bean.PreCarBillBean;
import com.smona.app.evaluationcar.framework.provider.DBDelegator;
import com.smona.app.evaluationcar.util.UrlConstants;

import java.util.List;

/**
 * Created by Moth on 2017/4/6.
 */

public class DataDelegator {
    private volatile static DataDelegator sInstance;

    private DataDelegator() {
    }

    public static DataDelegator getInstance() {
        if (sInstance == null) {
            sInstance = new DataDelegator();
        }
        return sInstance;
    }

    public void checkUser(Params params, ResponseCallback callback) {
        if (params instanceof UserParam) {
            UserParam user = (UserParam) params;
            String url = UrlConstants.getInterface(UrlConstants.CHECK_USER) + "?userName=" + user.userName;
            boolean cache = CacheDelegator.getInstance().checkCacheExit(url);
            if (cache) {
                String cacheData = CacheDelegator.getInstance().loadCacheByUrl(url);
                if (!TextUtils.isEmpty(cacheData)) {
                    CacheDelegator.getInstance().checkUser(cacheData, callback);
                    return;
                }
            }
            HttpDelegator.getInstance().checkUser(user, callback);
        } else {
            callback.onFailed("params not UserParam!");
        }
    }

    public void requestBanner(Params params, ResponseCallback callback) {
        if (params instanceof BannerParam) {
            String url = UrlConstants.getInterface(UrlConstants.QUERY_NEWS_LATEST);
            boolean cache = CacheDelegator.getInstance().checkCacheExit(url);
            if (cache) {
                CacheDelegator.getInstance().requestBanner((BannerParam) params, callback);
            } else {
                HttpDelegator.getInstance().requestLatestNews((BannerParam) params, callback);
            }
        } else {
            callback.onFailed("params not BannerParam!");
        }
    }

    public void requestNews(Params params, ResponseCallback callback) {
        if (params instanceof BannerParam) {
            String url = UrlConstants.getInterface(UrlConstants.QUERY_NEWS_LATEST);
            boolean cache = CacheDelegator.getInstance().checkCacheExit(url);
            if (cache) {
                CacheDelegator.getInstance().requestNews((BannerParam) params, callback);
            } else {
                HttpDelegator.getInstance().requestLatestNews((BannerParam) params, callback);
            }
        } else {
            callback.onFailed("params not BannerParam!");
        }
    }

    public void uploadImage(String userName, CarImageBean bean, ResponseCallback callback) {
        HttpDelegator.getInstance().uploadImage(userName, bean, callback);
    }

    public void submitCarBill(String userName, CarBillBean carBill, ResponseCallback callback) {
        HttpDelegator.getInstance().submitCarBill(userName, carBill, callback);
    }

    public List<CarBillBean> queryLocalCarbill(int curPage, int pageSize) {
        List<CarBillBean> dataList = DBDelegator.getInstance().queryLocalCarbill(curPage, pageSize);
        return dataList;
    }

    public void queryCarbillList(CarbillParam param, ResponseCallback<String> callback) {
        HttpDelegator.getInstance().queryCarbillList(param, callback);
    }

    public void requestImageMeta(ResponseCallback<String> callback) {
        String url = UrlConstants.getInterface(UrlConstants.QUERY_NEWS_LATEST);
        boolean cache = CacheDelegator.getInstance().checkCacheExit(url);
        if (cache) {
            CacheDelegator.getInstance().queryOperationDesc(callback);
        } else {
            HttpDelegator.getInstance().queryOperationDesc(callback);
        }
    }

    public void requestCarbillCount(String userName, ResponseCallback<String> callback) {
        String url = UrlConstants.getInterface(UrlConstants.QUERY_CARBILL_COUNT);
        boolean cache = CacheDelegator.getInstance().checkCacheExit(url);
        if (cache) {
            CacheDelegator.getInstance().queryCarbillCount(callback);
        } else {
            HttpDelegator.getInstance().queryCarbillCount(userName, callback);
        }
    }

    public void requestNotice(ResponseCallback<String> callback) {
        String url = UrlConstants.getInterface(UrlConstants.QUERY_NEWS_LATEST);
        boolean cache = CacheDelegator.getInstance().checkCacheExit(url);
        if (cache) {
            CacheDelegator.getInstance().requestNotice(callback);
        } else {
            HttpDelegator.getInstance().requestNotice(callback);
        }
    }

    public ImageMetaBean requestImageMeta(String imageClass, int imageSeqNum) {
        return DBDelegator.getInstance().queryImageMeta(imageClass, imageSeqNum);
    }

    public void requestUpgradeInfo(ResponseCallback<String> callback) {
        HttpDelegator.getInstance().requestUpgradeInfo(callback);
    }

    public void submitPreCallBill(String userName, PreCarBillBean bean, ResponseCallback<String> callback) {
        HttpDelegator.getInstance().submitPreCallBill(userName, bean, callback);
    }

    public void queryPreCarbillList(CarbillParam params, ResponseCallback<String> callback) {
        HttpDelegator.getInstance().queryPreCarbillList(params, callback);
    }

    public void queryPageElementLatest(ResponseCallback<String> callback) {
        HttpDelegator.getInstance().queryPageElementLatest(callback);
    }

    public void queryPageElementDetail(int pageId, ResponseCallback<String> callback) {
        HttpDelegator.getInstance().queryPageElementDetail(pageId, callback);
    }

    public void queryNewsDetail(int newsId, ResponseCallback callback) {
        HttpDelegator.getInstance().queryNewsDetail(newsId, callback);
    }

    public void queryMoreNews(String classType, PageParam page, ResponseCallback callback) {
        HttpDelegator.getInstance().queryMoreNews(classType, page, callback);
    }
}
