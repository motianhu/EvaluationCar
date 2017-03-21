package com.smona.app.evaluationcar.framework.http;

import android.app.Application;

import com.smona.app.evaluationcar.framework.IProxy;
import com.smona.app.evaluationcar.framework.upload1.UploadUtils;
import com.smona.app.evaluationcar.util.UrlConstants;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;

/**
 * Created by Moth on 2016/12/18.
 */

public class HttpProxy implements IProxy {

    public static void init(Application app) {
        x.Ext.init(app);
    }

    public static void getCarBillId(Callback.CommonCallback callback) {
        RequestParams params = createParams(UrlConstants.CREATE_CARBILLID);
        x.http().get(params, callback);
    }

    public interface ResponseCallback<T> extends Callback.CommonCallback{}


    private void uploadImage(String filePath,Callback.CommonCallback callback) {
        RequestParams params = createParams(UrlConstants.UPLOAD_IMAGE);
        params.addParameter("createUser","cy");
        params.addBodyParameter("image", new File(filePath));
        UploadUtils.uploadMethod(params, callback);
    }

    private static RequestParams createParams(int type) {
        String url = UrlConstants.getInterface(type);
        RequestParams params = new RequestParams(url);
        return params;
    }
}
