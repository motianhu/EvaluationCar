package com.smona.app.evaluationcar.ui.common.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.smona.app.evaluationcar.data.item.UserItem;

/**
 * Created by Moth on 2016/12/15.
 */

public abstract class BaseActivity extends AppCompatActivity {
    protected UserItem mUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUser = new UserItem();
        mUser.readSelf(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
