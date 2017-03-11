package com.smona.app.evaluationcar.ui.evaluation;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.smona.app.evaluationcar.R;
import com.smona.app.evaluationcar.ui.common.activity.BaseActivity;

/**
 * Created by Moth on 2016/12/18.
 */

public class EvaluationActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
