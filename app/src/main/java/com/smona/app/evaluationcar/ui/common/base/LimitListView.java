package com.smona.app.evaluationcar.ui.common.base;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * Created by motianhu on 3/7/17.
 */

public class LimitListView extends ListView {
    public LimitListView(Context paramContext) {
        super(paramContext);
    }

    public LimitListView(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
    }

    public LimitListView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
        super(paramContext, paramAttributeSet, paramInt);
    }

    public boolean dispatchTouchEvent(MotionEvent paramMotionEvent) {
        if (paramMotionEvent.getAction() == MotionEvent.ACTION_MOVE)
            return true;
        return super.dispatchTouchEvent(paramMotionEvent);
    }

//    protected void onMeasure(int paramInt1, int paramInt2) {
//        super.onMeasure(paramInt1, View.MeasureSpec.makeMeasureSpec(536870911, -2147483648));
//    }
}
