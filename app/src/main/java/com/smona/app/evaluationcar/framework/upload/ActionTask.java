package com.smona.app.evaluationcar.framework.upload;

/**
 * Created by motianhu on 4/5/17.
 */

public abstract class ActionTask {
    public int mImageId;
    public String mCarBillId;
    public String userName;
    public ActionTask mNextTask;
    public boolean mSuccess;

    public abstract void startTask();

    protected void nextTask(String carBillId, boolean success) {
        //单号和是否成功传递给下一个任务
        if (mNextTask != null) {
            mNextTask.mSuccess = success;
            mNextTask.mCarBillId = carBillId;
            mNextTask.startTask();
        }
    }

    public boolean isSelf(ActionTask task) {
        return ((mCarBillId != null) && mCarBillId.equals(task.mCarBillId))
                ||
                ((mCarBillId == null) && mImageId == task.mImageId);
    }
}
