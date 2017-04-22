package com.smona.app.evaluationcar.ui.home;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.smona.app.evaluationcar.R;
import com.smona.app.evaluationcar.data.item.NewsItem;
import com.smona.app.evaluationcar.framework.imageloader.ImageLoaderProxy;
import com.smona.app.evaluationcar.ui.common.AbstractAdapter;
import com.smona.app.evaluationcar.util.ViewUtil;

import java.util.List;

/**
 * Created by Moth on 2017/2/24.
 */

public class HomeAdapter extends AbstractAdapter {

    public HomeAdapter(Context context) {
        super(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        NewsItem info = (NewsItem) (mDatas.get(position));
        if (convertView == null) {
            convertView = ViewUtil.inflater(mContext, R.layout.home_list_item);
        }
        ImageView image = (ImageView) convertView.findViewById(R.id.image);
        ImageLoaderProxy.loadImage(info.imageThumb, image);
        TextView title = (TextView) convertView.findViewById(R.id.title);
        title.setText(info.title);
        TextView time = (TextView) convertView.findViewById(R.id.time);
        time.setText(info.createTime);
        TextView summary = (TextView) convertView.findViewById(R.id.summary);
        summary.setText(info.shortContent);
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
