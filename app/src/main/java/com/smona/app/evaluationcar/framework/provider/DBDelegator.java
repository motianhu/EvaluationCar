package com.smona.app.evaluationcar.framework.provider;

import android.content.ContentValues;
import android.content.Context;

import com.smona.app.evaluationcar.data.bean.CarBillBean;
import com.smona.app.evaluationcar.data.bean.CarImageBean;
import com.smona.app.evaluationcar.data.bean.ImageMetaBean;
import com.smona.app.evaluationcar.data.bean.UploadTaskBean;
import com.smona.app.evaluationcar.framework.provider.dao.BaseDao;
import com.smona.app.evaluationcar.framework.provider.dao.DaoFactory;
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

    public List<CarImageBean> queryImages(String carBillId) {
        BaseDao<CarImageBean> dao = DaoFactory.buildDaoEntry(mAppContext, DaoFactory.TYPE_IMAGE);
        String select = CarImageTable.CARBILLID + "=" + carBillId;
        List<CarImageBean> list = dao.getResult(select, null, null);
        return list;
    }

    public List<ImageMetaBean> queryImageMeta() {
        BaseDao<ImageMetaBean> dao = DaoFactory.buildDaoEntry(mAppContext, DaoFactory.TYPE_IMAGEMETA);
        List<ImageMetaBean> list = dao.getResult(null, null, null);
        return list;
    }

    public List<CarBillBean> queryCarBill(String carBillId) {
        BaseDao<CarBillBean> dao = DaoFactory.buildDaoEntry(mAppContext, DaoFactory.TYPE_CARBILL);
        String select = CarImageTable.CARBILLID + "=" + carBillId;
        List<CarBillBean> list = dao.getResult(select, null, null);
        return list;
    }

    public List<UploadTaskBean> queryUploadTask(String carBillId) {
        BaseDao<UploadTaskBean> dao = DaoFactory.buildDaoEntry(mAppContext, DaoFactory.TYPE_UPLOADTASK);
        String select = CarImageTable.CARBILLID + "=" + carBillId;
        List<UploadTaskBean> list = dao.getResult(select, null, null);
        return list;
    }

    public int getDBMaxId() {
        BaseDao<CarImageBean> dao = DaoFactory.buildDaoEntry(mAppContext, DaoFactory.TYPE_IMAGE);
        List<CarImageBean> list = dao.getResult(null, null, " imageId desc ");
        if (list != null && list.size() > 1) {
            return list.get(0).imageId;
        }
        return 0;
    }

}
