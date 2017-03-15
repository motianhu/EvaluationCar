package com.smona.app.evaluationcar.framework.cache;

import com.smona.app.evaluationcar.framework.IProxy;

/**
 * Created by Moth on 2017/3/15.
 */

public class CacheProxy implements IProxy {
    private long getLastSuccessTime() {
        return 1;
    }

    private void putLastSuccessTime() {

    }

    private boolean isOverTime() {
        return false;
    }
}
