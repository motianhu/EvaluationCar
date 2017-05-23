package com.smona.app.evaluationcar.ui.evaluation.preevaluation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.smona.app.evaluationcar.R;
import com.smona.app.evaluationcar.ui.common.activity.HeaderActivity;
import com.smona.app.evaluationcar.ui.evaluation.preevaluation.list.PreevaluationListActivity;
import com.smona.app.evaluationcar.ui.evaluation.preevaluation.quick.PreEvaluationQuickLayer;
import com.smona.app.evaluationcar.ui.status.StatusPagerAdapter;
import com.smona.app.evaluationcar.util.ActivityUtils;
import com.smona.app.evaluationcar.util.ViewUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Moth on 2017/3/6.
 */
public class PreEvaluationActivity extends HeaderActivity {

    private ViewPager mViewPager;
    private ResultCallback mResultCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_preevaluation;
    }

    @Override
    protected boolean showDelete() {
        return true;
    }

    @Override
    protected int getHeaderTitle() {
        return R.string.evalution_pre;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initViews() {
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        View scrollView = ViewUtil.inflater(this, R.layout.preevaluation_edit_layer);
        PreEvaluationEditLayer view1 = (PreEvaluationEditLayer) scrollView.findViewById(R.id.preEditor);
        mResultCallback = view1;

        PreEvaluationQuickLayer view2 = (PreEvaluationQuickLayer) ViewUtil.inflater(this, R.layout.preevaluation_quick_layer);


        List<View> viewList = new ArrayList<View>();
        viewList.add(view1);
        viewList.add(view2);

        List<String> titleList = new ArrayList<String>();
        titleList.add(this.getResources().getString(R.string.evalution_pre));
        titleList.add(this.getResources().getString(R.string.evalution_pre_quick));

        StatusPagerAdapter pagerAdapter = new StatusPagerAdapter(titleList, viewList);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.addTab(tabLayout.newTab().setText(titleList.get(0)));
        tabLayout.addTab(tabLayout.newTab().setText(titleList.get(1)));

        mViewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(mViewPager);
        mViewPager.setAdapter(pagerAdapter);
    }

    public void onDelete() {
        ActivityUtils.jumpOnlyActivity(this, PreevaluationListActivity.class);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null) {
            if (mResultCallback != null) {
                mResultCallback.onResult(requestCode, data);
            }
        }
    }
}
