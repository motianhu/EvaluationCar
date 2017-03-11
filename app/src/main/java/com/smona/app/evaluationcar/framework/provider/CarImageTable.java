package com.smona.app.evaluationcar.framework.provider;

/**
 * Created by motianhu on 3/8/17.
 */

public class CarImageTable extends BaseCarColumns {
    public static final String TABLE_NAME = "carimage";

    private static final String IMAGEID = "imageId";
    private static final String CARBILLID = "carBillId"; //对应单据ID
    private static final String IMAGESEQNUM = "imageSeqNum"; //汽车图片当前序号
    private static final String IMAGECLASS = "imageClass"; //图片分类
    private static final String IMAGEURL = "imageUrl";  //服务器地址,有值则代表已上传成功
    private static final String IMAGEURI = "iamgeUri"; //本机地址

    private static volatile CarImageTable mInstance = null;

    private CarImageTable() {
        super();
    }

    public synchronized static CarImageTable getInstance() {
        if (mInstance == null) {
            mInstance = new CarImageTable();
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
                + IMAGEID + " INTEGER , "
                + CARBILLID + " TEXT, "
                + IMAGESEQNUM + " INTEGER, "
                + IMAGECLASS + " TEXT, "
                + IMAGEURL + " TEXT, "
                + IMAGEURI + " TEXT " + ")";
    }

    @Override
    public String updateTableSql() {
        return null;
    }
}
