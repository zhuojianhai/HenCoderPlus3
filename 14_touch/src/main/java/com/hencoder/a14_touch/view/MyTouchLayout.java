package com.hencoder.a14_touch.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

/**
 * 如果自定义的viewGroup不是滑动控件，就需要重写shoulDelayChildPressedState方法，返回false
 */
public class MyTouchLayout extends ViewGroup {
    float downY;

    public MyTouchLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //是否在滑动控件中，如果不是就返回false


    @Override
    public boolean shouldDelayChildPressedState() {
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int tapTimeout = ViewConfiguration.getTapTimeout();
        float delta = ev.getY() - downY;

        if (Math.abs(delta) < 80) {
            return false;
        } else {
            return true;
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }
}
