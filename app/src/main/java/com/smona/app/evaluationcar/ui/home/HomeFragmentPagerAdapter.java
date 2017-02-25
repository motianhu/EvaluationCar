package com.smona.app.evaluationcar.ui.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.smona.app.evaluationcar.ui.HomeActivity;
import com.smona.app.evaluationcar.util.CarLog;

/**
 * Created by Moth on 2015/8/31 0031.
 */
public class HomeFragmentPagerAdapter extends FragmentPagerAdapter {

    private final int PAGER_COUNT = 5;
    private ContentFragment mFragmentHome = null;
    private ContentFragment mFragmentEvaluation = null;
    private ContentFragment mFragmentMessage = null;
    private ContentFragment mFragmentList = null;
    private ContentFragment mFragmentSetting = null;


    public HomeFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        mFragmentHome = new HomeFragment();
        mFragmentEvaluation = new EvaluationFragment();
        mFragmentMessage = new MessageFragment();
        mFragmentList = new ListFragment();
        mFragmentSetting = new SettingFragment();
    }


    @Override
    public int getCount() {
        return PAGER_COUNT;
    }

    @Override
    public Object instantiateItem(ViewGroup vg, int position) {
        return super.instantiateItem(vg, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        CarLog.d(this, "position Destory" + position);
        super.destroyItem(container, position, object);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case HomeActivity.PAGE_HOME:
                fragment = mFragmentHome;
                break;
            case HomeActivity.PAGE_EVALUATION:
                fragment = mFragmentEvaluation;
                break;
            case HomeActivity.PAGE_MESSAGE:
                fragment = mFragmentMessage;
                break;
            case HomeActivity.PAGE_LIST:
                fragment = mFragmentList;
                break;
            case HomeActivity.PAGE_SETTING:
                fragment = mFragmentSetting;
                break;
        }
        return fragment;
    }


}

