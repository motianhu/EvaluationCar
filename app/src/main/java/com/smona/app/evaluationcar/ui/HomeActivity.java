package com.smona.app.evaluationcar.ui;

import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.smona.app.evaluationcar.R;
import com.smona.app.evaluationcar.business.ResponseCallback;
import com.smona.app.evaluationcar.data.bean.ImageMetaBean;
import com.smona.app.evaluationcar.data.event.UpgradeEvent;
import com.smona.app.evaluationcar.data.model.ResImageMetaArray;
import com.smona.app.evaluationcar.data.model.ResUpgradeApi;
import com.smona.app.evaluationcar.framework.cache.DataDelegator;
import com.smona.app.evaluationcar.framework.event.EventProxy;
import com.smona.app.evaluationcar.framework.imageloader.ImageLoaderProxy;
import com.smona.app.evaluationcar.framework.json.JsonParse;
import com.smona.app.evaluationcar.framework.provider.DBDelegator;
import com.smona.app.evaluationcar.ui.common.NoScrollViewPager;
import com.smona.app.evaluationcar.ui.common.activity.UserActivity;
import com.smona.app.evaluationcar.ui.home.fragment.HomeFragmentPagerAdapter;
import com.smona.app.evaluationcar.util.ActivityUtils;
import com.smona.app.evaluationcar.util.CarLog;
import com.smona.app.evaluationcar.util.Utils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by Moth on 2016/12/15.
 */

public class HomeActivity extends UserActivity implements RadioGroup.OnCheckedChangeListener {
    //几个代表页面的常量
    public static final int PAGE_HOME = 0;
    public static final int PAGE_EVALUATION = 1;
    public static final int PAGE_MESSAGE = 2;
    public static final int PAGE_LIST = 3;
    public static final int PAGE_SETTING = 4;
    private static final String TAG = HomeActivity.class.getSimpleName();
    //UI Objects
    private RadioGroup mRbGroup;
    private RadioButton[] mRadioFunc = new RadioButton[5];
    private NoScrollViewPager mViewPager;
    private HomeFragmentPagerAdapter mFragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        EventProxy.register(this);
        initViews();
        initDatas();
        startUploadService();
    }

    private void startUploadService() {
        ActivityUtils.startUpService(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventProxy.unregister(this);
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

    private void initDatas() {
        requestImageMetas();
        requestUpgradeInfo();
    }

    private void requestImageMetas() {
        DataDelegator.getInstance().requestImageMeta(mImageMetaCallback);
    }

    private void requestUpgradeInfo() {
        DataDelegator.getInstance().requestUpgradeInfo(mUpgradeCallback);
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void update(UpgradeEvent event) {
        UpgradeUtils.showUpdataDialog(this, event.mResBaseApi);
    }

    private ResponseCallback<String> mUpgradeCallback = new ResponseCallback<String>() {
        @Override
        public void onFailed(String error) {
            CarLog.d(TAG, "onFailed error=" + error);
        }

        @Override
        public void onSuccess(String content) {
            CarLog.d(TAG, "mUpgradeCallback onSuccess result=" + content);
            ResUpgradeApi newBaseApi = JsonParse.parseJson(content, ResUpgradeApi.class);
            if(newBaseApi != null) {
                if(newBaseApi.versionCode > Utils.getVersion(HomeActivity.this)) {
                    UpgradeEvent upgradeEvent = new UpgradeEvent();
                    upgradeEvent.mResBaseApi = newBaseApi;
                    EventProxy.post(upgradeEvent);
                }
            }
        }
    };

    private ResponseCallback<String> mImageMetaCallback = new ResponseCallback<String>() {
        @Override
        public void onFailed(String error) {
            CarLog.d(TAG, "onFailed error=" + error);
        }

        @Override
        public void onSuccess(String content) {
            ResImageMetaArray imageMetas = JsonParse.parseJson(content, ResImageMetaArray.class);
            if (imageMetas.data != null && imageMetas.data.size() > 0) {
                for (ImageMetaBean bean : imageMetas.data) {
                    boolean success = DBDelegator.getInstance().insertImageMeta(bean);
                    if (success) {
                        continue;
                    }
                    DBDelegator.getInstance().updateImageMeta(bean);
                    ImageLoaderProxy.loadUrl(bean.imageDesc);
                    ImageLoaderProxy.loadUrl(bean.waterMark);
                }
            }
        }
    };


}
