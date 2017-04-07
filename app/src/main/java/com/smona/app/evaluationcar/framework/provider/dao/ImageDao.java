package com.smona.app.evaluationcar.framework.provider.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.smona.app.evaluationcar.data.bean.CarImageBean;
import com.smona.app.evaluationcar.framework.provider.table.CarImageTable;
import com.smona.app.evaluationcar.util.CarLog;

import java.util.List;

/**
 * Created by motianhu on 3/21/17.
 */

public class ImageDao extends BaseDao<CarImageBean> {
    private static final String TAG = ImageDao.class.getSimpleName();

    public ImageDao(Context context) {
        super(context);
    }

    @Override
    public void initTable() {
        mTable = CarImageTable.getInstance();
    }

    @Override
    public void deleteList(List<CarImageBean> itemInfoList) {

    }

    @Override
    public void deleteItem(CarImageBean itemInfo) {

    }

    @Override
    public void updateList(List<CarImageBean> itemInfoList) {

    }

    @Override
    public void updateItem(CarImageBean carImage) {
        String where = CarImageTable.IMAGEID + "=? and " + CarImageTable.IMAGESEQNUM + "=?";
        String[] whereArgs = new String[]{
                carImage.imageId + "", carImage.imageSeqNum + ""
        };
        int count = mContentResolver.update(mTable.mContentUriNoNotify,
                modelToContentValues(carImage), where, whereArgs);
        CarLog.d(TAG, "updateItem count=" + count);
    }

    @Override
    public CarImageBean cursorToModel(Cursor cursor) {
        CarImageBean item = new CarImageBean();
        item.imageId = getInt(cursor, CarImageTable.IMAGEID);
        item.carBillId = getString(cursor, CarImageTable.CARBILLID);
        item.imageClass = getString(cursor, CarImageTable.IMAGECLASS);
        item.imageSeqNum = getInt(cursor, CarImageTable.IMAGESEQNUM);
        item.imageLocalUrl = getString(cursor, CarImageTable.IMAGELOCALURL);
        item.imageRemoteUrl = getString(cursor, CarImageTable.IMAGEREMOTEURL);
        item.createTime = getString(cursor, CarImageTable.CREATETIME);
        item.updateTime = getString(cursor, CarImageTable.UPDATETIEM);
        return item;
    }

    @Override
    public ContentValues modelToContentValues(CarImageBean item) {
        ContentValues values = new ContentValues();
        values.put(CarImageTable.IMAGEID, item.imageId);
        values.put(CarImageTable.CARBILLID, item.carBillId);
        values.put(CarImageTable.IMAGECLASS, item.imageClass);
        values.put(CarImageTable.IMAGESEQNUM, item.imageSeqNum);
        values.put(CarImageTable.IMAGELOCALURL, item.imageLocalUrl);
        values.put(CarImageTable.IMAGEREMOTEURL, item.imageRemoteUrl);
        values.put(CarImageTable.CREATETIME, item.createTime);
        values.put(CarImageTable.UPDATETIEM, item.updateTime);
        return values;
    }
}
