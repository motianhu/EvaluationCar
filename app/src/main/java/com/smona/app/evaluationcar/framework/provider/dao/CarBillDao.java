package com.smona.app.evaluationcar.framework.provider.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.smona.app.evaluationcar.data.bean.CarBillBean;
import com.smona.app.evaluationcar.framework.provider.table.CarBillTable;

import java.util.List;

/**
 * Created by motianhu on 3/21/17.
 */

public class CarBillDao extends BaseDao<CarBillBean> {

    public CarBillDao(Context context) {
        super(context);
    }

    @Override
    public void initTable() {

    }

    @Override
    public void deleteList(List<CarBillBean> itemInfoList) {

    }

    @Override
    public void deleteItem(CarBillBean itemInfo) {

    }

    @Override
    public void updateList(List<CarBillBean> itemInfoList) {

    }

    @Override
    public void updateItem(CarBillBean itemInfo) {

    }

    @Override
    public CarBillBean cursorToModel(Cursor cursor) {
        CarBillBean item = new CarBillBean();
        item.carBillId = getString(cursor, CarBillTable.CARBILLID);
        item.status = getInt(cursor, CarBillTable.BILLSTATUS);
        item.createTime = getString(cursor, CarBillTable.CREATETIME);
        item.updateTime = getString(cursor, CarBillTable.UPDATETIME);
        item.cpTime = getString(cursor, CarBillTable.CPTIME);
        item.zpTime = getString(cursor, CarBillTable.ZPTIME);
        item.gpTime = getString(cursor, CarBillTable.GPTIME);
        item.price = getDouble(cursor, CarBillTable.PRICE);
        item.thumbUrl = getString(cursor, CarBillTable.THUMBUrl);
        item.description = getString(cursor, CarBillTable.DESCRIPTION);
        return item;
    }

    @Override
    public ContentValues modelToContentValues(CarBillBean item) {
        ContentValues values = new ContentValues();
        values.put(CarBillTable.CARBILLID, item.carBillId);
        values.put(CarBillTable.BILLSTATUS, item.status);
        values.put(CarBillTable.CREATETIME, item.createTime);
        values.put(CarBillTable.UPDATETIME, item.updateTime);
        values.put(CarBillTable.CPTIME, item.cpTime);
        values.put(CarBillTable.ZPTIME, item.zpTime);
        values.put(CarBillTable.GPTIME, item.gpTime);
        values.put(CarBillTable.PRICE, item.price);
        values.put(CarBillTable.THUMBUrl, item.thumbUrl);
        values.put(CarBillTable.DESCRIPTION, item.description);
        return null;
    }
}
