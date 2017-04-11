package com.smona.app.evaluationcar.ui.evaluation;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.smona.app.evaluationcar.R;
import com.smona.app.evaluationcar.ui.common.activity.UserActivity;
import com.smona.app.evaluationcar.ui.common.group.GroupListView;
import com.smona.app.evaluationcar.util.ViewUtil;

/**
 * Created by Moth on 2017/3/6.
 */
//http://blog.csdn.net/u010335298/article/details/51178179
public class PreEvaluationActivity extends UserActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preevaluation);

        initListView(R.id.brandListView);
        initListView(R.id.setListView);
    }

    private void initListView(int id) {
        GroupListView listView = (GroupListView) findViewById(id);
        LinearLayout header1 = (LinearLayout) ViewUtil.inflater(this, R.layout.preevaluation_list_item);
        ((TextView) header1.findViewById(R.id.textItem)).setText("HEADER 1");
        LinearLayout header2 = (LinearLayout) ViewUtil.inflater(this, R.layout.preevaluation_list_item);
        ((TextView) header2.findViewById(R.id.textItem)).setText("HEADER 2");
        LinearLayout footer = (LinearLayout) ViewUtil.inflater(this, R.layout.preevaluation_list_item);
        ((TextView) footer.findViewById(R.id.textItem)).setText("FOOTER");
        listView.addHeaderView(header1);
        listView.addHeaderView(header2);
        listView.addFooterView(footer);
        BrandGroupAdapter sectionedAdapter = new BrandGroupAdapter();
        listView.setAdapter(sectionedAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
