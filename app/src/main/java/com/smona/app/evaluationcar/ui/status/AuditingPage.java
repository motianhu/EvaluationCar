package com.smona.app.evaluationcar.ui.status;

import android.content.Context;
import android.util.AttributeSet;

import com.smona.app.evaluationcar.data.bean.CarBillBean;
import com.smona.app.evaluationcar.data.event.AuditingStatusEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.util.List;

/**
 * Created by motianhu on 2/28/17.
 */

public class AuditingPage extends StatusListView {
    private static final String TAG = AuditingPage.class.getSimpleName();

    public AuditingPage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void update(AuditingStatusEvent event) {
        List<CarBillBean> datas = (List<CarBillBean>) event.getContent();
        mAdapter.update(datas);
        mAdapter.notifyDataSetChanged();
    }
}
