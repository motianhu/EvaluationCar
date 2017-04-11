package com.smona.app.evaluationcar.ui.common.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * @author Moth
 */
public abstract class BaseRelatetiveLayout extends RelativeLayout implements INotifyInterface {

    public BaseRelatetiveLayout(Context context) {
        super(context);
    }

    public BaseRelatetiveLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseRelatetiveLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        addObserver();
    }

    @Override
    protected void onDetachedFromWindow() {
        deleteObserver();
        super.onDetachedFromWindow();
    }

}
