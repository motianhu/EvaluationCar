package com.smona.app.evaluationcar.framework;

import android.app.Application;

import com.smona.app.evaluationcar.business.HttpDelegator;
import com.smona.app.evaluationcar.framework.cache.DataDelegator;
import com.smona.app.evaluationcar.framework.chatclient.ChatClientProxy;
import com.smona.app.evaluationcar.framework.crashreport.CrashReportProxy;
import com.smona.app.evaluationcar.framework.imageloader.ImageLoaderProxy;
import com.smona.app.evaluationcar.framework.provider.DBDelegator;
import com.smona.app.evaluationcar.framework.provider.EvaluationProvider;
import com.smona.app.evaluationcar.framework.provider.GenerateMaxId;
import com.smona.app.evaluationcar.framework.push.PushProxy;
import com.smona.app.evaluationcar.framework.storage.DeviceStorageManager;
import com.smona.app.evaluationcar.ui.evaluation.ImageModelDelegator;
import com.smona.app.evaluationcar.util.ScreenInfo;

import java.lang.ref.WeakReference;

/**
 * Created by Moth on 2016/12/18.
 */

public class EvaluationApp extends Application {
    private WeakReference<EvaluationProvider> mProvider;

    @Override
    public void onCreate() {
        super.onCreate();
        ImageLoaderProxy.init(this);
        PushProxy.init(this);
        ScreenInfo.getInstance().init(this);
        DBDelegator.getInstance().init(this);
        DataDelegator.getInstance().init(this);
        HttpDelegator.getInstance().init(this);
        GenerateMaxId.getInstance().initMaxId();
        ImageModelDelegator.getInstance().init(this);
        CrashReportProxy.init(this);
        DeviceStorageManager.getInstance().setContext(this);
        DeviceStorageManager.getInstance().initPath();
        ChatClientProxy.getInstance().init(this);
    }

    public EvaluationProvider getWallpaperProvider() {
        return mProvider.get();
    }

    public void setWallpaperProvider(EvaluationProvider provider) {
        mProvider = new WeakReference<EvaluationProvider>(provider);
    }
}
