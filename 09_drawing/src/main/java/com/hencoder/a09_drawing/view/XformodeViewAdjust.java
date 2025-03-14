package com.hencoder.a09_drawing.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.hencoder.a09_drawing.Utils;

/**
 * author zjh
 * date 2025/3/14 11:08
 * desc Xfermode 一些列学习
 *
 *
 */
public class XformodeViewAdjust extends View {

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    Xfermode xformode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);

    RectF bounds = new RectF();


    Bitmap circleBitmap = Bitmap.createBitmap((int) Utils.dp2px(150),(int) Utils.dp2px(150), Bitmap.Config.ARGB_8888);
    Bitmap squareBitmap = Bitmap.createBitmap((int) Utils.dp2px(150),(int) Utils.dp2px(150), Bitmap.Config.ARGB_8888);


    PorterDuff.Mode[] modes = {
            PorterDuff.Mode.SRC,
            PorterDuff.Mode.SRC_IN,
            PorterDuff.Mode.SRC_ATOP,
            PorterDuff.Mode.SRC_OUT,
            PorterDuff.Mode.SRC_OVER,

            PorterDuff.Mode.DST,
            PorterDuff.Mode.DST_IN,
            PorterDuff.Mode.DST_ATOP,
            PorterDuff.Mode.DST_OUT,
            PorterDuff.Mode.DST_OVER,
            PorterDuff.Mode.CLEAR,
            PorterDuff.Mode.SCREEN,
            PorterDuff.Mode.DARKEN,
            PorterDuff.Mode.ADD,
            PorterDuff.Mode.XOR,
            PorterDuff.Mode.MULTIPLY,
            PorterDuff.Mode.OVERLAY


    };

    public XformodeViewAdjust(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        Canvas   canvas = new Canvas(circleBitmap);
        paint.setColor(Color.parseColor("#D81B60"));
        canvas.drawOval(Utils.dp2px(50),Utils.dp2px(0),Utils.dp2px(150),Utils.dp2px(100),paint);
        paint.setColor(Color.parseColor("#2197F3"));
        canvas.setBitmap(squareBitmap);
        canvas.drawRect(Utils.dp2px(0),Utils.dp2px(50),Utils.dp2px(100),Utils.dp2px(150),paint);

    }
//    {
//        Canvas   canvas = new Canvas(circleBitmap);
//        paint.setColor(Color.parseColor("#D81B60"));
//        canvas.drawOval(Utils.dp2px(50),Utils.dp2px(0),Utils.dp2px(150),Utils.dp2px(100),paint);
//        paint.setColor(Color.parseColor("#2197F3"));
//        canvas.setBitmap(squareBitmap);
//        canvas.drawRect(Utils.dp2px(0),Utils.dp2px(50),Utils.dp2px(100),Utils.dp2px(150),paint);
//    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        bounds.set(Utils.dp2px(150),Utils.dp2px(50),Utils.dp2px(300),Utils.dp2px(200));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int restoreCount = canvas.saveLayer(bounds, null);
        canvas.drawBitmap(circleBitmap,Utils.dp2px(150),Utils.dp2px(50),paint);
        paint.setXfermode(xformode);
        canvas.drawBitmap(squareBitmap,Utils.dp2px(150),Utils.dp2px(50),paint);
        paint.setXfermode(null);
        canvas.restoreToCount(restoreCount);

        paint.setTextSize(40);
        canvas.drawText("点击切换xformode",getWidth()/2,getHeight()/3,paint);
    }

    int index = 0;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_UP:
                if (index>=modes.length){
                    index=0;
                }
                xformode = new PorterDuffXfermode(modes[index++]);
                invalidate();
                break;
        }
        return true;
    }
}
