package com.smona.app.evaluationcar.util;

import android.content.Context;
import android.util.Log;

import com.smona.app.evaluationcar.data.item.UserItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

public class Utils {

    private static final String FILENAME = "userinfo.json"; // 用户保存文件名

    /* 保存用户登录信息列表 */
    public static void saveUser(Context context, UserItem user)
            throws Exception {
        Writer writer = null;
        OutputStream out = null;
        JSONArray array = new JSONArray();
        array.put(user.toJSON());
        try {
            out = context.openFileOutput(FILENAME, Context.MODE_PRIVATE); // 覆盖
            writer = new OutputStreamWriter(out);
            writer.write(array.toString());
        } finally {
            if (writer != null)
                writer.close();
        }

    }

    /* 获取用户登录信息列表 */
    public static ArrayList<UserItem> getUserList(Context context) {
		/* 加载 */
        FileInputStream in = null;
        ArrayList<UserItem> users = new ArrayList<UserItem>();
        try {

            in = context.openFileInput(FILENAME);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(in));
            StringBuilder jsonString = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonString.append(line);
            }

            JSONArray jsonArray = (JSONArray) new JSONTokener(jsonString.toString())
                    .nextValue(); // 把字符串转换成JSONArray对象
            for (int i = 0; i < jsonArray.length(); i++) {
                UserItem user = new UserItem(jsonArray.getJSONObject(i));
                users.add(user);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return users;
    }
}
