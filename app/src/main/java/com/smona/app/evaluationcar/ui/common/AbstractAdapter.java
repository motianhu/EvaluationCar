package com.smona.app.evaluationcar.ui.common;

import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by Moth on 2017/2/24.
 */

public abstract class AbstractAdapter extends BaseAdapter {
    protected List<Object>  mDatas;
    public  void setDatas(List<Object> datas) {
        mDatas = datas;
    }
}
