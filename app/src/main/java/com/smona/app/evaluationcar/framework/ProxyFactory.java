package com.smona.app.evaluationcar.framework;

import com.smona.app.evaluationcar.framework.event.EventProxy;
import com.smona.app.evaluationcar.framework.http.HttpProxy;
import com.smona.app.evaluationcar.framework.imageloader.ImageLoaderProxy;
import com.smona.app.evaluationcar.framework.upload.UploadServiceProxy;

/**
 * Created by Moth on 2016/12/18.
 */

public class ProxyFactory {
    private volatile static  ProxyFactory sProxyFactory;
    private IProxy mImageLoaderProxy;
    private IProxy mEventProxy;
    private IProxy mHttpProxy;
    private IProxy mUploadServiceProxy;

    private ProxyFactory(){
        mImageLoaderProxy = new ImageLoaderProxy();
        mEventProxy = new EventProxy();
        mHttpProxy = new HttpProxy();
        mUploadServiceProxy = new UploadServiceProxy();
    }

    public static ProxyFactory getInstance() {
        synchronized (ProxyFactory.class) {
            if(sProxyFactory == null) {
                sProxyFactory = new ProxyFactory();
            }
            return sProxyFactory;
        }
    }

    public ImageLoaderProxy getImageLoaderProxy() {
        return (ImageLoaderProxy)mImageLoaderProxy;
    }

    public EventProxy getEventProxy() {
        return (EventProxy)mEventProxy;
    }

    public HttpProxy getHttpProxy() {
        return (HttpProxy)mHttpProxy;
    }

    public UploadServiceProxy getUploadServiceProxy() {
        return (UploadServiceProxy)mUploadServiceProxy;
    }


}