package com.smona.app.evaluationcar.framework;

import android.app.Application;

import com.smona.app.evaluationcar.framework.imageloader.ImageLoaderConfig;
import com.smona.app.evaluationcar.framework.imageloader.ImageLoaderProxy;

/**
 * Created by Moth on 2016/12/18.
 */

public class EvaluationApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ImageLoaderProxy.init(this);
    }
}
