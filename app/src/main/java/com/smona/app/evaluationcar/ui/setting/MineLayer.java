package com.smona.app.evaluationcar.ui.setting;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.smona.app.evaluationcar.R;
import com.smona.app.evaluationcar.ui.common.base.BaseLinearLayout;
import com.smona.app.evaluationcar.data.event.SettingEvent;
import com.smona.app.evaluationcar.util.ActivityUtils;
import com.smona.app.evaluationcar.util.CarLog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by Moth on 2016/12/18.
 */

public class MineLayer extends BaseLinearLayout implements View.OnClickListener {

    //头像
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

        findViewById(R.id.setting_info).setOnClickListener(this);
        findViewById(R.id.setting_update).setOnClickListener(this);
        findViewById(R.id.setting_about).setOnClickListener(this);
        findViewById(R.id.setting_phone).setOnClickListener(this);
        findViewById(R.id.setting_logout).setOnClickListener(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void update(SettingEvent event) {
        CarLog.d(this, "update SettingEvent: " + event);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch(id) {
            case R.id.setting_info:
                break;
            case R.id.setting_update:
                break;
            case R.id.setting_about:
                break;
            case R.id.setting_phone:
                ActivityUtils.callPhone(getContext(), "123456789");
                break;
            case R.id.setting_logout:
                //弹出对话框，退出
                break;
        }
    }
}
