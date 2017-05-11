package com.smona.app.evaluationcar.ui.evaluation.preevaluation;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.smona.app.evaluationcar.R;
import com.smona.app.evaluationcar.util.CarLog;
import com.smona.app.evaluationcar.util.ViewUtil;

import java.util.List;

/**
 * Created by Moth on 2017/5/11.
 */

public class IndexLetterAdapter extends BaseAdapter {
    private List<String> mLetters;
    private Context mContext;

    public IndexLetterAdapter(Context context) {
        mContext = context;
    }

    public void setLettes(List<String> letters) {
        mLetters = letters;
    }

    @Override
    public int getCount() {
        return mLetters.size();
    }

    @Override
    public String getItem(int position) {
        return mLetters.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String letter = mLetters.get(position);
        ViewHolder viewHolder;
        CarLog.d("", "getView" + letter);
        if (convertView == null) {
            convertView = ViewUtil.inflater(mContext, R.layout.letter_item);
            viewHolder = new ViewHolder();
            viewHolder.tvLvRightItem = (TextView) convertView.findViewById(R.id.letter);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvLvRightItem.setText(letter);
        return convertView;
    }

    private class ViewHolder {
        private TextView tvLvRightItem;
    }
}
