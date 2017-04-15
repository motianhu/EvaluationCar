package com.smona.app.evaluationcar.ui.evaluation.preevaluation;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.smona.app.evaluationcar.R;
import com.smona.app.evaluationcar.data.item.BrandItem;
import com.smona.app.evaluationcar.framework.imageloader.ImageLoaderProxy;
import com.smona.app.evaluationcar.util.ViewUtil;

import java.util.List;

/**
 * Created by motianhu on 4/15/17.
 */

public class ExpandableListViewAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> mGroupList;
    private List<GroupInfo> mItemsList;

    public ExpandableListViewAdapter(Context context) {
        this.context = context;
    }

    public void setGroupList(List<String> groupList, List<GroupInfo> itemsList) {
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
        BrandItem item = mItemsList.get(groupPosition).childList.get(childPosition);
        ItemHolder itemHolder = null;
        if (convertView == null) {
            convertView = ViewUtil.inflater(context, R.layout.expendlist_item);
            itemHolder = new ItemHolder();
            itemHolder.brandName = (TextView) convertView.findViewById(R.id.brandName);
            itemHolder.icon = (ImageView) convertView.findViewById(R.id.icon);
            convertView.setTag(itemHolder);
        } else {
            itemHolder = (ItemHolder) convertView.getTag();
        }
        itemHolder.brandName.setText(item.brandName);
        ImageLoaderProxy.loadImage(item.iconUrl, itemHolder.icon);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}

class GroupHolder {
    TextView letter;
}

class ItemHolder {
    ImageView icon;
    TextView brandName;
}


