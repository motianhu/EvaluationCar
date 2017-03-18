package com.smona.app.evaluationcar.framework.upload;

import android.graphics.Bitmap;

public class BitmapUtil {

    public static Bitmap createBitmap(int width, int height, Bitmap.Config config) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, config);
        return bitmap;
    }

    public static void recycleBitmap(Bitmap bitmap) {

    }
}
