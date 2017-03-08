package com.smona.app.evaluationcar.ui.home;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.smona.app.evaluationcar.R;
import com.smona.app.evaluationcar.data.NewsInfo;
import com.smona.app.evaluationcar.framework.imageloader.ImageLoaderProxy;
import com.smona.app.evaluationcar.ui.common.AbstractAdapter;
import com.smona.app.evaluationcar.util.ViewUtils;

import java.util.List;

/**
 * Created by Moth on 2017/2/24.
 */

public class HomeAdapter extends AbstractAdapter {

    public HomeAdapter(Context context) {
        super(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        NewsInfo info = (NewsInfo) (mDatas.get(position));
        if(convertView == null) {
            convertView = ViewUtils.inflater(mContext, R.layout.home_list_item);
        }
        ImageView image = (ImageView) convertView.findViewById(R.id.image);
        ImageLoaderProxy.loadImage(info.getImgurl(), image);
        TextView summary = (TextView) convertView.findViewById(R.id.summary);
        summary.setText(info.getSummary());
        return convertView;
    }

    @Override
    public void update(List datas) {
        mDatas.clear();
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {

    }
}
