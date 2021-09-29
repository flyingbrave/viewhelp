package com.yxy.viewhelp.layoutborder;





import static com.yxy.viewhelp.checkview.ContextUtils.getContext;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.yxy.viewhelp.R;
import com.yxy.viewhelp.checkview.BaseFloatPage;
import com.yxy.viewhelp.checkview.TouchProxy;
import com.yxy.viewhelp.checkview.UIUtils;


public class LayoutLevelFloatPage extends BaseFloatPage implements TouchProxy.OnTouchEventListener {
    private WindowManager mWindowManager;

    private CheckBox mSwitchButton;
    private View mClose;

    private TouchProxy mTouchProxy = new TouchProxy(this);

    private ScalpelFrameLayout mScalpelFrameLayout;

    private boolean mIsCheck;

    public void resolveActivity(Activity activity) {
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
        mScalpelFrameLayout = new ScalpelFrameLayout(root.getContext());
        while (root.getChildCount() != 0) {
            View child = root.getChildAt(0);
            if (child instanceof ScalpelFrameLayout) {
                mScalpelFrameLayout = (ScalpelFrameLayout) child;
                return;
            }
            root.removeView(child);
            mScalpelFrameLayout.addView(child);
        }
        mScalpelFrameLayout.setLayerInteractionEnabled(mIsCheck);
        root.addView(mScalpelFrameLayout);
    }

    @Override
    protected View onCreateView(Context context, ViewGroup view) {
        return LayoutInflater.from(context).inflate(R.layout.yxy_float_layout_level, view, false);
    }

    @Override
    protected void onViewCreated(View view) {
        super.onViewCreated(view);
        mSwitchButton = findViewById(R.id.switch_btn);
        Log.i("tag5","mSwitchButton初始化");
        mSwitchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (mScalpelFrameLayout != null) {
                        mScalpelFrameLayout.setLayerInteractionEnabled(true);
                    }
                } else {
                    if (mScalpelFrameLayout != null) {
                        mScalpelFrameLayout.setLayerInteractionEnabled(false);
                    }
                }
                mIsCheck = isChecked;
            }
        });

        getRootView().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return mTouchProxy.onTouchEvent(v, event);
            }
        });

    }

    @Override
    protected void onLayoutParamsCreated(WindowManager.LayoutParams params) {
        params.x = 0;
        params.y = UIUtils.getHeightPixels() - UIUtils.dp2px(getContext(), 125);
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
    }

    @Override
    protected void onCreate(Context context) {
        super.onCreate(context);
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        resolveActivity((Activity) context);
        Log.i("tag5","resolveActivity");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mScalpelFrameLayout != null) {
            mScalpelFrameLayout.setLayerInteractionEnabled(false);
            mScalpelFrameLayout = null;
        }

    }

    @Override
    public void onMove(int x, int y, int dx, int dy) {
        getLayoutParams().x += dx;
        getLayoutParams().y += dy;
        mWindowManager.updateViewLayout(getRootView(), getLayoutParams());
    }

    @Override
    public void onUp(int x, int y) {

    }

    @Override
    public void onDown(int x, int y) {

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

