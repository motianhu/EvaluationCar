package com.smona.app.evaluationcar.ui.evaluation.preevaluation.simple;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.smona.app.evaluationcar.R;
import com.smona.app.evaluationcar.business.ResponseCallback;
import com.smona.app.evaluationcar.data.bean.PreCarBillBean;
import com.smona.app.evaluationcar.data.item.BrandItem;
import com.smona.app.evaluationcar.data.item.CityItem;
import com.smona.app.evaluationcar.data.item.SetItem;
import com.smona.app.evaluationcar.data.item.TypeItem;
import com.smona.app.evaluationcar.data.item.UserItem;
import com.smona.app.evaluationcar.data.model.ResBaseModel;
import com.smona.app.evaluationcar.framework.cache.DataDelegator;
import com.smona.app.evaluationcar.framework.json.JsonParse;
import com.smona.app.evaluationcar.ui.common.activity.BaseActivity;
import com.smona.app.evaluationcar.util.ActivityUtils;
import com.smona.app.evaluationcar.util.CarLog;
import com.smona.app.evaluationcar.util.ToastUtils;
import com.smona.app.evaluationcar.util.ViewUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by motianhu on 4/15/17.
 */

public class NormalPreEvaluationLayer extends RelativeLayout implements ResultCallback {
    private static final String TAG = NormalPreEvaluationLayer.class.getSimpleName();

    private TextView mCarModel;
    private TextView mCarDate;
    private TextView mCarColor;
    private TextView mCarLicheng;
    private TextView mCarCity;
    private TextView mCarMark;


    private BrandItem mBrandItem;
    private SetItem mSetItem;
    private TypeItem mTypeItem;

    private CityItem mCityItem;

    private ProgressDialog mProgressDialog;
    private ResponseCallback<String> mPreCarBillCallback = new ResponseCallback<String>() {
        @Override
        public void onFailed(String error) {
            CarLog.d(TAG, "mPreCarBillCallback onFailed error=" + error);
            dissDialog(false);
        }

        @Override
        public void onSuccess(String result) {
            CarLog.d(TAG, "mPreCarBillCallback onSuccess result=" + result);
            ResBaseModel<String> model = JsonParse.parseJson(result, ResBaseModel.class);
            dissDialog(model.success);
        }
    };
    private View.OnClickListener mOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id) {
                case R.id.container_cartime:
                    initSelectDate();
                    break;
                case R.id.container_cartype:
                    ActivityUtils.jumpResultActivity((Activity) getContext(), CarTypeActivity.class, ActivityUtils.ACTION_CAR_BRAND);
                    break;
                case R.id.container_city:
                    ActivityUtils.jumpResultActivity((Activity) getContext(), CityActivity.class, ActivityUtils.ACTION_CAR_CITY);
                    break;
                case R.id.pre_submit:
                    onSubmit();
                    break;
                case R.id.btn_delete:
                    clear();
                    break;
            }

        }
    };

    public NormalPreEvaluationLayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mCarModel = (TextView) findViewById(R.id.tv_type_content);
        mCarDate = (TextView) findViewById(R.id.tv_timecontent);
        mCarColor = (TextView) findViewById(R.id.tv_car_color);
        mCarLicheng = (TextView) findViewById(R.id.tv_car_licheng);
        mCarCity = (TextView) findViewById(R.id.tv_car_city);
        mCarMark = (TextView) findViewById(R.id.tv_car_mark);

        findViewById(R.id.container_cartype).setOnClickListener(mOnClickListener);
        findViewById(R.id.container_cartime).setOnClickListener(mOnClickListener);
        findViewById(R.id.container_city).setOnClickListener(mOnClickListener);
        findViewById(R.id.pre_submit).setOnClickListener(mOnClickListener);
        findViewById(R.id.btn_delete).setOnClickListener(mOnClickListener);
    }

    private void initSelectDate() {
        View view = ViewUtil.inflater(getContext(), R.layout.activity_timepicker);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(view);

        AlertDialog dialog = builder.create();
        initNumberPicker(dialog, view);
        dialog.show();
    }

    private void initNumberPicker(final AlertDialog dialog, View view) {
        final Calendar calendar = Calendar.getInstance();
        int mYear = calendar.get(Calendar.YEAR);

        final NumberPicker year = (NumberPicker) view.findViewById(R.id.selecYear);
        year.setMaxValue(mYear);
        year.setMinValue(mYear - 4);
        year.setFocusable(false);
        year.setFocusableInTouchMode(false);
        year.setWrapSelectorWheel(true);
        year.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        final NumberPicker month = (NumberPicker) view.findViewById(R.id.selectMonth);
        month.setMaxValue(12);
        month.setMinValue(1);
        month.setFocusable(false);
        month.setFocusableInTouchMode(false);
        month.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        view.findViewById(R.id.finish_select).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String time = String.format(getContext().getResources().getString(R.string.car_time),
                        year.getValue() + "", month.getValue() + "");
                mCarDate.setText(time);
                dialog.dismiss();
            }
        });
    }

    private void onSubmit() {
        String carType = mCarModel.getText().toString();
        if (TextUtils.isEmpty(carType)) {
            ToastUtils.show(getContext(), R.string.no_select_cartype);
            return;
        }
        String carColor = mCarColor.getText().toString();
        if (TextUtils.isEmpty(carColor)) {
            ToastUtils.show(getContext(), R.string.no_select_color);
            return;
        }
        String carDate = mCarDate.getText().toString();
        if (TextUtils.isEmpty(carDate)) {
            ToastUtils.show(getContext(), R.string.no_select_date);
            return;
        }
        String carLicheng = mCarLicheng.getText().toString();
        if (TextUtils.isEmpty(carLicheng)) {
            ToastUtils.show(getContext(), R.string.no_select_licheng);
            return;
        }
        String carCity = mCarCity.getText().toString();
        if (TextUtils.isEmpty(carCity)) {
            ToastUtils.show(getContext(), R.string.no_select_city);
            return;
        }

        PreCarBillBean bean = new PreCarBillBean();
        bean.carTypeId = mTypeItem.id;
        bean.color = carColor;
        bean.runNum = Integer.valueOf(carLicheng);
        bean.regDate = carDate;
        bean.cityId = mCityItem.code;
        bean.mark = mCarMark.getText().toString();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        bean.createTime = df.format(new Date());

        UserItem item = new UserItem();
        item.readSelf(getContext());
        submitTask(item.mId, bean);
        clear();
    }

    private void submitTask(String userName, PreCarBillBean bean) {
        mProgressDialog = getmProgressDialog();
        mProgressDialog.setMessage(getResources().getString(R.string.is_contact_customer));
        mProgressDialog.show();

        DataDelegator.getInstance().submitPreCallBill(userName, bean, mPreCarBillCallback);
    }

    private ProgressDialog getmProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getContext());
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {

                }
            });
        }
        return mProgressDialog;
    }

    private void dissDialog(final boolean isSuccess) {
        ((BaseActivity) getContext()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mProgressDialog.dismiss();
                if (isSuccess) {
                    clear();
                    ToastUtils.show(getContext(), R.string.submit_precallbill_success);
                }
            }
        });
    }

    private void clear() {
        mCarModel.setText("");
        mCarColor.setText("");
        mCarDate.setText("");
        mCarLicheng.setText("");
        mCarCity.setText("");
        mCarMark.setText("");
    }

    @Override
    public void onResult(int requestCode, Intent data) {
        if (requestCode == ActivityUtils.ACTION_CAR_BRAND) {
            mBrandItem = (BrandItem) data.getSerializableExtra(ActivityUtils.ACTION_DATA_BRAND);
            mSetItem = (SetItem) data.getSerializableExtra(ActivityUtils.ACTION_DATA_SET);
            mTypeItem = (TypeItem) data.getSerializableExtra(ActivityUtils.ACTION_DATA_TYPE);

            mCarModel.setText(mBrandItem.brandName + " " + mSetItem.carSetName + " " + mTypeItem.carTypeName);
        } else if (requestCode == ActivityUtils.ACTION_CAR_CITY) {
            mCityItem = (CityItem) data.getSerializableExtra(ActivityUtils.ACTION_DATA_CITY);
            mCarCity.setText(mCityItem.cityName);
        }
    }
}
