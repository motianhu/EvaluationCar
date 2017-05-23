package com.smona.app.evaluationcar.ui.evaluation.preevaluation.simle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;

import com.smona.app.evaluationcar.R;
import com.smona.app.evaluationcar.business.HttpDelegator;
import com.smona.app.evaluationcar.business.ResponseCallback;
import com.smona.app.evaluationcar.data.event.CityActionEvent;
import com.smona.app.evaluationcar.data.item.CityItem;
import com.smona.app.evaluationcar.data.model.ResCityPage;
import com.smona.app.evaluationcar.framework.event.EventProxy;
import com.smona.app.evaluationcar.framework.json.JsonParse;
import com.smona.app.evaluationcar.ui.common.activity.HeaderActivity;
import com.smona.app.evaluationcar.util.ActivityUtils;
import com.smona.app.evaluationcar.util.CarLog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Moth on 2017/4/16.
 */

public class CityActivity extends HeaderActivity {
    private static  final String TAG = CityActivity.class.getSimpleName();

    private ExpandableListView mCityListView;
    private CityListViewAdapter mCityAdapter;
    private List<CityItem> mCityList = new ArrayList<>();
    private List<String> mCityLetterList = new ArrayList<>();
    private List<GroupCityInfo> mCityGroupByList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_city;
    }

    @Override
    protected boolean showDelete() {
        return false;
    }

    @Override
    protected int getHeaderTitle() {
        return R.string.select_city;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventProxy.register(this);
        initViews();
        queryCity();
    }

    @Override
    protected void onDelete() {
        super.onDelete();
        EventProxy.unregister(this);
    }

    private void initViews() {
        mCityListView = (ExpandableListView) findViewById(R.id.cityListView);
        mCityListView.setGroupIndicator(null);
        mCityListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if (mCityGroupByList.get(groupPosition) == null) {
                    return true;
                }
                return false;
            }
        });
        mCityListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                CityItem item = mCityGroupByList.get(groupPosition).childList.get(childPosition);
                Intent intent = new Intent();
                intent.putExtra(ActivityUtils.ACTION_DATA_CITY, item);
                setResult(RESULT_OK, intent);
                finish();
                return false;
            }
        });
        mCityAdapter = new CityListViewAdapter(this);
        mCityAdapter.setGroupList(mCityLetterList, mCityGroupByList);
        mCityListView.setAdapter(mCityAdapter);
    }


    private void queryCity() {
        HttpDelegator.getInstance().queryCity(mCityCallback);
    }


    private ResponseCallback<String> mCityCallback = new ResponseCallback<String>() {
        @Override
        public void onFailed(String error) {
            CarLog.d(TAG, "onFailed error=" + error);
        }

        @Override
        public void onSuccess(String content) {
            CarLog.d(TAG, "mCityCallback onSuccess content=" + content);
            ResCityPage typePage = JsonParse.parseJson(content, ResCityPage.class);
            clearType();
            if (typePage.data != null && typePage.data.size() > 0) {
                mCityList.addAll(typePage.data);
                processCityLetter();
                processCityGroupBY();

                EventProxy.post(new CityActionEvent());
            }
        }
    };

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void actionMainEvent(CityActionEvent actionEvent) {
        CarLog.d(TAG, "actionMainEvent CityActionEvent");
        mCityAdapter.notifyDataSetChanged();
        for (int i = 0; i < mCityAdapter.getGroupCount(); i++) {
            mCityListView.expandGroup(i);
        }
    }

    private void clearType() {
        mCityList.clear();
        mCityLetterList.clear();
        for (GroupCityInfo info : mCityGroupByList) {
            info.childList.clear();
        }
        mCityGroupByList.clear();
    }


    private void processCityLetter() {
        HashMap<String, String> letterMap = new HashMap<>();
        boolean isExist;
        String provinceName;
        for (CityItem item : mCityList) {
            provinceName = item.provinceName;
            isExist = letterMap.containsKey(provinceName);
            if (!isExist) {
                letterMap.put(provinceName, provinceName);
                mCityLetterList.add(provinceName);
            }
        }
    }

    private void processCityGroupBY() {
        HashMap<String, Integer> letterMap = new HashMap<>();
        Integer index;
        String provinceName;
        for (CityItem item: mCityList) {
            provinceName = item.provinceName;
            index = letterMap.get(provinceName);
            if (index == null) {
                List<CityItem> value = new ArrayList<>();
                value.add(item);
                GroupCityInfo info = new GroupCityInfo();
                info.childList = value;
                info.letter = provinceName;
                mCityGroupByList.add(info);
                letterMap.put(provinceName, mCityGroupByList.size() - 1);
            } else {
                GroupCityInfo info = mCityGroupByList.get(index.intValue());
                info.childList.add(item);
            }
        }
    }

}
