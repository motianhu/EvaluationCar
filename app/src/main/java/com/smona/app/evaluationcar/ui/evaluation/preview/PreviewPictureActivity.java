package com.smona.app.evaluationcar.ui.evaluation.preview;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.smona.app.evaluationcar.R;
import com.smona.app.evaluationcar.ui.common.activity.BaseActivity;

/**
 * Created by Moth on 2017/3/9.
 */

public class PreviewPictureActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_picture);
        ZoomImageView mImageView = (ZoomImageView) findViewById(R.id.image);

        Drawable bitmap = ContextCompat.getDrawable(this, R.drawable.mask_dashboard);
        BitmapDrawable bd = (BitmapDrawable) bitmap;
        mImageView.setImageBitmap(bd.getBitmap());
        mImageView.setCanMove(true);


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


}
