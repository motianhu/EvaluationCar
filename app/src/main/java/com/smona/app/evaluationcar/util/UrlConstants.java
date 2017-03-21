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


    public static final int CREATE_CARBILLID = 1;
    public static final int UPLOAD_IMAGE = 2;


    private static Map<Integer, String> INTEFACES = new HashMap<>();

    static {
        INTEFACES.put(CREATE_CARBILLID, "external/carBill/getCarBillIdNextVal.html");
        INTEFACES.put(UPLOAD_IMAGE, "external/app/uploadImage.html");
    }


    public static String getInterface(int type) {
        String domain;
        if (IS_TEST_ENV) {
            domain = DOMAIN_TEST;
        } else {
            domain = DOMAIN;
        }
        return getInterface(domain, type);
    }

    private static String getInterface(String domain, int type) {
        String url = domain + ":" + PORT + "/" + PROJECT + "/";
        String interfaceUrl = INTEFACES.get(type);
        return url + interfaceUrl;
    }

}
