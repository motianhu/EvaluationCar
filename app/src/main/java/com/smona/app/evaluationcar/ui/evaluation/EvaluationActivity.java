package com.smona.app.evaluationcar.ui.evaluation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.smona.app.evaluationcar.R;
import com.smona.app.evaluationcar.business.HttpDelegator;
import com.smona.app.evaluationcar.business.ResponseCallback;
import com.smona.app.evaluationcar.data.bean.CarBillBean;
import com.smona.app.evaluationcar.data.bean.CarImageBean;
import com.smona.app.evaluationcar.data.event.background.TaskBackgroundEvent;
import com.smona.app.evaluationcar.data.model.ResNormal;
import com.smona.app.evaluationcar.framework.event.EventProxy;
import com.smona.app.evaluationcar.framework.json.JsonParse;
import com.smona.app.evaluationcar.framework.provider.DBDelegator;
import com.smona.app.evaluationcar.framework.upload.ActionTask;
import com.smona.app.evaluationcar.framework.upload.CarBillTask;
import com.smona.app.evaluationcar.framework.upload.CompleteTask;
import com.smona.app.evaluationcar.framework.upload.ImageTask;
import com.smona.app.evaluationcar.framework.upload.UploadTaskExecutor;
import com.smona.app.evaluationcar.ui.common.activity.HeaderActivity;
import com.smona.app.evaluationcar.ui.common.base.BaseScrollView;
import com.smona.app.evaluationcar.ui.common.base.LimitGridView;
import com.smona.app.evaluationcar.util.ActivityUtils;
import com.smona.app.evaluationcar.util.CacheContants;
import com.smona.app.evaluationcar.util.CarLog;
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

    private enum BillStatus {
        NONE, SAVE, RETURN
    }

    private BillStatus mCurBillStatus = BillStatus.NONE;

    //save data
    private int mImageId = -1;
    //carbill data
    private String mCarBillId = null;
    private CarBillBean mCarBill = null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDatas();
        initViews();
        bindViews();
        EventProxy.register(this);
    }

    private void initDatas() {
        initStatus();
        initCarImage();
        initCarBill();
        initOther();
    }

    private void initStatus() {
        int billStatus = (int) SPUtil.get(this, CacheContants.BILL_STATUS, StatusUtils.BILL_STATUS_NONE);

        CarLog.d(TAG, "initStatus billStatus=" + billStatus);

        if (billStatus == StatusUtils.BILL_STATUS_SAVE) {
            mCurBillStatus = BillStatus.SAVE;
        } else if (billStatus == StatusUtils.BILL_STATUS_RETURN) {
            mCurBillStatus = BillStatus.RETURN;
        } else if (billStatus == StatusUtils.BILL_STATUS_NONE) {
            mCurBillStatus = BillStatus.NONE;
        }
    }

    private void initCarImage() {
        mImageId = (int) SPUtil.get(this, CacheContants.IMAGEID, -1);
        CarLog.d(TAG, "initCarImage imageId=" + mImageId);
    }

    private void initCarBill() {
        mCarBillId = (String) SPUtil.get(this, CacheContants.CARBILLID, null);
        CarLog.d(TAG, "initCarBill carBillId=" + mCarBillId);

        if (!TextUtils.isEmpty(mCarBillId)) {
            mCarBill = DBDelegator.getInstance().queryCarBill(mCarBillId);
        }
    }

    private void initOther() {
        mAddPicStr = getString(R.string.add_picture);
    }

    private void uploadImage() {
        uploadImage(ImageModelDelegator.IMAGE_Registration);
        uploadImage(ImageModelDelegator.IMAGE_CarBody);
        uploadImage(ImageModelDelegator.IMAGE_CarFrame);
        //uploadImage(ImageModelDelegator.IMAGE_DifferenceSupplement);
        uploadImage(ImageModelDelegator.IMAGE_DrivingLicense);
        //uploadImage(ImageModelDelegator.IMAGE_OriginalCarInsurancet);
        uploadImage(ImageModelDelegator.IMAGE_VehicleInterior);
        uploadImage(ImageModelDelegator.IMAGE_VehicleNameplate);
    }


    private void uploadImage(int type) {
        List<CarImageBean> carImageBeenList = ImageModelDelegator.getInstance().getDefaultModel(type);
        for (int i = 0; i < 1; i++) {
            CarImageBean carImageBean = carImageBeenList.get(i);
            carImageBean.carBillId = mCarBillId;
            carImageBean.imageLocalUrl = "/sdcard/Screenshots/Screenshot.png";
            carImageBean.imageClass = ImageModelDelegator.getInstance().getImageClassForType(type);
            carImageBean.imageSeqNum = i;
            ImageTask task = new ImageTask();
            //task. = mUploadImageCallback;
            task.userName = "cy";
            task.carImageBean = carImageBean;
            UploadTaskExecutor.pushTask(task);
        }
    }

    private ResponseCallback mUploadImageCallback = new ResponseCallback<String>() {
        @Override
        public void onSuccess(String result) {
            ResNormal resp = JsonParse.parseJson(result, ResNormal.class);
            CarLog.d(this, "onSuccess Object: " + result + ";resp: " + resp.success);
            UploadTaskExecutor.nextTask();
        }

        @Override
        public void onFailed(String error) {
            CarLog.d(TAG, "onError ex: " + error);
        }
    };

    private void queryCarbillImages() {
        HttpDelegator.getInstance().getCarbillImages("cy", "NS201703240001", new ResponseCallback<String>() {
            @Override
            public void onSuccess(String result) {
                CarLog.d(this, "queryCarbillImages onSuccess Object: " + result);
                ResNormal resp = JsonParse.parseJson(result, ResNormal.class);
            }

            @Override
            public void onFailed(String error) {
                CarLog.d(TAG, "onError ex: " + error);
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


        if (mCarBill != null) {
            mPrice.setText(mCarBill.preSalePrice + "");
            mNote.setText(mCarBill.mark);
        }
    }

    private void initImageData(List<CarImageBean> data, int type) {
        String imageClass = ImageModelDelegator.getInstance().getImageClassForType(type);
        if (statusIsNone()) {
            List<CarImageBean> tempData = ImageModelDelegator.getInstance().getDefaultModel(type);

            CarImageBean bean = new CarImageBean();
            bean.displayName = mAddPicStr;
            bean.imageClass = imageClass;
            bean.imageSeqNum = tempData.size();
            tempData.add(bean);

            data.addAll(tempData);
        } else if (statusIsSave()) {
            List<CarImageBean> tempData = ImageModelDelegator.getInstance().getSaveModel(type, mImageId);

            CarImageBean bean = new CarImageBean();
            bean.displayName = mAddPicStr;
            bean.imageClass = imageClass;
            bean.imageSeqNum = tempData.size();
            tempData.add(bean);

            data.addAll(tempData);
        } else {
            List<CarImageBean> tempData = DBDelegator.getInstance().queryImages(mCarBillId, imageClass);

            CarImageBean bean = new CarImageBean();
            bean.displayName = mAddPicStr;
            bean.imageClass = imageClass;
            bean.imageSeqNum = tempData.size();
            tempData.add(bean);

            data.addAll(tempData);
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

//        if (isTakePhoto()) {
//            return;
//        }
//
//        String preScalePrice = mPrice.getText().toString();
//        if (TextUtils.isEmpty(preScalePrice)) {
//            return;
//        }


        String mark = mNote.getText().toString();
        CarBillBean bean = new CarBillBean();
        bean.carBillId = mCarBillId;
        //bean.preSalePrice = Double.valueOf(preScalePrice);
        bean.mark = mark;
        bean.imageId = mImageId;

        //send background post
        TaskBackgroundEvent event = new TaskBackgroundEvent();
        event.setContent(bean);
        EventProxy.post(event);
    }

    private boolean isTakePhoto() {
        return checkPhoto(mClassRegistrationAdapter) ||
                checkPhoto(mClassDrivingLicenseAdapter) ||
                checkPhoto(mClassVehicleNameplateAdapter) ||
                checkPhoto(mClassCarBodyAdapter) ||
                checkPhoto(mClassCarFrameAdapter) ||
                checkPhoto(mClassVehicleInteriorAdapter) ||
                checkPhoto(mClassDifferenceSupplementAdapter) ||
                checkPhoto(mClassOriginalCarInsurancetAdapter);
    }

    private boolean checkPhoto(ImageModelAdapter adapter) {
        int index;
        index = adapter.checkPhoto();
        if (index > -1) {
            Toast.makeText(this, "", Toast.LENGTH_SHORT);
            return true;
        }
        return false;
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void startTask(TaskBackgroundEvent event) {
        CarLog.d(TAG, "TaskBackgroundEvent " + event);
        CarBillBean bean = (CarBillBean) event.getContent();

        CarBillTask carBillTask = new CarBillTask();
        carBillTask.mCarBill = bean;
        carBillTask.userName = "cy";

        List<CarImageBean> images = DBDelegator.getInstance().queryImages(bean.imageId);

        ActionTask preTask = carBillTask;

        for(CarImageBean image: images) {
            ImageTask task = new ImageTask();
            task.carImageBean = image;
            task.userName = "cy";
            preTask.mNextTask = task;

            preTask = task;
        }

        CompleteTask comleteTask = new CompleteTask();
        comleteTask.carBill = bean;
        comleteTask.userName = "cy";

        preTask.mNextTask = comleteTask;

        carBillTask.startTask();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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
