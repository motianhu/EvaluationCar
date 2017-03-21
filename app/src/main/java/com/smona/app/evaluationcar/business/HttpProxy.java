package com.smona.app.evaluationcar.business;

import android.app.Application;

import com.smona.app.evaluationcar.framework.IProxy;
import com.smona.app.evaluationcar.framework.request.HomeDeletor;
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

    private volatile static HttpProxy sInstance;

    private HttpProxy() {
    }

    public static HttpProxy getInstance() {
        if (sInstance == null) {
            sInstance = new HttpProxy();
        }
        return sInstance;
    }

    public void init(Application app) {
        x.Ext.init(app);
    }

    public void getCarBillId(Callback.CommonCallback callback) {
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

    private RequestParams createParams(int type) {
        String url = UrlConstants.getInterface(type);
        RequestParams params = new RequestParams(url);
        return params;
    }


    public void requestImageMeta(Callback.CommonCallback callback) {
        RequestParams params = createParams(UrlConstants.CREATE_CARBILLID);
        x.http().get(params, callback);
    }
}
