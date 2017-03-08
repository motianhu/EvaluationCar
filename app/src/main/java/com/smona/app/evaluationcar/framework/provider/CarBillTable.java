package com.smona.app.evaluationcar.framework.provider;

/**
 * Created by motianhu on 3/8/17.
 */

public class CarBillTable extends BaseCarColumns {
    public static final String TABLE_NAME = "carbill";

    public static final String CARBILLID = "carBillId";
    public static final String CREATETIME = "createTime";
    public static final String UPDATETIME = "updateTime";
    public static final String CPTIME = "cpTime";
    public static final String ZPTIME = "zpTime";
    public static final String GPTIME = "zsTime";
    public static final String BILLSTATUS = "billStatus";
    public static final String PRICE = "price";
    public static final String DESCRIPTION = "description";

    private static volatile CarBillTable sInstance = null;

    private CarBillTable() {
        super();
    }

    public synchronized static CarBillTable getInstance() {
        if (sInstance == null) {
            sInstance = new CarBillTable();
        }
        return sInstance;
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public String createTableSql() {
        return "CREATE TABLE " + TABLE_NAME + "(" + _ID
                + " INTEGER PRIMARY KEY, " + CARBILLID + " TEXT , "
                + CREATETIME + " TEXT, " + UPDATETIME
                + " INTEGER, " + CPTIME + " TEXT, " + ZPTIME + " TEXT, "
                + GPTIME + " TEXT, " + BILLSTATUS + " INTEGER, "
                + DESCRIPTION + " TEXT, "
                + PRICE + " TEXT " + ")";
    }

    @Override
    public String updateTableSql() {
        return null;
    }
}
