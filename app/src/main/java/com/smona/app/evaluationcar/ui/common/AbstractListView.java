package com.smona.app.evaluationcar.ui.common;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Moth on 2017/2/24.
 */

public abstract class AbstractListView extends ListView {

    protected AbstractAdapter mAdapter;

    public AbstractListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public abstract void init();

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDetachedFromWindow() {;
        super.onDetachedFromWindow();
        EventBus.getDefault().unregister(this);
    }
}
