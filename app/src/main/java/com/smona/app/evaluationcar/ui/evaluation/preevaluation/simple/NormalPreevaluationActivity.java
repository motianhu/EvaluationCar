package com.smona.app.evaluationcar.ui.evaluation.preevaluation.simple;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.smona.app.evaluationcar.R;
import com.smona.app.evaluationcar.ui.common.activity.HeaderActivity;

/**
 * Created by motianhu on 5/23/17.
 */

public class NormalPreevaluationActivity extends HeaderActivity {

    private ResultCallback mResultCallback;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_preevaluation_sample;
    }

    @Override
    protected boolean showDelete() {
        return false;
    }

    @Override
    protected int getHeaderTitle() {
        return R.string.evalution_pre_normal;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }

    private void initViews() {
        NormalPreEvaluationLayer layer = (NormalPreEvaluationLayer) findViewById(R.id.preEditor);
        mResultCallback = layer;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null) {
            if (mResultCallback != null) {
                mResultCallback.onResult(requestCode, data);
            }
        }
    }
}
