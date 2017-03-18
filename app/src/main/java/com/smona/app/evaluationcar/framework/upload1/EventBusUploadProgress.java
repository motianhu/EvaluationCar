package com.smona.app.evaluationcar.framework.upload1;

/**
 * Created by ligang on 10/12/16.
 */

public class EventBusUploadProgress {
    private String id;
    private float allCount;
    private float sucessCount;
    private float uploadAllByteCount;
    private float uploadByteCount;
    private boolean successStatus = true;

    public float getAllCount() {
        return allCount;
    }

    public void setAllCount(float allCount) {
        this.allCount = allCount;
    }

    public float getUploadAllByteCount() {
        return uploadAllByteCount;
    }

    public void setUploadAllByteCount(float uploadAllByteCount) {
        this.uploadAllByteCount = uploadAllByteCount;
    }

    public float getUploadByteCount() {
        return uploadByteCount;
    }

    public void setUploadByteCount(float uploadByteCount) {
        this.uploadByteCount = uploadByteCount;
    }

    public float getSucessCount() {
        return sucessCount;
    }

    public void setSucessCount(float sucessCount) {
        this.sucessCount = sucessCount;
    }

    public boolean isSuccessStatus() {
        return successStatus;
    }

    public void setSuccessStatus(boolean successStatus) {
        this.successStatus = successStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
