package com.smona.app.evaluationcar.ui.home.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.smona.app.evaluationcar.R;
import com.smona.app.evaluationcar.ui.status.StatusListView;
import com.smona.app.evaluationcar.ui.status.StatusPagerAdapter;
import com.smona.app.evaluationcar.util.ViewUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Moth on 2015/8/28 0028.
 */

public class StatusFragment extends ContentFragment {

    private ViewPager mViewPager;

    protected int getLayoutId() {
        return R.layout.fragment_list;
    }



    protected void init(View root) {
        mViewPager = (ViewPager) root.findViewById(R.id.viewpager);
        TabLayout tabLayout = (TabLayout) root.findViewById(R.id.tabs);

        StatusListView view1 = (StatusListView)ViewUtil.inflater(getContext(), R.layout.status_listview);
        view1.setType(0);
        StatusListView view2 = (StatusListView)ViewUtil.inflater(getContext(), R.layout.status_listview);
        view2.setType(1);
        StatusListView view3 = (StatusListView)ViewUtil.inflater(getContext(), R.layout.status_listview);
        view3.setType(2);
        StatusListView view4 = (StatusListView)ViewUtil.inflater(getContext(), R.layout.status_listview);
        view4.setType(3);

        List<View> viewList = new ArrayList<View>();
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);
        viewList.add(view4);

        List<String> titleList = new ArrayList<String>();
        titleList.add(getContext().getResources().getString(R.string.uncommit));
        titleList.add(getContext().getResources().getString(R.string.auditing));
        titleList.add(getContext().getResources().getString(R.string.notpass));
        titleList.add(getContext().getResources().getString(R.string.pass));

        StatusPagerAdapter pagerAdapter = new StatusPagerAdapter(titleList, viewList);

        tabLayout.setTabMode(TabLayout.MODE_FIXED);//设置tab模式，当前为系统默认模式
        tabLayout.addTab(tabLayout.newTab().setText(titleList.get(0)));//添加tab选项卡
        tabLayout.addTab(tabLayout.newTab().setText(titleList.get(1)));
        tabLayout.addTab(tabLayout.newTab().setText(titleList.get(2)));
        tabLayout.addTab(tabLayout.newTab().setText(titleList.get(3)));


        mViewPager.setAdapter(pagerAdapter);//给ViewPager设置适配器
        tabLayout.setupWithViewPager(mViewPager);//将TabLayout和ViewPager关联起来。

        mViewPager.setAdapter(pagerAdapter);
    }

    public void changeFragment(int position) {
        mViewPager.setCurrentItem(position);
    }
}
