package com.smona.app.evaluationcar.framework.cache;

import com.smona.app.evaluationcar.business.HttpDelegator;
import com.smona.app.evaluationcar.business.ResponseCallback;
import com.smona.app.evaluationcar.business.param.BannerParam;
import com.smona.app.evaluationcar.business.param.Params;
import com.smona.app.evaluationcar.business.param.UserParam;
import com.smona.app.evaluationcar.framework.storage.DeviceStorageManager;
import com.smona.app.evaluationcar.util.CarLog;
import com.smona.app.evaluationcar.util.FileUtils;
import com.smona.app.evaluationcar.util.MD5;
import com.smona.app.evaluationcar.util.UrlConstants;

/**
 * Created by Moth on 2017/4/6.
 */

public class DataDelegator {
    private static final String TAG = DataDelegator.class.getSimpleName();

    private volatile static DataDelegator sInstance;

    private DataDelegator() {
    }

    public static DataDelegator getInstance() {
        if (sInstance == null) {
            sInstance = new DataDelegator();
        }
        return sInstance;
    }

    private static boolean checkCacheExit(String url) {
        return FileUtils.isFileExist(getFilePathByUrl(url));
    }

    private static String getFilePathByUrl(String url) {
        String md5 = MD5.getMD5(url);
        String allPath = DeviceStorageManager.getInstance().getMd5Path() + md5;
        CarLog.d(TAG, "getFilePathByUrl url = " + url + " allPath = "
                + allPath);
        return allPath;
    }

    public static boolean clearOldCacheByUrl(String url) {
        boolean result = FileUtils.deleteFile(getFilePathByUrl(url));
        CarLog.d(TAG, "clearOldCacheByUrl url = " + url + " result = "
                + result);
        return result;
    }

    public static void saveNewCacheByUrl(String url, String content) {
        FileUtils.writeFile(getFilePathByUrl(url), content, false);
    }


    public void checkUser(Params params, ResponseCallback callback) {
        if (params instanceof UserParam) {
            String url = UrlConstants.getInterface(UrlConstants.CHECK_USER);
            boolean cache = checkCacheExit(url);
            if (cache) {
                CacheDelegator.getInstance().checkUser((UserParam) params, callback);
            } else {
                HttpDelegator.getInstance().checkUser((UserParam) params, callback);
            }
        } else {
            callback.onFailed("params not UserParam!");
        }
    }

    public void requestBanner(Params params, ResponseCallback callback) {
        if (params instanceof UserParam) {
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
        if (params instanceof UserParam) {
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

}
