package com.smona.app.evaluationcar.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;

import com.smona.app.evaluationcar.data.BannerInfo;
import com.smona.app.evaluationcar.ui.WebActivity;

/**
 * Created by motianhu on 2/27/17.
 */

public class ActivityUtils {

    public static void jumpBannerDetail(Context context, BannerInfo banner) {
        Intent intent = new Intent();
        if (banner instanceof Parcelable) {
            intent.putExtra("", banner);
        }
        intent.setClass(context, WebActivity.class);
        context.startActivity(intent);
    }

    public static void callPhone(Context context, String number) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + number));
        context.startActivity(intent);
    }
}
