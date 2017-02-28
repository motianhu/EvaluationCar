package com.smona.app.evaluationcar.ui.common;

import android.content.Context;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Moth on 2017/2/24.
 */

public abstract class AbstractAdapter<T> extends BaseAdapter {

    protected Context mContext;
    protected List<T> mDatas;

    public abstract void update(List<T> datas);

    public AbstractAdapter(Context context) {
        mDatas = new ArrayList<T>();
        mContext = context;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}
