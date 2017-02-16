package com.smona.app.evaluationcar.ui.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.smona.app.evaluationcar.R;

/**
 * Created by Jay on 2015/8/28 0028.
 */
public class SettingFragment extends ContentFragment {

    public SettingFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting,container,false);
        TextView txt_content = (TextView) view.findViewById(R.id.txt_content);
        txt_content.setText(getString(R.string.home_fragment_setting));
        return view;
    }
}
