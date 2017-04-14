package com.smona.app.evaluationcar.ui;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

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

    public static final String UPGRADE = "upgrade_service";
    public static final int UPGRADE_NORMAL = 0;
    public static final int UPGRADE_SETTING = 1;

    public static void showUpdataDialog(final Context context, final ResUpgradeApi upgrade) {
        AlertDialog.Builder builer = new AlertDialog.Builder(context);
        builer.setTitle(R.string.upgrade_title);
        String content = context.getResources().getString(R.string.upgrade_desc);
        content = String.format(content, upgrade.versionName);
        builer.setMessage(content);
        final boolean isForce = ResUpgradeApi.UPDATE_TYPE_FORCE.equalsIgnoreCase(upgrade.updateType);
        builer.setPositiveButton(R.string.upgrade_ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                CarLog.d(TAG, "下载apk,更新");
                downLoadApk(context, upgrade);
            }
        });
        builer.setNegativeButton(R.string.upgrade_cancle, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (!isForce) {
                    return;
                }
                System.exit(0);
            }
        });
        AlertDialog dialog = builer.create();
        dialog.setCanceledOnTouchOutside(!isForce);
        dialog.show();
    }


    public static File downloadApk(String path, ProgressDialog pd) throws Exception {
        //如果相等的话表示当前的sdcard挂载在手机上并且是可用的
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            //获取到文件的大小
            pd.setMax(conn.getContentLength());
            InputStream is = conn.getInputStream();
            File file = new File(DeviceStorageManager.getInstance().getMd5Path(), DeviceStorageManager.getInstance().getTemp());
            FileOutputStream fos = new FileOutputStream(file);
            BufferedInputStream bis = new BufferedInputStream(is);
            byte[] buffer = new byte[1024];
            int len;
            int total = 0;
            while ((len = bis.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
                total += len;
                pd.setProgress(total);
            }
            fos.close();
            bis.close();
            is.close();
            return file;
        } else {
            return null;
        }
    }

    private static void downLoadApk(final Context context, final ResUpgradeApi upgrade) {
        final ProgressDialog pd;    //进度条对话框
        pd = new ProgressDialog(context);
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setMessage("正在下载更新");
        pd.show();
        new Thread() {
            @Override
            public void run() {
                try {
                    File file = downloadApk(upgrade.apiURL, pd);
                    sleep(3000);
                    installApk(context, file);
                    pd.dismiss(); //结束掉进度条对话框
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private static void installApk(final Context context, File file) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    public static boolean compareVersion(String versionServer, String versionLocal) {
        String server = versionServer;
        String local = versionLocal;
        if (server == null || server.length() == 0 || local == null || local.length() == 0) {
            CarLog.d(TAG, "params valid! server=" + server + ",local=" + local);
            return false;
        }

        int index1 = 0;
        int index2 = 0;
        while (index1 < server.length() && index2 < local.length()) {
            int[] numberServer = getValue(server, index1);
            int[] numberLocal = getValue(local, index2);

            if (numberServer[0] < numberLocal[0]) {
                return false;
            } else if (numberServer[0] > numberLocal[0]) {
                return true;
            } else {
                index1 = numberServer[1] + 1;
                index2 = numberLocal[1] + 1;
            }
        }
        if (index1 == server.length() && index2 == local.length()) {
            return false;
        }
        if (index1 < server.length()) {
            return true;
        } else {
            return false;
        }
    }

    private static int[] getValue(String version, int index) {
        int[] value_index = new int[2];
        StringBuilder sb = new StringBuilder();
        while (index < version.length() && version.charAt(index) != '.') {
            sb.append(version.charAt(index));
            index++;
        }
        value_index[0] = Integer.parseInt(sb.toString());
        value_index[1] = index;

        return value_index;
    }
}
