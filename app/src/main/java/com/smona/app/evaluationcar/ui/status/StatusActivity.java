package com.smona.app.evaluationcar.ui.status;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.smona.app.evaluationcar.R;
import com.smona.app.evaluationcar.data.bean.CarBillBean;
import com.smona.app.evaluationcar.ui.common.activity.HeaderActivity;
import com.smona.app.evaluationcar.util.CacheContants;
import com.smona.app.evaluationcar.util.StatusUtils;

/**
 * Created by Moth on 2016/12/18.
 */

public class StatusActivity extends HeaderActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }

    private void initViews() {
        CarBillBean bean = (CarBillBean)getIntent().getSerializableExtra(CacheContants.CARBILLBEAN);

        View parent = findViewById(R.id.carbill);
        TextView textKey = (TextView) parent.findViewById(R.id.key);
        TextView textValue = (TextView) parent.findViewById(R.id.value);
        textKey.setText(R.string.status_cabillid);
        textValue.setText(bean.carBillId);


        parent = findViewById(R.id.billstatus);
        textKey = (TextView) parent.findViewById(R.id.key);
        textValue = (TextView) parent.findViewById(R.id.value);
        textKey.setText(R.string.status_bill_status);
        textValue.setText(StatusUtils.BILL_STATUS_MAP.get(bean.status));

        parent = findViewById(R.id.billtime);
        textKey = (TextView) parent.findViewById(R.id.key);
        textValue = (TextView) parent.findViewById(R.id.value);
        textKey.setText(R.string.status_time);
        textValue.setText(bean.modifyTime);

        textValue = (TextView) findViewById(R.id.mark);
        textValue.setText(bean.mark);

        WebView webView = (WebView) findViewById(R.id.note);
        String str = "<html><head><title>欢迎你</title></head><body>"
                + bean.applyAllOpinion
                +"</body></html>";

        webView.loadDataWithBaseURL(null, str, "text/html", "utf-8", null);
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
