package com.smona.app.evaluationcar.framework.chatclient;

import android.content.Context;

import com.hyphenate.chat.ChatClient;
import com.hyphenate.helpdesk.easeui.UIProvider;
import com.smona.app.evaluationcar.framework.IProxy;

/**
 * Created by Moth on 2017/5/6.
 */

public class ChatClientProxy implements IProxy {
    public void  init(Context context) {
        ChatClient.Options options = new ChatClient.Options();
        options.setAppkey("1112170506115622#kefuchannelapp41042");
        options.setTenantId("41042");
        // Kefu SDK 初始化
        if (!ChatClient.getInstance().init(context, options)){
            return;
        }
        // Kefu EaseUI的初始化
        UIProvider.getInstance().init(context);
    }
}
