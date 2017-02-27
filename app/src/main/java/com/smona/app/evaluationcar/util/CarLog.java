package com.smona.app.evaluationcar.util;

import android.util.Log;

/**
 * Created by Moth on 2017/2/16.
 */

public class CarLog {
    private static final String TAG = "CarLog";

    public static void d(Object clazz, Object msg) {
        Log.d(TAG, clazz.getClass().getSimpleName() + ": " + msg);
    }

    public static void i(Object clazz, Object msg) {
        Log.i(TAG, clazz.getClass().getSimpleName() + ": " + msg);
    }

    public static void e(Object clazz, Object msg) {
        Log.e(TAG, clazz.getClass().getSimpleName() + ": " + msg);
    }
}