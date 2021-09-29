package com.yxy.viewhelp.checkview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.yxy.viewhelp.R;


/**
 * 相当于鼠标，用来挪动view
 */
public class AimCircleView extends View {
    private Paint mPaint;

    public AimCircleView(Context context) {
        super(context);
        init();
    }

    public AimCircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AimCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCircle(canvas);
    }

    private void drawCircle(Canvas canvas) {
        float cx = getWidth() / 2;
        float cy = getWidth() / 2;
        float radius = getWidth() / 2;

        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.parseColor("#FFFFFF"));
        canvas.drawCircle(cx, cy, radius, mPaint);
        radius = getResources().getDimensionPixelSize(R.dimen.dk_dp_40) / 2;
        mPaint.setColor(Color.parseColor("#30CC3A4B"));
        canvas.drawCircle(cx, cy, radius, mPaint);
        radius = getResources().getDimensionPixelSize(R.dimen.dk_dp_20) / 2;
        mPaint.setColor(Color.parseColor("#CC3A4B"));
        canvas.drawCircle(cx, cy, radius, mPaint);

        radius = getWidth() / 2;
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(4);
        mPaint.setColor(Color.parseColor("#337CC4"));
        canvas.drawCircle(cx, cy, radius - 2, mPaint);
    }
}