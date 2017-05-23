package com.smona.app.evaluationcar.ui.evaluation.preevaluation.list;

import android.os.Bundle;

import com.smona.app.evaluationcar.R;
import com.smona.app.evaluationcar.ui.common.activity.HeaderActivity;

/**
 * Created by motianhu on 5/23/17.
 */

public class PreevaluationListActivity extends HeaderActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_preevaluation_list;
    }

    @Override
    protected boolean showDelete() {
        return false;
    }

    @Override
    protected int getHeaderTitle() {
        return R.string.evalution_pre_status;
    }
}
