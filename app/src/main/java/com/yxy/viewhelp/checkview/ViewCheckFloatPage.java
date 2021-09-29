package com.yxy.viewhelp.checkview;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;


import com.yxy.viewhelp.R;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
 * 上层添加层级,用来获取view具体是触摸的哪个view
 */
public class ViewCheckFloatPage extends BaseFloatPage implements TouchProxy.OnTouchEventListener {
    private static final String TAG = "ViewCheckFloatPage";

    private TouchProxy mTouchProxy = new TouchProxy(this);

    protected WindowManager mWindowManager;

    private List<OnViewSelectListener> mViewSelectListeners = new ArrayList<>();

    private Activity mResumedActivity;


    @Override
    protected void onCreate(Context context) {
        super.onCreate(context);
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        mResumedActivity = (Activity) context;
    }


    @Override
    protected View onCreateView(Context context, ViewGroup view) {
        return LayoutInflater.from(context).inflate(R.layout.yxy_float_view_check, null);
    }

    @Override
    protected void onLayoutParamsCreated(WindowManager.LayoutParams params) {
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        params.x = UIUtils.getWidthPixels() / 2;
        params.y = UIUtils.getHeightPixels() / 2;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
    }

    @Override
    protected void onViewCreated(View view) {
        super.onViewCreated(view);
        getRootView().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return mTouchProxy.onTouchEvent(v, event);
            }
        });
    }

    private View findSelectView(int x, int y) {
        if (mResumedActivity == null) {
            return null;
        }
        if (mResumedActivity.getWindow() == null) {
            return null;
        }
        return traverseViews(mResumedActivity.getWindow().getDecorView(), x, y);
    }

    private View traverseViews(View view, int x, int y) {
        int[] location = new int[2];
        view.getLocationInWindow(location);
        int left = location[0];
        int top = location[1];
        int right = left + view.getWidth();
        int bottom = top + view.getHeight();
        boolean findView = left < x && x < right && top < y && y < bottom;
        if (view instanceof ViewGroup) {
            int childCount = ((ViewGroup) view).getChildCount();
            if (childCount != 0) {
                for (int index = childCount - 1; index >= 0; index--) {
                    View v = traverseViews(((ViewGroup) view).getChildAt(index), x, y);
                    if (v != null) {
                        return v;
                    }
                }
            }
            if (findView) {
                return view;
            } else {
                return null;
            }
        } else {
            if (findView) {
                return view;
            } else {
                return null;
            }
        }
    }

    public void setViewSelectListener(OnViewSelectListener viewSelectListener) {
        mViewSelectListeners.add(viewSelectListener);
    }

    public void removeViewSelectListener(OnViewSelectListener viewSelectListener) {
        mViewSelectListeners.remove(viewSelectListener);
    }

    @Override
    public void onMove(int x, int y, int dx, int dy) {
        getLayoutParams().x += dx;
        getLayoutParams().y += dy;
        mWindowManager.updateViewLayout(getRootView(), getLayoutParams());
    }

    @Override
    public void onUp(int x, int y) {
        View selectView = findSelectView(getLayoutParams().x + getRootView().getWidth() / 2, getLayoutParams().y + getRootView().getHeight() / 2);
        onViewSelected(selectView);
    }

    @Override
    public void onDown(int x, int y) {

    }

    private void onViewSelected(View view) {
        for (OnViewSelectListener listener : mViewSelectListeners) {
            listener.onViewSelected(view);
        }
        traversalView(view);

    }


    public void traversalView(View view) {
        if (null == view) {
            return;
        }
        Log.i("tag5", "开始打印");
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            LinkedList<ViewGroup> queue = new LinkedList<ViewGroup>();
            queue.add(viewGroup);
            printView(viewGroup);
            while (!queue.isEmpty()) {
                ViewGroup current = queue.removeFirst();
                for (int i = 0; i < current.getChildCount(); i++) {
                    View cv1 = current.getChildAt(i);
                    if (cv1 instanceof ViewGroup) {
                        printView(cv1);
                        queue.addLast((ViewGroup) current.getChildAt(i));
                    } else {
                        printView(cv1);
                    }
                }
            }
        } else {
            printView(view);
        }
    }

    private void printView(View view) {
        String info=UIUtils.getIdText(view);
        if (view instanceof ViewGroup) {
            Log.i("tag5", "viewGroup  " + info);
        } else {
            Log.i("tag5", "view  " + info);
        }
        Toast.makeText(getRootView().getContext(), ""+info, Toast.LENGTH_SHORT).show();

    }


    public interface OnViewSelectListener {
        void onViewSelected(View view);
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
