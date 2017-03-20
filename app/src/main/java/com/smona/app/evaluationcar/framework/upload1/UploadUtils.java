package com.smona.app.evaluationcar.framework.upload1;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;

/**
 * Created by Moth on 2017/3/20.
 */

public class UploadUtils {
    public static void submitPost(String url, String filePath, Callback.CommonCallback callback) {
        RequestParams params = new RequestParams(url);
        params.addBodyParameter(filePath.replace("/", ""), new File(filePath));
        uploadMethod(params, callback);
    }

    public static void uploadMethod(RequestParams params, Callback.CommonCallback callback) {
        x.http().post(params, callback);
    }
}
