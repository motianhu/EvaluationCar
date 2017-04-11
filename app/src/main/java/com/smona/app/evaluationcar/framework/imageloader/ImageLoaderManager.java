package com.smona.app.evaluationcar.framework.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.smona.app.evaluationcar.framework.EvaluationApp;

public class ImageLoaderManager {
    private static final String TAG = "ImageLoaderManager";
    private volatile static ImageLoaderManager sInstance;

    public synchronized static ImageLoaderManager getInstance() {
        if (sInstance == null) {
            sInstance = new ImageLoaderManager();
        }
        return sInstance;
    }

    private ImageLoaderManager() {
    }

    public void initImageLoader(Context appContext) {
        if (!(appContext instanceof EvaluationApp)) {
            throw new RuntimeException("appContext is not Application Context!");
        }
        ImageLoaderConfig.initImageLoader(appContext, null);
    }

    public void loadImage(String uri, ImageView imageView) {
        ImageLoader.getInstance().displayImage(uri, imageView, ImageLoaderConfig.getBannerOptions(), mImageListenser);
    }

    public void loadWaterImage(String uri, ImageView imageView) {
        ImageLoader.getInstance().displayImage(uri, imageView, ImageLoaderConfig.getWaterOptions(), mImageListenser);
    }

    public void loadImageDesc(String uri, ImageView imageView) {
        ImageLoader.getInstance().displayImage(uri, imageView, ImageLoaderConfig.getImageDesc(), mImageListenser);
    }

    ImageLoadingListener mImageListenser = new ImageLoadingListener() {

        @Override
        public void onLoadingStarted(String imageUri, View view) {

        }

        @Override
        public void onLoadingFailed(String imageUri, View view,
                                    FailReason failReason) {
            //CarLog.d(this, "onLoadingFailed imageUri: " + imageUri + ", failReason: " + failReason.getCause());
        }

        @Override
        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            //CarLog.d(this, "onLoadingComplete imageUri: " + imageUri);
        }

        @Override
        public void onLoadingCancelled(String imageUri, View view) {
            //CarLog.d(this, "onLoadingCancelled imageUri: " + imageUri);
        }
    };
}
