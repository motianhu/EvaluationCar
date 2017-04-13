package com.smona.app.evaluationcar.framework.provider;

import android.content.Context;

import com.smona.app.evaluationcar.data.bean.CarBillBean;
import com.smona.app.evaluationcar.data.bean.CarImageBean;
import com.smona.app.evaluationcar.data.bean.ImageMetaBean;
import com.smona.app.evaluationcar.data.bean.UploadTaskBean;
import com.smona.app.evaluationcar.framework.provider.dao.BaseDao;
import com.smona.app.evaluationcar.framework.provider.dao.DaoFactory;
import com.smona.app.evaluationcar.framework.provider.table.CarBillTable;
import com.smona.app.evaluationcar.framework.provider.table.CarImageTable;
import com.smona.app.evaluationcar.framework.provider.table.ImageMetaTable;
import com.smona.app.evaluationcar.util.CarLog;
import com.smona.app.evaluationcar.util.StatusUtils;

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


    //Car Image
    public List<CarImageBean> queryHttpImages(String carBillId) {
        BaseDao<CarImageBean> dao = DaoFactory.buildDaoEntry(mAppContext, DaoFactory.TYPE_IMAGE);
        String select = CarImageTable.CARBILLID + "=?";
        List<CarImageBean> list = dao.getResult(select, new String[]{carBillId}, CarImageTable.IMAGESEQNUM + " asc ");
        return list;
    }

    public List<CarImageBean> queryImages(int imageId) {
        BaseDao<CarImageBean> dao = DaoFactory.buildDaoEntry(mAppContext, DaoFactory.TYPE_IMAGE);
        String select = CarImageTable.IMAGEID + "=?";
        List<CarImageBean> list = dao.getResult(select, new String[]{imageId + ""}, CarImageTable.IMAGESEQNUM + " asc ");
        return list;
    }

    public List<CarImageBean> queryUpdateImages(String carBillId) {
        BaseDao<CarImageBean> dao = DaoFactory.buildDaoEntry(mAppContext, DaoFactory.TYPE_IMAGE);
        String select = CarImageTable.CARBILLID + " =? and " + CarImageTable.IMAGEUPDATE + " =? ";
        List<CarImageBean> list = dao.getResult(select, new String[]{carBillId, StatusUtils.IMAGE_UPDATE + ""}, CarImageTable.IMAGESEQNUM + " asc ");
        return list;
    }


    public List<CarImageBean> queryImages(String imageClass, int imageId) {
        BaseDao<CarImageBean> dao = DaoFactory.buildDaoEntry(mAppContext, DaoFactory.TYPE_IMAGE);
        String select = CarImageTable.IMAGEID + "=? and " + CarImageTable.IMAGECLASS + "=?";
        List<CarImageBean> list = dao.getResult(select, new String[]{imageId + "", imageClass}, CarImageTable.IMAGESEQNUM + " asc ");
        return list;
    }

    public List<CarImageBean> queryImages(String imageClass, String carBillId) {
        BaseDao<CarImageBean> dao = DaoFactory.buildDaoEntry(mAppContext, DaoFactory.TYPE_IMAGE);
        String select = CarImageTable.CARBILLID + "=? and " + CarImageTable.IMAGECLASS + "=? ";
        List<CarImageBean> list = dao.getResult(select, new String[]{carBillId, imageClass}, CarImageTable.IMAGESEQNUM + " asc ");
        return list;
    }

    public CarImageBean queryImageClassForImageId(int imageId, String imageClass, int imageSeqNum) {
        BaseDao<CarImageBean> dao = DaoFactory.buildDaoEntry(mAppContext, DaoFactory.TYPE_IMAGE);
        String select = CarImageTable.IMAGEID + "=? and " + CarImageTable.IMAGECLASS + "=? and " + CarImageTable.IMAGESEQNUM + "=?";
        List<CarImageBean> list = dao.getResult(select, new String[]{imageId + "", imageClass, imageSeqNum + ""}, CarImageTable.IMAGESEQNUM + " asc ");
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    public CarImageBean queryImageClassForCarBillId(String carBillId, String imageClass, int imageSeqNum) {
        BaseDao<CarImageBean> dao = DaoFactory.buildDaoEntry(mAppContext, DaoFactory.TYPE_IMAGE);
        String select = CarImageTable.CARBILLID + "=? and " + CarImageTable.IMAGECLASS + "=? and " + CarImageTable.IMAGESEQNUM + "=?";
        List<CarImageBean> list = dao.getResult(select, new String[]{carBillId, imageClass, imageSeqNum + ""}, CarImageTable.IMAGESEQNUM + " asc ");
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    public boolean insertCarImage(CarImageBean bean) {
        BaseDao<CarImageBean> dao = DaoFactory.buildDaoEntry(mAppContext, DaoFactory.TYPE_IMAGE);
        return dao.insertItem(bean);
    }

    public void updateCarImage(CarImageBean carBill) {
        BaseDao<CarImageBean> dao = DaoFactory.buildDaoEntry(mAppContext, DaoFactory.TYPE_IMAGE);
        dao.updateItem(carBill);
    }

    public void batchUpdateCarImage(List<CarImageBean> dataList) {
        BaseDao<CarImageBean> dao = DaoFactory.buildDaoEntry(mAppContext, DaoFactory.TYPE_IMAGE);
        dao.updateList(dataList);
    }

    //ImageMeta
    public ImageMetaBean queryImageMeta(String imageClass, int imageSeqNum) {
        BaseDao<ImageMetaBean> dao = DaoFactory.buildDaoEntry(mAppContext, DaoFactory.TYPE_IMAGEMETA);
        String where = ImageMetaTable.IMAGECLASS + "=? and " + ImageMetaTable.IMAGESEQNUM + "=?";
        String[] whereArgs = new String[]{imageClass, imageSeqNum + ""};
        List<ImageMetaBean> list = dao.getResult(where, whereArgs, CarImageTable.IMAGESEQNUM + " asc ");
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    //CarBill
    public CarBillBean queryCarBill(String carBillId) {
        BaseDao<CarBillBean> dao = DaoFactory.buildDaoEntry(mAppContext, DaoFactory.TYPE_CARBILL);
        String where = CarBillTable.CARBILLID + "=?";
        String[] whereArgs = new String[]{carBillId};
        List<CarBillBean> list = dao.getResult(where, whereArgs, null);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    public List<CarBillBean> queryLocalCarbill(int curPage, int pageSize) {
        BaseDao<CarBillBean> dao = DaoFactory.buildDaoEntry(mAppContext, DaoFactory.TYPE_CARBILL);
        String select = CarBillTable.CARBILLID + " ='' and " + CarBillTable.IMAGEID + " >0";
        String order = CarBillTable.CREATETIME + " desc limit " + (curPage - 1) * pageSize + "," + pageSize;
        List<CarBillBean> list = dao.getResult(select, null, order);
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

    public int queryLocalBillCount() {
        BaseDao<CarBillBean> dao = DaoFactory.buildDaoEntry(mAppContext, DaoFactory.TYPE_CARBILL);
        String select = CarBillTable.CARBILLID + " is null and " + CarBillTable.IMAGEID + ">0";
        List<CarBillBean> list = dao.getResult(select, null, null);
        CarLog.d("DBDelegator", "queryLocalBillCount " + list.size());
        if (list != null && list.size() > 0) {
            return list.size();
        } else {
            return 0;
        }
    }

    public void updateCarBill(CarBillBean carBill) {
        BaseDao<CarBillBean> dao = DaoFactory.buildDaoEntry(mAppContext, DaoFactory.TYPE_CARBILL);
        dao.updateItem(carBill);
    }

    public boolean insertCarBill(CarBillBean bean) {
        BaseDao<CarBillBean> dao = DaoFactory.buildDaoEntry(mAppContext, DaoFactory.TYPE_CARBILL);
        return dao.insertItem(bean);
    }

    public List<CarBillBean> queryCarBillInUpload() {
        BaseDao<CarBillBean> dao = DaoFactory.buildDaoEntry(mAppContext, DaoFactory.TYPE_CARBILL);
        String where = CarBillTable.UPLOADStATUS + "=?";
        String[] whereArgs = new String[]{StatusUtils.BILL_UPLOAD_STATUS_UPLOADING + ""};
        String orderBy = CarBillTable.MODIFYTIME + " desc ";
        List<CarBillBean> list = dao.getResult(where, whereArgs, orderBy);
        return list;
    }

    //Image Meta
    public boolean insertImageMeta(ImageMetaBean bean) {
        BaseDao<ImageMetaBean> dao = DaoFactory.buildDaoEntry(mAppContext, DaoFactory.TYPE_IMAGEMETA);
        return dao.insertItem(bean);
    }

    public void updateImageMeta(ImageMetaBean bean) {
        BaseDao<ImageMetaBean> dao = DaoFactory.buildDaoEntry(mAppContext, DaoFactory.TYPE_IMAGEMETA);
        dao.updateItem(bean);
    }


    //Upload Task
    public List<UploadTaskBean> queryUploadTask(String carBillId) {
        BaseDao<UploadTaskBean> dao = DaoFactory.buildDaoEntry(mAppContext, DaoFactory.TYPE_UPLOADTASK);
        String select = CarImageTable.CARBILLID + "=" + carBillId;
        List<UploadTaskBean> list = dao.getResult(select, null, null);
        return list;
    }


    //AUTO MAX ID
    public int getDBMaxId() {
        BaseDao<CarImageBean> dao = DaoFactory.buildDaoEntry(mAppContext, DaoFactory.TYPE_IMAGE);
        List<CarImageBean> list = dao.getResult(null, null, " imageId desc ");
        if (list != null && list.size() > 1) {
            return list.get(0).imageId;
        }
        return 0;
    }

}
