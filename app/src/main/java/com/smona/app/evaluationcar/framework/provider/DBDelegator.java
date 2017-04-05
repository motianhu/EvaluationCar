package com.smona.app.evaluationcar.framework.provider;

import android.content.ContentValues;
import android.content.Context;

import com.smona.app.evaluationcar.data.bean.CarBillBean;
import com.smona.app.evaluationcar.data.bean.CarImageBean;
import com.smona.app.evaluationcar.data.bean.ImageMetaBean;
import com.smona.app.evaluationcar.data.bean.UploadTaskBean;
import com.smona.app.evaluationcar.framework.provider.dao.BaseDao;
import com.smona.app.evaluationcar.framework.provider.dao.DaoFactory;
import com.smona.app.evaluationcar.framework.provider.table.CarBillTable;
import com.smona.app.evaluationcar.framework.provider.table.CarImageTable;
import com.smona.app.evaluationcar.util.CarLog;

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

    public List<CarImageBean> queryHttpImages(String carBillId) {
        BaseDao<CarImageBean> dao = DaoFactory.buildDaoEntry(mAppContext, DaoFactory.TYPE_IMAGE);
        String select = CarImageTable.CARBILLID + "=?";
        List<CarImageBean> list = dao.getResult(select, new String[]{carBillId}, CarImageTable.IMAGESEQNUM + " asc ");
        return list;
    }


    public List<CarImageBean> queryImages(String imageClass, int imageId) {
        BaseDao<CarImageBean> dao = DaoFactory.buildDaoEntry(mAppContext, DaoFactory.TYPE_IMAGE);
        String select = CarImageTable.IMAGEID + "=? and " + CarImageTable.IMAGECLASS + "=?";
        List<CarImageBean> list = dao.getResult(select, new String[]{imageId + "", imageClass}, CarImageTable.IMAGESEQNUM + " asc ");
        return list;
    }

    public List<CarImageBean> queryImages(String carBillId, String imageClass) {
        BaseDao<CarImageBean> dao = DaoFactory.buildDaoEntry(mAppContext, DaoFactory.TYPE_IMAGE);
        String select = CarImageTable.CARBILLID + "=? and " + CarImageTable.IMAGECLASS + "=? ";
        List<CarImageBean> list = dao.getResult(select, new String[]{carBillId, imageClass}, CarImageTable.IMAGESEQNUM + " asc ");
        return list;
    }

    public CarImageBean queryImages(int imageId, String imageClass, int imageSeqNum) {
        BaseDao<CarImageBean> dao = DaoFactory.buildDaoEntry(mAppContext, DaoFactory.TYPE_IMAGE);
        String select = CarImageTable.IMAGEID + "=? and " + CarImageTable.IMAGECLASS + "=? and " + CarImageTable.IMAGESEQNUM + "=?";
        List<CarImageBean> list = dao.getResult(select, new String[]{imageId + "", imageClass, imageSeqNum + ""}, CarImageTable.IMAGESEQNUM + " asc ");
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    public List<ImageMetaBean> queryImageMeta() {
        BaseDao<ImageMetaBean> dao = DaoFactory.buildDaoEntry(mAppContext, DaoFactory.TYPE_IMAGEMETA);
        List<ImageMetaBean> list = dao.getResult(null, null, null);
        return list;
    }

    public CarBillBean queryCarBill(String carBillId) {
        BaseDao<CarBillBean> dao = DaoFactory.buildDaoEntry(mAppContext, DaoFactory.TYPE_CARBILL);
        String select = CarImageTable.CARBILLID + "=" + carBillId;
        List<CarBillBean> list = dao.getResult(select, null, null);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    public List<CarBillBean> queryLocalCarbill() {
        BaseDao<CarBillBean> dao = DaoFactory.buildDaoEntry(mAppContext, DaoFactory.TYPE_CARBILL);
        String select = CarBillTable.CARBILLID + " is null and " + CarBillTable.IMAGEID + ">0";
        List<CarBillBean> list = dao.getResult(select, null, null);
        return list;
    }

    public CarBillBean queryLocalCarbill(int imageId) {
        BaseDao<CarBillBean> dao = DaoFactory.buildDaoEntry(mAppContext, DaoFactory.TYPE_CARBILL);
        String select = CarBillTable.CARBILLID + " is null and " + CarBillTable.IMAGEID + "=" + imageId;
        List<CarBillBean> list = dao.getResult(select, null, null);
        CarLog.d("DBDelegator", "queryLocalCarbill " + list.size());
        if (list != null && list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    public List<UploadTaskBean> queryUploadTask(String carBillId) {
        BaseDao<UploadTaskBean> dao = DaoFactory.buildDaoEntry(mAppContext, DaoFactory.TYPE_UPLOADTASK);
        String select = CarImageTable.CARBILLID + "=" + carBillId;
        List<UploadTaskBean> list = dao.getResult(select, null, null);
        return list;
    }

    public boolean insertCarBill(CarBillBean bean) {
        BaseDao<CarBillBean> dao = DaoFactory.buildDaoEntry(mAppContext, DaoFactory.TYPE_CARBILL);
        return dao.insertItem(bean);
    }


    public boolean insertCarImage(CarImageBean bean) {
        BaseDao<CarImageBean> dao = DaoFactory.buildDaoEntry(mAppContext, DaoFactory.TYPE_IMAGE);
        return dao.insertItem(bean);
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
