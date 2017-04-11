package com.smona.app.evaluationcar.ui.common.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.smona.app.evaluationcar.data.bean.UserInfoBean;
import com.smona.app.evaluationcar.data.item.UserItem;
import com.smona.app.evaluationcar.data.model.ResUserModel;
import com.smona.app.evaluationcar.framework.cache.CacheDelegator;
import com.smona.app.evaluationcar.framework.json.JsonParse;
import com.smona.app.evaluationcar.util.CarLog;
import com.smona.app.evaluationcar.util.UrlConstants;

/**
 * Created by motianhu on 4/11/17.
 */

public class UserActivity extends PermissionActivity {
    private static final String TAG = UserActivity.class.getSimpleName();
    protected UserInfoBean mUserBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUserInfo();
    }

    private void initUserInfo() {
        UserItem item = new UserItem();
        item.readSelf(this);
        String url = UrlConstants.getInterface(UrlConstants.CHECK_USER) + "?userName=" + item.mId;
        String cacheData = CacheDelegator.getInstance().loadCacheByUrl(url);
        if (!TextUtils.isEmpty(cacheData)) {
            ResUserModel resUser = JsonParse.parseJson(cacheData, ResUserModel.class);
            mUserBean = resUser.object;
            CarLog.d(TAG, "initUserInfo " + mUserBean);
        }
    }

    public UserInfoBean getUserBean() {
        return mUserBean;
    }
}
