package com.smona.app.evaluationcar.ui.evaluation.preevaluation;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.smona.app.evaluationcar.R;
import com.smona.app.evaluationcar.data.bean.PreCarBillBean;
import com.smona.app.evaluationcar.util.ViewUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by motianhu on 4/7/17.
 */

public class PreEvaluationListAdapter extends BaseAdapter implements View.OnClickListener {
    private static final String TAG = PreEvaluationListAdapter.class.getSimpleName();

    private int mScrollState = AbsListView.OnScrollListener.SCROLL_STATE_IDLE;
    private Context mContext;
    private List<PreCarBillBean> mDataList = new ArrayList<>();

    public PreEvaluationListAdapter(Context context) {
        mContext = context;
    }

    public void update(List deltaList) {
        if (deltaList != null) {
            mDataList.addAll(mDataList.size(), deltaList);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PreCarBillBean carbill = mDataList.get(position);
        if (convertView == null) {
            convertView = ViewUtil.inflater(mContext,
                    R.layout.status_list_preevalution_item);
        }

        convertView.setOnClickListener(this);
        convertView.setTag(carbill);

        View container = convertView.findViewById(R.id.carType);
        setNameValue(container, mContext.getString(R.string.title_car_type) + ":", carbill.carBrandName + " " + carbill.carSetName + " " + carbill.carTypeName);

        container = convertView.findViewById(R.id.carColor);
        setNameValue(container, mContext.getString(R.string.title_car_color) + ":", carbill.color);

        container = convertView.findViewById(R.id.runNum);
        setNameValue(container, mContext.getString(R.string.title_car_licheng) + ":", carbill.runNum + mContext.getString(R.string.select_car_licheng_unit));


        container = convertView.findViewById(R.id.regDate);
        setNameValue(container, mContext.getString(R.string.title_car_time) + ":", carbill.regDate);


        container = convertView.findViewById(R.id.city);
        setNameValue(container, mContext.getString(R.string.title_car_city) + ":", carbill.cityName);

        container = convertView.findViewById(R.id.mark);
        setNameValue(container, mContext.getString(R.string.title_mark_1) + ":", carbill.mark);

        return convertView;
    }

    private void setNameValue(View parent, String name, String value) {
        TextView tvName = (TextView) parent.findViewById(R.id.name);
        TextView tvValue = (TextView) parent.findViewById(R.id.value);
        tvName.setText(name);
        tvValue.setText(value);
    }

    @Override
    public void onClick(View v) {
        Object tag = v.getTag();

    }

    protected void setScrollState(int state) {
        mScrollState = state;
    }

    public void clear() {
        mDataList.clear();
    }
}
