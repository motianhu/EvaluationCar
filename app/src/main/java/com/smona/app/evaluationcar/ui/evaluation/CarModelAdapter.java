package com.smona.app.evaluationcar.ui.evaluation;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.smona.app.evaluationcar.R;
import com.smona.app.evaluationcar.data.bean.ImageMeta;
import com.smona.app.evaluationcar.util.ScreenInfo;
import com.smona.app.evaluationcar.util.ViewUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Moth on 2017/3/16.
 */

public class CarModelAdapter extends BaseAdapter {

    private List<ImageMeta> mDatas = new ArrayList<ImageMeta>();
    private Context mContext;
    private int mImageWidth;

    public CarModelAdapter(Context context) {
        mContext = context;
        int i = ScreenInfo.getInstance().getScreenWidth();
        int j = context.getResources().getDimensionPixelSize(R.dimen.activity_horizontal_margin);
        mImageWidth = ((i - j * 3) / 2);
    }

    public void update(List<ImageMeta> datas) {
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
        if (convertView == null) {
            convertView = ViewUtils.inflater(mContext, R.layout.evaluation_image_item);
        }
//        TextView title = (TextView)convertView.findViewById(R.id.name);
//        title.setText("aaaa");
        ImageView image = (ImageView) convertView.findViewById(R.id.image);
        ViewGroup.LayoutParams localLayoutParams = image.getLayoutParams();
        localLayoutParams.width = mImageWidth;
        localLayoutParams.height = (3 * mImageWidth / 4);
        image.setLayoutParams(localLayoutParams);

        return convertView;
    }
}
