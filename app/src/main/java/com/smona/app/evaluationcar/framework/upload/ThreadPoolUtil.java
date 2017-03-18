package com.smona.app.evaluationcar.framework.upload;

import android.os.Message;

import com.smona.app.evaluationcar.util.CarLog;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;

public class ThreadPoolUtil {
    private static final String TAG = "ThreadPoolUtil";
    private static volatile ExecutorService sThreadPool;

    private static ExecutorService getThreadPool() {
        if (sThreadPool == null) {
            sThreadPool = Executors.newCachedThreadPool();
        }
        return sThreadPool;
    }

    public static void post(Runnable task) {
        try {
            getThreadPool().execute(task);
        } catch (RejectedExecutionException e) {
            CarLog.e(TAG, "ThreadPool name = " + task.getClass() + e);
        }
    }

    public static void postDelayed(final Runnable task, long delayMillis) {
        Message msg = new Message();
        msg.obj = task;
        //AcgnApp.getInstance().mMainHandler.sendMessageDelayed(msg, delayMillis);
    }

    public static void removeCallbacks(Runnable task) {
        //AcgnApp.getInstance().mMainHandler.removeCallbacksAndMessages(task);
    }

}
