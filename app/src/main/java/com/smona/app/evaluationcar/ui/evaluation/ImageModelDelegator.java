package com.smona.app.evaluationcar.ui.evaluation;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;

import com.smona.app.evaluationcar.R;
import com.smona.app.evaluationcar.data.bean.CarImageBean;
import com.smona.app.evaluationcar.framework.provider.DBDelegator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private Map<String, Integer> mImageClassMap = null;

    private String mAddPic;

    private volatile static ImageModelDelegator sInstance;

    private ImageModelDelegator() {
        mImageClassMap = new HashMap<String, Integer>();
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

        mAddPic = res.getString(R.string.add_picture);

        mImageClassMap.put(mImageClass[IMAGE_Registration], IMAGE_Registration);
        mImageClassMap.put(mImageClass[IMAGE_DrivingLicense], IMAGE_DrivingLicense);
        mImageClassMap.put(mImageClass[IMAGE_VehicleNameplate], IMAGE_VehicleNameplate);
        mImageClassMap.put(mImageClass[IMAGE_CarBody], IMAGE_CarBody);
        mImageClassMap.put(mImageClass[IMAGE_CarFrame], IMAGE_CarFrame);
        mImageClassMap.put(mImageClass[IMAGE_VehicleInterior], IMAGE_VehicleInterior);
        mImageClassMap.put(mImageClass[IMAGE_DifferenceSupplement], IMAGE_DifferenceSupplement);
        mImageClassMap.put(mImageClass[IMAGE_OriginalCarInsurancet], IMAGE_OriginalCarInsurancet);
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

        String imageClass = null;

        for (int i = 0; i < mImageClassItems[type].size(); i++) {
            CarImageBean bean = new CarImageBean();
            bean.imageClass = imageClass = mImageClass[type];
            bean.displayName = mImageClassItems[type].get(i);
            bean.imageSeqNum = i;
            defaultList.add(bean);
        }

        CarImageBean bean = new CarImageBean();
        bean.displayName = mAddPic;
        bean.imageClass = imageClass;
        bean.imageSeqNum = defaultList.size();
        defaultList.add(bean);

        return defaultList;
    }

    public List<CarImageBean> getSaveModel(int type, int imageId) {
        String imageClass = getImageClassForType(type);

        List<CarImageBean> saveList = DBDelegator.getInstance().queryImages(imageClass, imageId);
        List<CarImageBean> defaultList = getDefaultModel(type);

        boolean isMatch = false;
        int size = 0;
        for (CarImageBean saveCar : saveList) {
            isMatch = false;
            size = defaultList.size();
            for (int i = 0; i < size; i++)
                if (i == saveCar.imageSeqNum) {
                    defaultList.remove(i);
                    defaultList.add(i, saveCar);
                    isMatch = true;
                    break;
                }
            if (!isMatch) {
                defaultList.add(saveCar);
            }
        }

        CarImageBean bean = new CarImageBean();
        bean.displayName = mAddPic;
        bean.imageClass = imageClass;
        bean.imageSeqNum = defaultList.size();
        defaultList.add(bean);

        return defaultList;
    }

    public String getImageClassForType(int type) {
        return mImageClass[type];
    }

    public int getTypeForImageClass(String imageClass) {
        return mImageClassMap.get(imageClass);
    }
}
