package com.smona.app.evaluationcar.framework.request;

/**
 * Created by Moth on 2017/3/15.
 */

public class Deletor {
    private volatile static Deletor sInstance;

    private Deletor() {
    }

    public static Deletor getInstance() {
        if (sInstance == null) {
            sInstance = new Deletor();
        }
        return sInstance;
    }

    public void requestBanner() {
        HomeDeletor.getInstance().requestBanner();
    }

    public void requestNews() {
        HomeDeletor.getInstance().requestNews();
    }

    public void requestTotallBill() {HomeDeletor.getInstance().requestTotallBill();}

    public void requestPerBillCount() {HomeDeletor.getInstance().requestTotallBill();}

    public void requestNotice() {HomeDeletor.getInstance().requestTotallBill();}
}
