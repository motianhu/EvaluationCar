package com.smona.app.evaluationcar.ui.evaluation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.smona.app.evaluationcar.R;
import com.smona.app.evaluationcar.data.bean.ImageMeta;
import com.smona.app.evaluationcar.framework.http.HttpProxy;
import com.smona.app.evaluationcar.ui.common.activity.BaseActivity;
import com.smona.app.evaluationcar.ui.common.base.BaseScrollView;
import com.smona.app.evaluationcar.ui.common.base.LimitGridView;
import com.smona.app.evaluationcar.util.CarLog;
import com.smona.app.evaluationcar.util.IntentConstants;

import org.xutils.common.Callback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Moth on 2016/12/18.
 */

public class EvaluationActivity extends BaseActivity implements View.OnClickListener {

    private BaseScrollView mScrollView;

    private View mCarTitle;
    private LimitGridView mCarModelsGridView;
    private CarModelAdapter mCarModelsAdapter;


    private View mCertTitle;
    private LimitGridView mCertificatesGridView;
    private CarModelAdapter mCertificatesAdapter;

    private View mAppendTitle;
    private LimitGridView mAppendGridView;
    private CarModelAdapter mAppendAdapter;

    private View mInputGroup;


    //data
    private String mCarBillId;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation);
        initViews();
        initDatas();
    }

    private void initDatas() {
        mCarBillId = getIntent().getStringExtra(IntentConstants.CARBILLID);
        if(TextUtils.isEmpty(mCarBillId)) {
            HttpProxy.getCarBillId(new Callback.CommonCallback<String>(){
                @Override
                public void onSuccess(String result) {
                    CarLog.d(this, "onSuccess result: " + result);
                    mCarBillId = result;
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    CarLog.d(this, "Throwable result: " + ex + "; isOnCallback: " + isOnCallback);
                }

                @Override
                public void onCancelled(CancelledException cex) {

                }

                @Override
                public void onFinished() {
                    CarLog.d(this, "onFinished");
                }
            });
        }
    }

    private void initViews() {
        mScrollView = (BaseScrollView) findViewById(R.id.baseScrollView);

        mCarModelsGridView = (LimitGridView) findViewById(R.id.grid_car_models);
        mCarModelsAdapter = new CarModelAdapter(this);
        mCarModelsAdapter.update(createCarModel(42));
        mCarModelsGridView.setAdapter(mCarModelsAdapter);

        mCertificatesGridView = (LimitGridView) findViewById(R.id.grid_certificates);
        mCertificatesAdapter = new CarModelAdapter(this);
        mCertificatesAdapter.update(createCarModel(14));
        mCertificatesGridView.setAdapter(mCertificatesAdapter);

        mAppendGridView = (LimitGridView) findViewById(R.id.grid_appended);
        mAppendAdapter = new CarModelAdapter(this);
        mAppendAdapter.update(createCarModel(10));
        mAppendGridView.setAdapter(mAppendAdapter);


        mCarTitle = findViewById(R.id.title_car);
        mCertTitle = findViewById(R.id.title_certificates);
        mAppendTitle = findViewById(R.id.title_append);
        mInputGroup = findViewById(R.id.include_input);

        //设置定位按钮事件及初始化定位
        findViewById(R.id.rb_car_models).setOnClickListener(this);
        findViewById(R.id.rb_certificates).setOnClickListener(this);
        findViewById(R.id.rb_appended).setOnClickListener(this);
        findViewById(R.id.rb_editor).setOnClickListener(this);
        findViewById(R.id.rb_car_models).performClick();
    }

    private List<ImageMeta> createCarModel(int count) {
        List<ImageMeta> data = new ArrayList<ImageMeta>();
        for (int i = 0; i < count; i++) {
            data.add(new ImageMeta());
        }
        return data;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.rb_car_models:
                mScrollView.smoothScrollTo(0, mCarTitle.getTop());
                return;
            case R.id.rb_certificates:
                mScrollView.smoothScrollTo(0, mCertTitle.getTop());
                return;
            case R.id.rb_appended:
                mScrollView.smoothScrollTo(0, mAppendTitle.getTop());
                return;
            case R.id.rb_editor:
                mScrollView.smoothScrollTo(0, mInputGroup.getTop());
                return;
        }
    }
}
