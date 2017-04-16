package com.smona.app.evaluationcar.ui.evaluation.preevaluation;

import android.os.Bundle;
import com.smona.app.evaluationcar.R;
import com.smona.app.evaluationcar.ui.common.activity.BaseActivity;
import com.smona.app.evaluationcar.ui.common.numberpicker.NumberPicker;

import java.util.Calendar;

/**
 * Created by Moth on 2017/4/16.
 */

public class TimePickerActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timepicker);
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);

        NumberPicker year = (NumberPicker) findViewById(R.id.selecYear);
        year.setMaxValue(10);
        year.setMinValue(mYear - 10);
        year.setFocusable(false);
        year.setFocusableInTouchMode(false);


        NumberPicker month = (NumberPicker) findViewById(R.id.selectMonth);
        month.setMaxValue(12);
        month.setMinValue(1);
        month.setFocusable(false);
        month.setFocusableInTouchMode(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
