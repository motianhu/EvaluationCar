package com.smona.app.evaluationcar.ui.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.smona.app.evaluationcar.R;
import com.smona.app.evaluationcar.business.ResponseCallback;
import com.smona.app.evaluationcar.business.param.UserParam;
import com.smona.app.evaluationcar.data.bean.UserInfoBean;
import com.smona.app.evaluationcar.data.item.UserItem;
import com.smona.app.evaluationcar.data.model.ResUserModel;
import com.smona.app.evaluationcar.framework.cache.CacheDelegator;
import com.smona.app.evaluationcar.framework.cache.DataDelegator;
import com.smona.app.evaluationcar.framework.json.JsonParse;
import com.smona.app.evaluationcar.ui.common.activity.HeaderActivity;
import com.smona.app.evaluationcar.util.CarLog;
import com.smona.app.evaluationcar.util.UrlConstants;
import com.smona.app.evaluationcar.util.ViewUtil;

/**
 * Created by motianhu on 4/14/17.
 */

public class MineActivity extends HeaderActivity {
    private static final String TAG = MineActivity.class.getSimpleName();

    private View mContent;
    private View mLoading;
    private View mNoContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        if (mUserBean == null) {
            requestUserInfo();
        } else {
            refreshViews(mUserBean);
        }
    }

    public void initViews() {
        mContent = findViewById(R.id.content);
        mLoading = findViewById(R.id.loading);
        mNoContent = findViewById(R.id.no_content);
        mNoContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestUserInfo();
            }
        });
    }

    private void requestUserInfo() {
        setLoading();
        final UserItem user = new UserItem();
        user.readSelf(this);
        UserParam param = new UserParam();
        param.userName = user.mId;
        param.password = user.mPwd;
        DataDelegator.getInstance().checkUser(param, new ResponseCallback<String>() {
            @Override
            public void onSuccess(String result) {
                ResUserModel normal = JsonParse.parseJson(result, ResUserModel.class);
                CarLog.d(TAG, "onSuccess normal: " + normal);
                if(normal.success) {
                    String url = UrlConstants.getInterface(UrlConstants.CHECK_USER) + "?userName=" + user.mId;
                    CacheDelegator.getInstance().saveNewCacheByUrl(url, result);
                    runUI(normal.object);
                } else {
                    runUI(null);
                }
            }

            @Override
            public void onFailed(String error) {
                runUI(null);
                CarLog.d(TAG, "onError ex: " + error);
            }
        });
    }

    private void runUI(final UserInfoBean bean) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(bean != null) {
                    refreshViews(bean);
                } else {
                    setNoData();
                }
            }
        });
    }


    private void refreshViews(UserInfoBean bean) {
        setHasData();

        ViewGroup parent = (ViewGroup) findViewById(R.id.name);
        TextView key = (TextView) parent.findViewById(R.id.key);
        key.setText(R.string.mine_display_name);
        TextView value = (TextView) parent.findViewById(R.id.value);
        value.setText(bean.userChineseName);


        parent = (ViewGroup) findViewById(R.id.company);
        key = (TextView) parent.findViewById(R.id.key);
        key.setText(R.string.mine_belong_company);
        value = (TextView) parent.findViewById(R.id.value);
        value.setText(bean.companyName);
    }

    private void setHasData() {
        ViewUtil.setViewVisible(mNoContent, false);
        ViewUtil.setViewVisible(mLoading, false);
        ViewUtil.setViewVisible(mContent, true);
    }

    private void setNoData() {
        ViewUtil.setViewVisible(mNoContent, true);
        ViewUtil.setViewVisible(mLoading, false);
        ViewUtil.setViewVisible(mContent, false);
    }

    private void setLoading() {
        ViewUtil.setViewVisible(mNoContent, false);
        ViewUtil.setViewVisible(mLoading, true);
        ViewUtil.setViewVisible(mContent, false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mine;
    }

    @Override
    protected boolean showDelete() {
        return false;
    }

    @Override
    protected int getHeaderTitle() {
        return R.string.mine_info;
    }
}
