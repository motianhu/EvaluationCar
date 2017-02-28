package com.smona.app.evaluationcar.ui.home.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.smona.app.evaluationcar.R;
import com.smona.app.evaluationcar.ui.status.StatusPagerAdapter;
import com.smona.app.evaluationcar.util.ViewUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Moth on 2015/8/28 0028.
 */
public class StatusFragment extends ContentFragment {
    protected int getLayoutId() {
        return R.layout.fragment_list;
    }


    protected void init(View root) {
        ViewPager viewPager = (ViewPager) root.findViewById(R.id.viewpager);
        TabLayout tabLayout = (TabLayout) root.findViewById(R.id.tabs);

        View view1 = ViewUtils.inflater(getContext(), R.layout.status_listview);
        View view2 = ViewUtils.inflater(getContext(), R.layout.status_listview);
        View view3 = ViewUtils.inflater(getContext(), R.layout.status_listview);
        View view4 = ViewUtils.inflater(getContext(), R.layout.status_listview);

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


        viewPager.setAdapter(pagerAdapter);//给ViewPager设置适配器
        tabLayout.setupWithViewPager(viewPager);//将TabLayout和ViewPager关联起来。

        viewPager.setAdapter(pagerAdapter);
    }
}
