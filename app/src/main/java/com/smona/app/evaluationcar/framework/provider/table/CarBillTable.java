package com.smona.app.evaluationcar.framework.provider.table;

/**
 * Created by motianhu on 3/8/17.
 */

public class CarBillTable extends BaseTable {
    public static final String TABLE_NAME = "carbill";

    public static final String CARBILLID = "carBillId";
    public static final String CREATETIME = "createTime";
    public static final String MODIFYTIME = "modifyTime";
    public static final String BILLSTATUS = "billStatus";
    public static final String PRESALEPRICE = "preSalePrice";
    public static final String EVALUATEPRICE = "evaluatePrice";
    public static final String THUMBUrl = "thumbUrl";
    public static final String APPLYALLOPINION = "applyAllOpinion";
    public static final String MARK = "mark";
    public static final String IMAGEID = "imageId";

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
        return "CREATE TABLE " + TABLE_NAME + "("
                + _ID + " INTEGER PRIMARY KEY, "
                + CARBILLID + " TEXT , "
                + CREATETIME + " TEXT, "
                + MODIFYTIME + " TEXT, "
                + BILLSTATUS + " INTEGER, "
                + APPLYALLOPINION + " TEXT, "
                + MARK + " TEXT, "
                + THUMBUrl + " TEXT, "
                + IMAGEID + " INTEGER, "
                + EVALUATEPRICE + " DOUBLE, "
                + PRESALEPRICE + " DOUBLE "
                + ")";
    }

    @Override
    public String updateTableSql() {
        return null;
    }
}
