package com.smona.app.evaluationcar.framework.provider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.smona.app.evaluationcar.framework.provider.table.CarImageTable;

import java.util.List;

/**
 * Created by motianhu on 3/20/17.
 */

public class DBDelegator {

    private static volatile DBDelegator sInstance;
    private Context mAppContext;

    private DBDelegator() {
    }

    public static DBDelegator getInstance() {
        if (sInstance == null) {
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

    public void loadImages(String carBillId, List<String> images) {
        Cursor cursor = null;
        try {
            cursor = mAppContext.getContentResolver().query(CarImageTable.getInstance().mContentUriNoNotify, null, CarImageTable.CARBILLID + "=?", new String[]{carBillId}, null);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        //mAppContext.getContentResolver().update();
    }

}
