package com.smona.app.evaluationcar.ui.status;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.smona.app.evaluationcar.R;
import com.smona.app.evaluationcar.data.CarBillInfo;
import com.smona.app.evaluationcar.ui.common.AbstractAdapter;
import com.smona.app.evaluationcar.util.ViewUtils;

import java.util.List;

/**
 * Created by motianhu on 2/28/17.
 */

public class StatusAdapter extends AbstractAdapter {

    private Object mLock = new Object();

    @Override
    public void update(List datas) {
        synchronized (mLock) {
            mDatas.clear();
            mDatas.addAll(datas);
        }
    }

    public StatusAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CarBillInfo carbill = (CarBillInfo)mDatas.get(position);
        if (convertView == null) {
            convertView = ViewUtils.inflater(mContext,
                    R.layout.status_list_item);
        }
        return convertView;
    }
}
