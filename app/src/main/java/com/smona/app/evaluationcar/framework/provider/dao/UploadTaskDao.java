package com.smona.app.evaluationcar.framework.provider.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.smona.app.evaluationcar.data.bean.UploadTaskBean;
import com.smona.app.evaluationcar.framework.provider.table.UploadTaskTable;

import java.util.List;

/**
 * Created by motianhu on 3/21/17.
 */

public class UploadTaskDao extends BaseDao<UploadTaskBean> {

    public UploadTaskDao(Context context) {
        super(context);
    }

    @Override
    public void initTable() {
        mTable = UploadTaskTable.getInstance();
    }

    @Override
    public void deleteList(List<UploadTaskBean> itemInfoList) {

    }

    @Override
    public void deleteItem(UploadTaskBean itemInfo) {

    }

    @Override
    public void updateList(List<UploadTaskBean> itemInfoList) {

    }

    @Override
    public void updateItem(UploadTaskBean itemInfo) {

    }

    @Override
    public UploadTaskBean cursorToModel(Cursor cursor) {
        UploadTaskBean item = new UploadTaskBean();
        item.carBillId = getString(cursor, UploadTaskTable.CARBILLID);
        item.imageCount = getInt(cursor, UploadTaskTable.IMAGECOUNT);
        item.uploadStatus = getInt(cursor, UploadTaskTable.UPLOADSTATUS);
        item.imageUploadCount = getInt(cursor, UploadTaskTable.IMAGEUPLOADCOUNT);
        return item;
    }

    @Override
    public ContentValues modelToContentValues(UploadTaskBean item) {
        ContentValues values = new ContentValues();
        values.put(UploadTaskTable.CARBILLID, item.carBillId);
        values.put(UploadTaskTable.IMAGECOUNT, item.imageCount);
        values.put(UploadTaskTable.UPLOADSTATUS, item.uploadStatus);
        values.put(UploadTaskTable.IMAGEUPLOADCOUNT, item.imageUploadCount);
        return values;
    }
}