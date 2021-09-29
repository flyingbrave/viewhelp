package com.yxy.viewhelp.checkview;

import android.content.Context;

public class ContextUtils {
    private static Context mContext;

    public static void setContext(Context context) {
        mContext = context;
    }

    public static Context getContext() {
        return mContext;
    }
}
