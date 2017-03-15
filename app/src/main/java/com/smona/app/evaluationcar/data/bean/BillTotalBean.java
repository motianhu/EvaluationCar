package com.smona.app.evaluationcar.data.bean;

import android.os.Parcel;

/**
 * Created by Moth on 2017/3/15.
 */

public class BillTotalBean extends BaseBean {
    private int unCommitCount;
    private int passCount;
    private int notPassCount;
    private int auditingCount;
    public BillTotalBean(Parcel in) {
        super(in);
    }

    public int getUnCommitCount() {
        return unCommitCount;
    }

    public void setUnCommitCount(int unCommitCount) {
        this.unCommitCount = unCommitCount;
    }

    public int getPassCount() {
        return passCount;
    }

    public void setPassCount(int passCount) {
        this.passCount = passCount;
    }

    public int getNotPassCount() {
        return notPassCount;
    }

    public void setNotPassCount(int notPassCount) {
        this.notPassCount = notPassCount;
    }

    public int getAuditingCount() {
        return auditingCount;
    }

    public void setAuditingCount(int auditingCount) {
        this.auditingCount = auditingCount;
    }
}