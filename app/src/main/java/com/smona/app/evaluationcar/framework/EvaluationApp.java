package com.smona.app.evaluationcar.framework;

import android.app.Application;

import com.smona.app.evaluationcar.framework.imageloader.ImageLoaderProxy;
import com.smona.app.evaluationcar.framework.provider.EvaluationProvider;

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
    }


    public void setWallpaperProvider(EvaluationProvider provider) {
        mProvider = new WeakReference<EvaluationProvider>(provider);
    }

    public EvaluationProvider getWallpaperProvider() {
        return mProvider.get();
    }
}
