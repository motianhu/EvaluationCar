package com.smona.app.evaluationcar.framework.upload;

import com.smona.app.evaluationcar.data.item.UploadItem;
import com.smona.app.evaluationcar.util.CarLog;

public class UploadTask {

    public final static int STATE_NONE = 0;
    public final static int STATE_RUNNING = 1;
    public final static int STATE_COMPLETED = 2;

    protected UploadItem mUploadBean;
    private OnUploadCompleteListener mOnUploadCompleteListener;

    protected int mErrorCode = ResultCode.NONE;
    private int mState = STATE_NONE;

    public UploadTask(UploadItem uploadBean) {
        this.mUploadBean = uploadBean;
    }

    public void setOnUploadCompleteListener(OnUploadCompleteListener onUploadCompleteListener) {
        this.mOnUploadCompleteListener = onUploadCompleteListener;
    }

    public void start() {
        if (STATE_RUNNING == mState) {
            return;
        }
        mState = STATE_RUNNING;
        UploadTaskExecutor.pushTask(this);
    }

    public int getErrorCode() {
        return mErrorCode;
    }

    public void stop() {
        mOnUploadCompleteListener = null;
    }

    public int getState() {
        return mState;
    }

    protected void onUploadComplete() {
        mState = STATE_COMPLETED;
        CarLog.d(this, "listener = " + mOnUploadCompleteListener);
        if (null != mOnUploadCompleteListener) {
            mOnUploadCompleteListener.onUploadComplete(this);
        }
    }
}
