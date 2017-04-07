package com.smona.app.evaluationcar.framework.cache;

import com.smona.app.evaluationcar.business.ResponseCallback;
import com.smona.app.evaluationcar.business.param.BannerParam;
import com.smona.app.evaluationcar.business.param.UserParam;
import com.smona.app.evaluationcar.framework.IProxy;
/**
 * Created by Moth on 2017/3/15.
 */

public class CacheDelegator implements IProxy {
    private static final String TAG = CacheDelegator.class.getSimpleName();

    private volatile static CacheDelegator sInstance;

    private CacheDelegator() {
    }

    public static CacheDelegator getInstance() {
        if (sInstance == null) {
            sInstance = new CacheDelegator();
        }
        return sInstance;
    }

    private long getLastSuccessTime() {
        return 1;
    }

    private void putLastSuccessTime() {

    }

    private boolean isOverTime() {
        return false;
    }


    public void checkUser(UserParam params, ResponseCallback callback) {

    }

    public void requestBanner(BannerParam params, ResponseCallback callback) {

    }

    public void requestLatestNews(BannerParam params, ResponseCallback callback) {

    }

    public void requestNews(BannerParam params, ResponseCallback callback) {

    }

    public void queryOperationDesc(ResponseCallback<String> callback) {

    }
}
