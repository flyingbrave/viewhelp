package com.yxy.viewhelp.layoutborder;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;




public class LayoutBorderManager {
    private boolean isRunning;
    private ViewBorderFrameLayout mViewBorderFrameLayout;

    private void resolveActivity(Activity activity) {
        if (activity == null ) {
            return;
        }
        Window window = activity.getWindow();
        if (window == null) {
            return;
        }
        final ViewGroup root = (ViewGroup) window.getDecorView();
        if (root == null) {
            return;
        }
        mViewBorderFrameLayout = new ViewBorderFrameLayout(root.getContext());
        while (root.getChildCount() != 0) {
            View child = root.getChildAt(0);
            if (child instanceof ViewBorderFrameLayout) {
                mViewBorderFrameLayout = (ViewBorderFrameLayout) child;
                return;
            }
            root.removeView(child);
            mViewBorderFrameLayout.addView(child);
        }
        root.addView(mViewBorderFrameLayout);
    }

    private static class Holder {
        private static LayoutBorderManager INSTANCE = new LayoutBorderManager();
    }

    private LayoutBorderManager() {
    }

    public static LayoutBorderManager getInstance() {
        return Holder.INSTANCE;
    }

    public void start(Activity activity) {
        isRunning = true;
        resolveActivity(activity);
    }

    public void stop() {
        isRunning = false;
        if (mViewBorderFrameLayout != null) {
            mViewBorderFrameLayout.requestLayout();
        }
        mViewBorderFrameLayout = null;
    }

    public boolean isRunning() {
        return isRunning;
    }
}