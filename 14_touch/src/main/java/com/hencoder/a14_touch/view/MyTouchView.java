package com.hencoder.a14_touch.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * author zjh
 * date 2025/3/9 11:26
 * desc
 */
public class MyTouchView extends View {
    public MyTouchView(Context context) {
        super(context);
    }

    public MyTouchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int eventId = event.getActionMasked();//支持多点触碰
        switch (event.getAction()){
           case  MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_DOWN:
            case MotionEvent.ACTION_POINTER_UP:

            break;
        }

        return super.onTouchEvent(event);
    }
}
