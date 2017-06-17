package com.smona.app.evaluationcar.data.bean;

import com.smona.app.evaluationcar.util.StatusUtils;

/**
 * Created by motianhu on 3/21/17.
 */

public class PreCarBillBean extends BaseBean {
    //common
    public String createTime;
    public String modifyTime;
    public double evaluatePrice;
    public String carBillType = "fast"; //fast;routine;

    //text evaluation
    public String color;
    public String regDate;
    public int runNum;
    public String carBrandName;
    public String carSetName;
    public String carTypeName;
    public String carTypeId;
    public String cityName;
    public String cityId;
    public String mark;

    //image evaluation
    public String carBillId;
    public int status;
    public int imageId;
    public String imageThumbPath;
    public int uploadStatus = StatusUtils.BILL_UPLOAD_STATUS_NONE;
}
