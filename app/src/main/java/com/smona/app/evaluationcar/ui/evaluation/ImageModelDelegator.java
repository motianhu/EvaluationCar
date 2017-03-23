package com.smona.app.evaluationcar.ui.evaluation;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;

import com.smona.app.evaluationcar.R;
import com.smona.app.evaluationcar.data.bean.CarImageBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by motianhu on 3/23/17.
 */

public class ImageModelDelegator {
    public static final int IMAGE_Registration = 0;
    public static final int IMAGE_DrivingLicense = 1;
    public static final int IMAGE_VehicleNameplate = 2;
    public static final int IMAGE_CarBody = 3;
    public static final int IMAGE_CarFrame = 4;
    public static final int IMAGE_VehicleInterior = 5;
    public static final int IMAGE_DifferenceSupplement = 6;
    public static final int IMAGE_OriginalCarInsurancet = 7;

    private String[] mImageClass = null;
    private List<String>[] mImageClassItems = null;

    private volatile static ImageModelDelegator sInstance;

    private ImageModelDelegator() {
    }

    public static ImageModelDelegator getInstance() {
        if (sInstance == null) {
            sInstance = new ImageModelDelegator();
        }
        return sInstance;
    }

    public void init(Context context) {
        Resources res = context.getResources();
        mImageClass = res.getStringArray(R.array.image_class);
        String[] array = res.getStringArray(R.array.image_class_detail);
        mImageClassItems = getTwoDimensionalArray(array);
    }

    private List<String>[] getTwoDimensionalArray(String[] array) {
        List<String>[] twoDimensionalArray = new ArrayList[array.length];
        for (int i = 0; i < array.length; i++) {
            String[] tempArray = array[i].split(",");
            if (twoDimensionalArray[i] == null) {
                twoDimensionalArray[i] = new ArrayList<String>();
            }
            if (TextUtils.isEmpty(array[i])) {
                continue;
            }
            for (int j = 0; j < tempArray.length; j++) {
                twoDimensionalArray[i].add(tempArray[j]);
            }
        }
        return twoDimensionalArray;
    }


    public List<CarImageBean> getDefaultModel(int type) {
        List<CarImageBean> defaultList = new ArrayList<CarImageBean>();
        for (int i = 0; i < mImageClassItems[type].size(); i++) {
            CarImageBean bean = new CarImageBean();
            bean.imageClass = mImageClass[type];
            bean.displayName = mImageClassItems[type].get(i);
            bean.imageSeqNum = i;
            defaultList.add(bean);
        }
        return defaultList;
    }

    public String getImageClass(int type) {
        return mImageClass[type];
    }
}