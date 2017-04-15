package com.smona.app.evaluationcar.ui.evaluation.preevaluation;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.ExpandableListView;

import com.smona.app.evaluationcar.R;
import com.smona.app.evaluationcar.business.HttpDelegator;
import com.smona.app.evaluationcar.business.ResponseCallback;
import com.smona.app.evaluationcar.data.event.BrandActionEvent;
import com.smona.app.evaluationcar.data.event.SetActionEvent;
import com.smona.app.evaluationcar.data.item.BrandItem;
import com.smona.app.evaluationcar.data.item.SetItem;
import com.smona.app.evaluationcar.data.model.ResBrandPage;
import com.smona.app.evaluationcar.data.model.ResSetPage;
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

    private ExpandableListView mBrandListView;
    private BrandListViewAdapter mBrandAdapter;
    private List<BrandItem> mBrandList = new ArrayList<>();
    private List<String> mBrandLetterList = new ArrayList<>();
    private List<GroupBrandInfo> mBrandGroupByList = new ArrayList<>();

    private ExpandableListView mSetListView;
    private SetListViewAdapter mSetAdapter;
    private List<SetItem> mSetList = new ArrayList<>();
    private List<String> mSetLetterList = new ArrayList<>();
    private List<GroupSetInfo> mSetGroupByList = new ArrayList<>();


    private ExpandableListView mTypeListView;
    private SetListViewAdapter mTypeAdapter;
    private List<BrandItem> mTypeList = new ArrayList<>();
    private List<String> mTypeLetterList = new ArrayList<>();
    private List<GroupBrandInfo> mTypeGroupByList = new ArrayList<>();

    private DrawerLayout mCarBrandDrawer;
    private DrawerLayout mCarSetDrawer;

    private String mSelectBrandId;
    private String mSelectSetId;
    private String mSelectTypeId;


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
        mCarBrandDrawer = (DrawerLayout)findViewById(R.id.drawerCarBrand);
        mCarBrandDrawer.setScrimColor(Color.TRANSPARENT);

        mCarSetDrawer = (DrawerLayout)findViewById(R.id.drawerCarSet);
        mCarSetDrawer.setScrimColor(Color.TRANSPARENT);

        //车品牌
        mBrandListView = (ExpandableListView) findViewById(R.id.carBrandList);
        mBrandListView.setGroupIndicator(null);
        mBrandListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if (mBrandGroupByList.get(groupPosition) == null) {
                    return true;
                }
                return false;
            }
        });

        // 监听每个分组里子控件的点击事件
        mBrandListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                mCarBrandDrawer.openDrawer(Gravity.RIGHT);
                BrandItem item = mBrandGroupByList.get(groupPosition).childList.get(childPosition);
                mSelectBrandId = item.id;
                querySet(item.id);
                return false;
            }
        });

        mBrandAdapter = new BrandListViewAdapter(this);
        mBrandAdapter.setGroupList(mBrandLetterList, mBrandGroupByList);
        mBrandListView.setAdapter(mBrandAdapter);


        //车系
        mSetListView = (ExpandableListView) findViewById(R.id.carSetList);
        mSetListView.setGroupIndicator(null);
        mSetListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if (mSetGroupByList.get(groupPosition) == null) {
                    return true;
                }
                return false;
            }
        });

        // 监听每个分组里子控件的点击事件
        mSetListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                mCarSetDrawer.openDrawer(Gravity.RIGHT);

                return false;
            }
        });

        mSetAdapter = new SetListViewAdapter(this);
        mSetAdapter.setGroupList(mSetLetterList, mSetGroupByList);
        mSetListView.setAdapter(mSetAdapter);
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

    private void querySet(String brandId) {
        HttpDelegator.getInstance().queryCarSet(brandId, mSetCallback);
    }

    private void queryType(String brandId, String setId) {
        HttpDelegator.getInstance().queryCarType(brandId, setId, mSetCallback);
    }

    private ResponseCallback<String> mBrandCallback = new ResponseCallback<String>() {
        @Override
        public void onFailed(String error) {
            CarLog.d(TAG, "onFailed error=" + error);
        }

        @Override
        public void onSuccess(String content) {
            CarLog.d(TAG, "mBrandCallback onSuccess content=" + content);
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

    private ResponseCallback<String> mSetCallback = new ResponseCallback<String>() {
        @Override
        public void onFailed(String error) {
            CarLog.d(TAG, "onFailed error=" + error);
        }

        @Override
        public void onSuccess(String content) {
            CarLog.d(TAG, "mSetCallback onSuccess content=" + content);
            ResSetPage setPage = JsonParse.parseJson(content, ResSetPage.class);
            clearSet();
            if (setPage.data != null && setPage.data.size() > 0) {
                mSetList.addAll(setPage.data);
                processSetLetter();
                processSetGroupBY();

                EventProxy.post(new SetActionEvent());
            }
        }
    };

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void actionMainEvent(BrandActionEvent actionEvent) {
        CarLog.d(TAG, "actionMainEvent BrandActionEvent");
        mBrandAdapter.notifyDataSetChanged();
        for (int i = 0; i < mBrandAdapter.getGroupCount(); i++) {
            mBrandListView.expandGroup(i);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void actionMainEvent(SetActionEvent actionEvent) {
        CarLog.d(TAG, "actionMainEvent SetActionEvent");
        mSetAdapter.notifyDataSetChanged();
        for (int i = 0; i < mSetAdapter.getGroupCount(); i++) {
            mSetListView.expandGroup(i);
        }
    }


    private void clear() {
        mBrandList.clear();
        mBrandLetterList.clear();
        for (GroupBrandInfo info : mBrandGroupByList) {
            info.childList.clear();
        }
        mBrandGroupByList.clear();
    }


    private void clearSet() {
        mSetList.clear();
        mSetLetterList.clear();
        for (GroupSetInfo info : mSetGroupByList) {
            info.childList.clear();
        }
        mSetGroupByList.clear();
    }


    private void processLetter() {
        HashMap<String, String> letterMap = new HashMap<>();
        boolean isExist;
        for (BrandItem brand : mBrandList) {
            isExist = letterMap.containsKey(brand.brandFirstName);
            if (!isExist) {
                letterMap.put(brand.brandFirstName, brand.brandFirstName);
                mBrandLetterList.add(brand.brandFirstName);
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
                GroupBrandInfo info = new GroupBrandInfo();
                info.childList = value;
                info.letter = brand.brandFirstName;
                mBrandGroupByList.add(info);
                letterMap.put(brand.brandFirstName, mBrandGroupByList.size() - 1);
            } else {
                GroupBrandInfo info = mBrandGroupByList.get(index.intValue());
                info.childList.add(brand);
            }
        }
    }

    private void processSetLetter() {
        HashMap<String, String> letterMap = new HashMap<>();
        boolean isExist;
        for (SetItem set : mSetList) {
            isExist = letterMap.containsKey(set.carSetFirstName);
            if (!isExist) {
                letterMap.put(set.carSetFirstName, set.carSetFirstName);
                mSetLetterList.add(set.carSetFirstName);
            }
        }
    }
    private void processSetGroupBY() {
        HashMap<String, Integer> letterMap = new HashMap<>();
        Integer index;
        for (SetItem set : mSetList) {
            index = letterMap.get(set.carSetFirstName);
            if (index == null) {
                List<SetItem> value = new ArrayList<>();
                value.add(set);
                GroupSetInfo info = new GroupSetInfo();
                info.childList = value;
                info.letter = set.carSetFirstName;
                mSetGroupByList.add(info);
                letterMap.put(set.carSetFirstName, mSetGroupByList.size() - 1);
            } else {
                GroupSetInfo info = mSetGroupByList.get(index.intValue());
                info.childList.add(set);
            }
        }
    }

}
