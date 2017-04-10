package com.bcm.account.application;

import android.app.Application;

import cn.bmob.v3.Bmob;

/**
 * Created by Bean on 2017/4/10.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Bmob.initialize(getApplicationContext(),"0450526eb3fccef2b112f2263be2c31e");
    }
}
