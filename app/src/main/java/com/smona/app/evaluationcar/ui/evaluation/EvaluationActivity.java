package com.smona.app.evaluationcar.ui.evaluation;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.smona.app.evaluationcar.R;
import com.smona.app.evaluationcar.data.bean.ImageMeta;
import com.smona.app.evaluationcar.ui.common.activity.BaseActivity;
import com.smona.app.evaluationcar.ui.common.base.LimitGridView;

import java.util.ArrayList;
import java.util.List;

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
        mCarModelsAdapter.update(createCarModel(42));
        mCarModelsGridView.setAdapter(mCarModelsAdapter);

        mCertificatesGridView = (LimitGridView) findViewById(R.id.grid_certificates);
        mCertificatesAdapter = new CarModelAdapter(this);
        mCertificatesAdapter.update(createCarModel(4));
        mCertificatesGridView.setAdapter(mCertificatesAdapter);

        mAppendGridView = (LimitGridView) findViewById(R.id.grid_appended);
        mAppendAdapter = new CarModelAdapter(this);
        mAppendGridView.setAdapter(mAppendAdapter);
    }

    private List<ImageMeta> createCarModel(int count) {
        List<ImageMeta> data = new ArrayList<ImageMeta>();
        for(int i =0;i<count;i++) {
            data.add(new ImageMeta());
        }
        return data;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
