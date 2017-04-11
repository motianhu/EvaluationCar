package com.smona.app.evaluationcar.framework.cache;

import android.text.TextUtils;

import com.smona.app.evaluationcar.business.HttpDelegator;
import com.smona.app.evaluationcar.business.ResponseCallback;
import com.smona.app.evaluationcar.business.param.BannerParam;
import com.smona.app.evaluationcar.business.param.CarbillParam;
import com.smona.app.evaluationcar.business.param.Params;
import com.smona.app.evaluationcar.business.param.UserParam;
import com.smona.app.evaluationcar.data.bean.CarBillBean;
import com.smona.app.evaluationcar.data.bean.CarImageBean;
import com.smona.app.evaluationcar.data.bean.ImageMetaBean;
import com.smona.app.evaluationcar.framework.provider.DBDelegator;
import com.smona.app.evaluationcar.framework.storage.DeviceStorageManager;
import com.smona.app.evaluationcar.util.FileUtils;
import com.smona.app.evaluationcar.util.MD5;
import com.smona.app.evaluationcar.util.UrlConstants;

import java.util.List;

/**
 * Created by Moth on 2017/4/6.
 */

public class DataDelegator {
    private static final String TAG = DataDelegator.class.getSimpleName();
    private static final String CHARSET = "UTF-8";

    private volatile static DataDelegator sInstance;

    private DataDelegator() {
    }

    public static DataDelegator getInstance() {
        if (sInstance == null) {
            sInstance = new DataDelegator();
        }
        return sInstance;
    }

    private boolean checkCacheExit(String url) {
        return FileUtils.isFileExist(getFilePathByUrl(url));
    }

    private String getFilePathByUrl(String url) {
        String md5 = MD5.getMD5(url);
        String allPath = DeviceStorageManager.getInstance().getMd5Path() + md5;
        return allPath;
    }

    public boolean clearOldCacheByUrl(String url) {
        boolean result = FileUtils.deleteFile(getFilePathByUrl(url));
        return result;
    }

    public void saveNewCacheByUrl(Params params, String content) {
        if (params instanceof UserParam) {
            String url = UrlConstants.getInterface(UrlConstants.CHECK_USER);
            clearOldCacheByUrl(url);
            FileUtils.writeFile(getFilePathByUrl(url), content, false);
        } else {

        }
    }

    public String loadCacheByUrl(String url) {
        if (checkCacheExit(url)) {
            return FileUtils.readFile(getFilePathByUrl(url), CHARSET).toString();
        } else {
            return null;
        }
    }

    public String loadUserInfo(String url) {
        if (checkCacheExit(url)) {
            return FileUtils.readFile(getFilePathByUrl(url), CHARSET).toString();
        } else {
            return null;
        }
    }

    public void checkUser(Params params, ResponseCallback callback) {
        if (params instanceof UserParam) {
            String url = UrlConstants.getInterface(UrlConstants.CHECK_USER);
            boolean cache = checkCacheExit(url);
            if (cache) {
                String cacheData = loadCacheByUrl(url);
                if (!TextUtils.isEmpty(cacheData)) {
                    CacheDelegator.getInstance().checkUser(cacheData, callback);
                    return;
                }
            }
            HttpDelegator.getInstance().checkUser((UserParam) params, callback);
        } else {
            callback.onFailed("params not UserParam!");
        }
    }

    public void requestBanner(Params params, ResponseCallback callback) {
        if (params instanceof BannerParam) {
            String url = UrlConstants.getInterface(UrlConstants.QUERY_NEWS_LATEST);
            boolean cache = checkCacheExit(url);
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
            boolean cache = checkCacheExit(url);
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
        boolean cache = checkCacheExit(url);
        if (cache) {
            CacheDelegator.getInstance().queryOperationDesc(callback);
        } else {
            HttpDelegator.getInstance().queryOperationDesc(callback);
        }
    }

    public void requestCarbillCount(String userName, ResponseCallback<String> callback) {
        String url = UrlConstants.getInterface(UrlConstants.QUERY_CARBILL_COUNT);
        boolean cache = checkCacheExit(url);
        if (cache) {
            CacheDelegator.getInstance().queryCarbillCount(callback);
        } else {
            HttpDelegator.getInstance().queryCarbillCount(userName, callback);
        }
    }

    public void requestNotice(ResponseCallback<String> callback) {
        String url = UrlConstants.getInterface(UrlConstants.QUERY_NEWS_LATEST);
        boolean cache = checkCacheExit(url);
        if (cache) {
            CacheDelegator.getInstance().requestNotice(callback);
        } else {
            HttpDelegator.getInstance().requestNotice(callback);
        }
    }

    public ImageMetaBean requestImageMeta(String imageClass, int imageSeqNum) {
        return DBDelegator.getInstance().queryImageMeta(imageClass, imageSeqNum);
    }
}
