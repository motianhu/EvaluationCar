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
        mCarModel = (TextView) findViewById(R.id.car_model);
        mCarModel.setOnClickListener(mOnClickListener);
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            ActivityUtils.jumpOnlyActivity(getContext(), CarTypeActivity.class);
        }
    };
}
