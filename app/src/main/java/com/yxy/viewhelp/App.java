package com.yxy.viewhelp;

import android.app.Application;

import com.yxy.viewhelp.checkview.ContextUtils;


public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ContextUtils.setContext(this);

    }
}
