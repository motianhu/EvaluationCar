package com.smona.app.evaluationcar.framework.provider;

//Gionee <Moth><2015-03-14> add for CR01454311 begin

import android.net.Uri;
import android.provider.BaseColumns;

public abstract class BaseCarColumns implements BaseColumns {
    protected static final String PARAMETER_NOTIFY = "notify";

    public String mTableName;
    public Uri mContentUriNotify;
    public Uri mContentUriNoNotify;

    protected BaseCarColumns() {
        mTableName = getTableName();
        mContentUriNotify = Uri.parse("content://" + DBConstants.AUTHORITY + "/" + mTableName);
        mContentUriNoNotify = Uri.parse("content://" + DBConstants.AUTHORITY + "/"
                + mTableName + "?" + PARAMETER_NOTIFY + "=false");
    }

    protected abstract String getTableName();
    protected abstract String createTableSql();
    protected abstract String updateTableSql();

    public String dropTableSql() {
        return "DROP TABLE IF EXISTS " + mTableName;
    }

    public String deleteTableSql() {
        return "DELETE TABLE " + mTableName;
    }


}
// Gionee <Moth><2015-03-14> add for CR01454311 end