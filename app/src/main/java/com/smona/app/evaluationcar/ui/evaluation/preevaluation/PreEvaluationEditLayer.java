package com.smona.app.evaluationcar.ui.evaluation.preevaluation;

import android.app.AlertDialog;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.smona.app.evaluationcar.R;
import com.smona.app.evaluationcar.util.ActivityUtils;
import com.smona.app.evaluationcar.util.ViewUtil;

import java.util.Calendar;

/**
 * Created by motianhu on 4/15/17.
 */

public class PreEvaluationEditLayer extends RelativeLayout {

    private TextView mCarModel;
    private TextView mCarDate;

    public PreEvaluationEditLayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mCarModel = (TextView) findViewById(R.id.tv_type_content);
        mCarDate = (TextView) findViewById(R.id.tv_timecontent);

        findViewById(R.id.container_cartype).setOnClickListener(mOnClickListener);
        findViewById(R.id.container_cartime).setOnClickListener(mOnClickListener);
        findViewById(R.id.container_city).setOnClickListener(mOnClickListener);
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id) {
                case R.id.container_cartime:
                    //ActivityUtils.jumpOnlyActivity(getContext(), TimePickerActivity.class);
                    initSelectDate();
                    break;
                case R.id.container_cartype:
                    ActivityUtils.jumpOnlyActivity(getContext(), CarTypeActivity.class);
                    break;
                case R.id.container_city:
                    ActivityUtils.jumpOnlyActivity(getContext(), CityActivity.class);
                    break;
            }

        }
    };

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
        year.setMinValue(mYear - 6);
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
}
