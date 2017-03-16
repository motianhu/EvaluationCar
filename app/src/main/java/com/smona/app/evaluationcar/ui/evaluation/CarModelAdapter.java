package com.smona.app.evaluationcar.ui.evaluation;

import android.content.Context;
import android.media.Image;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.smona.app.evaluationcar.R;
import com.smona.app.evaluationcar.data.bean.ImageMeta;
import com.smona.app.evaluationcar.util.ViewUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Moth on 2017/3/16.
 */

public class CarModelAdapter extends BaseAdapter {

    private List<ImageMeta> mDatas = new ArrayList<ImageMeta>();
    private Context mContext;
    public CarModelAdapter(Context context) {
        mContext =context;
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
        if(convertView == null) {
            convertView = ViewUtils.inflater(mContext, R.layout.evaluation_image_item);
        }
        TextView title = (TextView)convertView.findViewById(R.id.name);
        title.setText("aaaa");
        ImageView image = (ImageView)convertView.findViewById(R.id.image);

        return convertView;
    }
}
