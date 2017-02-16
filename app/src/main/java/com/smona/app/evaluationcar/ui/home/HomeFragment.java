package com.smona.app.evaluationcar.ui.home;

import android.view.View;
import android.widget.TextView;
import com.smona.app.evaluationcar.R;

/**
 * Created by Moth on 2015/8/28 0028.
 */
public class HomeFragment extends ContentFragment {

    public HomeFragment() {
    }

    protected  int getLayoutId() {
        return R.layout.fragment_home;
    }

    protected  void init(View view){
        TextView txt_content = (TextView) view.findViewById(R.id.txt_content);
        txt_content.setText(getString(R.string.home_fragment_home));
    }


}
