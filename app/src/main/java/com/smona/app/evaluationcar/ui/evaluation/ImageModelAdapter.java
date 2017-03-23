package com.smona.app.evaluationcar.ui.evaluation;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.smona.app.evaluationcar.R;
import com.smona.app.evaluationcar.data.bean.CarImageBean;
import com.smona.app.evaluationcar.ui.evaluation.camera.CameraActivity;
import com.smona.app.evaluationcar.util.ActivityUtils;
import com.smona.app.evaluationcar.util.ScreenInfo;
import com.smona.app.evaluationcar.util.ViewUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Moth on 2017/3/16.
 */

public class ImageModelAdapter extends BaseAdapter {

    private List<CarImageBean> mDatas = new ArrayList<CarImageBean>();
    private Context mContext;
    private int mImageWidth;

    public ImageModelAdapter(Context context) {
        mContext = context;
        int i = ScreenInfo.getInstance().getScreenWidth();
        int j = context.getResources().getDimensionPixelSize(R.dimen.activity_horizontal_margin);
        mImageWidth = ((i - j * 3) / 2);
    }

    public void update(List<CarImageBean> datas) {
        mDatas.addAll(datas);
    }


    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CarImageBean bean = mDatas.get(position);
        if (convertView == null) {
            convertView = ViewUtil.inflater(mContext, R.layout.evaluation_image_item);
        }

        View leftView = convertView.findViewById(R.id.tv_part_left);
        View centerView = convertView.findViewById(R.id.lin_center);

        ImageView image = (ImageView) convertView.findViewById(R.id.image);
        ImageView centerImage = (ImageView) convertView.findViewById(R.id.iv_add_center);

        TextView partLeft = (TextView) convertView.findViewById(R.id.tv_part_left);
        TextView partCenter = (TextView) convertView.findViewById(R.id.tv_part_center);

        partCenter.setText(bean.displayName);
        if(position == (mDatas.size() - 1)) {
            centerImage.setImageResource(R.drawable.icon_add_photo);
            ViewUtil.setViewVisible(leftView, false);
            ViewUtil.setViewVisible(partLeft, false);
        } else {
            centerImage.setImageResource(R.drawable.icon_camera);
            ViewUtil.setViewVisible(leftView, true);
            ViewUtil.setViewVisible(partLeft, false);
        }

        ViewGroup.LayoutParams localLayoutParams = image.getLayoutParams();
        localLayoutParams.width = mImageWidth;
        localLayoutParams.height = (3 * mImageWidth / 4);
        image.setLayoutParams(localLayoutParams);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.jumpOnlyActivity(mContext, CameraActivity.class);
            }
        });

        return convertView;
    }
}
