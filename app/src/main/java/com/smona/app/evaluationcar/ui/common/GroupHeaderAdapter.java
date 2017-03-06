package com.smona.app.evaluationcar.ui.common;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Moth on 2017/3/6.
 */

public interface GroupHeaderAdapter {

    boolean isSectionHeader(int position);

    int getSectionForPosition(int position);

    View getSectionHeaderView(int section, View convertView, ViewGroup parent);

    int getSectionHeaderViewType(int section);

    int getCount();
}
