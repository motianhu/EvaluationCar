package com.smona.app.evaluationcar.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by motianhu on 3/21/17.
 */

public class UrlConstants {
    private static boolean IS_TEST_ENV = true;

    private static final String DOMAIN = "http://119.23.128.214";
    private static final String DOMAIN_TEST = "http://119.23.128.214";
    private static final String PORT = "8080";
    private static final String PROJECT = "carWeb";

    private static Map<Integer, String> INTEFACES = new HashMap<>();


    public static final int CHECK_USER = 1;
    public static final int CREATE_CARBILLID = 2;
    public static final int UPLOAD_IMAGE = 3;
    public static final int SUBMIT_CARBILL = 4;
    public static final int QUERY_CARBILL_LIST = 5;
    public static final int QUERY_CARBILL_IMAGE = 6;
    public static final int QUERY_CARBILL_COUNT = 7;
    public static final int QUERY_OPERATION_DESC = 8;
    public static final int QUERY_NEWS_LATEST = 9;
    public static final int QUERY_NEWS_MORE = 10;
    public static final int QUERY_NEWS_DETAIL = 11;

    static {
        INTEFACES.put(CHECK_USER, "/external/app/checkUser.html");
        INTEFACES.put(CREATE_CARBILLID, "/external/carBill/getCarBillIdNextVal.html");
        INTEFACES.put(UPLOAD_IMAGE, "/external/app/uploadAppImage.html");
        INTEFACES.put(SUBMIT_CARBILL, "/external/app/finishCreateAppCarBill.html");
        INTEFACES.put(QUERY_CARBILL_LIST, "/external/app/getAppBillList.html");
        INTEFACES.put(QUERY_CARBILL_IMAGE, "/external/app/getAppBillImageList.html");
        INTEFACES.put(QUERY_CARBILL_COUNT, "/external/app/getApplyCountInfo.html");
        INTEFACES.put(QUERY_OPERATION_DESC, "/external/source/operation-desc.json");
        INTEFACES.put(QUERY_NEWS_LATEST, "/external/news/latestList.html");
        INTEFACES.put(QUERY_NEWS_MORE, "/external/news/moreList.html");
        INTEFACES.put(QUERY_NEWS_DETAIL, "/external/news/newsDetail.html");
    }


    public static String getInterface(int type) {
        String domain = getProjectInterface();
        return getInterface(domain, type);
    }

    public static String getProjectInterface() {
        String domain;
        if (IS_TEST_ENV) {
            domain = DOMAIN_TEST;
        } else {
            domain = DOMAIN;
        }
        return domain + ":" + PORT + "/" + PROJECT;
    }

    private static String getInterface(String domain, int type) {
        String interfaceUrl = INTEFACES.get(type);
        return domain + interfaceUrl;
    }

}
