package com.smona.app.evaluationcar.ui.evaluation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.smona.app.evaluationcar.R;
import com.smona.app.evaluationcar.business.HttpProxy;
import com.smona.app.evaluationcar.data.bean.ImageMetaBean;
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

    //登记证
    private View mClassRegistrationTitle;
    private LimitGridView mClassRegistrationGrid;
    private CarModelAdapter mClassRegistrationAdapter;

    //行驶证
    private View mClassDrivingLicenseTitle;
    private LimitGridView mClassDrivingLicenseGrid;
    private CarModelAdapter mClassDrivingLicenseAdapter;

    //车辆铭牌
    private View mClassVehicleNameplateTitle;
    private LimitGridView mClassVehicleNameplateGrid;
    private CarModelAdapter mClassVehicleNameplateAdapter;

    //车身外观
    private View mClassCarBodyTitle;
    private LimitGridView mClassCarBodyGrid;
    private CarModelAdapter mClassCarBodyAdapter;

    //车骨架
    private View mClassCarFrameTitle;
    private LimitGridView mClassCarFrameGrid;
    private CarModelAdapter mClassCarFrameAdapter;

    //车辆内饰
    private View mClassVehicleInteriorTitle;
    private LimitGridView mClassVehicleInteriorGrid;
    private CarModelAdapter mClassVehicleInteriorAdapter;

    //差异补充
    private View mClassDifferenceSupplementTitle;
    private LimitGridView mClassDifferenceSupplementGrid;
    private CarModelAdapter mClassDifferenceSupplementAdapter;

    //原车保险
    private View mClassOriginalCarInsurancetTitle;
    private LimitGridView mClassOriginalCarInsurancetGrid;
    private CarModelAdapter mClassOriginalCarInsurancetAdapter;

    private View mInputGroup;

    private EditText mPrice;
    private EditText mNote;


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
        if (TextUtils.isEmpty(mCarBillId)) {
            HttpProxy.getInstance().getCarBillId(new Callback.CommonCallback<String>() {
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

        //登记证
        mClassRegistrationTitle = findViewById(R.id.class_registration_layer);
        mClassRegistrationGrid = (LimitGridView) findViewById(R.id.class_registration);
        mClassRegistrationAdapter = new CarModelAdapter(this);
        mClassRegistrationAdapter.update(createCarModel(3));
        mClassRegistrationGrid.setAdapter(mClassRegistrationAdapter);

        //行驶证
        mClassDrivingLicenseTitle = findViewById(R.id.class_driving_license_layer);
        mClassDrivingLicenseGrid = (LimitGridView) findViewById(R.id.class_driving_license);
        mClassDrivingLicenseAdapter = new CarModelAdapter(this);
        mClassDrivingLicenseAdapter.update(createCarModel(2));
        mClassDrivingLicenseGrid.setAdapter(mClassDrivingLicenseAdapter);

        //车辆铭牌
        mClassVehicleNameplateTitle = findViewById(R.id.class_vehicle_nameplate_layer);
        mClassVehicleNameplateGrid = (LimitGridView) findViewById(R.id.class_vehicle_nameplate);
        mClassVehicleNameplateAdapter = new CarModelAdapter(this);
        mClassVehicleNameplateAdapter.update(createCarModel(2));
        mClassVehicleNameplateGrid.setAdapter(mClassVehicleNameplateAdapter);

        //车身外观
        mClassCarBodyTitle = findViewById(R.id.class_car_body_layer);
        mClassCarBodyGrid = (LimitGridView) findViewById(R.id.class_car_body);
        mClassCarBodyAdapter = new CarModelAdapter(this);
        mClassCarBodyAdapter.update(createCarModel(5));
        mClassCarBodyGrid.setAdapter(mClassCarBodyAdapter);

        //车骨架
        mClassCarFrameTitle = findViewById(R.id.class_car_frame_layer);
        mClassCarFrameGrid = (LimitGridView) findViewById(R.id.class_car_frame);
        mClassCarFrameAdapter = new CarModelAdapter(this);
        mClassCarFrameAdapter.update(createCarModel(16));
        mClassCarFrameGrid.setAdapter(mClassCarFrameAdapter);

        //车辆内饰
        mClassVehicleInteriorTitle = findViewById(R.id.class_vehicle_interior_layer);
        mClassVehicleInteriorGrid = (LimitGridView) findViewById(R.id.class_vehicle_interior);
        mClassVehicleInteriorAdapter = new CarModelAdapter(this);
        mClassVehicleInteriorAdapter.update(createCarModel(5));
        mClassVehicleInteriorGrid.setAdapter(mClassVehicleInteriorAdapter);

        //差异补充
        mClassDifferenceSupplementTitle = findViewById(R.id.class_difference_supplement_layer);
        mClassDifferenceSupplementGrid = (LimitGridView) findViewById(R.id.class_difference_supplement);
        mClassDifferenceSupplementAdapter = new CarModelAdapter(this);
        mClassDifferenceSupplementAdapter.update(createCarModel(1));
        mClassDifferenceSupplementGrid.setAdapter(mClassDifferenceSupplementAdapter);

        //原车保险
        mClassOriginalCarInsurancetTitle = findViewById(R.id.class_original_car_insurancet_layer);
        mClassOriginalCarInsurancetGrid = (LimitGridView) findViewById(R.id.class_original_car_insurancet);
        mClassOriginalCarInsurancetAdapter = new CarModelAdapter(this);
        mClassOriginalCarInsurancetAdapter.update(createCarModel(1));
        mClassOriginalCarInsurancetGrid.setAdapter(mClassOriginalCarInsurancetAdapter);


        mInputGroup = findViewById(R.id.include_input);

        //price and remark
        mPrice = (EditText) findViewById(R.id.et_price);
        mNote = (EditText) findViewById(R.id.et_remark);

        //设置定位按钮事件及初始化定位
        findViewById(R.id.rb_car_models).setOnClickListener(this);
        findViewById(R.id.rb_certificates).setOnClickListener(this);
        findViewById(R.id.rb_appended).setOnClickListener(this);
        findViewById(R.id.rb_editor).setOnClickListener(this);
        findViewById(R.id.rb_car_models).performClick();
    }

    private List<ImageMetaBean> createCarModel(int count) {
        List<ImageMetaBean> data = new ArrayList<ImageMetaBean>();
        for (int i = 0; i < count; i++) {
            data.add(new ImageMetaBean());
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
                mScrollView.smoothScrollTo(0, mClassRegistrationTitle.getTop());
                return;
            case R.id.rb_certificates:
                mScrollView.smoothScrollTo(0, mClassCarFrameTitle.getTop());
                return;
            case R.id.rb_appended:
                mScrollView.smoothScrollTo(0, mClassVehicleInteriorTitle.getTop());
                return;
            case R.id.rb_editor:
                mScrollView.smoothScrollTo(0, mInputGroup.getTop());
                return;
        }
    }
}
