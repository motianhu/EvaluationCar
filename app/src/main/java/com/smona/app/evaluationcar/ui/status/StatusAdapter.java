package com.smona.app.evaluationcar.ui.status;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.smona.app.evaluationcar.R;
import com.smona.app.evaluationcar.data.bean.CarBillBean;
import com.smona.app.evaluationcar.framework.imageloader.ImageLoaderProxy;
import com.smona.app.evaluationcar.ui.common.AbstractAdapter;
import com.smona.app.evaluationcar.ui.evaluation.EvaluationActivity;
import com.smona.app.evaluationcar.util.ActivityUtils;
import com.smona.app.evaluationcar.util.CacheContants;
import com.smona.app.evaluationcar.util.CarLog;
import com.smona.app.evaluationcar.util.ConstantsUtils;
import com.smona.app.evaluationcar.util.ViewUtil;

import java.util.List;

/**
 * Created by motianhu on 2/28/17.
 */

public class StatusAdapter extends AbstractAdapter {
    private static final String TAG = StatusListView.class.getSimpleName();

    private Object mLock = new Object();
    private int mType = -1;

    public void setType(int type) {
        mType = type;
    }

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
        CarBillBean carbill = (CarBillBean) mDatas.get(position);
        if (convertView == null) {
            convertView = ViewUtil.inflater(mContext,
                    R.layout.status_list_item);
        }

        convertView.setOnClickListener(this);
        convertView.setTag(carbill);

        ImageView carImage = (ImageView) convertView.findViewById(R.id.carImage);
        ImageLoaderProxy.loadImage(carbill.thumbUrl, carImage);

        TextView textNum = (TextView) convertView.findViewById(R.id.carNum);
        textNum.setText(mContext.getString(R.string.list_item_number) + " " + carbill.carBillId);

        TextView textTime = (TextView) convertView.findViewById(R.id.carTime);
        textTime.setText(mContext.getString(R.string.list_item_time) + " " + carbill.createTime);

        TextView textNote = (TextView) convertView.findViewById(R.id.carNote);
        textNote.setText(mContext.getString(R.string.list_item_note) + " " + carbill.mark);


        return convertView;
    }

    @Override
    public void onClick(View v) {
        Object tag = v.getTag();
        if (tag instanceof CarBillBean) {
            CarBillBean info = (CarBillBean) tag;
            CarLog.d(TAG, "Type: " + mType  + " ,info: " + info);
            //not pass
            if (mType == CacheContants.TYPE_NOTPASS) {
                ActivityUtils.jumpEvaluation(mContext, ConstantsUtils.BILL_STATUS_RETURN, info.carBillId, info.imageId, EvaluationActivity.class);
            } else if (mType == CacheContants.TYPE_SAVE) {
                ActivityUtils.jumpEvaluation(mContext, ConstantsUtils.BILL_STATUS_SAVE, info.carBillId, info.imageId, EvaluationActivity.class);
            } else {
                ActivityUtils.jumpStatus(mContext, info.carBillId, StatusActivity.class);
            }
        }
    }
}
