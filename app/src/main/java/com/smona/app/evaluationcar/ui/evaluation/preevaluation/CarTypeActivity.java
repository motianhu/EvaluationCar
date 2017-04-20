package com.smona.app.evaluationcar.ui.evaluation.preevaluation;

import android.content.Intent;
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
import com.smona.app.evaluationcar.data.event.TypeActionEvent;
import com.smona.app.evaluationcar.data.item.BrandItem;
import com.smona.app.evaluationcar.data.item.CityItem;
import com.smona.app.evaluationcar.data.item.SetItem;
import com.smona.app.evaluationcar.data.item.TypeItem;
import com.smona.app.evaluationcar.data.model.ResBrandPage;
import com.smona.app.evaluationcar.data.model.ResSetPage;
import com.smona.app.evaluationcar.data.model.ResTypePage;
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
    private TypeListViewAdapter mTypeAdapter;
    private List<TypeItem> mTypeList = new ArrayList<>();
    private List<String> mTypeLetterList = new ArrayList<>();
    private List<GroupTypeInfo> mTypeGroupByList = new ArrayList<>();

    private DrawerLayout mCarBrandDrawer;
    private DrawerLayout mCarSetDrawer;

    private BrandItem mSelectedBrand;
    private SetItem mSelectedSet;


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

        /********车品牌************/
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
        mBrandListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                mCarBrandDrawer.openDrawer(Gravity.RIGHT);
                mCarSetDrawer.closeDrawer(Gravity.RIGHT);
                mSelectedBrand = mBrandGroupByList.get(groupPosition).childList.get(childPosition);
                querySet(mSelectedBrand.id);
                return false;
            }
        });
        mBrandAdapter = new BrandListViewAdapter(this);
        mBrandAdapter.setGroupList(mBrandLetterList, mBrandGroupByList);
        mBrandListView.setAdapter(mBrandAdapter);


        /********车系************/
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
        mSetListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                mCarSetDrawer.openDrawer(Gravity.RIGHT);
                mSelectedSet = mSetGroupByList.get(groupPosition).childList.get(childPosition);
                queryType(mSelectedBrand.id, mSelectedSet.id);
                return false;
            }
        });
        mSetAdapter = new SetListViewAdapter(this);
        mSetAdapter.setGroupList(mSetLetterList, mSetGroupByList);
        mSetListView.setAdapter(mSetAdapter);


        /********车型************/
        mTypeListView = (ExpandableListView) findViewById(R.id.carTypeList);
        mTypeListView.setGroupIndicator(null);
        mTypeListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if (mTypeGroupByList.get(groupPosition) == null) {
                    return true;
                }
                return false;
            }
        });
        mTypeListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                TypeItem type = mTypeGroupByList.get(groupPosition).childList.get(childPosition);
                Intent intent = new Intent();
                intent.putExtra(ActivityUtils.ACTION_DATA_TYPE, type);
                intent.putExtra(ActivityUtils.ACTION_DATA_BRAND, mSelectedBrand);
                intent.putExtra(ActivityUtils.ACTION_DATA_SET, mSelectedSet);
                setResult(RESULT_OK, intent);
                finish();
                return false;
            }
        });
        mTypeAdapter = new TypeListViewAdapter(this);
        mTypeAdapter.setGroupList(mTypeLetterList, mTypeGroupByList);
        mTypeListView.setAdapter(mTypeAdapter);
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
        HttpDelegator.getInstance().queryCarType(brandId, setId, mTypeCallback);
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

    private ResponseCallback<String> mTypeCallback = new ResponseCallback<String>() {
        @Override
        public void onFailed(String error) {
            CarLog.d(TAG, "onFailed error=" + error);
        }

        @Override
        public void onSuccess(String content) {
            CarLog.d(TAG, "mTypeCallback onSuccess content=" + content);
            ResTypePage typePage = JsonParse.parseJson(content, ResTypePage.class);
            clearType();
            if (typePage.data != null && typePage.data.size() > 0) {
                mTypeList.addAll(typePage.data);
                processTypeLetter();
                processTypeGroupBY();

                EventProxy.post(new TypeActionEvent());
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void actionMainEvent(TypeActionEvent actionEvent) {
        CarLog.d(TAG, "actionMainEvent TypeActionEvent");
        mTypeAdapter.notifyDataSetChanged();
        for (int i = 0; i < mTypeAdapter.getGroupCount(); i++) {
            mTypeListView.expandGroup(i);
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

    private void clearType() {
        mTypeList.clear();
        mTypeLetterList.clear();
        for (GroupTypeInfo info : mTypeGroupByList) {
            info.childList.clear();
        }
        mTypeGroupByList.clear();
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


    private void processTypeLetter() {
        mTypeLetterList.add(getResources().getString(R.string.evalution_car_type));
    }

    private void processTypeGroupBY() {
        List<TypeItem> value = new ArrayList<>();
        GroupTypeInfo info = new GroupTypeInfo();
        info.childList = value;
        info.letter = getResources().getString(R.string.evalution_car_type);
        for (TypeItem type : mTypeList) {
            value.add(type);
        }
        mTypeGroupByList.add(info);
    }
}
