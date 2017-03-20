package com.smona.app.evaluationcar.framework.upload;

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
}
