package com.smona.app.evaluationcar.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.smona.app.evaluationcar.business.ResponseCallback;
import com.smona.app.evaluationcar.data.event.UpgradeEvent;
import com.smona.app.evaluationcar.data.model.ResUpgradeApi;
import com.smona.app.evaluationcar.framework.cache.DataDelegator;
import com.smona.app.evaluationcar.framework.event.EventProxy;
import com.smona.app.evaluationcar.framework.json.JsonParse;
import com.smona.app.evaluationcar.ui.UpgradeUtils;
import com.smona.app.evaluationcar.util.CarLog;
import com.smona.app.evaluationcar.util.Utils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by motianhu on 4/14/17.
 */

public class UpgradeService extends Service {
    private static final String TAG = UpgradeService.class.getSimpleName();

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        CarLog.d(TAG, "onCreate");
        EventProxy.register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        CarLog.d(TAG, "onDestroy");
        EventProxy.unregister(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        CarLog.d(TAG, "onStartCommand");
        requestUpgradeInfo();
        return super.onStartCommand(intent, flags, startId);
    }

    private void requestUpgradeInfo() {
        DataDelegator.getInstance().requestUpgradeInfo(mUpgradeCallback);
    }

    private ResponseCallback<String> mUpgradeCallback = new ResponseCallback<String>() {
        @Override
        public void onFailed(String error) {
            CarLog.d(TAG, "onFailed error=" + error);
        }

        @Override
        public void onSuccess(String content) {
            ResUpgradeApi newBaseApi = JsonParse.parseJson(content, ResUpgradeApi.class);
            CarLog.d(TAG, "mUpgradeCallback onSuccess result=" + content);
            if (newBaseApi != null) {
                //if (UpgradeUtils.compareVersion(newBaseApi.versionName, Utils.getVersion(UpgradeService.this))) {
                    UpgradeEvent upgradeEvent = new UpgradeEvent();
                    upgradeEvent.mResBaseApi = newBaseApi;
                    EventProxy.post(upgradeEvent);
                //}
            }
        }
    };

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void update(UpgradeEvent event) {
        CarLog.d(TAG, "update");
        UpgradeUtils.showUpdataDialog(this, event.mResBaseApi);
    }
}
