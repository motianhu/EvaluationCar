package com.smona.app.evaluationcar.framework.provider.table;

/**
 * Created by motianhu on 3/11/17.
 */

public class ImageMetaTable extends BaseTable {
    public static final String TABLE_NAME = "imagemeta";

    public static final String IMAGEID = "imageId";
    public static final String GROUPNAME = "groupName"; //分组名
    public static final String LEAST = "least"; //拍照个数
    public static final String MOST = "most"; //拍照个数
    public static final String DISPLAYNAME = "displayName"; //水印名称
    public static final String NOTE = "note"; //水印提示
    public static final String MASKURL = "maskUrl";  //水印url
    public static final String TODOURI = "todoUrl"; //提示url
    public static final String TODO = "todo"; //提示内容

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
                + MOST + " INTEGER, "
                + DISPLAYNAME + " TEXT, "
                + NOTE + " TEXT, "
                + MASKURL + " TEXT, "
                + TODOURI + " TEXT, "
                + TODO + " TEXT " + ")";
    }

    @Override
    public String updateTableSql() {
        return null;
    }
}
