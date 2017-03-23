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
        item.modifyTime = getString(cursor, CarBillTable.MODIFYTIME);
        item.preSalePrice = getDouble(cursor, CarBillTable.PRESALEPRICE);
        item.thumbUrl = getString(cursor, CarBillTable.THUMBUrl);
        item.mark = getString(cursor, CarBillTable.MARK);
        item.evaluatePrice = getString(cursor, CarBillTable.EVALUATEPRICE);
        item.applyAllOpinion = getString(cursor, CarBillTable.APPLYALLOPINION);
        return item;
    }

    @Override
    public ContentValues modelToContentValues(CarBillBean item) {
        ContentValues values = new ContentValues();
        values.put(CarBillTable.CARBILLID, item.carBillId);
        values.put(CarBillTable.BILLSTATUS, item.status);
        values.put(CarBillTable.CREATETIME, item.createTime);
        values.put(CarBillTable.MODIFYTIME, item.modifyTime);
        values.put(CarBillTable.PRESALEPRICE, item.preSalePrice);
        values.put(CarBillTable.EVALUATEPRICE, item.evaluatePrice);
        values.put(CarBillTable.THUMBUrl, item.thumbUrl);
        values.put(CarBillTable.MARK, item.mark);
        values.put(CarBillTable.APPLYALLOPINION, item.applyAllOpinion);
        return values;
    }
}
