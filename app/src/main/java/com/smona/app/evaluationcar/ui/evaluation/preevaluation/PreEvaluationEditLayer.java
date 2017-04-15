package com.smona.app.evaluationcar.ui.evaluation.preevaluation;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.smona.app.evaluationcar.R;
import com.smona.app.evaluationcar.util.ActivityUtils;

/**
 * Created by motianhu on 4/15/17.
 */

public class PreEvaluationEditLayer extends RelativeLayout {

    private TextView mCarModel;

    public PreEvaluationEditLayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mCarModel = (TextView) findViewById(R.id.tv_type_content);
        findViewById(R.id.container_cartype).setOnClickListener(mOnClickListener);
        findViewById(R.id.container_cartime).setOnClickListener(mOnClickListener);
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id) {
                case R.id.container_cartime:
                    ActivityUtils.jumpOnlyActivity(getContext(), TimePickerActivity.class);
                    break;
                case R.id.container_cartype:
                    ActivityUtils.jumpOnlyActivity(getContext(), CarTypeActivity.class);
                    break;
            }

        }
    };
}
