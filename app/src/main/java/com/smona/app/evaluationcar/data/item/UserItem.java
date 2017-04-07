package com.smona.app.evaluationcar.data.item;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.smona.app.evaluationcar.util.AESUtils;
import com.smona.app.evaluationcar.util.CacheContants;
import com.smona.app.evaluationcar.util.CarLog;
import com.smona.app.evaluationcar.util.SPUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;
import org.xutils.x;

public class UserItem {
    private static final String TAG = UserItem.class.getSimpleName();
    private static final String masterPassword = "www.smonatech.com"; // AES加密算法的种子

    public String mId;
    public String mPwd;

    public void saveSelf(Context context,String id, String pwd) {
        try {
            String userName = AESUtils.encrypt(masterPassword, id);
            String password = AESUtils.encrypt(masterPassword, pwd);
            CarLog.d(TAG, "saveSelf userName=" + userName + ", password=" + password);
            SPUtil.put(context, CacheContants.LOGIN_USERNAME, userName);
            SPUtil.put(context, CacheContants.LOGIN_PASSWORD, password);

        } catch (Exception e) {
            e.printStackTrace();
            CarLog.d(TAG, "saveSelf e=" + e);
        }
    }

    public boolean readSelf(Context context) {
        String userName = (String)SPUtil.get(context, CacheContants.LOGIN_USERNAME, "");
        String password = (String)SPUtil.get(context, CacheContants.LOGIN_PASSWORD, "");

        if(TextUtils.isEmpty(userName) || TextUtils.isEmpty(password)) {
            return false;
        }
        try {
            mId = AESUtils.decrypt(masterPassword, userName);
            mPwd = AESUtils.decrypt(masterPassword, password);
        }catch(Exception e) {
            e.printStackTrace();
            CarLog.d(TAG, "readSelf e=" + e);
        }
        return false;
    }
}
