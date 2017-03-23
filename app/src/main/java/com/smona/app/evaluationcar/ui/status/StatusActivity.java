package com.smona.app.evaluationcar.ui.status;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.smona.app.evaluationcar.R;
import com.smona.app.evaluationcar.ui.common.activity.BaseActivity;
import com.smona.app.evaluationcar.ui.common.activity.HeaderActivity;

/**
 * Created by Moth on 2016/12/18.
 */

public class StatusActivity extends HeaderActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_status;
    }

    @Override
    protected boolean showDelete() {
        return false;
    }

    @Override
    protected int getHeaderTitle() {
        return R.string.carbill_result;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
