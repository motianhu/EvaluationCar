package com.smona.app.evaluationcar.framework.provider.table;

/**
 * Created by motianhu on 3/20/17.
 */

public class UploadTaskTable extends BaseTable {
    public static final String TABLE_NAME = "uploadtask";

    public static final String CARBILLID = "carBillId"; //对应单据ID
    public static final String IMAGECOUNT = "imageCount"; //
    public static final String UPLOADSTATUS = "uploadStatus"; //
    public static final String IMAGEUPLOADCOUNT = "imageUploadCount";  //
    public static final String CREATETIME = "createtime";  //

    private static volatile UploadTaskTable mInstance = null;

    private UploadTaskTable() {
        super();
    }

    public synchronized static UploadTaskTable getInstance() {
        if (mInstance == null) {
            mInstance = new UploadTaskTable();
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
                + IMAGECOUNT + " INTEGER, "
                + IMAGEUPLOADCOUNT + " INTEGER, "
                + CREATETIME + " TimeStamp NOT NULL DEFAULT CURRENT_TIMESTAMP, "
                + UPLOADSTATUS + " INTEGER "
                + ")";
    }

    @Override
    public String updateTableSql() {
        return null;
    }
}
