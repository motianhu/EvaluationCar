package com.smona.app.evaluationcar.framework.provider.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.smona.app.evaluationcar.data.bean.ImageMetaBean;
import com.smona.app.evaluationcar.data.bean.ImageMetaBean;
import com.smona.app.evaluationcar.framework.provider.table.ImageMetaTable;

import java.util.List;

/**
 * Created by motianhu on 3/21/17.
 */

public class ImageMetaDao extends BaseDao<ImageMetaBean>  {

    public ImageMetaDao(Context context) {
        super(context);
    }

    @Override
    public void initTable() {
        mTable = ImageMetaTable.getInstance();
    }

    @Override
    public void deleteList(List<ImageMetaBean> itemInfoList) {

    }

    @Override
    public void deleteItem(ImageMetaBean itemInfo) {

    }

    @Override
    public void updateList(List<ImageMetaBean> itemInfoList) {

    }

    @Override
    public void updateItem(ImageMetaBean itemInfo) {

    }

    @Override
    public ImageMetaBean cursorToModel(Cursor cursor) {
        ImageMetaBean item = new ImageMetaBean();
        item.imageId = getInt(cursor, ImageMetaTable.IMAGEID);
        item.groupName = getString(cursor, ImageMetaTable.GROUPNAME);
        item.least = getInt(cursor, ImageMetaTable.LEAST);
        item.most = getInt(cursor, ImageMetaTable.MOST);
        item.displayName = getString(cursor, ImageMetaTable.DISPLAYNAME);
        item.note = getString(cursor, ImageMetaTable.NOTE);
        item.maskUrl = getString(cursor, ImageMetaTable.MASKURL);
        item.todoUrl = getString(cursor, ImageMetaTable.TODOURI);
        item.todo = getString(cursor, ImageMetaTable.TODO);
        return item;
    }

    @Override
    public ContentValues modelToContentValues(ImageMetaBean item) {
        ContentValues values = new ContentValues();
        values.put(ImageMetaTable.IMAGEID, item.imageId);
        values.put(ImageMetaTable.GROUPNAME, item.groupName);
        values.put(ImageMetaTable.LEAST, item.least);
        values.put(ImageMetaTable.MOST, item.most);
        values.put(ImageMetaTable.DISPLAYNAME, item.displayName);
        values.put(ImageMetaTable.NOTE, item.note);
        values.put(ImageMetaTable.MASKURL, item.maskUrl);
        values.put(ImageMetaTable.TODOURI, item.todoUrl);
        values.put(ImageMetaTable.TODO, item.todo);
        return values;
    }
}
