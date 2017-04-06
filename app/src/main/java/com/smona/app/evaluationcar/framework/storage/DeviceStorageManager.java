package com.smona.app.evaluationcar.framework.storage;

import android.content.Context;
import android.os.Environment;

import com.smona.app.evaluationcar.util.CarLog;
import com.smona.app.evaluationcar.util.FileUtils;
import com.smona.app.evaluationcar.util.FolderContants;

/**
 * Created by Moth on 2017/4/6.
 */

public class DeviceStorageManager {
    private static final String TAG = DeviceStorageManager.class.getSimpleName();
    private volatile static DeviceStorageManager sInstance;
    private Context mAppContext;
    private String mAppCachePath;
    private String mPrefixPath;
    private String mThumbnailPath;
    private String mMd5Path;

    private DeviceStorageManager() {
    }

    public static DeviceStorageManager getInstance() {
        if (sInstance == null) {
            sInstance = new DeviceStorageManager();
        }
        return sInstance;
    }

    public void setContext(Context context) {
        mAppContext = context;
        mAppCachePath = context.getCacheDir().getAbsolutePath();
    }

    public void initPath() {
        mPrefixPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        String rootPath = mPrefixPath + "/" + FolderContants.ROOT;
        makeFolder(rootPath);
        mMd5Path = rootPath + "/" + FolderContants.MD5CACHE;
        boolean success = makeFolder(mMd5Path);
        CarLog.d(TAG, "success " + success + "; mMd5Path=" + mMd5Path);
        mThumbnailPath = rootPath + "/" + FolderContants.THUMBNAIL;
        success = makeFolder(mThumbnailPath);
        CarLog.d(TAG, "success " + success + "; mThumbnailPath="  +mThumbnailPath);
    }

    private static boolean makeFolder(String folder) {
        return FileUtils.makeFolders(folder);
    }

    public  String getMd5Path() {
        return mMd5Path;
    }

    public String getThumbnailPath() {
        return mThumbnailPath;
    }
}
