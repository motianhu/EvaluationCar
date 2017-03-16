package com.smona.app.evaluationcar.ui.evaluation;

import android.content.Context;
import android.media.Image;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.smona.app.evaluationcar.data.bean.ImageMeta;
import com.smona.app.evaluationcar.framework.provider.ImageMetaTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Moth on 2017/3/16.
 */

public class CarModelAdapter extends BaseAdapter {

    private List<ImageMeta> mData = new ArrayList<ImageMeta>();
    private Context mContext;
    public CarModelAdapter(Context context) {
        mContext =context;
    }

    public void update(List<ImageMeta> datas) {
        mData.addAll(datas);
    }


    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
