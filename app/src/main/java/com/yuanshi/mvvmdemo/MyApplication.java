package com.yuanshi.mvvmdemo;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        Fresco.initialize(this);
        super.onCreate();
    }
}
