package com.yxy.viewhelp.checkview;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

import androidx.annotation.IdRes;

/**
 * 布局抽象类，主要是用来在屏幕上方新建view并且设置其参数
 */

public abstract class BaseFloatPage {

    private View mRootView;
    private WindowManager.LayoutParams mLayoutParams;
    private String mTag;
    public void performCreate(Context context) {

        onCreate(context);
        mRootView = new FrameLayout(context) {
            @Override
            public boolean dispatchKeyEvent(KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP) {
                    if (event.getKeyCode() == KeyEvent.KEYCODE_BACK || event.getKeyCode() == KeyEvent.KEYCODE_HOME) {
                        return onBackPressed();
                    }
                }
                return super.dispatchKeyEvent(event);
            }
        };
        View view = onCreateView(context, (ViewGroup) mRootView);
        ((ViewGroup) mRootView).addView(view);
        onViewCreated(mRootView);
        mLayoutParams = new WindowManager.LayoutParams();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mLayoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            mLayoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
        mLayoutParams.format = PixelFormat.TRANSPARENT;
        mLayoutParams.gravity = Gravity.LEFT | Gravity.TOP;
        onLayoutParamsCreated(mLayoutParams);

    }



    protected <T extends View> T findViewById(@IdRes int id) {
        return mRootView.findViewById(id);
    }

    protected void onViewCreated(View view) {

    }

    protected abstract View onCreateView(Context context, ViewGroup view);

    protected void onLayoutParamsCreated(WindowManager.LayoutParams params) {

    }

    protected void onCreate(Context context) {

    }

    protected void onDestroy() {

    }

    public View getRootView() {
        return mRootView;
    }

    public WindowManager.LayoutParams getLayoutParams() {
        return mLayoutParams;
    }


    public void onEnterBackground() {

    }

    public void onEnterForeground() {

    }



    protected boolean onBackPressed() {
        return false;
    }





    public String getTag() {
        return mTag;
    }


}
