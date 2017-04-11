package com.smona.app.evaluationcar.ui.home;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.smona.app.evaluationcar.R;
import com.smona.app.evaluationcar.business.ResponseCallback;
import com.smona.app.evaluationcar.data.event.BillTotalEvent;
import com.smona.app.evaluationcar.data.item.BillTotalItem;
import com.smona.app.evaluationcar.data.model.ResCountPage;
import com.smona.app.evaluationcar.framework.event.EventProxy;
import com.smona.app.evaluationcar.framework.json.JsonParse;
import com.smona.app.evaluationcar.ui.common.base.BaseLinearLayout;
import com.smona.app.evaluationcar.util.CarLog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by motianhu on 4/11/17.
 */

public class HomeLayer extends BaseLinearLayout {
    private static final String TAG = HomeLayer.class.getSimpleName();

    private TextView mTvBillTotal;

    public HomeLayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void init() {
        mTvBillTotal = (TextView) findViewById(R.id.billtotal);
        String content = getResources().getString(R.string.home_bill_total);
        mTvBillTotal.setText(String.format(content, 0));
    }

    private ResponseCallback<String> mCallbillCountCallback = new ResponseCallback<String>() {
        @Override
        public void onFailed(String error) {
            CarLog.d(TAG, "mCallbillCountCallback onFailed error= " + error);
        }

        @Override
        public void onSuccess(String content) {
            CarLog.d(TAG, "mCallbillCountCallback onSuccess content= " + content);
            ResCountPage resCountPage = JsonParse.parseJson(content, ResCountPage.class);
            notifyUICount(resCountPage);
        }
    };

    private void notifyUICount(ResCountPage page) {
        BillTotalEvent event = new BillTotalEvent();
        event.setContent(page);
        EventProxy.post(event);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void update(BillTotalEvent event) {
        ResCountPage bean = (ResCountPage) event.getContent();
        if (bean != null && bean.total > 0) {
            for (BillTotalItem item : bean.data) {
                if (BillTotalItem.ALLCOUNT.equals(item.infoType)) {
                    String content = getResources().getString(R.string.home_bill_total);
                    mTvBillTotal.setText(String.format(content, item.countInfo));
                    return;
                }
            }
        }
    }
}
