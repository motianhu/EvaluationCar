package com.smona.app.evaluationcar.ui.evaluation.preevaluation;

import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;

import com.smona.app.evaluationcar.R;
import com.smona.app.evaluationcar.ui.common.activity.HeaderActivity;
import com.smona.app.evaluationcar.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Moth on 2017/4/15.
 */

public class CarTypeActivity extends HeaderActivity {

    private ExpandableListView mExpandableListView;
    private List<String> mGroupList;
    private List<String> item_lt;
    private List<List<String>> item_list;
    private List<List<Integer>> item_list2;
    private List<List<Integer>> gr_list2;

    private ExpandableListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }

    private void initViews() {
        mGroupList = new ArrayList<String>();
        mGroupList.add("我的好友");
        mGroupList.add("我的家人");
        mGroupList.add("兄弟姐妹");
        mGroupList.add("我的同学");

        item_lt = new ArrayList<String>();
        item_lt.add("张三丰");
        item_lt.add("董存瑞");
        item_lt.add("李大钊");

        item_list = new ArrayList<List<String>>();
        item_list.add(item_lt);
        item_list.add(item_lt);
        item_list.add(item_lt);
        item_list.add(item_lt);

        List<Integer> tmp_list = new ArrayList<Integer>();
        tmp_list.add(R.drawable.ic_launcher);
        tmp_list.add(R.drawable.ic_launcher);
        tmp_list.add(R.drawable.ic_launcher);
        tmp_list.add(R.drawable.ic_launcher);

        item_list2 = new ArrayList<List<Integer>>();
        item_list2.add(tmp_list);
        item_list2.add(tmp_list);
        item_list2.add(tmp_list);
        item_list2.add(tmp_list);

        List<Integer> gr_list = new ArrayList<Integer>();
        gr_list.add(R.drawable.ic_launcher);
        gr_list.add(R.drawable.ic_launcher);
        gr_list.add(R.drawable.ic_launcher);
        gr_list.add(R.drawable.ic_launcher);

        gr_list2 = new ArrayList<List<Integer>>();
        gr_list2.add(gr_list);
        gr_list2.add(gr_list);
        gr_list2.add(gr_list);
        gr_list2.add(gr_list);

        mExpandableListView = (ExpandableListView) findViewById(R.id.expendlist);
        mExpandableListView.setGroupIndicator(null);

        // 监听组点击
        mExpandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if (item_list.get(groupPosition).isEmpty()) {
                    return true;
                }
                return false;
            }
        });

        // 监听每个分组里子控件的点击事件
        mExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                ToastUtils.show(CarTypeActivity.this, "group=" + groupPosition + "---child=" + childPosition + "---" + item_list.get(groupPosition).get(childPosition));

                return false;
            }
        });

        ExpandableListViewAdapter adapter = new ExpandableListViewAdapter(this);
        adapter.setGroupList(mGroupList, item_list);
        mExpandableListView.setAdapter(adapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cartype;
    }

    @Override
    protected boolean showDelete() {
        return false;
    }

    @Override
    protected int getHeaderTitle() {
        return R.string.evalution_car_type;
    }
}
