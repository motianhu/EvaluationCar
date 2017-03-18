package com.smona.app.evaluationcar.framework.upload1;


import com.smona.app.evaluationcar.util.CarLog;

import org.greenrobot.eventbus.EventBus;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class UploadUtil {
    private static final String TAG = "uploadFile";
    private static final int SINGLE_SEND_COUNT = 8 * 1024;
    public static final String FILE_NOT_EXIST_ERROR = "-999";

    /**
     * 上传文件到服务器
     *
     * @param file       需要上传的文件
     * @param requestURL 请求的url
     * @return 返回响应的内容
     */
    public static String uploadFile(String requestURL, String fileName, String srcFileName, EventBusUploadProgress progress) {
        File file = new File(srcFileName);
        if (!file.exists()) {
            return FILE_NOT_EXIST_ERROR;
        }
        String res = null;
        CarLog.d(TAG, "uploadFile requestURL = " + requestURL);
        String end = "\r\n";
        String twoHyphens = "--";
        String boundary = "******";
        try {
            URL url = new URL(requestURL);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url
                    .openConnection();
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
            httpURLConnection.setRequestProperty("Charset", "UTF-8");
            httpURLConnection.setRequestProperty("Content-Type",
                    "multipart/form-data;boundary=" + boundary);

            DataOutputStream dos = new DataOutputStream(
                    httpURLConnection.getOutputStream());
            dos.writeBytes(twoHyphens + boundary + end);
            dos.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\"; filename=\""
                    + fileName
                    + "\""
                    + end);
            dos.writeBytes(end);

            InputStream is = new FileInputStream(file);
            byte[] bytes = new byte[1024];
            int len = 0;
            progress.setUploadAllByteCount(file.length());
            int count = 0;
            while ((len = is.read(bytes)) != -1) {
                dos.write(bytes, 0, len);
                count = count + len;
                progress.setUploadByteCount(count);
                EventBus.getDefault().post(progress);
            }
            is.close();

            dos.writeBytes(end);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + end);
            dos.flush();
            dos.close();

            int resCode = httpURLConnection.getResponseCode();
            CarLog.d(TAG, "response code:" + resCode);
            if (resCode == 200) {
                CarLog.d(TAG, "request success");
                InputStream input = httpURLConnection.getInputStream();
                StringBuffer sb1 = new StringBuffer();
                int ss;
                while ((ss = input.read()) != -1) {
                    sb1.append((char) ss);
                }
                input.close();
                res = sb1.toString();
                CarLog.d(TAG, "result : " + res);
            } else {
                CarLog.d(TAG, "request error");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }


    /**
     * 上传文件到服务器
     *
     * @param bytes      需要上传的文件
     * @param requestURL 请求的rul
     * @return 返回响应的内容
     */
    public static String uploadFile(String requestURL, String fileName, byte[] bytes, EventBusUploadProgress progress) {
        progress.setUploadAllByteCount(bytes.length);
        String res = null;
        CarLog.d(TAG, "uploadFile requestURL = " + requestURL);
        String end = "\r\n";
        String twoHyphens = "--";
        String boundary = "******";
        try {
            URL url = new URL(requestURL);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url
                    .openConnection();
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
            httpURLConnection.setRequestProperty("Charset", "UTF-8");
            httpURLConnection.setRequestProperty("Content-Type",
                    "multipart/form-data;boundary=" + boundary);

            DataOutputStream dos = new DataOutputStream(
                    httpURLConnection.getOutputStream());
            dos.writeBytes(twoHyphens + boundary + end);
            dos.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\"; filename=\""
                    + fileName
                    + "\""
                    + end);
            dos.writeBytes(end);

            //dos.write(bytes, 0, bytes.length);
            int count = 0;
            while (count < bytes.length) {
                int nextCount = bytes.length - count;
                if (nextCount > SINGLE_SEND_COUNT) {
                    nextCount = SINGLE_SEND_COUNT;
                }
                dos.write(bytes, count, nextCount);
                count = nextCount + count;
                progress.setUploadByteCount(count);
                EventBus.getDefault().post(progress);
            }

            dos.writeBytes(end);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + end);
            dos.flush();
            dos.close();

            int resCode = httpURLConnection.getResponseCode();
            CarLog.d(TAG, "response code:" + resCode);
            if (resCode == 200) {
                CarLog.d(TAG, "request success");
                InputStream input = httpURLConnection.getInputStream();
                StringBuffer sb1 = new StringBuffer();
                int ss;
                while ((ss = input.read()) != -1) {
                    sb1.append((char) ss);
                }
                input.close();
                res = sb1.toString();
                CarLog.d(TAG, "result : " + res);
            } else {
                CarLog.d(TAG, "request error");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
}