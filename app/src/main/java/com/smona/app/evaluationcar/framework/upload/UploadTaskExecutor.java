package com.smona.app.evaluationcar.framework.upload;

import android.os.Handler;

import com.smona.app.evaluationcar.business.HttpProxy;
import com.smona.app.evaluationcar.util.CarLog;
import java.util.LinkedList;

public final class UploadTaskExecutor {
    private static LinkedList<UploadImageTask> sTasks = new LinkedList<UploadImageTask>();
    private static int sRunCount = 0;

    private static Handler sHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            UploadImageTask waitTask = sTasks.poll();
            if (null != waitTask) {
                HttpProxy.getInstance().uploadImage(waitTask.userName, waitTask.carImageBean, waitTask.callback);
            } else {
                sRunCount--;
            }
            CarLog.d(this, "sRunCount:" +sRunCount+ "  sTasks.size():" + sTasks.size());
        }
    };

    public static void pushTask(UploadImageTask task) {
        if (sRunCount >= 1) {
            sTasks.offer(task);
        } else {
            HttpProxy.getInstance().uploadImage(task.userName, task.carImageBean, task.callback);
            sRunCount++;
        }
    }

    public static void nextTask() {
        sHandler.sendEmptyMessage(0);
    }
}
