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

        TextView carType = (TextView) convertView.findViewById(R.id.carType);
        carType.setText(mContext.getString(R.string.list_item_time) + " " + carbill.createTime);

        TextView carColor = (TextView) convertView.findViewById(R.id.carColor);
        carColor.setText(mContext.getString(R.string.list_item_time) + " " + carbill.color);

        TextView carNum = (TextView) convertView.findViewById(R.id.runNum);
        carNum.setText(mContext.getString(R.string.list_item_time) + " " + carbill.runNum);

        TextView textTime = (TextView) convertView.findViewById(R.id.regDate);
        textTime.setText(mContext.getString(R.string.list_item_time) + " " + carbill.regDate);

        TextView city = (TextView) convertView.findViewById(R.id.city);
        city.setText(mContext.getString(R.string.list_item_time) + " " + carbill.city);

        TextView mark = (TextView) convertView.findViewById(R.id.mark);
        mark.setText(mContext.getString(R.string.list_item_time) + " " + carbill.mark);

        return convertView;
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
