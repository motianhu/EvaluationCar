package com.smona.app.evaluationcar.framework.provider;

/**
 * Created by motianhu on 3/20/17.
 */

public class UploadTaskTable extends BaseCarColumns {
    public static final String TABLE_NAME = "uploadtask";

    private static final String CARBILLID = "carBillId"; //对应单据ID
    private static final String IMAGECOUNT = "imageCount"; //
    private static final String UPLOADSTATUS = "uploadStatus"; //
    private static final String IMAGEUPLOADCOUNT = "imageUploadCount";  //

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
                + UPLOADSTATUS + " INTEGER, "
                + ")";
    }

    @Override
    public String updateTableSql() {
        return null;
    }
}
