package com.smona.app.evaluationcar.ui.evaluation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.smona.app.evaluationcar.R;
import com.smona.app.evaluationcar.business.HttpProxy;
import com.smona.app.evaluationcar.data.bean.CarBillBean;
import com.smona.app.evaluationcar.data.bean.CarImageBean;
import com.smona.app.evaluationcar.data.model.ResNormal;
import com.smona.app.evaluationcar.framework.json.JsonParse;
import com.smona.app.evaluationcar.ui.common.activity.BaseActivity;
import com.smona.app.evaluationcar.ui.common.activity.HeaderActivity;
import com.smona.app.evaluationcar.ui.common.base.BaseScrollView;
import com.smona.app.evaluationcar.ui.common.base.LimitGridView;
import com.smona.app.evaluationcar.util.CarLog;
import com.smona.app.evaluationcar.util.ConstantsUtils;
import com.smona.app.evaluationcar.util.IntentConstants;

import org.xutils.common.Callback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Moth on 2016/12/18.
 */

public class EvaluationActivity extends HeaderActivity implements View.OnClickListener {

    private BaseScrollView mScrollView;

    //登记证
    private View mClassRegistrationTitle;
    private LimitGridView mClassRegistrationGrid;
    private ImageModelAdapter mClassRegistrationAdapter;
    private List<CarImageBean> mClassRegistrationList;

    //行驶证
    private View mClassDrivingLicenseTitle;
    private LimitGridView mClassDrivingLicenseGrid;
    private ImageModelAdapter mClassDrivingLicenseAdapter;
    private List<CarImageBean> mClassDrivingLicenseList;

    //车辆铭牌
    private View mClassVehicleNameplateTitle;
    private LimitGridView mClassVehicleNameplateGrid;
    private ImageModelAdapter mClassVehicleNameplateAdapter;
    private List<CarImageBean> mClassVehicleNameplateList;

    //车身外观
    private View mClassCarBodyTitle;
    private LimitGridView mClassCarBodyGrid;
    private ImageModelAdapter mClassCarBodyAdapter;
    private List<CarImageBean> mClassCarBodyList;

    //车骨架
    private View mClassCarFrameTitle;
    private LimitGridView mClassCarFrameGrid;
    private ImageModelAdapter mClassCarFrameAdapter;
    private List<CarImageBean> mClassCarFrameList;

    //车辆内饰
    private View mClassVehicleInteriorTitle;
    private LimitGridView mClassVehicleInteriorGrid;
    private ImageModelAdapter mClassVehicleInteriorAdapter;
    private List<CarImageBean> mClassVehicleInteriorList;

    //差异补充
    private View mClassDifferenceSupplementTitle;
    private LimitGridView mClassDifferenceSupplementGrid;
    private ImageModelAdapter mClassDifferenceSupplementAdapter;
    private List<CarImageBean> mClassDifferenceSupplementList;

    //原车保险
    private View mClassOriginalCarInsurancetTitle;
    private LimitGridView mClassOriginalCarInsurancetGrid;
    private ImageModelAdapter mClassOriginalCarInsurancetAdapter;
    private List<CarImageBean> mClassOriginalCarInsurancetList;

    private View mInputGroup;

    private EditText mPrice;
    private EditText mNote;


    private enum BillStatus {
        NONE, SAVE, RETURN
    }

    private int mBillStatus = -1;
    private BillStatus mCurBillStatus = BillStatus.NONE;
    //data
    private String mCarBillId = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDatas();
        initViews();
        bindViews();
    }

    private void initDatas() {
        initStatus();
        initCarBill();
    }

    private void initStatus() {
        int billStatus = getIntent().getIntExtra(IntentConstants.BILL_STATUS, ConstantsUtils.BILL_STATUS_NONE);
        mBillStatus = billStatus;
        CarLog.d(this, "initStatus billStatus=" + billStatus);
        if (billStatus == ConstantsUtils.BILL_STATUS_SAVE) {
            mCurBillStatus = BillStatus.SAVE;
        } else if (billStatus == ConstantsUtils.BILL_STATUS_RETURN) {
            mCurBillStatus = BillStatus.RETURN;
        } else if (billStatus == ConstantsUtils.BILL_STATUS_NONE) {
            mCurBillStatus = BillStatus.NONE;
        }
    }

    private void initCarBill() {
        if (statusIsNone()) {
            return;
        }
        if (statusIsSave()) {
            return;
        }
        mCarBillId = getIntent().getStringExtra(IntentConstants.CARBILLID);
        CarLog.d(this, "initCarBill mCarBillId=" + mCarBillId);
        if (!TextUtils.isEmpty(mCarBillId)) {
            return;
        }
        HttpProxy.getInstance().getCarBillId(new HttpProxy.ResonpseCallback<String>() {
            @Override
            public void onSuccess(String result) {
                CarLog.d(this, "onSuccess result: " + result);
                mCarBillId = result.substring(1,result.length() - 1);
                CarLog.d(this, "onSuccess mCarBillId: " + mCarBillId);
                CarImageBean bean = new CarImageBean();
                bean.carBillId = mCarBillId;
                bean.imageLocalUrl = "/sdcard/Screenshots/Screenshot_20170315-225739.png";
                bean.imageClass="车骨架";
                bean.imageSeqNum=1;
                HttpProxy.getInstance().uploadImage("cy", bean, new HttpProxy.ResonpseCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        CarLog.d(this, "onSuccess Object: " + result);
                        ResNormal resp = JsonParse.parseJson(result, ResNormal.class);
                        CarLog.d(this, "onSuccess Object: " + resp);
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        CarLog.d(this, "onError ex: " + ex);
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });
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


    private void initViews() {
        mScrollView = (BaseScrollView) findViewById(R.id.baseScrollView);

        //登记证
        mClassRegistrationTitle = findViewById(R.id.class_registration_layer);
        mClassRegistrationGrid = (LimitGridView) findViewById(R.id.class_registration);
        mClassRegistrationAdapter = new ImageModelAdapter(this);
        mClassRegistrationGrid.setAdapter(mClassRegistrationAdapter);

        //行驶证
        mClassDrivingLicenseTitle = findViewById(R.id.class_driving_license_layer);
        mClassDrivingLicenseGrid = (LimitGridView) findViewById(R.id.class_driving_license);
        mClassDrivingLicenseAdapter = new ImageModelAdapter(this);
        mClassDrivingLicenseGrid.setAdapter(mClassDrivingLicenseAdapter);

        //车辆铭牌
        mClassVehicleNameplateTitle = findViewById(R.id.class_vehicle_nameplate_layer);
        mClassVehicleNameplateGrid = (LimitGridView) findViewById(R.id.class_vehicle_nameplate);
        mClassVehicleNameplateAdapter = new ImageModelAdapter(this);
        mClassVehicleNameplateGrid.setAdapter(mClassVehicleNameplateAdapter);

        //车身外观
        mClassCarBodyTitle = findViewById(R.id.class_car_body_layer);
        mClassCarBodyGrid = (LimitGridView) findViewById(R.id.class_car_body);
        mClassCarBodyAdapter = new ImageModelAdapter(this);
        mClassCarBodyGrid.setAdapter(mClassCarBodyAdapter);

        //车骨架
        mClassCarFrameTitle = findViewById(R.id.class_car_frame_layer);
        mClassCarFrameGrid = (LimitGridView) findViewById(R.id.class_car_frame);
        mClassCarFrameAdapter = new ImageModelAdapter(this);
        mClassCarFrameGrid.setAdapter(mClassCarFrameAdapter);

        //车辆内饰
        mClassVehicleInteriorTitle = findViewById(R.id.class_vehicle_interior_layer);
        mClassVehicleInteriorGrid = (LimitGridView) findViewById(R.id.class_vehicle_interior);
        mClassVehicleInteriorAdapter = new ImageModelAdapter(this);
        mClassVehicleInteriorGrid.setAdapter(mClassVehicleInteriorAdapter);

        //差异补充
        mClassDifferenceSupplementTitle = findViewById(R.id.class_difference_supplement_layer);
        mClassDifferenceSupplementGrid = (LimitGridView) findViewById(R.id.class_difference_supplement);
        mClassDifferenceSupplementAdapter = new ImageModelAdapter(this);
        mClassDifferenceSupplementGrid.setAdapter(mClassDifferenceSupplementAdapter);

        //原车保险
        mClassOriginalCarInsurancetTitle = findViewById(R.id.class_original_car_insurancet_layer);
        mClassOriginalCarInsurancetGrid = (LimitGridView) findViewById(R.id.class_original_car_insurancet);
        mClassOriginalCarInsurancetAdapter = new ImageModelAdapter(this);
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

        //保存及提交
        findViewById(R.id.btn_save).setOnClickListener(this);
        findViewById(R.id.btn_submit).setOnClickListener(this);
    }

    private void bindViews() {
        mClassRegistrationList = new ArrayList<CarImageBean>();
        initImageData(mClassRegistrationList, ImageModelDelegator.IMAGE_Registration);
        mClassRegistrationAdapter.update(mClassRegistrationList);

        mClassDrivingLicenseList = new ArrayList<CarImageBean>();
        initImageData(mClassDrivingLicenseList, ImageModelDelegator.IMAGE_DrivingLicense);
        mClassDrivingLicenseAdapter.update(mClassDrivingLicenseList);

        mClassVehicleNameplateList = new ArrayList<CarImageBean>();
        initImageData(mClassVehicleNameplateList, ImageModelDelegator.IMAGE_VehicleNameplate);
        mClassVehicleNameplateAdapter.update(mClassVehicleNameplateList);

        mClassCarBodyList = new ArrayList<CarImageBean>();
        initImageData(mClassCarBodyList, ImageModelDelegator.IMAGE_CarBody);
        mClassCarBodyAdapter.update(mClassCarBodyList);

        mClassCarFrameList = new ArrayList<CarImageBean>();
        initImageData(mClassCarFrameList, ImageModelDelegator.IMAGE_CarFrame);
        mClassCarFrameAdapter.update(mClassCarFrameList);

        mClassVehicleInteriorList = new ArrayList<CarImageBean>();
        initImageData(mClassVehicleInteriorList, ImageModelDelegator.IMAGE_VehicleInterior);
        mClassVehicleInteriorAdapter.update(mClassVehicleInteriorList);

        mClassDifferenceSupplementList = new ArrayList<CarImageBean>();
        initImageData(mClassDifferenceSupplementList, ImageModelDelegator.IMAGE_DifferenceSupplement);
        mClassDifferenceSupplementAdapter.update(mClassDifferenceSupplementList);

        mClassOriginalCarInsurancetList = new ArrayList<CarImageBean>();
        initImageData(mClassOriginalCarInsurancetList, ImageModelDelegator.IMAGE_OriginalCarInsurancet);
        mClassOriginalCarInsurancetAdapter.update(mClassOriginalCarInsurancetList);
    }

    private void initImageData(List<CarImageBean> data, int type) {
        if (statusIsNone()) {
            List<CarImageBean> tempData = ImageModelDelegator.getInstance().getDefaultModel(type);
            CarImageBean bean = new CarImageBean();
            bean.displayName = getResources().getString(R.string.add_picture);
            bean.imageClass = ImageModelDelegator.getInstance().getImageClass(type);
            bean.imageSeqNum = data.size();
            tempData.add(bean);

            data.addAll(tempData);
        } else if (statusIsSave()) {

        } else {

        }

    }

    private boolean statusIsNone() {
        return mCurBillStatus == BillStatus.NONE;
    }

    private boolean statusIsSave() {
        return mCurBillStatus == BillStatus.SAVE;
    }

    private boolean statusIsReturn() {
        return mCurBillStatus == BillStatus.RETURN;
    }

    private void onSave(){

    }

    private void onSubmit(){
        CarBillBean bean = new CarBillBean();
        bean.carBillId = mCarBillId;
        bean.price = 10000.0;
        bean.description = "测试单";
        HttpProxy.getInstance().submitCarBill("cy", bean, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                CarLog.d(this, "onSuccess result: " + result);
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


    @Override
    protected int getLayoutId() {
        return R.layout.activity_evaluation;
    }

    @Override
    protected boolean showDelete() {
        return false;
    }

    @Override
    protected int getHeaderTitle() {
        return R.string.create_carbill;
    }


    @Override
    public void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);
        int billStatus = state.getInt(IntentConstants.BILL_STATUS);
        String carBillId = state.getString(IntentConstants.CARBILLID);
        CarLog.d(this, "onRestoreInstanceState billStatus=" + billStatus + ", carBillId=" + carBillId);
        if (billStatus != ConstantsUtils.BILL_STATUS_NONE) {
            mBillStatus = billStatus;
        }
        if (!TextUtils.isEmpty(carBillId)) {
            mCarBillId = carBillId;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        CarLog.d(this, "onRestoreInstanceState mBillStatus=" + mBillStatus + ", mCarBillId=" + mCarBillId);
        outState.putInt(IntentConstants.BILL_STATUS, mBillStatus);
        outState.putString(IntentConstants.CARBILLID, mCarBillId);
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
            case R.id.btn_save:
                onSave();
                break;
            case R.id.btn_submit:
                onSubmit();
                break;
        }
    }
}
