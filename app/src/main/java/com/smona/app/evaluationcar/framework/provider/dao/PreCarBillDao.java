package com.smona.app.evaluationcar.framework.provider.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.smona.app.evaluationcar.data.bean.PreCarBillBean;
import com.smona.app.evaluationcar.framework.provider.table.PreCarBillTable;

import java.util.List;

/**
 * Created by motianhu on 3/21/17.
 */

public class PreCarBillDao extends BaseDao<PreCarBillBean> {

    public PreCarBillDao(Context context) {
        super(context);
    }

    @Override
    public void initTable() {
        mTable = PreCarBillTable.getInstance();
    }

    @Override
    public void deleteList(List<PreCarBillBean> itemInfoList) {

    }

    @Override
    public void deleteItem(PreCarBillBean itemInfo) {

    }

    @Override
    public void updateList(List<PreCarBillBean> itemInfoList) {

    }

    @Override
    public void updateItem(PreCarBillBean itemInfo) {

    }

    @Override
    public PreCarBillBean cursorToModel(Cursor cursor) {
        PreCarBillBean item = new PreCarBillBean();
        item.carBillId = getString(cursor, PreCarBillTable.CARBILLID);
        item.carColor = getString(cursor, PreCarBillTable.CARCOLOR);
        item.cardTime = getString(cursor, PreCarBillTable.CARDTIME);
        item.mileage = getString(cursor, PreCarBillTable.MILEAGE);
        item.carBrand = getString(cursor, PreCarBillTable.CARBRAND);
        item.carSet = getString(cursor, PreCarBillTable.CARSET);
        item.carType = getString(cursor, PreCarBillTable.CARTYPE);
        item.city = getString(cursor, PreCarBillTable.CITY);
        item.mark = getString(cursor, PreCarBillTable.MARK);
        item.createTime = getString(cursor, PreCarBillTable.CREATETIME);
        item.updateTime = getString(cursor, PreCarBillTable.UPDATETIME);
        return item;
    }

    @Override
    public ContentValues modelToContentValues(PreCarBillBean item) {
        ContentValues values = new ContentValues();
        values.put(PreCarBillTable.CARBILLID, item.carBillId);
        values.put(PreCarBillTable.CARCOLOR, item.carColor);
        values.put(PreCarBillTable.CARDTIME, item.cardTime);
        values.put(PreCarBillTable.MILEAGE, item.mileage);
        values.put(PreCarBillTable.CARBRAND, item.carBrand);
        values.put(PreCarBillTable.CARSET, item.carSet);
        values.put(PreCarBillTable.CARTYPE, item.carType);
        values.put(PreCarBillTable.CITY, item.city);
        values.put(PreCarBillTable.MARK, item.mark);
        values.put(PreCarBillTable.CREATETIME, item.createTime);
        values.put(PreCarBillTable.UPDATETIME, item.updateTime);
        return values;
    }
}