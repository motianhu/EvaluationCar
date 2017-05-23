package com.smona.app.evaluationcar.ui.evaluation.preevaluation.quick;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.WebView;
import android.widget.RelativeLayout;

import com.smona.app.evaluationcar.R;
import com.smona.app.evaluationcar.data.bean.CarImageBean;
import com.smona.app.evaluationcar.ui.common.base.LimitGridView;
import com.smona.app.evaluationcar.ui.evaluation.ImageModelAdapter;
import com.smona.app.evaluationcar.ui.evaluation.ImageModelDelegator;
import com.smona.app.evaluationcar.util.ViewUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by motianhu on 4/15/17.
 */

public class PreEvaluationQuickLayer extends RelativeLayout {
    private static final String TAG = PreEvaluationQuickLayer.class.getSimpleName();

    private View mRease;
    private WebView mReaseWebView;

    private LimitGridView mBaseGrid;
    private QuickImageModelAdapter mBaseAdapter;
    private List<CarImageBean> mBaseData;

    private LimitGridView mSupplementGrid;
    private QuickImageModelAdapter mSupplementAdapter;
    private List<CarImageBean> mSupplementData;

    public PreEvaluationQuickLayer(Context context, AttributeSet attrs) {
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
        mBaseAdapter = new QuickImageModelAdapter(getContext());
        mBaseGrid.setAdapter(mBaseAdapter);

        //补充照片
        mSupplementGrid = (LimitGridView) findViewById(R.id.class_supplement);
        mSupplementAdapter = new QuickImageModelAdapter(getContext());
        mSupplementGrid.setAdapter(mSupplementAdapter);

        initImageList();
        updateImageViews();
    }

    private void initImageList() {
        mBaseData = new ArrayList<CarImageBean>();
        int index = 0;

        //登记证书首页
        CarImageBean bean = new CarImageBean();
        bean.imageClass = ImageModelDelegator.getInstance().getImageClassForType(ImageModelDelegator.IMAGE_Registration);
        bean.displayName = ImageModelDelegator.getInstance().getDisplayName(ImageModelDelegator.IMAGE_Registration, index);
        bean.imageSeqNum = index + 1;
        mBaseData.add(bean);

        bean = new CarImageBean();
        index = 1;
        bean.imageClass = ImageModelDelegator.getInstance().getImageClassForType(ImageModelDelegator.IMAGE_Registration);
        bean.displayName = ImageModelDelegator.getInstance().getDisplayName(ImageModelDelegator.IMAGE_Registration, index);
        bean.imageSeqNum = index + 1;
        mBaseData.add(bean);


        mSupplementData = new ArrayList<CarImageBean>();

        //车左前45度
        bean = new CarImageBean();
        index = 0;
        bean.imageClass = ImageModelDelegator.getInstance().getImageClassForType(ImageModelDelegator.IMAGE_CarBody);
        bean.displayName = ImageModelDelegator.getInstance().getDisplayName(ImageModelDelegator.IMAGE_CarBody, index);
        bean.imageSeqNum = index + 1;
        mSupplementData.add(bean);

        //中控台含排挡杆
        bean = new CarImageBean();
        index = 2;
        bean.imageClass = ImageModelDelegator.getInstance().getImageClassForType(ImageModelDelegator.IMAGE_VehicleInterior);
        bean.displayName = ImageModelDelegator.getInstance().getDisplayName(ImageModelDelegator.IMAGE_VehicleInterior, index);
        bean.imageSeqNum = index + 1;
        mSupplementData.add(bean);
    }

    private void updateImageViews() {
        mBaseAdapter.update(mBaseData);
        mSupplementAdapter.update(mSupplementData);
    }

}
