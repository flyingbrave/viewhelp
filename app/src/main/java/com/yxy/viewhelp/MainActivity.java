package com.yxy.viewhelp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;

import com.yxy.viewhelp.checkview.FloatPageManager;
import com.yxy.viewhelp.checkview.ViewCheckDrawFloatPage;
import com.yxy.viewhelp.checkview.ViewCheckFloatPage;
import com.yxy.viewhelp.layoutborder.LayoutBorderManager;
import com.yxy.viewhelp.layoutborder.LayoutLevelFloatPage;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.view_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Build.VERSION.SDK_INT >= 23) {
                    if (!Settings.canDrawOverlays(MainActivity.this)) {
                        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                                Uri.parse("package:" + getPackageName()));
                        startActivityForResult(intent, 10);
                    } else {
                        WindowManager mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
                        ViewCheckFloatPage page = new ViewCheckFloatPage();
                        FloatPageManager.mMapPages.put(FloatPageManager.VIEW_CHECK_PAGE, page);
                        page.performCreate(MainActivity.this);
                        mWindowManager.addView(page.getRootView(),
                                page.getLayoutParams());

                        ViewCheckDrawFloatPage viewCheckDrawFloatPage = new ViewCheckDrawFloatPage();
                        viewCheckDrawFloatPage.performCreate(MainActivity.this);
                        FloatPageManager.mMapPages.put(FloatPageManager.VIEW_DRAW_PAGE, viewCheckDrawFloatPage);
                        mWindowManager.addView(viewCheckDrawFloatPage.getRootView(),
                                viewCheckDrawFloatPage.getLayoutParams());
                    }
                }


            }
        });
        findViewById(R.id.view_text2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= 23) {
                    if (!Settings.canDrawOverlays(MainActivity.this)) {
                        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                                Uri.parse("package:" + getPackageName()));
                        startActivityForResult(intent, 10);
                    } else {
                        WindowManager mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
                        LayoutBorderManager.getInstance().start(MainActivity.this);
                        LayoutLevelFloatPage layoutLevelFloatPage = new LayoutLevelFloatPage();
                        layoutLevelFloatPage.performCreate(MainActivity.this);
                        mWindowManager.addView(layoutLevelFloatPage.getRootView(),
                                layoutLevelFloatPage.getLayoutParams());
                    }
                }


            }
        });
    }
}