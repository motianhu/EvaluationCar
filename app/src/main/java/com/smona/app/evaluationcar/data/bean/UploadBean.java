package com.smona.app.evaluationcar.data.bean;

import android.os.Parcel;

import java.util.List;

/**
 * Created by motianhu on 3/20/17.
 */

public class UploadBean extends BaseBean {
    private String carBillId;
    private List<CategoryInfo> imageInfos;
    private int uploadCount;

    protected UploadBean(Parcel in) {
        super(in);
    }

    public String getCarBillId() {
        return carBillId;
    }

    public void setCarBillId(String carBillId) {
        this.carBillId = carBillId;
    }

    public List<CategoryInfo> getImageInfos() {
        return imageInfos;
    }

    public void setImageInfos(List<CategoryInfo> imageInfos) {
        this.imageInfos = imageInfos;
    }


}
