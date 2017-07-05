package com.smona.app.evaluationcar.ui.evaluation.preevaluation.progress;

import android.app.ProgressDialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.smona.app.evaluationcar.R;
import com.smona.app.evaluationcar.business.HttpDelegator;
import com.smona.app.evaluationcar.business.ResponseCallback;
import com.smona.app.evaluationcar.data.bean.CarBillBean;
import com.smona.app.evaluationcar.data.bean.QuickPreCarBillBean;
import com.smona.app.evaluationcar.data.item.UserItem;
import com.smona.app.evaluationcar.data.model.ResBaseModel;
import com.smona.app.evaluationcar.framework.imageloader.ImageLoaderProxy;
import com.smona.app.evaluationcar.framework.json.JsonParse;
import com.smona.app.evaluationcar.framework.provider.DBDelegator;
import com.smona.app.evaluationcar.ui.evaluation.EvaluationActivity;
import com.smona.app.evaluationcar.util.ActivityUtils;
import com.smona.app.evaluationcar.util.CarLog;
import com.smona.app.evaluationcar.util.StatusUtils;
import com.smona.app.evaluationcar.util.ToastUtils;
import com.smona.app.evaluationcar.util.UrlConstants;
import com.smona.app.evaluationcar.util.ViewUtil;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import static java.security.AccessController.getContext;

/**
 * Created by motianhu on 4/7/17.
 */

public class PreEvaluationListAdapter extends BaseAdapter implements View.OnClickListener {
    private static final String TAG = PreEvaluationListAdapter.class.getSimpleName();

    private int mScrollState = AbsListView.OnScrollListener.SCROLL_STATE_IDLE;
    private Context mContext;
    private List<QuickPreCarBillBean> mDataList = new ArrayList<>();
    private UserItem mUserItem;

    public PreEvaluationListAdapter(Context context) {
        mContext = context;
        mUserItem = new UserItem();
        mUserItem.readSelf(context);
        mUserItem.readUserProp(context);
    }

    public void update(List deltaList) {
        if (deltaList != null) {
            mDataList.addAll(mDataList.size(), deltaList);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        QuickPreCarBillBean carbill = mDataList.get(position);
        if (convertView == null) {
            convertView = ViewUtil.inflater(mContext,
                    R.layout.status_list_preevalution_item);
        }

        convertView.setOnClickListener(this);
        convertView.setTag(carbill);

        ImageView carImage = (ImageView) convertView.findViewById(R.id.carImage);
        ImageLoaderProxy.loadCornerImage(UrlConstants.getProjectInterface() + carbill.imageThumbPath, carImage);

        TextView textNum = (TextView) convertView.findViewById(R.id.carNum);
        String carTitle = TextUtils.isEmpty(carbill.carBillId) ? mContext.getString(R.string.no_carbillid) : carbill.carBillId;
        textNum.setText(mContext.getString(R.string.list_item_number) + " " + carTitle);

        TextView textTime = (TextView) convertView.findViewById(R.id.carTime);
        textTime.setText(mContext.getString(R.string.list_item_time) + " " + carbill.createTime);

        TextView textStatus = (TextView) convertView.findViewById(R.id.carStatus);
        textStatus.setText(mContext.getString(R.string.status_bill_progress) + " " + StatusUtils.PREBILL_STATUS_MAP.get(carbill.status));

        View changeCarBill = convertView.findViewById(R.id.btnSubmitCarbill);
        ViewUtil.setViewVisible(changeCarBill, StatusUtils.isPrePass(carbill.status) && TextUtils.isEmpty(carbill.normalCarBillId));
        changeCarBill.setOnClickListener(this);
        changeCarBill.setTag(carbill);

        return convertView;
    }

    @Override
    public void onClick(View v) {
        Object tag = v.getTag();
        int id = v.getId();
        if(id == R.id.btnSubmitCarbill) {
            ToastUtils.show(mContext,R.string.submit_evaluation_waiting);
            onClickChange(((QuickPreCarBillBean)tag).carBillId);
            return;
        }

        if (tag instanceof QuickPreCarBillBean) {
            QuickPreCarBillBean info = (QuickPreCarBillBean) tag;
            if(StatusUtils.isPrePass(info.status)) {
                ActivityUtils.jumpReportWebActivity(mContext, info.carBillId);
            }
        }
    }

    protected void setScrollState(int state) {
        mScrollState = state;
    }

    public void clear() {
        mDataList.clear();
    }


    //
    private ProgressDialog mProgressDiag;
    private void onClickChange(String carBillId) {
        closeDialog();
        mProgressDiag = ProgressDialog.show(mContext, mContext.getString(R.string.submit_evaluation_title), mContext.getString(R.string.submit_evaluation_waiting));
        HttpDelegator.getInstance().submitChangeCarBil(mUserItem.mId, carBillId, mChangeCarBillCallBack);
    }

    private void closeDialog() {
        if (mProgressDiag != null) {
            mProgressDiag.dismiss();
            mProgressDiag = null;
        }
    }

    private ResponseCallback<String> mChangeCarBillCallBack = new ResponseCallback<String>() {
        @Override
        public void onFailed(String error) {
            CarLog.d(TAG, "mChangeCarBillCallBack onFailed error: " + error);
            closeDialog();
            ToastUtils.show(mContext, R.string.submit_evaluation_failed);
        }

        @Override
        public void onSuccess(String content) {
            CarLog.d(TAG, "mChangeCarBillCallBack onSuccess: " + content);
            ResBaseModel resModel = JsonParse.parseJson(content, ResBaseModel.class);
            if(resModel.success) {
                HttpDelegator.getInstance().queryCarBillForId(mUserItem.mId, (String)resModel.object, mGetCarBillCallback);
            } else {
                closeDialog();
                ToastUtils.show(mContext, R.string.submit_evaluation_failed);
            }
        }
    };

    private ResponseCallback<String> mGetCarBillCallback = new ResponseCallback<String>() {
        @Override
        public void onFailed(String error) {
            CarLog.d(TAG, "mGetCarBillCallback onFailed error: " + error);
            closeDialog();
            ToastUtils.show(mContext, R.string.submit_evaluation_failed);
        }

        @Override
        public void onSuccess(String content) {
            CarLog.d(TAG, "mGetCarBillCallback onSuccess: " + content);
            CarBillBean bean = JsonParse.parseJson(content, CarBillBean.class);
            closeDialog();
            saveToDB(bean);
            ActivityUtils.jumpEvaluation(mContext, StatusUtils.BILL_STATUS_RETURN, bean.carBillId, bean.imageId, bean.leaseTerm != 0, EvaluationActivity.class);
        }
    };

    private void saveToDB(CarBillBean bean) {
        CarBillBean temp = DBDelegator.getInstance().queryCarBill(bean.carBillId);
        if (temp == null) {
            DBDelegator.getInstance().insertCarBill(bean);
        } else {
            bean.imageId = temp.imageId;
            bean.uploadStatus = temp.uploadStatus;
            DBDelegator.getInstance().updateCarBill(bean);
        }
    }
}
