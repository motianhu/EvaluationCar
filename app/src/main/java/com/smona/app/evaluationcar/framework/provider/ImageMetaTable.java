package com.smona.app.evaluationcar.framework.provider;

/**
 * Created by motianhu on 3/11/17.
 */

public class ImageMetaTable extends BaseCarColumns {
    public static final String TABLE_NAME = "imagemeta";

    private static final String IMAGEID = "imageId";
    private static final String GROUPNAME = "groupName"; //分组名
    private static final String LEAST = "least"; //拍照个数
    private static final String MOST = "most"; //拍照个数
    private static final String DISPLAYNAME = "displayName"; //水印名称
    private static final String NOTE = "note"; //水印提示
    private static final String MASKURL = "maskUrl";  //水印url
    private static final String TODOURI = "todoUrl"; //提示url
    private static final String TODO = "todo"; //提示内容

    private static volatile ImageMetaTable mInstance = null;

    private ImageMetaTable() {
        super();
    }

    public synchronized static ImageMetaTable getInstance() {
        if (mInstance == null) {
            mInstance = new ImageMetaTable();
        }
        return mInstance;
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public String createTableSql() {
        return "CREATE TABLE " + TABLE_NAME + "(" + _ID
                + " INTEGER PRIMARY KEY, "
                + IMAGEID + " INTEGER , "
                + GROUPNAME + " TEXT, "
                + LEAST + " INTEGER, "
                + DISPLAYNAME + " TEXT, "
                + NOTE + " TEXT, "
                + MOST + " INTEGER, "
                + MASKURL + " TEXT, "
                + TODOURI + " TEXT, "
                + TODO + " TEXT " + ")";
    }

    @Override
    public String updateTableSql() {
        return null;
    }
}
