package com.smona.app.evaluationcar.ui;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.Toast;

import com.smona.app.evaluationcar.R;
import com.smona.app.evaluationcar.data.item.UserItem;
import com.smona.app.evaluationcar.ui.common.activity.BaseActivity;
import com.smona.app.evaluationcar.ui.common.activity.PermissionActivity;
import com.smona.app.evaluationcar.util.CarLog;
import com.smona.app.evaluationcar.util.Utils;

import java.util.ArrayList;

public class LoginActivity extends PermissionActivity implements OnClickListener, OnDismissListener {
    protected static final String TAG = "LoginActivity";
    private LinearLayout mLoginLinearLayout; // 登录内容的容器
    private Animation mTranslate; // 位移动画
    private Dialog mLoginingDlg; // 显示正在登录的Dialog
    private EditText mIdEditText; // 登录ID编辑框
    private EditText mPwdEditText; // 登录密码编辑框
    private Button mLoginButton; // 登录按钮
    private String mIdString;
    private String mPwdString;
    private UserItem mUser; // 用户列表
    private View mRegisterView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initUserList();
        initView();
        setListener();
        initAnim();
        bindEditText();
    }

    private void bindEditText() {
        if (mUser != null) {
            /* 将列表中的第一个user显示在编辑框 */
            mIdEditText.setText(mUser.getId());
            mPwdEditText.setText(mUser.getPwd());
        }
    }

    private void initAnim() {
        mTranslate = AnimationUtils.loadAnimation(this, R.anim.my_translate); // 初始化动画对象
        mLoginLinearLayout.startAnimation(mTranslate); // Y轴水平移动
    }

    private void initUserList() {
        /* 获取已经保存好的用户密码 */
        ArrayList<UserItem> users = Utils.getUserList(LoginActivity.this);
        mUser = users.size() > 0 ? users.get(0) : null;
    }

    private void setListener() {
        mIdEditText.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                mIdString = s.toString();
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            public void afterTextChanged(Editable s) {
            }
        });
        mPwdEditText.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                mPwdString = s.toString();
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            public void afterTextChanged(Editable s) {
            }
        });
        mLoginButton.setOnClickListener(this);
        mRegisterView.setOnClickListener(this);
    }

    private void initView() {
        mIdEditText = (EditText) findViewById(R.id.login_edtId);
        mPwdEditText = (EditText) findViewById(R.id.login_edtPwd);
        mLoginButton = (Button) findViewById(R.id.login_btnLogin);
        mLoginLinearLayout = (LinearLayout) findViewById(R.id.login_linearLayout);
        mRegisterView = findViewById(R.id.register);

        initLoginingDlg();
    }

    /* 初始化正在登录对话框 */
    private void initLoginingDlg() {

        mLoginingDlg = new Dialog(this, R.style.loginingDlg);
        mLoginingDlg.setContentView(R.layout.logining_dlg);

        Window window = mLoginingDlg.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        // 获取和mLoginingDlg关联的当前窗口的属性，从而设置它在屏幕中显示的位置

        // 获取屏幕的高宽
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int cxScreen = dm.widthPixels;
        int cyScreen = dm.heightPixels;

        int height = (int) getResources().getDimension(
                R.dimen.loginingdlg_height);// 高42dp
        int lrMargin = (int) getResources().getDimension(
                R.dimen.loginingdlg_lr_margin); // 左右边沿10dp
        int topMargin = (int) getResources().getDimension(
                R.dimen.loginingdlg_top_margin); // 上沿20dp

        params.y = (-(cyScreen - height) / 2) + topMargin; // -199
        /* 对话框默认位置在屏幕中心,所以x,y表示此控件到"屏幕中心"的偏移量 */

        params.width = cxScreen;
        params.height = height;
        // width,height表示mLoginingDlg的实际大小

        mLoginingDlg.setCanceledOnTouchOutside(true); // 设置点击Dialog外部任意区域关闭Dialog
    }

    /* 显示正在登录对话框 */
    private void showLoginingDlg() {
        if (mLoginingDlg != null)
            mLoginingDlg.show();
    }

    /* 关闭正在登录对话框 */
    private void closeLoginingDlg() {
        if (mLoginingDlg != null && mLoginingDlg.isShowing())
            mLoginingDlg.dismiss();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btnLogin:
                // 启动登录
                showLoginingDlg(); // 显示"正在登录"对话框,因为此Demo没有登录到web服务器,所以效果可能看不出.可以结合情况使用

                CarLog.d(this, mIdString + "  " + mPwdString);
                if (mIdString == null || mIdString.equals("")) { // 账号为空时
                    Toast.makeText(LoginActivity.this, "请输入账号", Toast.LENGTH_SHORT)
                            .show();
                } else if (mPwdString == null || mPwdString.equals("")) {// 密码为空时
                    Toast.makeText(LoginActivity.this, "请输入密码", Toast.LENGTH_SHORT)
                            .show();
                } else {// 账号和密码都不为空时
                    mUser = new UserItem(mIdString, mPwdString);
                    closeLoginingDlg();// 关闭对话框
                    Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
                    gotoStartup();
                }
                break;
            case R.id.register:
                gotoRegister();
                break;
            default:
                break;
        }

    }

    /* PopupWindow对象dismiss时的事件 */
    @Override
    public void onDismiss() {
        CarLog.d(this, "切换为角向下图标");
    }

    /* 退出此Activity时保存users */
    @Override
    public void onPause() {
        super.onPause();
        try {
            Utils.saveUser(LoginActivity.this, mUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void gotoStartup() {
        Intent intent = new Intent();
        intent.setClass(this, StartupActivity.class);
        startActivity(intent);
        finish();
    }

    private void gotoRegister() {
        Intent intent = new Intent();
        intent.setClass(this, RegisterActivity.class);
        startActivity(intent);
    }


}
