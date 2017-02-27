package com.smona.app.evaluationcar.ui.setting;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.smona.app.evaluationcar.R;
import com.smona.app.evaluationcar.framework.event.EventProxy;
import com.smona.app.evaluationcar.framework.imageloader.ImageLoaderProxy;
import com.smona.app.evaluationcar.ui.BaseActivity;
import com.smona.app.evaluationcar.ui.common.BaseLinearLayout;
import com.smona.app.evaluationcar.ui.common.event.HomeEvent;
import com.smona.app.evaluationcar.ui.common.event.SettingEvent;
import com.smona.app.evaluationcar.util.CarLog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by Moth on 2016/12/18.
 */

public class MineLayer extends BaseLinearLayout {

    private ImageView mImage;
    private TextView mName;

    public MineLayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void init() {
        mImage = (ImageView) findViewById(R.id.mine_image);
        mName = (TextView) findViewById(R.id.mine_name);
        mName.setText(R.string.app_name);
    }


    @Subscribe(threadMode = ThreadMode.POSTING)
    public void update(SettingEvent event) {
        CarLog.d(this, "update SettingEvent: " + event);
    }
}
