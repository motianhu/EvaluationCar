package com.smona.app.evaluationcar.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;

import com.smona.app.evaluationcar.data.bean.CarBillBean;
import com.smona.app.evaluationcar.data.bean.CarImageBean;
import com.smona.app.evaluationcar.data.item.BannerItem;
import com.smona.app.evaluationcar.ui.WebActivity;

/**
 * Created by motianhu on 2/27/17.
 */

public class ActivityUtils {

    public static final int ACTION_GALLERY = 1;
    public static final int ACTION_CAMERA = 2;

    public static void jumpBannerDetail(Context context, BannerItem banner) {
        Intent intent = new Intent();
        if (banner instanceof Parcelable) {
            intent.putExtra("", banner);
        }
        intent.setClass(context, WebActivity.class);
        context.startActivity(intent);
    }

    public static void jumpEvaluation(Context context, int billStatus, String carBillId, int imageId, Class clazz) {
        Intent intent = new Intent();
        SPUtil.put(context, CacheContants.BILL_STATUS, billStatus);
        SPUtil.put(context, CacheContants.CARBILLID, carBillId);
        SPUtil.put(context, CacheContants.IMAGEID, imageId);
        intent.setClass(context, clazz);
        context.startActivity(intent);
    }

    public static void jumpStatus(Context context, CarBillBean bean,Class clazz) {
        Intent intent = new Intent();
        intent.putExtra(CacheContants.CARBILLBEAN, bean);
        intent.setClass(context, clazz);
        context.startActivity(intent);
    }

    public static void callPhone(Context context, String number) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + number));
        context.startActivity(intent);
    }

    //ImageId,CarBillId 来源于 EvaluationActivity
    public static void jumpCameraActivity(Context contet, CarImageBean bean, Class clazz) {
        Intent intent = new Intent();
        intent.setClass(contet, clazz);
        SPUtil.put(contet, CacheContants.IMAGECLASS, bean.imageClass);
        SPUtil.put(contet, CacheContants.IMAGESEQNUM, bean.imageSeqNum);
        contet.startActivity(intent);
    }

    public static void jumpOnlyActivity(Context context, Class clazz) {
        Intent intent = new Intent();
        intent.setClass(context, clazz);
        context.startActivity(intent);
    }
}
