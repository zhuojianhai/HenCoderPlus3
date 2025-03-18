package com.hencoder.a16_multi_touch.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.hencoder.a16_multi_touch.Utils;

public class MultiTouchViewRelay extends View {
    private static final float IMGAGE_WIDTH = Utils.dpToPixel(200);

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Bitmap bitmap;

    float offsetX;
    float offsetY;

    float downX;
    float downY;

    float originalOffsetX;
    float originalOffsetY;


    int trackingPointerId;

    public MultiTouchViewRelay(Context context, AttributeSet attrs) {
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
                trackingPointerId = event.getPointerId(0);
                downX = event.getX();
                downY = event.getY();
                originalOffsetX = offsetX;
                originalOffsetY = offsetY;
                break;

            case MotionEvent.ACTION_MOVE:
                int pointerIndex = event.findPointerIndex(trackingPointerId);
                offsetX = originalOffsetX + event.getX(pointerIndex)-downX;
                offsetY = originalOffsetX + event.getY(pointerIndex)-downY;
                invalidate();
                break;

            case MotionEvent.ACTION_POINTER_DOWN:
                int actionIndex = event.getActionIndex();
                trackingPointerId = event.getPointerId(actionIndex);
                downX = event.getX(actionIndex);
                downY = event.getY(actionIndex);

                originalOffsetX = offsetX;
                originalOffsetY = offsetY;

                break;

            case MotionEvent.ACTION_POINTER_UP:
                actionIndex = event.getActionIndex();
                int pointId = event.getPointerId(actionIndex);
                if (pointId ==trackingPointerId){
                    int newIndex;
                    if (actionIndex == event.getPointerCount()-1){
                        newIndex = event.getPointerCount()-2;
                    }else{
                        newIndex = event.getPointerCount()-1;
                    }

                    trackingPointerId = event.getPointerId(newIndex);
                    downX = event.getX(newIndex);
                    downY = event.getY(newIndex);
                    originalOffsetX = offsetX;
                    originalOffsetY = offsetY;

                }
                break;
        }
        return true;
    }
}
