package com.smona.app.evaluationcar.ui.common;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

import com.smona.app.evaluationcar.data.BannerInfo;
import com.smona.app.evaluationcar.data.HomeInfo;
import com.smona.app.evaluationcar.framework.event.EventProxy;
import com.smona.app.evaluationcar.ui.common.event.HomeEvent;
import com.smona.app.evaluationcar.util.CarLog;

import java.util.ArrayList;

/**
 * Created by Moth on 2017/2/24.
 */

public abstract class AbstractListView extends ListView {

    protected AbstractAdapter mAdapter;



    public AbstractListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        init();
    }

    public abstract void init();

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        EventProxy.register(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        EventProxy.unregister(this);
    }
}
