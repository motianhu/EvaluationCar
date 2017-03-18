package com.smona.app.evaluationcar.framework.upload;

import com.smona.app.evaluationcar.util.CarLog;

public class UploadTask {

    private static final String TAG = "Api.UploadTask";

    public final static int STATE_NONE = 0;
    public final static int STATE_RUNNING = 1;
    public final static int STATE_COMPLETED = 2;

    protected final static int TYPE_DEFAULT = 0;
    public final static int TYPE_FACE = 1;

    private String mFilePath;
    private int mType = TYPE_DEFAULT;
    private Object mTag;
    private OnUploadCompleteListener mOnUploadCompleteListener;

    protected int mErrorCode = ErrorCode.NONE;
    protected String mResponseImgUrl;
    private int mState = STATE_NONE;

    public UploadTask(String filePath) {
        this.mFilePath = filePath;
    }

    public UploadTask(String filePath, int type) {
        this.mFilePath = filePath;
        this.mType = type;
    }

    public int getType() {
        return mType;
    }

    public Object getTag() {
        return mTag;
    }

    public void setTag(Object tag) {
        this.mTag = tag;
    }

    public String getFilePath() {
        return mFilePath;
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

    public String getResponseImgUrl() {
        return mResponseImgUrl;
    }

    public void stop() {
        mOnUploadCompleteListener = null;
    }

    public int getState() {
        return mState;
    }

    protected void onUploadComplete() {
        mState = STATE_COMPLETED;
        CarLog.d(TAG, "listener = " + mOnUploadCompleteListener);
        if (null != mOnUploadCompleteListener) {
            mOnUploadCompleteListener.onUploadComplete(this);
        }
    }

    public static interface OnUploadCompleteListener {
        void onUploadComplete(UploadTask uploadTask);
    }
}
