package com.smona.app.evaluationcar.ui.common.base;

import android.content.Context;
import android.util.AttributeSet;

import com.smona.app.evaluationcar.framework.event.EventProxy;
import com.smona.app.evaluationcar.ui.common.AbstractAdapter;
import com.smona.app.evaluationcar.ui.common.pullup.XListView;

/**
 * Created by motianhu on 4/7/17.
 */

public abstract class RefreshListView extends XListView {
    protected AbstractAdapter mAdapter;

    public RefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
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
