package com.smona.app.evaluationcar.ui.common.pullup;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class LoadingImageView extends ImageView {

    private AnimationDrawable mLoadingAnimal;

    public LoadingImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mLoadingAnimal = (AnimationDrawable) getDrawable();
    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        if (VISIBLE != visibility) {
            stopAnimal();
        }
    }

    public void startAnimal() {
        mLoadingAnimal.start();
    }

    public void stopAnimal() {
        if (mLoadingAnimal.isRunning()) {
            mLoadingAnimal.stop();
        }
        mLoadingAnimal.selectDrawable(0);
    }

}
