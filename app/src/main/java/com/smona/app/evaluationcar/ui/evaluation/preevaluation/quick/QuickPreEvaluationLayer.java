package com.smona.app.evaluationcar.ui.evaluation.preevaluation.quick;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.WebView;
import android.widget.RelativeLayout;

import com.smona.app.evaluationcar.R;
import com.smona.app.evaluationcar.data.bean.CarImageBean;
import com.smona.app.evaluationcar.ui.common.base.LimitGridView;
import com.smona.app.evaluationcar.ui.evaluation.ImageModelDelegator;
import com.smona.app.evaluationcar.util.ViewUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by motianhu on 4/15/17.
 */

public class QuickPreEvaluationLayer extends RelativeLayout {
    private static final String TAG = QuickPreEvaluationLayer.class.getSimpleName();

    private View mRease;
    private WebView mReaseWebView;

    private LimitGridView mBaseGrid;
    private QuickImageModelAdapter mBaseAdapter;
    private List<CarImageBean> mBaseData;

    private LimitGridView mSupplementGrid;
    private QuickImageModelAdapter mSupplementAdapter;
    private List<CarImageBean> mSupplementData;

    public QuickPreEvaluationLayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //驳回原因
        mRease = findViewById(R.id.reason);
        mReaseWebView = (WebView) findViewById(R.id.reason_webview);
        ViewUtil.setViewVisible(mRease, false);
        ViewUtil.setViewVisible(mReaseWebView, false);

        //基础照片
        mBaseGrid = (LimitGridView) findViewById(R.id.class_base);
        mBaseAdapter = new QuickImageModelAdapter(getContext(), ImageModelDelegator.QUICK_BASE);
        mBaseGrid.setAdapter(mBaseAdapter);

        //补充照片
        mSupplementGrid = (LimitGridView) findViewById(R.id.class_supplement);
        mSupplementAdapter = new QuickImageModelAdapter(getContext(), ImageModelDelegator.QUICK_SUPPLEMENT);
        mSupplementGrid.setAdapter(mSupplementAdapter);

        initImageList();
        updateImageViews();
    }

    private void initImageList() {
        mBaseData = new ArrayList<>();
        mBaseData.addAll(ImageModelDelegator.getInstance().getQuickBaseModel(ImageModelDelegator.QUICK_BASE));

        mSupplementData = new ArrayList<>();
        mSupplementData.addAll(ImageModelDelegator.getInstance().getQuickBaseModel(ImageModelDelegator.QUICK_SUPPLEMENT));
    }

    private void updateImageViews() {
        mBaseAdapter.update(mBaseData);
        mSupplementAdapter.update(mSupplementData);
    }

}
