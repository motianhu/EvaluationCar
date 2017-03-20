package com.smona.app.evaluationcar.framework.upload;

import android.os.Handler;
import com.smona.app.evaluationcar.util.CarLog;
import java.util.LinkedList;

public final class UploadTaskExecutor {
    private static LinkedList<UploadTask> sTasks = new LinkedList<UploadTask>();
    private static int sRunCount;

    private static Handler sHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            UploadTask task = (UploadTask) msg.obj;
            task.onUploadComplete();

            UploadTask waitTask = sTasks.poll();
            if (null != waitTask) {
                ThreadPoolUtil.post(new UploadRunable(waitTask));
            } else {
                sRunCount--;
            }
            CarLog.d(this, "sRunCount:" +sRunCount+ "  sTasks.size():" + sTasks.size());
        }
    };

    protected static void pushTask(UploadTask task) {
        if (sRunCount >= 3) {
            sTasks.offer(task);
        } else {
            ThreadPoolUtil.post(new UploadRunable(task));
            sRunCount++;
        }
    }

    protected static void onExecuteComplete(UploadTask task) {
        sHandler.obtainMessage(0, task).sendToTarget();
    }
}
