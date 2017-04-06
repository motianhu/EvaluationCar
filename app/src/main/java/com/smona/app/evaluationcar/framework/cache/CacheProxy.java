package com.smona.app.evaluationcar.framework.cache;

import com.smona.app.evaluationcar.framework.IProxy;
import com.smona.app.evaluationcar.framework.storage.DeviceStorageManager;
import com.smona.app.evaluationcar.util.CarLog;
import com.smona.app.evaluationcar.util.FileUtils;
import com.smona.app.evaluationcar.util.MD5;

/**
 * Created by Moth on 2017/3/15.
 */

public class CacheProxy implements IProxy {
    private static final String TAG = CacheProxy.class.getSimpleName();
    private long getLastSuccessTime() {
        return 1;
    }

    private void putLastSuccessTime() {

    }

    private boolean isOverTime() {
        return false;
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
}
