package com.hencoder.a16_multi_touch.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.hencoder.a16_multi_touch.Utils;

/**
 * 手指拖动图片任意移动
 */
public class SingleTouchView extends View {
    private static final float IMGAGE_WIDTH = Utils.dpToPixel(200);

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Bitmap bitmap;

    float offsetX;
    float offsetY;

    float downX;
    float downY;

    float originalOffsetX;
    float originalOffsetY;



    public SingleTouchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        bitmap = Utils.getAvatar(getResources(),200);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(bitmap,offsetX,offsetY,paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                downY = event.getY();
                originalOffsetX = offsetX;
                originalOffsetY = offsetY;
                break;

            case MotionEvent.ACTION_MOVE:
                offsetX = originalOffsetX + event.getX()-downX;
                offsetY = originalOffsetX + event.getY()-downY;
                invalidate();
                break;
        }
        return true;
    }
}
