package com.smona.app.evaluationcar.ui.home.fragment;

import android.view.View;

import com.smona.app.evaluationcar.R;
import com.smona.app.evaluationcar.ui.chat.CheckChatActivity;
import com.smona.app.evaluationcar.util.ActivityUtils;

/**
 * Created by Moth on 2015/8/28 0028.
 */
public class MessageFragment extends ContentFragment {
    protected int getLayoutId() {
        return R.layout.fragment_message;
    }

    protected void init(View root) {
        root.findViewById(R.id.txt_content).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ActivityUtils.jumpOnlyActivity(getContext(), CheckChatActivity.class);
            }
        });

    }
}
