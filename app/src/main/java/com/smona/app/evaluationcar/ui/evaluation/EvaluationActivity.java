package com.smona.app.evaluationcar.ui.evaluation;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.smona.app.evaluationcar.R;
import com.smona.app.evaluationcar.ui.common.activity.BaseActivity;
import com.smona.app.evaluationcar.ui.common.base.LimitGridView;

/**
 * Created by Moth on 2016/12/18.
 */

public class EvaluationActivity extends BaseActivity {

    private LimitGridView mCarModelsGridView;
    private CarModelAdapter mCarModelsAdapter;


    private LimitGridView mCertificatesGridView;
    private CarModelAdapter mCertificatesAdapter;

    private LimitGridView mAppendGridView;
    private CarModelAdapter mAppendAdapter;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation);
        initViews();
    }

    private void initViews() {
        mCarModelsGridView = (LimitGridView) findViewById(R.id.grid_car_models);
        mCarModelsAdapter = new CarModelAdapter(this);
        mCarModelsGridView.setAdapter(mCarModelsAdapter);

        mCertificatesGridView = (LimitGridView) findViewById(R.id.grid_certificates);
        mCertificatesAdapter = new CarModelAdapter(this);
        mCertificatesGridView.setAdapter(mCertificatesAdapter);

        mAppendGridView = (LimitGridView) findViewById(R.id.grid_appended);
        mAppendAdapter = new CarModelAdapter(this);
        mAppendGridView.setAdapter(mAppendAdapter);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
