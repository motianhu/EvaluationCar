package com.smona.app.evaluationcar.ui.evaluation.camera;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.smona.app.evaluationcar.R;
import com.smona.app.evaluationcar.framework.imageloader.ImageLoaderProxy;
import com.smona.app.evaluationcar.util.ActivityUtils;
import com.smona.app.evaluationcar.util.CarLog;
import com.smona.app.evaluationcar.util.ViewUtil;

import java.io.File;
import java.io.IOException;

public class CameraActivity extends Activity implements SurfaceHolder.Callback, View.OnClickListener {

    private Camera mCamera;
    private SurfaceView mSurfaceView;
    private SurfaceHolder mHolder;
    private int mCameraId = 0;

    //屏幕宽高
    private int screenWidth;
    private int picHeight;

    //闪光灯模式 0:关闭 1: 开启 2: 自动
    private int mLightModel = 0;
    private ImageView mFlashLight;

    private boolean mIsPreview = false;
    private ImageView mClose;
    private ImageView mTakePhoto;

    private ImageView mThumbnail;

    private TextView mDescription;
    private TextView mNote;
    private TextView mNumPhoto;

    private TextView mGallery;
    private TextView mCancel;

    //Animation
    private View mExplainView;
    private Animation mCollapseAnimation;
    private Animation mExpandAnimation;

    private Bitmap mBitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        initView();
        initData();
        initAnimate();
    }

    private void initView() {
        mSurfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        mHolder = mSurfaceView.getHolder();
        mHolder.addCallback(this);

        mThumbnail  =(ImageView)findViewById(R.id.thumbnail);
        ViewUtil.setViewVisible(mThumbnail, false);

        mTakePhoto = (ImageView) findViewById(R.id.img_camera);
        mTakePhoto.setOnClickListener(this);

        //关闭相机界面按钮
        mClose = (ImageView) findViewById(R.id.camera_close);
        mClose.setOnClickListener(this);

        //闪光灯
        mFlashLight = (ImageView) findViewById(R.id.flash_light);
        mFlashLight.setOnClickListener(this);

        mGallery = (TextView) findViewById(R.id.gallery);
        mGallery.setOnClickListener(this);
        mCancel = (TextView) findViewById(R.id.cancel);
        mCancel.setOnClickListener(this);

        mDescription = (TextView) findViewById(R.id.description);
        mDescription.setText("车辆左前45度");
        mNote = (TextView) findViewById(R.id.note);
        mNote.setText("(请打开天窗)");
        mNumPhoto = (TextView) findViewById(R.id.numPhoto);
        mNumPhoto.setText("1/21");

        mExplainView = findViewById(R.id.lin_explain_btn);
        mExplainView.setOnClickListener(this);

        changeViewStatus(false);
    }

    private void initData() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        screenWidth = dm.widthPixels;
    }

    private void initAnimate() {
        mExpandAnimation = AnimationUtils.loadAnimation(this, R.anim.expend_up);
        mCollapseAnimation = AnimationUtils.loadAnimation(this, R.anim.collapse_down);
        mCollapseAnimation.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationEnd(Animation paramAnimation) {
            }

            public void onAnimationRepeat(Animation paramAnimation) {
            }

            public void onAnimationStart(Animation paramAnimation) {
            }
        });
    }

    private void changeViewStatus(boolean isPreview) {
        ViewUtil.setViewVisible(mThumbnail, isPreview);
        mGallery.setText(isPreview?R.string.take_again:R.string.gallery);
        mCancel.setText(isPreview?R.string.take_next:R.string.cancel);
        ViewUtil.setViewVisible(mFlashLight, !isPreview);
        ViewUtil.setViewVisible(mTakePhoto, !isPreview);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_camera:
                onCamera();
                break;
            //退出相机界面 释放资源
            case R.id.camera_close:
                finish();
                break;
            //闪光灯
            case R.id.flash_light:
                onFlashLight();
                break;
            case R.id.gallery:
                actionGallery();
                break;
            case R.id.cancel:
                finish();
                break;
            case R.id.lin_explain_btn:
                onAnimationExplain();
                break;
        }
    }

    private void onAnimationExplain() {
        if (mExplainView.getVisibility() == View.VISIBLE) {
            closeAnimal();
            return;
        }
        mExplainView.setVisibility(View.VISIBLE);
        mExplainView.startAnimation(this.mExpandAnimation);
    }

    private void closeAnimal() {
        if (mExplainView.getVisibility() != View.VISIBLE)
            return;
        this.mExplainView.setVisibility(View.GONE);
        this.mExplainView.startAnimation(this.mCollapseAnimation);
    }

    private void onCamera() {
        if (mIsPreview) {
            switch (mLightModel) {
                case 0:
                    //关闭
                    CameraUtil.getInstance().turnLightOff(mCamera);
                    break;
                case 1:
                    CameraUtil.getInstance().turnLightOn(mCamera);
                    break;
                case 2:
                    //自动
                    CameraUtil.getInstance().turnLightAuto(mCamera);
                    break;
            }
            captrue();
            mIsPreview = false;
        }
    }

    private void onFlashLight() {
        Camera.Parameters parameters = mCamera.getParameters();
        switch (mLightModel) {
            case 0:
                //打开
                mLightModel = 1;
                mFlashLight.setImageResource(R.drawable.btn_camera_flash_on);
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);//开启
                mCamera.setParameters(parameters);
                break;
            case 1:
                //自动
                mLightModel = 2;
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_AUTO);
                mCamera.setParameters(parameters);
                mFlashLight.setImageResource(R.drawable.btn_camera_flash_auto);
                break;
            case 2:
                //关闭
                mLightModel = 0;
                //关闭
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                mCamera.setParameters(parameters);
                mFlashLight.setImageResource(R.drawable.btn_camera_flash_off);
                break;
        }
    }

    private void actionGallery() {
        String text = mGallery.getText().toString();
        if (getResources().getString(R.string.gallery).equals(text)) {
            Intent intent = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, ActivityUtils.ACTION_GALLERY);
        } else {
            changeViewStatus(false);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ActivityUtils.ACTION_GALLERY && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = null;
            try {
                c = getContentResolver().query(selectedImage, filePathColumns, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePathColumns[0]);
                String imagePath = c.getString(columnIndex);
                //todo picture
                CarLog.d(this, "onActivityResult imagePath: " + imagePath);

                BitmapFactory.Options options = new BitmapFactory.Options();
                options.outHeight = picHeight;
                options.outWidth = screenWidth;
                Bitmap saveBitmap = BitmapFactory.decodeFile(imagePath, options);

                Matrix matrix = new Matrix();
                matrix.setRotate(270f);

                recycle(mBitmap);
                mBitmap = Bitmap.createBitmap(saveBitmap, 0, 0, screenWidth, screenWidth * 4 / 3,matrix, true);

                String img_path = getExternalFilesDir(Environment.DIRECTORY_DCIM).getPath() +
                        File.separator + System.currentTimeMillis() + ".jpeg";
                BitmapUtils.saveJPGE_After(CameraActivity.this, saveBitmap, img_path, 100);
                recycle(saveBitmap);

                setPreview();
            } finally {
                if (c != null) {
                    c.close();
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mCamera == null) {
            mCamera = getCamera(mCameraId);
            if (mHolder != null) {
                startPreview(mCamera, mHolder);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseCamera();
    }

    /**
     * 获取Camera实例
     *
     * @return
     */
    private Camera getCamera(int id) {
        Camera camera = null;
        try {
            camera = Camera.open(id);
        } catch (Exception e) {
            CarLog.d(this, "getCamera e: " + e);
        }
        return camera;
    }

    /**
     * 预览相机
     */
    private void startPreview(Camera camera, SurfaceHolder holder) {
        try {
            setupCamera(camera);
            camera.setPreviewDisplay(holder);
            CameraUtil.getInstance().setCameraDisplayOrientation(this, mCameraId, camera);
            camera.startPreview();
            mIsPreview = true;
        } catch (IOException e) {
            e.printStackTrace();
            CarLog.d(this, "startPreview e: " + e);
        }
    }


    private void captrue() {
        mCamera.takePicture(null, null, new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                mIsPreview = false;
                //将data 转换为位图 或者你也可以直接保存为文件使用 FileOutputStream
                //这里我相信大部分都有其他用处把 比如加个水印 后续再讲解
                Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                Bitmap saveBitmap = CameraUtil.getInstance().setTakePicktrueOrientation(mCameraId, bitmap);

                saveBitmap = Bitmap.createScaledBitmap(saveBitmap, screenWidth, picHeight, true);
                
                Matrix matrix = new Matrix();
                matrix.setRotate(270f);

                recycle(bitmap);
                recycle(mBitmap);
                mBitmap = Bitmap.createBitmap(saveBitmap, 0, 0, screenWidth, screenWidth * 4 / 3,matrix, true);

                String img_path = getExternalFilesDir(Environment.DIRECTORY_DCIM).getPath() +
                        File.separator + System.currentTimeMillis() + ".jpeg";
                BitmapUtils.saveJPGE_After(CameraActivity.this, saveBitmap, img_path, 100);
                recycle(saveBitmap);

                setPreview();
            }
        });
    }

    private void recycle(Bitmap b) {
        if (b != null && !b.isRecycled()) {
            b.recycle();
        }
    }

    private void setPreview() {
        mThumbnail.setImageBitmap(mBitmap);
        changeViewStatus(true);
    }

    /**
     * 设置
     */
    private void setupCamera(Camera camera) {
        Camera.Parameters parameters = camera.getParameters();

        if (parameters.getSupportedFocusModes().contains(
                Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        }

        //这里第三个参数为最小尺寸 getPropPreviewSize方法会对从最小尺寸开始升序排列 取出所有支持尺寸的最小尺寸
        Camera.Size previewSize = CameraUtil.getInstance().getPropSizeForHeight(parameters.getSupportedPreviewSizes(), 800);
        parameters.setPreviewSize(previewSize.width, previewSize.height);

        Camera.Size pictrueSize = CameraUtil.getInstance().getPropSizeForHeight(parameters.getSupportedPictureSizes(), 800);
        parameters.setPictureSize(pictrueSize.width, pictrueSize.height);

        camera.setParameters(parameters);

        /**
         * 设置surfaceView的尺寸 因为camera默认是横屏，所以取得支持尺寸也都是横屏的尺寸
         * 我们在startPreview方法里面把它矫正了过来，但是这里我们设置设置surfaceView的尺寸的时候要注意 previewSize.height<previewSize.width
         * previewSize.width才是surfaceView的高度
         * 一般相机都是屏幕的宽度 这里设置为屏幕宽度 高度自适应 你也可以设置自己想要的大小
         *
         */
        picHeight = (screenWidth * pictrueSize.width) / pictrueSize.height;
    }

    /**
     * 释放相机资源
     */
    private void releaseCamera() {
        if (mCamera != null) {
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        startPreview(mCamera, holder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        mCamera.stopPreview();
        startPreview(mCamera, holder);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        releaseCamera();
    }

}
