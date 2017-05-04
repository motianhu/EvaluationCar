package com.smona.app.evaluationcar.framework.cache;

import com.smona.app.evaluationcar.business.ResponseCallback;
import com.smona.app.evaluationcar.business.param.BannerParam;
import com.smona.app.evaluationcar.framework.IProxy;
import com.smona.app.evaluationcar.framework.storage.DeviceStorageManager;
import com.smona.app.evaluationcar.util.FileUtils;
import com.smona.app.evaluationcar.util.MD5;

/**
 * Created by Moth on 2017/3/15.
 */

public class CacheDelegator implements IProxy {
    private static final String TAG = CacheDelegator.class.getSimpleName();
    private static final String CHARSET = "UTF-8";

    private volatile static CacheDelegator sInstance;

    private CacheDelegator() {
    }

    public static CacheDelegator getInstance() {
        if (sInstance == null) {
            sInstance = new CacheDelegator();
        }
        return sInstance;
    }

    public boolean checkCacheExit(String url) {
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

    public void saveNewCacheByUrl(String url, String content) {
        clearOldCacheByUrl(url);
        FileUtils.writeFile(getFilePathByUrl(url), content, false);
    }

    public String loadCacheByUrl(String url) {
        if (checkCacheExit(url)) {
            return FileUtils.readFile(getFilePathByUrl(url), CHARSET).toString();
        } else {
            return null;
        }
    }

    public void checkUser(String cacheData, ResponseCallback callback) {
        callback.onSuccess(cacheData);
    }

    public void requestBanner(BannerParam params, ResponseCallback callback) {

    }

    public void requestLatestNews(BannerParam params, ResponseCallback callback) {

    }

    public void requestNews(BannerParam params, ResponseCallback callback) {

    }

    public void queryOperationDesc(ResponseCallback<String> callback) {

    }

    public void queryCarbillCount(ResponseCallback<String> callback) {

    }

    public void requestNotice(ResponseCallback<String> callback) {

    }

    public void requestUpgradeInfo(ResponseCallback<String> callback) {

    }

    public void queryPageElementLatest(ResponseCallback<String> callback) {

    }
}
