package com.smona.app.evaluationcar.ui.evaluation;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.smona.app.evaluationcar.R;
import com.smona.app.evaluationcar.ui.common.group.GroupBaseAdapter;
import com.smona.app.evaluationcar.util.ViewUtils;

/**
 * Created by Moth on 2017/3/6.
 */

public class BrandGroupAdapter extends GroupBaseAdapter {

    @Override
    public Object getItem(int section, int position) {
        return null;
    }

    @Override
    public long getItemId(int section, int position) {
        return 0;
    }

    @Override
    public int getSectionCount() {
        return 7;
    }

    @Override
    public int getCountForSection(int section) {
        return section*4;
    }

    @Override
    public View getItemView(int section, int position, View convertView, ViewGroup parent) {
        LinearLayout layout = null;
        if (convertView == null) {
            layout = (LinearLayout)ViewUtils.inflater(parent.getContext(), R.layout.preevaluation_list_item);
        } else {
            layout = (LinearLayout) convertView;
        }
        ((TextView) layout.findViewById(R.id.textItem)).setText("Section " + section + " Item " + position);
        return layout;
    }

    @Override
    public View getSectionHeaderView(int section, View convertView, ViewGroup parent) {
        LinearLayout layout = null;
        if (convertView == null) {
            layout = (LinearLayout) ViewUtils.inflater(parent.getContext(), R.layout.preevaluation_header_item);
        } else {
            layout = (LinearLayout) convertView;
        }
        ((TextView) layout.findViewById(R.id.textItem)).setText("Header for section " + section);
        return layout;
    }

}
