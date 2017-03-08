package com.smona.app.evaluationcar.ui;

import android.content.ContentResolver;
import android.os.Bundle;

import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.smona.app.evaluationcar.*;
import com.smona.app.evaluationcar.framework.provider.CarBillTable;
import com.smona.app.evaluationcar.ui.common.NoScrollViewPager;
import com.smona.app.evaluationcar.ui.home.fragment.HomeFragmentPagerAdapter;

import android.support.v4.view.ViewPager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

/**
 * Created by Moth on 2016/12/15.
 */

public class HomeActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener,
        ViewPager.OnPageChangeListener {

    //UI Objects
    private TextView mTitle;
    private RadioGroup mRbGroup;

    private RadioButton mRbHome;
    private RadioButton mRbEvaluation;
    private RadioButton mRbMessage;
    private RadioButton mRbList;
    private RadioButton mRbSetting;
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
        mViewPager.addOnPageChangeListener(this);

        mTitle = (TextView) findViewById(R.id.tv_title);
        mRbGroup = (RadioGroup) findViewById(R.id.rg_home);
        mRbGroup.setOnCheckedChangeListener(this);

        mRbHome = (RadioButton) findViewById(R.id.rb_home);
        mRbEvaluation = (RadioButton) findViewById(R.id.rb_evaluation);
        mRbMessage = (RadioButton) findViewById(R.id.rb_message);
        mRbList = (RadioButton) findViewById(R.id.rb_list);
        mRbSetting = (RadioButton) findViewById(R.id.rb_setting);


        changeFragment(PAGE_HOME, R.string.home_fragment_home);
        mRbHome.setChecked(true);
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

    private void changeFragment(int pageHome, int titleId) {
        mViewPager.setCurrentItem(pageHome, false);
        mTitle.setText(titleId);
    }


    //重写ViewPager页面切换的处理方法
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        //state的状态有三个，0表示什么都没做，1正在滑动，2滑动完毕
        if (state == 2) {
            switch (mViewPager.getCurrentItem()) {
                case PAGE_HOME:
                    mRbHome.setChecked(true);
                    break;
                case PAGE_EVALUATION:
                    mRbEvaluation.setChecked(true);
                    break;
                case PAGE_MESSAGE:
                    mRbMessage.setChecked(true);
                    break;
                case PAGE_LIST:
                    mRbList.setChecked(true);
                    break;
                case PAGE_SETTING:
                    mRbSetting.setChecked(true);
                    break;
            }
        }
    }

}
