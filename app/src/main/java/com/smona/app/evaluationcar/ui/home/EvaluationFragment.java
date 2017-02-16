package com.smona.app.evaluationcar.ui.home;

import android.os.Bundle;s
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.smona.app.evaluationcar.R;
/**
 * Created by Jay on 2015/8/28 0028.
 */
public class EvaluationFragment extends ContentFragment {

    public EvaluationFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_evaluation,container,false);
        TextView txt_content = (TextView) view.findViewById(R.id.txt_content);
        txt_content.setText(getString(R.string.home_fragment_evaluation));
        return view;
    }
}
