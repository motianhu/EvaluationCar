package com.smona.app.evaluationcar.util;

import android.util.Log;

/**
 * Created by Moth on 2017/2/16.
 */

public class CarLog {
    private static final String TAG = "Carlog";
    public static void d(Object clazz, String msg) {
        Log.d(TAG, clazz.getClass().getName() + "-" + msg);
    }

    public static void i(Object clazz, String msg) {
        Log.i(TAG, clazz.getClass().getName() + "-" + msg);
    }

    public static void e(Object clazz, String msg) {
        Log.e(TAG, clazz.getClass().getName() + "-" + msg);
    }
}
