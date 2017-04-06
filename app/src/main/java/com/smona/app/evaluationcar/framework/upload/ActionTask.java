package com.smona.app.evaluationcar.framework.upload;

/**
 * Created by motianhu on 4/5/17.
 */

public abstract class ActionTask {

    public String mCarBillId;
    public String userName;
    public ActionTask mNextTask;

    public abstract void startTask();

    protected void nextTask(String carBillId) {
        mCarBillId = carBillId;
        if(mNextTask != null) {
            mNextTask.startTask();
        }
    }
}
