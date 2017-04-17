package com.smona.app.evaluationcar.framework.provider.table;

/**
 * Created by motianhu on 3/20/17.
 */

public class PreCarBillTable extends BaseTable {
    public static final String TABLE_NAME = "precarbill";

    public static final String CARBILLID = "carBillId"; //对应单据ID
    public static final String CARCOLOR = "carColor"; //
    public static final String CARDTIME = "cardTime";  //
    public static final String MILEAGE = "mileage"; //
    public static final String CARBRAND = "carBrand"; //
    public static final String CARSET = "carSet"; //
    public static final String CARTYPE = "carType"; //
    public static final String CITY = "city"; //
    public static final String MARK = "mark";  //
    public static final String CREATETIME = "createTime";  //
    public static final String UPDATETIME = "updateTime";  //


    private static volatile PreCarBillTable mInstance = null;

    private PreCarBillTable() {
        super();
    }

    public synchronized static PreCarBillTable getInstance() {
        if (mInstance == null) {
            mInstance = new PreCarBillTable();
        }
        return mInstance;
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public String createTableSql() {
        return "CREATE TABLE " + TABLE_NAME + "("
                + _ID + " INTEGER PRIMARY KEY, "
                + CARBILLID + " TEXT, "
                + CARBRAND + " TEXT, "
                + CARSET + " TEXT, "
                + CARTYPE + " TEXT, "
                + CARCOLOR + " TEXT, "
                + CITY + " TEXT, "
                + CARDTIME + " TEXT, "
                + MILEAGE + " TEXT, "
                + MARK + " TEXT, "
                + CREATETIME + " TimeStamp NOT NULL DEFAULT CURRENT_TIMESTAMP, "
                + UPDATETIME + " TimeStamp NOT NULL DEFAULT CURRENT_TIMESTAMP "
                + ")";
    }

    @Override
    public String updateTableSql() {
        return null;
    }
}
