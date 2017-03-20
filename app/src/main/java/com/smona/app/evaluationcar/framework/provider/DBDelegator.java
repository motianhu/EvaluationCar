package com.smona.app.evaluationcar.framework.provider;

import android.content.ContentValues;
import android.content.Context;

/**
 * Created by motianhu on 3/20/17.
 */

public class DBDelegator {

    private static volatile  DBDelegator sInstance;
    private Context mAppContext;

    private DBDelegator() {
    }

    public static DBDelegator getInstance() {
        if(sInstance == null) {
            sInstance = new DBDelegator();
        }
        return sInstance;
    }

    public void init(Context context) {
        mAppContext = context;
    }

    public void updateUploadStatus(String carBillId, String remoteUrl) {
        ContentValues values = new ContentValues();
        //mAppContext.getContentResolver().update();
    }

    public void updateImageRemoteUrl(String carBillId, String remoteUrl) {
        ContentValues values = new ContentValues();
        //mAppContext.getContentResolver().update();
    }

}
