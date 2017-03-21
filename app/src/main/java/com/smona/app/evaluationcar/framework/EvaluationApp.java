package com.smona.app.evaluationcar.framework;

import android.app.Application;

import com.smona.app.evaluationcar.framework.http.HttpProxy;
import com.smona.app.evaluationcar.framework.imageloader.ImageLoaderProxy;
import com.smona.app.evaluationcar.framework.provider.DBDelegator;
import com.smona.app.evaluationcar.framework.provider.EvaluationProvider;
import com.smona.app.evaluationcar.framework.push.PushProxy;
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
        HttpProxy.init(this);
    }


    public void setWallpaperProvider(EvaluationProvider provider) {
        mProvider = new WeakReference<EvaluationProvider>(provider);
    }

    public EvaluationProvider getWallpaperProvider() {
        return mProvider.get();
    }
}
