package com.smona.app.evaluationcar.ui.evaluation.preevaluation.simple;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.smona.app.evaluationcar.R;
import com.smona.app.evaluationcar.data.item.CityItem;
import com.smona.app.evaluationcar.util.ViewUtil;

import java.util.List;

/**
 * Created by Moth on 2017/4/16.
 */

class CityListViewAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> mGroupList;
    private List<GroupCityInfo> mItemsList;

    public CityListViewAdapter(Context context) {
        this.context = context;
    }

    public void setGroupList(List<String> groupList, List<GroupCityInfo> itemsList) {
        this.mGroupList = groupList;
        mItemsList = itemsList;
    }

    @Override
    public int getGroupCount() {
        return mGroupList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mItemsList.get(groupPosition).childList.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mGroupList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mItemsList.get(groupPosition).childList.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupHolder groupHolder = null;
        if (convertView == null) {
            convertView = ViewUtil.inflater(context, R.layout.expendlist_group);
            groupHolder = new GroupHolder();
            groupHolder.letter = (TextView) convertView.findViewById(R.id.letter);
            convertView.setTag(groupHolder);
        } else {
            groupHolder = (GroupHolder) convertView.getTag();
        }

        groupHolder.letter.setText(mGroupList.get(groupPosition));
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        CityItem item = mItemsList.get(groupPosition).childList.get(childPosition);
        ItemHolder itemHolder = null;
        if (convertView == null) {
            convertView = ViewUtil.inflater(context, R.layout.expendlist_city_item);
            itemHolder = new ItemHolder();
            itemHolder.brandName = (TextView) convertView.findViewById(R.id.cityName);
            itemHolder.icon = (ImageView) convertView.findViewById(R.id.icon);
            convertView.setTag(itemHolder);
        } else {
            itemHolder = (ItemHolder) convertView.getTag();
        }
        itemHolder.brandName.setText(item.cityName);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}
