package com.smona.app.evaluationcar.framework.upload;

import android.os.Handler;

import com.smona.app.evaluationcar.util.CarLog;

import java.util.LinkedList;

public final class UploadTaskExecutor {
    private static LinkedList<ActionTask> sTasks = new LinkedList<ActionTask>();
    private static int sRunCount = 0;

    private static Handler sHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            ActionTask waitTask = sTasks.poll();
            if (null != waitTask) {
                waitTask.startTask();
            } else {
                sRunCount--;
            }
            CarLog.d(this, "sRunCount:" + sRunCount + "  sTasks.size():" + sTasks.size());
        }
    };

    public static void pushTask(ActionTask task) {
        if (sRunCount >= 1) {
            sTasks.offer(task);
        } else {
            task.startTask();
            sRunCount++;
        }
    }

    public static void nextTask() {
        sHandler.sendEmptyMessage(0);
    }
}
