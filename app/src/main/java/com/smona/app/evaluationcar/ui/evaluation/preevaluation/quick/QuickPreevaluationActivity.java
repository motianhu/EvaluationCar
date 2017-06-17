package com.smona.app.evaluationcar.ui.evaluation.preevaluation.quick;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;

import com.smona.app.evaluationcar.R;
import com.smona.app.evaluationcar.business.HttpDelegator;
import com.smona.app.evaluationcar.business.ResponseCallback;
import com.smona.app.evaluationcar.data.bean.QuickPreCarBillBean;
import com.smona.app.evaluationcar.data.bean.QuickPreCarImageBean;
import com.smona.app.evaluationcar.data.event.EvaActionEvent;
import com.smona.app.evaluationcar.data.event.background.TaskSubEvent;
import com.smona.app.evaluationcar.data.model.ResCarImagePage;
import com.smona.app.evaluationcar.data.model.ResQuickPreCarImagePage;
import com.smona.app.evaluationcar.framework.event.EventProxy;
import com.smona.app.evaluationcar.framework.json.JsonParse;
import com.smona.app.evaluationcar.framework.provider.DBDelegator;
import com.smona.app.evaluationcar.ui.common.activity.HeaderActivity;
import com.smona.app.evaluationcar.ui.common.base.LimitGridView;
import com.smona.app.evaluationcar.util.ActivityUtils;
import com.smona.app.evaluationcar.util.CacheContants;
import com.smona.app.evaluationcar.util.CarLog;
import com.smona.app.evaluationcar.util.DateUtils;
import com.smona.app.evaluationcar.util.SPUtil;
import com.smona.app.evaluationcar.util.StatusUtils;
import com.smona.app.evaluationcar.util.ToastUtils;
import com.smona.app.evaluationcar.util.ViewUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by motianhu on 5/23/17.
 */

public class QuickPreevaluationActivity extends HeaderActivity {

    private static final String TAG = QuickPreevaluationActivity.class.getSimpleName();

    private View mRease;
    private WebView mReaseWebView;

    private LimitGridView mBaseGrid;
    private QuickImageModelAdapter mBaseAdapter;
    private List<QuickPreCarImageBean> mBaseData;

    private LimitGridView mSupplementGrid;
    private QuickImageModelAdapter mSupplementAdapter;
    private List<QuickPreCarImageBean> mSupplementData;

    private int mCurStatus = StatusUtils.BILL_STATUS_NONE;
    private String mQuickPreCarBillId = null;
    private QuickPreCarBillBean mPreQuickPreCarBillBean;
    private int mImageId = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_preevaluation_quick;
    }

    @Override
    protected boolean showDelete() {
        return false;
    }

    @Override
    protected int getHeaderTitle() {
        return R.string.evalution_pre_quick;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventProxy.register(this);
        //传递过来的状态和值
        initData();
        //控件初始化
        initViews();
        //数据链表初始化
        initImageList();
        //如果是驳回的，则获取服务器上的图片,保存到数据库
        requestImageForPreCarBillId();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initStatus();
        initCarImage();
        notifyReloadCarImage();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventProxy.unregister(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        CarLog.d(TAG, "requestCode: " + requestCode + "");
        if (requestCode == ActivityUtils.ACTION_CAMERA && resultCode == Activity.RESULT_OK && data != null) {

        }
    }

    private void initData() {
        initStatus();
        initCarImage();
        initCarBill();
    }

    private void initStatus() {
        if (!statusIsNone()) {
            return;
        }
        mCurStatus = (int) SPUtil.get(this, CacheContants.QUICK_BILL_STATUS, StatusUtils.BILL_STATUS_NONE);
        CarLog.d(TAG, "initStatus mCurStatus=" + mCurStatus);
    }

    private void initCarImage() {
        if (mImageId > 0) {
            return;
        }
        mImageId = (int) SPUtil.get(this, CacheContants.QUICK_IMAGEID, 0);
        CarLog.d(TAG, "initCarImage mImageId=" + mImageId);
    }

    private void initCarBill() {
        mQuickPreCarBillId = (String) SPUtil.get(this, CacheContants.QUICK_CARBILLID, "");
        CarLog.d(TAG, "initCarBill mQuickPreCarBillId=" + mQuickPreCarBillId);

        if (!TextUtils.isEmpty(mQuickPreCarBillId)) {
            mPreQuickPreCarBillBean = DBDelegator.getInstance().queryQuickPreCarBill(mQuickPreCarBillId);
        }
    }

    private void initViews() {
        //驳回原因
        mRease = findViewById(R.id.reason);
        mReaseWebView = (WebView) findViewById(R.id.reason_webview);
        ViewUtil.setViewVisible(mRease, false);
        ViewUtil.setViewVisible(mReaseWebView, false);

        //基础照片
        mBaseGrid = (LimitGridView) findViewById(R.id.class_base);
        mBaseAdapter = new QuickImageModelAdapter(this, QuickImageModelDelegator.QUICK_BASE);
        mBaseGrid.setAdapter(mBaseAdapter);

        //补充照片
        mSupplementGrid = (LimitGridView) findViewById(R.id.class_supplement);
        mSupplementAdapter = new QuickImageModelAdapter(this, QuickImageModelDelegator.QUICK_SUPPLEMENT);
        mSupplementGrid.setAdapter(mSupplementAdapter);
    }

    private void initImageList() {
        mBaseData = new ArrayList<>();
        mSupplementData = new ArrayList<>();
    }

    private void requestImageForPreCarBillId() {
        if (TextUtils.isEmpty(mQuickPreCarBillId)) {
            return;
        }
        HttpDelegator.getInstance().getQuickPreCarbillImages(mUserItem.mId, mQuickPreCarBillId, new ResponseCallback<String>() {
            @Override
            public void onSuccess(String result) {
                CarLog.d(TAG, "getCarbillImages onSuccess: " + result);
                ResQuickPreCarImagePage resp = JsonParse.parseJson(result, ResQuickPreCarImagePage.class);
                if (resp.total > 0) {
                    QuickPreCarImageBean tempBean = null;
                    for (QuickPreCarImageBean bean : resp.data) {
                        tempBean = DBDelegator.getInstance().queryImageForIdAndClass(bean.carBillId, bean.imageClass, bean.imageSeqNum);
                        if (tempBean == null) {
                            //依赖CarBillId更新
                            DBDelegator.getInstance().insertQuickPreCarImage(bean);
                        } else {
                            //依赖ImageID更新:可能是return的单,可能是save的单
                            tempBean.imageThumbPath = bean.imageThumbPath;
                            tempBean.imagePath = bean.imagePath;
                            DBDelegator.getInstance().updateQuickPreCarImage(tempBean);
                        }
                    }
                    notifyReloadCarImage();
                }
            }

            @Override
            public void onFailed(String error) {
                CarLog.d(TAG, "onError ex: " + error);
            }
        });
    }

    private void notifyReloadCarImage() {
        TaskSubEvent event = new TaskSubEvent();
        event.action = TaskSubEvent.ACTION_RELOAD;
        EventProxy.post(event);
        CarLog.d(TAG, "notifyReloadCarImage");
    }

    private void notifyActionMain(int action) {
        EvaActionEvent event = new EvaActionEvent();
        event.action = action;
        EventProxy.post(event);
        CarLog.d(TAG, "notifyActionMain");
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void actionSubEvent(TaskSubEvent event) {
        int action = event.action;
        CarLog.d(TAG, "actionSubEvent action= " + action);
        if (action == TaskSubEvent.ACTION_TASK) {
            QuickPreCarBillBean bean = (QuickPreCarBillBean) event.obj;
            if (statusIsNone()) {
                startTarkForReturn(bean);
            } else {
                startTarkForSave(bean);
            }
            notifyActionMain(EvaActionEvent.FINISH);
        } else {
            clearImageList();
            reloadImageList();
            notifyActionMain(EvaActionEvent.REFRESH);
        }
    }

    private void updateImageViews() {
        mBaseAdapter.update(mBaseData);
        mSupplementAdapter.update(mSupplementData);
    }

    private void reloadImageList() {
        initImageData(mBaseData, QuickImageModelDelegator.QUICK_BASE);
        initImageData(mSupplementData, QuickImageModelDelegator.QUICK_SUPPLEMENT);
    }

    private void clearImageList() {
        clearImageList(mBaseAdapter, mBaseData);
        clearImageList(mSupplementAdapter, mSupplementData);
    }

    private void clearImageList(QuickImageModelAdapter adapter, List<QuickPreCarImageBean> dataList) {
        if (adapter.isNeedReload()) {
            dataList.clear();
        }
    }

    private void initImageData(List<QuickPreCarImageBean> data, int type) {
        if (statusIsReturn()) {
            List<QuickPreCarImageBean> tempData = QuickImageModelDelegator.getInstance().getHttpModel(type, mQuickPreCarBillId);
            data.addAll(tempData);
        } else {
            List<QuickPreCarImageBean> tempData = QuickImageModelDelegator.getInstance().getSaveModel(type, mImageId);
            data.addAll(tempData);
        }
    }

    public void startTarkForReturn(QuickPreCarBillBean bean) {
        QuickPreCarBillBean carBillBean = DBDelegator.getInstance().queryQuickPreCarBill(mQuickPreCarBillId);
        carBillBean.preSalePrice = bean.preSalePrice;
        carBillBean.mark = bean.mark;
        carBillBean.uploadStatus = StatusUtils.BILL_UPLOAD_STATUS_UPLOADING;
        DBDelegator.getInstance().updatePreCarBill(carBillBean);

        ActivityUtils.startUpService(this);
    }

    public void startTarkForSave(QuickPreCarBillBean bean) {
        QuickPreCarBillBean localBean = DBDelegator.getInstance().queryLocalQuickPreCarbill(bean.imageId);
        localBean.createTime = TextUtils.isEmpty(localBean.createTime) ? DateUtils.getCurrDate() : localBean.createTime;
        localBean.modifyTime = DateUtils.getCurrDate();
        localBean.uploadStatus = StatusUtils.BILL_UPLOAD_STATUS_UPLOADING;
        localBean.preSalePrice = bean.preSalePrice;
        localBean.mark = bean.mark;
        DBDelegator.getInstance().updatePreCarBill(localBean);

        CarLog.d(TAG, "startTarkForSave localBean=" + localBean);
        ActivityUtils.startUpService(this);
    }

    private boolean statusIsNone() {
        return mCurStatus == StatusUtils.BILL_STATUS_NONE;
    }

    private boolean statusIsReturn() {
        return mCurStatus == StatusUtils.BILL_STATUS_RETURN;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void actionMainEvent(EvaActionEvent event) {
        int action = event.action;
        CarLog.d(TAG, "actionMainEvent action " + action);
        if (action == EvaActionEvent.REFRESH) {
            updateImageViews();
        } else {
            runFinish();
        }
    }

    private void runFinish() {
        ToastUtils.show(this, R.string.evaluation_submit_tips);
        finish();
    }
}
