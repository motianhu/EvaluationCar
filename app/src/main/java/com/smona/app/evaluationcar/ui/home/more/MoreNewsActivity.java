package com.smona.app.evaluationcar.ui.home.more;

import android.os.Bundle;

import com.smona.app.evaluationcar.R;
import com.smona.app.evaluationcar.framework.event.EventProxy;
import com.smona.app.evaluationcar.ui.common.activity.HeaderActivity;

/**
 * Created by motianhu on 5/3/17.
 */

public class MoreNewsActivity extends HeaderActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventProxy.unregister(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_more_news;
    }

    @Override
    protected boolean showDelete() {
        return false;
    }

    @Override
    protected int getHeaderTitle() {
        return R.string.more_news;
    }
}
