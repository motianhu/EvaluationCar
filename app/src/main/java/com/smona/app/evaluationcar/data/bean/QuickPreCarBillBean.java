package com.smona.app.evaluationcar.data.bean;

import com.smona.app.evaluationcar.util.StatusUtils;

/**
 * Created by motianhu on 6/11/17.
 */

public class QuickPreCarBillBean extends BaseBean {
    public String carBillId;
    public int status;
    public int imageId;
    public String modifyTime;
    public String createTime;
    public String mark;
    public String imageThumbPath;
    public double preSalePrice;
    public int uploadStatus = StatusUtils.BILL_UPLOAD_STATUS_NONE;
}
