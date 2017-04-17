package com.smona.app.evaluationcar.ui.evaluation.preevaluation;

import android.os.Bundle;
import android.widget.NumberPicker;

import com.smona.app.evaluationcar.R;
import com.smona.app.evaluationcar.ui.common.activity.BaseActivity;

import java.util.Calendar;

/**
 * Created by Moth on 2017/4/16.
 */

public class TimePickerActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timepicker);
        final Calendar calendar = Calendar.getInstance();
        int mYear = calendar.get(Calendar.YEAR);
        int mMonth = calendar.get(Calendar.MONTH);

        NumberPicker year = (NumberPicker) findViewById(R.id.selecYear);
        year.setMaxValue(mYear);
        year.setMinValue(mYear - 10);
        year.setFocusable(false);
        year.setFocusableInTouchMode(false);
        year.setWrapSelectorWheel(true);
        year.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        NumberPicker month = (NumberPicker) findViewById(R.id.selectMonth);
        month.setMaxValue(12);
        month.setMinValue(1);
        month.setFocusable(false);
        month.setFocusableInTouchMode(false);
        month.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
