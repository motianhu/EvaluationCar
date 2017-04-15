package com.smona.app.evaluationcar.ui.evaluation.preevaluation;

import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;

import com.smona.app.evaluationcar.R;
import com.smona.app.evaluationcar.business.HttpDelegator;
import com.smona.app.evaluationcar.business.ResponseCallback;
import com.smona.app.evaluationcar.data.event.BrandActionEvent;
import com.smona.app.evaluationcar.data.item.BrandItem;
import com.smona.app.evaluationcar.data.model.ResBrandPage;
import com.smona.app.evaluationcar.framework.event.EventProxy;
import com.smona.app.evaluationcar.framework.json.JsonParse;
import com.smona.app.evaluationcar.ui.common.activity.HeaderActivity;
import com.smona.app.evaluationcar.util.CarLog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Moth on 2017/4/15.
 */

public class CarTypeActivity extends HeaderActivity {
    private static final String TAG = CarTypeActivity.class.getSimpleName();

    private ExpandableListView mExpandableListView;
    private ExpandableListViewAdapter mExpandableAdapter;

    private List<BrandItem> mBrandList = new ArrayList<>();
    private List<String> mLetterList = new ArrayList<>();
    private List<GroupInfo> mGroupByList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        EventProxy.register(this);
        queryBrand();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventProxy.unregister(this);
    }

    private void initViews() {
        mExpandableListView = (ExpandableListView) findViewById(R.id.expendlist);
        mExpandableListView.setGroupIndicator(null);
        mExpandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if (mGroupByList.get(groupPosition) == null) {
                    return true;
                }
                return false;
            }
        });

        // 监听每个分组里子控件的点击事件
        mExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                return false;
            }
        });

        mExpandableAdapter = new ExpandableListViewAdapter(this);
        mExpandableAdapter.setGroupList(mLetterList, mGroupByList);
        mExpandableListView.setAdapter(mExpandableAdapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cartype;
    }

    @Override
    protected boolean showDelete() {
        return false;
    }

    @Override
    protected int getHeaderTitle() {
        return R.string.evalution_car_type;
    }


    private void queryBrand() {
        HttpDelegator.getInstance().queryCarBrand(mBrandCallback);
    }

    private ResponseCallback<String> mBrandCallback = new ResponseCallback<String>() {
        @Override
        public void onFailed(String error) {
            CarLog.d(TAG, "onFailed error=" + error);
        }

        @Override
        public void onSuccess(String content) {
            CarLog.d(TAG, "onSuccess content=" + content);
            ResBrandPage brandPage = JsonParse.parseJson(content, ResBrandPage.class);
            clear();
            if (brandPage.data != null && brandPage.data.size() > 0) {
                mBrandList.addAll(brandPage.data);
                processLetter();
                processGroupBy();

                EventProxy.post(new BrandActionEvent());
            }
        }
    };

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void actionMainEvent(BrandActionEvent actionEvent) {
        CarLog.d(TAG, "actionMainEvent");
        mExpandableAdapter.notifyDataSetChanged();
        for (int i = 0; i < mExpandableAdapter.getGroupCount(); i++) {
            mExpandableListView.expandGroup(i);
        }
    }

    private void clear() {
        mBrandList.clear();
        mLetterList.clear();
        for (GroupInfo info : mGroupByList) {
            info.childList.clear();
        }
        mGroupByList.clear();
    }


    private void processLetter() {
        HashMap<String, String> letterMap = new HashMap<>();
        boolean isExist;
        for (BrandItem brand : mBrandList) {
            isExist = letterMap.containsKey(brand.brandFirstName);
            if (!isExist) {
                letterMap.put(brand.brandFirstName, brand.brandFirstName);
                mLetterList.add(brand.brandFirstName);
            }
        }
    }

    private void processGroupBy() {
        HashMap<String, Integer> letterMap = new HashMap<>();
        Integer index;
        for (BrandItem brand : mBrandList) {
            index = letterMap.get(brand.brandFirstName);
            if (index == null) {
                List<BrandItem> value = new ArrayList<>();
                value.add(brand);
                GroupInfo info = new GroupInfo();
                info.childList = value;
                info.letter = brand.brandFirstName;
                mGroupByList.add(info);
                letterMap.put(brand.brandFirstName, mGroupByList.size() - 1);
            } else {
                GroupInfo info = mGroupByList.get(index.intValue());
                info.childList.add(brand);
            }
        }
    }

}
