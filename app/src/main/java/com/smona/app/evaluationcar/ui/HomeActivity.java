package com.smona.app.evaluationcar.ui;

import android.os.Bundle;

import com.smona.app.evaluationcar.*;
import com.smona.app.evaluationcar.ui.common.activity.BaseActivity;
import com.smona.app.evaluationcar.ui.common.NoScrollViewPager;
import com.smona.app.evaluationcar.ui.home.fragment.HomeFragmentPagerAdapter;

import android.support.v4.view.ViewPager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * Created by Moth on 2016/12/15.
 */

public class HomeActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    //UI Objects
    private RadioGroup mRbGroup;
    private RadioButton[] mRadioFunc =new RadioButton[5];

    private NoScrollViewPager mViewPager;
    private HomeFragmentPagerAdapter mFragmentAdapter;

    //几个代表页面的常量
    public static final int PAGE_HOME = 0;
    public static final int PAGE_EVALUATION = 1;
    public static final int PAGE_MESSAGE = 2;
    public static final int PAGE_LIST = 3;
    public static final int PAGE_SETTING = 4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initViews();
    }

    private void initViews() {
        mFragmentAdapter = new HomeFragmentPagerAdapter(getSupportFragmentManager());
        mViewPager = (NoScrollViewPager) findViewById(R.id.vp_home);
        mViewPager.setNoScroll(true);
        mViewPager.setAdapter(mFragmentAdapter);

        mRbGroup = (RadioGroup) findViewById(R.id.rg_home);
        mRbGroup.setOnCheckedChangeListener(this);

        mRadioFunc[0] = (RadioButton) findViewById(R.id.rb_home);
        mRadioFunc[1] = (RadioButton) findViewById(R.id.rb_evaluation);
        mRadioFunc[2] = (RadioButton) findViewById(R.id.rb_message);
        mRadioFunc[3] = (RadioButton) findViewById(R.id.rb_list);
        mRadioFunc[4] = (RadioButton) findViewById(R.id.rb_setting);

        changeFragment(PAGE_HOME, R.string.home_fragment_home);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_home:
                changeFragment(PAGE_HOME, R.string.home_fragment_home);
                break;
            case R.id.rb_evaluation:
                changeFragment(PAGE_EVALUATION, R.string.home_fragment_evaluation);
                break;
            case R.id.rb_message:
                changeFragment(PAGE_MESSAGE, R.string.home_fragment_message);
                break;
            case R.id.rb_list:
                changeFragment(PAGE_LIST, R.string.home_fragment_list);
                break;
            case R.id.rb_setting:
                changeFragment(PAGE_SETTING, R.string.home_fragment_setting);
                break;
        }
    }

    public void changeList(int position) {
        changeFragment(PAGE_LIST, R.string.home_fragment_list);
        mFragmentAdapter.changeFragment(position);
    }


    private void changeFragment(int pageHome, int titleId) {
        mViewPager.setCurrentItem(pageHome, false);
        mRadioFunc[pageHome].setChecked(true);
    }
}
