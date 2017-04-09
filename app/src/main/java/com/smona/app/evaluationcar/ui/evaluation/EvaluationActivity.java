package com.smona.app.evaluationcar.ui.evaluation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.smona.app.evaluationcar.R;
import com.smona.app.evaluationcar.business.HttpDelegator;
import com.smona.app.evaluationcar.business.ResponseCallback;
import com.smona.app.evaluationcar.data.bean.CarBillBean;
import com.smona.app.evaluationcar.data.bean.CarImageBean;
import com.smona.app.evaluationcar.data.event.RefreshImageEvent;
import com.smona.app.evaluationcar.data.event.background.QueryCarImageBackgroundEvent;
import com.smona.app.evaluationcar.data.event.background.TaskBackgroundEvent;
import com.smona.app.evaluationcar.data.model.ResCarImagePage;
import com.smona.app.evaluationcar.framework.event.EventProxy;
import com.smona.app.evaluationcar.framework.json.JsonParse;
import com.smona.app.evaluationcar.framework.provider.DBDelegator;
import com.smona.app.evaluationcar.framework.upload.ActionTask;
import com.smona.app.evaluationcar.framework.upload.CarBillTask;
import com.smona.app.evaluationcar.framework.upload.CompleteTask;
import com.smona.app.evaluationcar.framework.upload.ImageTask;
import com.smona.app.evaluationcar.ui.common.activity.HeaderActivity;
import com.smona.app.evaluationcar.ui.common.base.BaseScrollView;
import com.smona.app.evaluationcar.ui.common.base.LimitGridView;
import com.smona.app.evaluationcar.util.ActivityUtils;
import com.smona.app.evaluationcar.util.CacheContants;
import com.smona.app.evaluationcar.util.CarLog;
import com.smona.app.evaluationcar.util.DateUtils;
import com.smona.app.evaluationcar.util.SPUtil;
import com.smona.app.evaluationcar.util.StatusUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Moth on 2016/12/18.
 */

public class EvaluationActivity extends HeaderActivity implements View.OnClickListener {
    private static final String TAG = EvaluationActivity.class.getSimpleName();
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

    //铭牌
    private View mClassVehicleNameplateTitle;
    private LimitGridView mClassVehicleNameplateGrid;
    private ImageModelAdapter mClassVehicleNameplateAdapter;
    private List<CarImageBean> mClassVehicleNameplateList;

    //车身外观
    private View mClassCarBodyTitle;
    private LimitGridView mClassCarBodyGrid;
    private ImageModelAdapter mClassCarBodyAdapter;
    private List<CarImageBean> mClassCarBodyList;

    //车体骨架
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

    private String mAddPicStr;
    private int mCurStatus = StatusUtils.BILL_STATUS_NONE;

    //save data
    private int mImageId = 0;
    //carbill data
    private String mCarBillId = null;
    private CarBillBean mCarBill = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDatas();
        initViews();
        initImageList();
        updateImageViews();
        requestImageForCarBillId();
        EventProxy.register(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initStatus();
        initCarImage();
        notifyReloadCarImage();
    }

    private void initDatas() {
        initStatus();
        initCarImage();
        initCarBill();
        initOther();
    }

    private void initStatus() {
        if (!statusIsNone()) {
            return;
        }
        mCurStatus = (int) SPUtil.get(this, CacheContants.BILL_STATUS, StatusUtils.BILL_STATUS_NONE);
        CarLog.d(TAG, "initStatus mCurStatus=" + mCurStatus);
    }

    private void initCarImage() {
        if (mImageId > 0) {
            return;
        }
        mImageId = (int) SPUtil.get(this, CacheContants.IMAGEID, 0);
        CarLog.d(TAG, "initCarImage imageId=" + mImageId);
    }

    private void initCarBill() {
        mCarBillId = (String) SPUtil.get(this, CacheContants.CARBILLID, "");
        CarLog.d(TAG, "initCarBill carBillId=" + mCarBillId);

        if (!TextUtils.isEmpty(mCarBillId)) {
            mCarBill = DBDelegator.getInstance().queryCarBill(mCarBillId);
        }
    }

    private void initOther() {
        mAddPicStr = getString(R.string.add_picture);
    }

    private void requestImageForCarBillId() {
        if (TextUtils.isEmpty(mCarBillId)) {
            return;
        }
        HttpDelegator.getInstance().getCarbillImages(mUser.mId, mCarBillId, new ResponseCallback<String>() {
            @Override
            public void onSuccess(String result) {
                ResCarImagePage resp = JsonParse.parseJson(result, ResCarImagePage.class);
                if (resp.total > 0) {
                    for (CarImageBean bean : resp.data) {
                        DBDelegator.getInstance().updateCarImage(bean);
                        CarLog.d(TAG, "requestImageForCarBillId bean: " + bean);
                    }
                    notifyReloadCarImage();
                }
            }

            @Override
            public void onFailed(String error) {
                CarLog.d(TAG, "onError ex: " + error);
            }
        });
    }

    private void initViews() {
        mScrollView = (BaseScrollView) findViewById(R.id.baseScrollView);
        mScrollView.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
        mScrollView.setFocusable(true);
        mScrollView.setFocusableInTouchMode(true);

        //登记证
        mClassRegistrationTitle = findViewById(R.id.class_registration_layer);
        mClassRegistrationGrid = (LimitGridView) findViewById(R.id.class_registration);
        mClassRegistrationAdapter = new ImageModelAdapter(this, ImageModelDelegator.IMAGE_Registration);
        mClassRegistrationGrid.setAdapter(mClassRegistrationAdapter);

        //行驶证
        mClassDrivingLicenseTitle = findViewById(R.id.class_driving_license_layer);
        mClassDrivingLicenseGrid = (LimitGridView) findViewById(R.id.class_driving_license);
        mClassDrivingLicenseAdapter = new ImageModelAdapter(this, ImageModelDelegator.IMAGE_DrivingLicense);
        mClassDrivingLicenseGrid.setAdapter(mClassDrivingLicenseAdapter);

        //车辆铭牌
        mClassVehicleNameplateTitle = findViewById(R.id.class_vehicle_nameplate_layer);
        mClassVehicleNameplateGrid = (LimitGridView) findViewById(R.id.class_vehicle_nameplate);
        mClassVehicleNameplateAdapter = new ImageModelAdapter(this, ImageModelDelegator.IMAGE_VehicleNameplate);
        mClassVehicleNameplateGrid.setAdapter(mClassVehicleNameplateAdapter);

        //车身外观
        mClassCarBodyTitle = findViewById(R.id.class_car_body_layer);
        mClassCarBodyGrid = (LimitGridView) findViewById(R.id.class_car_body);
        mClassCarBodyAdapter = new ImageModelAdapter(this, ImageModelDelegator.IMAGE_CarBody);
        mClassCarBodyGrid.setAdapter(mClassCarBodyAdapter);

        //车骨架
        mClassCarFrameTitle = findViewById(R.id.class_car_frame_layer);
        mClassCarFrameGrid = (LimitGridView) findViewById(R.id.class_car_frame);
        mClassCarFrameAdapter = new ImageModelAdapter(this, ImageModelDelegator.IMAGE_CarFrame);
        mClassCarFrameGrid.setAdapter(mClassCarFrameAdapter);

        //车辆内饰
        mClassVehicleInteriorTitle = findViewById(R.id.class_vehicle_interior_layer);
        mClassVehicleInteriorGrid = (LimitGridView) findViewById(R.id.class_vehicle_interior);
        mClassVehicleInteriorAdapter = new ImageModelAdapter(this, ImageModelDelegator.IMAGE_VehicleInterior);
        mClassVehicleInteriorGrid.setAdapter(mClassVehicleInteriorAdapter);

        //差异补充
        mClassDifferenceSupplementTitle = findViewById(R.id.class_difference_supplement_layer);
        mClassDifferenceSupplementGrid = (LimitGridView) findViewById(R.id.class_difference_supplement);
        mClassDifferenceSupplementAdapter = new ImageModelAdapter(this, ImageModelDelegator.IMAGE_DifferenceSupplement);
        mClassDifferenceSupplementGrid.setAdapter(mClassDifferenceSupplementAdapter);

        //原车保险
        mClassOriginalCarInsurancetTitle = findViewById(R.id.class_original_car_insurancet_layer);
        mClassOriginalCarInsurancetGrid = (LimitGridView) findViewById(R.id.class_original_car_insurancet);
        mClassOriginalCarInsurancetAdapter = new ImageModelAdapter(this, ImageModelDelegator.IMAGE_OriginalCarInsurancet);
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

        if (mCarBill != null) {
            mPrice.setText(mCarBill.preSalePrice + "");
            mNote.setText(mCarBill.mark);
        }
    }

    private void initImageList() {
        mClassRegistrationList = new ArrayList<CarImageBean>();
        mClassDrivingLicenseList = new ArrayList<CarImageBean>();
        mClassVehicleNameplateList = new ArrayList<CarImageBean>();
        mClassCarBodyList = new ArrayList<CarImageBean>();
        mClassCarFrameList = new ArrayList<CarImageBean>();
        mClassVehicleInteriorList = new ArrayList<CarImageBean>();
        mClassDifferenceSupplementList = new ArrayList<CarImageBean>();
        mClassOriginalCarInsurancetList = new ArrayList<CarImageBean>();
    }

    private void updateImageViews() {
        mClassRegistrationAdapter.update(mClassRegistrationList);
        mClassDrivingLicenseAdapter.update(mClassDrivingLicenseList);
        mClassVehicleNameplateAdapter.update(mClassVehicleNameplateList);
        mClassCarBodyAdapter.update(mClassCarBodyList);
        mClassCarFrameAdapter.update(mClassCarFrameList);
        mClassVehicleInteriorAdapter.update(mClassVehicleInteriorList);
        mClassDifferenceSupplementAdapter.update(mClassDifferenceSupplementList);
        mClassOriginalCarInsurancetAdapter.update(mClassOriginalCarInsurancetList);
    }


    private void notifyReloadCarImage() {
        QueryCarImageBackgroundEvent event = new QueryCarImageBackgroundEvent();
        EventProxy.post(event);
        CarLog.d(TAG, "notifyReloadCarImage");
    }

    private void notifyRefreshViews() {
        RefreshImageEvent event = new RefreshImageEvent();
        EventProxy.post(event);
        CarLog.d(TAG, "notifyRefreshViews");
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void backgroundLoadImageData(QueryCarImageBackgroundEvent queryCarImage) {
        CarLog.d(TAG, "backgroundLoadImageData");
        clearImageList();
        reloadImageList();
        notifyRefreshViews();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void mainRefreshImageViews(RefreshImageEvent queryCarImage) {
        CarLog.d(TAG, "mainRefreshImageViews");
        updateImageViews();
    }

    private void reloadImageList() {
        initImageData(mClassRegistrationList, ImageModelDelegator.IMAGE_Registration);
        initImageData(mClassDrivingLicenseList, ImageModelDelegator.IMAGE_DrivingLicense);
        initImageData(mClassVehicleNameplateList, ImageModelDelegator.IMAGE_VehicleNameplate);
        initImageData(mClassCarBodyList, ImageModelDelegator.IMAGE_CarBody);
        initImageData(mClassCarFrameList, ImageModelDelegator.IMAGE_CarFrame);
        initImageData(mClassVehicleInteriorList, ImageModelDelegator.IMAGE_VehicleInterior);
        initImageData(mClassDifferenceSupplementList, ImageModelDelegator.IMAGE_DifferenceSupplement);
        initImageData(mClassOriginalCarInsurancetList, ImageModelDelegator.IMAGE_OriginalCarInsurancet);
    }

    private void clearImageList() {
        mClassRegistrationList.clear();
        mClassDrivingLicenseList.clear();
        mClassVehicleNameplateList.clear();
        mClassCarBodyList.clear();
        mClassCarFrameList.clear();
        mClassVehicleInteriorList.clear();
        mClassDifferenceSupplementList.clear();
        mClassOriginalCarInsurancetList.clear();
    }

    private void initImageData(List<CarImageBean> data, int type) {
        String imageClass = ImageModelDelegator.getInstance().getImageClassForType(type);
        if (statusIsReturn()) {
            List<CarImageBean> tempData = DBDelegator.getInstance().queryImages(mCarBillId, imageClass);

            CarImageBean bean = new CarImageBean();
            bean.displayName = mAddPicStr;
            bean.imageClass = imageClass;
            bean.imageSeqNum = tempData.size();
            tempData.add(bean);

            data.addAll(tempData);
        } else {
            List<CarImageBean> tempData = ImageModelDelegator.getInstance().getSaveModel(type, mImageId);

            CarImageBean bean = new CarImageBean();
            bean.displayName = mAddPicStr;
            bean.imageClass = imageClass;
            bean.imageSeqNum = tempData.size();
            tempData.add(bean);

            data.addAll(tempData);
        }
    }

    private boolean statusIsNone() {
        return mCurStatus == StatusUtils.BILL_STATUS_NONE;
    }

    private boolean statusIsSave() {
        return mCurStatus == StatusUtils.BILL_STATUS_SAVE;
    }

    private boolean statusIsReturn() {
        return mCurStatus == StatusUtils.BILL_STATUS_RETURN;
    }

    private void onSave() {

    }

    private void onSubmit() {
        if (statusIsReturn()) {
            submitReturn();
        } else {
            submitNone();
        }
    }

    private void submitReturn() {

    }

    private void submitNone() {

        if (!isTakePhoto()) {
            return;
        }

        String preScalePrice = mPrice.getText().toString();
        if (TextUtils.isEmpty(preScalePrice)) {
            return;
        }


        String mark = mNote.getText().toString();
        CarBillBean bean = new CarBillBean();
        bean.carBillId = mCarBillId;
        bean.preSalePrice = Double.valueOf(preScalePrice);
        bean.mark = mark;
        bean.imageId = mImageId;

        //send background post
        TaskBackgroundEvent event = new TaskBackgroundEvent();
        event.setContent(bean);
        EventProxy.post(event);
    }

    private boolean isTakePhoto() {
        return checkPhoto(mClassRegistrationAdapter) &&
                checkPhoto(mClassDrivingLicenseAdapter) &&
                checkPhoto(mClassVehicleNameplateAdapter) &&
                checkPhoto(mClassCarBodyAdapter) &&
                checkPhoto(mClassCarFrameAdapter) &&
                checkPhoto(mClassVehicleInteriorAdapter);
    }

    private boolean checkPhoto(ImageModelAdapter adapter) {
        CarImageBean bean = adapter.checkPhoto();
        if (bean != null) {
            String tip = String.format(getString(R.string.evalution_submit_tips), bean.imageClass, bean.displayName);
            Toast.makeText(this, tip, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void startTask(TaskBackgroundEvent event) {
        CarLog.d(TAG, "TaskBackgroundEvent " + event);
        CarBillBean bean = (CarBillBean) event.getContent();

        CarBillBean localBean = DBDelegator.getInstance().queryLocalCarbill(bean.imageId);
        bean.createTime = TextUtils.isEmpty(localBean.createTime) ? DateUtils.getCurrDate() : localBean.createTime;
        bean.modifyTime = TextUtils.isEmpty(localBean.modifyTime) ? DateUtils.getCurrDate() : localBean.modifyTime;
        DBDelegator.getInstance().updateCarBill(bean);

        CarBillTask carBillTask = new CarBillTask();
        carBillTask.mCarBill = bean;
        carBillTask.userName = mUser.mId;

        List<CarImageBean> images = DBDelegator.getInstance().queryImages(bean.imageId);

        ActionTask preTask = carBillTask;

        for (CarImageBean image : images) {
            ImageTask task = new ImageTask();
            task.carImageBean = image;
            task.userName = mUser.mId;
            preTask.mNextTask = task;

            preTask = task;
        }

        CompleteTask comleteTask = new CompleteTask();
        comleteTask.carBill = bean;
        comleteTask.userName = mUser.mId;

        preTask.mNextTask = comleteTask;

        carBillTask.startTask();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        CarLog.d(TAG, "requestCode: " + requestCode + "");
        if (requestCode == ActivityUtils.ACTION_CAMERA && resultCode == Activity.RESULT_OK && data != null) {

        }
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
    protected void onDestroy() {
        super.onDestroy();
        EventProxy.unregister(this);
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
