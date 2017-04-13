package com.smona.app.evaluationcar.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AlertDialog;

import com.smona.app.evaluationcar.R;
import com.smona.app.evaluationcar.data.model.ResUpgradeApi;
import com.smona.app.evaluationcar.framework.storage.DeviceStorageManager;
import com.smona.app.evaluationcar.util.CarLog;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Moth on 2017/4/13.
 */

public class UpgradeUtils {
    private static final String TAG = UpgradeUtils.class.getSimpleName();

    public static void showUpdataDialog(final Activity activity, final ResUpgradeApi upgrade) {
        AlertDialog.Builder builer = new AlertDialog.Builder(activity);
        builer.setTitle(R.string.upgrade_title);
        String content = activity.getResources().getString(R.string.upgrade_desc);
        content = String.format(content, upgrade.versionName);
        builer.setMessage(content);
        final boolean isForce = ResUpgradeApi.UPDATE_TYPE_FORCE.equalsIgnoreCase(upgrade.updateType);
        builer.setPositiveButton(R.string.upgrade_ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                CarLog.d(TAG, "下载apk,更新");
                downLoadApk(activity, upgrade);
            }
        });
        builer.setNegativeButton(R.string.upgrade_cancle, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (!isForce) {
                    return;
                }
                activity.finish();
            }
        });
        AlertDialog dialog = builer.create();
        dialog.setCanceledOnTouchOutside(!isForce);
        dialog.show();
    }


    public static File downloadApk(String path, ProgressDialog pd) throws Exception{
        //如果相等的话表示当前的sdcard挂载在手机上并且是可用的
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            URL url = new URL(path);
            HttpURLConnection conn =  (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            //获取到文件的大小
            pd.setMax(conn.getContentLength());
            InputStream is = conn.getInputStream();
            File file = new File(DeviceStorageManager.getInstance().getMd5Path(), DeviceStorageManager.getInstance().getTemp());
            FileOutputStream fos = new FileOutputStream(file);
            BufferedInputStream bis = new BufferedInputStream(is);
            byte[] buffer = new byte[1024];
            int len ;
            int total=0;
            while((len =bis.read(buffer))!=-1){
                fos.write(buffer, 0, len);
                total+= len;
                pd.setProgress(total);
            }
            fos.close();
            bis.close();
            is.close();
            return file;
        }
        else{
            return null;
        }
    }

    private static void downLoadApk(final Activity activity, final ResUpgradeApi upgrade) {
        final ProgressDialog pd;    //进度条对话框
        pd = new  ProgressDialog(activity);
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setMessage("正在下载更新");
        pd.show();
        new Thread(){
            @Override
            public void run() {
                try {
                    File file = downloadApk(upgrade.apiURL, pd);
                    sleep(3000);
                    installApk(activity, file);
                    pd.dismiss(); //结束掉进度条对话框
                } catch (Exception e) {
                    e.printStackTrace();
                }}}.start();
    }

    private static void installApk(Activity activity, File file) {
        Intent intent = new Intent();
        //执行动作
        intent.setAction(Intent.ACTION_VIEW);
        //执行的数据类型
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        activity.startActivity(intent);
    }
}
