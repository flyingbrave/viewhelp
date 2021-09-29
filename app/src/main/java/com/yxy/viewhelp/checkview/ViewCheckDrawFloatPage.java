package com.yxy.viewhelp.checkview;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.yxy.viewhelp.R;
import com.yxy.viewhelp.checkview.view.LayoutBorderView;


/**
 * 上层添加层级,将目标view用红色的折线高亮起来
 */
public class ViewCheckDrawFloatPage extends BaseFloatPage implements ViewCheckFloatPage.OnViewSelectListener {
    private LayoutBorderView mLayoutBorderView;

    @Override
    protected void onCreate(Context context) {
        super.onCreate(context);
        ViewCheckFloatPage page = (ViewCheckFloatPage) FloatPageManager.mMapPages.get(FloatPageManager.VIEW_CHECK_PAGE);
        page.setViewSelectListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ViewCheckFloatPage page = (ViewCheckFloatPage) FloatPageManager.mMapPages.get(FloatPageManager.VIEW_CHECK_PAGE);
        if (page != null) {
            page.removeViewSelectListener(this);
        }
    }

    @Override
    protected View onCreateView(Context context, ViewGroup view) {
        return LayoutInflater.from(context).inflate(R.layout.yxy_float_view_check_draw, null);
    }

    @Override
    protected void onLayoutParamsCreated(WindowManager.LayoutParams params) {
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
    }

    @Override
    protected void onViewCreated(View view) {
        super.onViewCreated(view);
        mLayoutBorderView = findViewById(R.id.rect_view);
    }


    @Override
    public void onViewSelected(View view) {
        if (view == null) {
            mLayoutBorderView.showViewLayoutBorder(null);
        } else {
            mLayoutBorderView.showViewLayoutBorder(UIUtils.getViewRect(view));
        }
    }

    @Override
    public void onEnterForeground() {
        super.onEnterForeground();
        getRootView().setVisibility(View.VISIBLE);
    }

    @Override
    public void onEnterBackground() {
        super.onEnterBackground();
        getRootView().setVisibility(View.GONE);
    }
}

