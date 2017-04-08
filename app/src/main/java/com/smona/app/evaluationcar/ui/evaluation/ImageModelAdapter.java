package com.smona.app.evaluationcar.ui.evaluation;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.smona.app.evaluationcar.R;
import com.smona.app.evaluationcar.data.bean.CarImageBean;
import com.smona.app.evaluationcar.framework.imageloader.ImageLoaderProxy;
import com.smona.app.evaluationcar.ui.evaluation.camera.CameraActivity;
import com.smona.app.evaluationcar.util.ActivityUtils;
import com.smona.app.evaluationcar.util.CarLog;
import com.smona.app.evaluationcar.util.ScreenInfo;
import com.smona.app.evaluationcar.util.ViewUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Moth on 2017/3/16.
 */

public class ImageModelAdapter extends BaseAdapter {

    private static final String TAG = ImageModelAdapter.class.getSimpleName();

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
        if(datas ==null) {
            return;
        }
        mDatas.clear();
        mDatas.addAll(datas);
        notifyDataSetChanged();
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
        final CarImageBean bean = mDatas.get(position);
        if (convertView == null) {
            convertView = ViewUtil.inflater(mContext, R.layout.evaluation_image_item);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CarLog.d(TAG, "bean: " + bean);
                    ActivityUtils.jumpCameraActivity(mContext, bean, CameraActivity.class);
                }
            });
        }
        ImageView image = (ImageView) convertView.findViewById(R.id.image);
        boolean hasPic = processImage(bean, image);

        ImageView centerImage = (ImageView) convertView.findViewById(R.id.iv_add_center);
        ViewUtil.setViewVisible(centerImage, true);

        TextView centerText = (TextView) convertView.findViewById(R.id.tv_part_center);
        String diplayName = TextUtils.isEmpty(bean.displayName) ? mContext.getString(R.string.add_picture) : bean.displayName;
        centerText.setText(diplayName);
        ViewUtil.setViewVisible(centerText, true);

        TextView leftText = (TextView) convertView.findViewById(R.id.tv_part_left);
        ViewUtil.setViewVisible(leftText, false);

        if (position == (mDatas.size() - 1)) {
            centerImage.setImageResource(R.drawable.icon_add_photo);
        } else {
            if (hasPic) {
                ViewUtil.setViewVisible(centerImage, false);
                ViewUtil.setViewVisible(centerText, false);
                ViewUtil.setViewVisible(leftText, true);
                leftText.setText(diplayName);
            } else {
                centerImage.setImageResource(R.drawable.icon_camera);
            }
        }


        return convertView;
    }

    private boolean processImage(CarImageBean bean, ImageView image) {
        ViewGroup.LayoutParams localLayoutParams = image.getLayoutParams();
        localLayoutParams.width = mImageWidth;
        localLayoutParams.height = (3 * mImageWidth / 4);
        image.setLayoutParams(localLayoutParams);

        String picUrl = null;

        if (!TextUtils.isEmpty(bean.imageRemoteUrl)) {
            picUrl = bean.imageRemoteUrl;
            ImageLoaderProxy.loadImage(bean.imageRemoteUrl, image);
        } else if (!TextUtils.isEmpty(bean.imageLocalUrl)) {
            picUrl = "file://" + bean.imageLocalUrl;

        }
        if (!TextUtils.isEmpty(picUrl)) {
            ImageLoaderProxy.loadImage(picUrl, image);
        }

        return !TextUtils.isEmpty(picUrl);
    }

    public int checkPhoto() {
        for (int i = 0; i < mDatas.size(); i++) {
            if (TextUtils.isEmpty(mDatas.get(i).imageLocalUrl)) {
                return i;
            }
        }
        return -1;
    }
}
