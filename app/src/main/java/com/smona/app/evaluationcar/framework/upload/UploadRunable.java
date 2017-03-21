package com.smona.app.evaluationcar.framework.upload;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.text.TextUtils;

import com.smona.app.evaluationcar.data.item.CategoryItem;
import com.smona.app.evaluationcar.data.item.UploadItem;
import com.smona.app.evaluationcar.data.event.ProcessEvent;
import com.smona.app.evaluationcar.framework.event.EventProxy;
import com.smona.app.evaluationcar.framework.provider.DBDelegator;
import com.smona.app.evaluationcar.util.BitmapUtil;
import com.smona.app.evaluationcar.util.CarLog;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.GZIPInputStream;

public class UploadRunable implements Runnable {

    private static final int UPLOAD_IMG_MAX_W = 720;
    private static final String IMAGE = "img";
    private static final int COMPRESS_QUALITY = 90;
    private static final String FILEPATH = "img.jpg";
    private static final String UPLOAD_IMG_URL = "";

    private UploadTask mUploadTask;

    public UploadRunable(UploadTask uploadTask) {
        this.mUploadTask = uploadTask;
    }

    @Override
    public void run() {
        UploadItem bean =  mUploadTask.mUploadBean;
        List<CategoryItem> imageInfos = bean.getImageInfos();
        CarLog.d(this, "start upload : " + imageInfos);

        Map<String, String> params = new HashMap<String, String>();
        try {
            for(CategoryItem imageInfo: imageInfos) {
                //upload image
                String resutlJson = uploadFile(UPLOAD_IMG_URL, params, imageInfo.getLocalUrl());
                CarLog.d(this, " upload resultjson : " + resutlJson);
                if(!TextUtils.isEmpty(resutlJson)) {
                   continue;
                }
                //update db
                DBDelegator.getInstance().updateUploadStatus(bean.getCarBillId(), "");
                DBDelegator.getInstance().updateImageRemoteUrl(bean.getCarBillId(), "");
                //post update ui
                EventProxy.post(new ProcessEvent());
            }
        } catch (Exception e) {
            e.printStackTrace();
            CarLog.d(this, " upload Exception : " + e.toString() + e.getMessage());
        } finally {
            CarLog.d(this, " upload ResultCode : " + mUploadTask.getErrorCode());
            UploadTaskExecutor.onExecuteComplete(mUploadTask);
        }
    }

    public String uploadFile(String uploadUrl, Map<String, String> params, String filePath)
            throws IOException {
        String result = "";
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "******";
        HttpURLConnection connect = null;
        DataOutputStream dos = null;
        FileInputStream fis = null;
        try {
            String url = uploadUrl;
            CarLog.d(this, " uploading url : " + url);
            connect = (HttpURLConnection) new URL(url).openConnection();
            connect.setDoInput(true);
            connect.setDoOutput(true);
            connect.setUseCaches(false);
            connect.setRequestMethod("POST");
            connect.setRequestProperty("Charset", "UTF-8");
            connect.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            dos = new DataOutputStream(connect.getOutputStream());
            for (Entry<String, String> entry : params.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"" + key + "\"" + lineEnd);
                dos.writeBytes(lineEnd);
                dos.writeBytes(value + lineEnd);
            }

            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"" + IMAGE + "\"; filename=\"" + FILEPATH
                    + "\"" + lineEnd);
            dos.writeBytes(lineEnd);

            int[] imgWh = new int[2];
            BitmapUtil.getImgWH(filePath, imgWh);
            if (imgWh[0] <= UPLOAD_IMG_MAX_W) {
                CarLog.d(this, "read img file");
                fis = new FileInputStream(filePath);
                byte[] buffer = new byte[8192];
                int count;
                while ((count = fis.read(buffer)) != -1) {
                    dos.write(buffer, 0, count);
                }
            } else {
                Bitmap bitmap = BitmapUtil.getDesBitmap(filePath, UPLOAD_IMG_MAX_W, -1);
                bitmap = BitmapUtil.extractThumbnailIfNeed(bitmap, UPLOAD_IMG_MAX_W, -1);
                long size = dos.size();
                bitmap.compress(CompressFormat.JPEG, COMPRESS_QUALITY, dos);
                long zoomSize = dos.size() - size;
                long fileSize = new File(filePath).length();
                CarLog.d(this, "zoom size:" + zoomSize + " fileSize:" + fileSize + "  filePath:"
                        + filePath);
            }
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
            dos.flush();

            int code = connect.getResponseCode();
            if (HttpURLConnection.HTTP_OK == code) {
                return getConnectResult(connect);
            } else {
                mUploadTask.mErrorCode = code;
            }
        } finally {
            try {
                if (dos != null) {
                    dos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (connect != null) {
                connect.disconnect();
            }
        }
        return result;
    }

    private static String getConnectResult(URLConnection conn) throws IOException {
        InputStream finalInputStream = null;
        try {
            InputStream is = conn.getInputStream();
            if ("gzip".equals(conn.getContentEncoding())) {
                finalInputStream = new BufferedInputStream(new GZIPInputStream(is));
            } else {
                finalInputStream = is;
            }
            StringBuilder res = new StringBuilder();
            int ch;
            while ((ch = finalInputStream.read()) != -1) {
                res.append((char) ch);
            }
            return res.toString();
        } finally {
            try {
                if (finalInputStream != null) {
                    finalInputStream.close();
                }
            } catch (IOException e) {
            }
        }
    }
}
