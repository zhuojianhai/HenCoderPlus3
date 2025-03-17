package com.hencoder.a15_scalableimageview.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.OverScroller;

import com.hencoder.a15_scalableimageview.Utils;

import androidx.annotation.Nullable;
import androidx.core.view.GestureDetectorCompat;

public class ScalableImageView extends View {
    private static final float IMAGE_WIDTH = Utils.dpToPixel(300);
    private static final float OVER_SCALE_FACTOR = 1.5f;

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Bitmap bitmap;
    float originalOffsetX;//图像初始偏移X
    float originalOffsetY;//图像初始偏移Y
    float offsetX;//X轴内容偏移---手指移动距离
    float offsetY;//Y轴内容偏移---手指移动距离
    float smallScale;
    float bigScale;
    boolean big;
    float currentScale;
    ObjectAnimator scaleAnimator;
    OverScroller scroller;

    GestureDetectorCompat detector;
    HenGestureListener henGestureListener = new HenGestureListener();
    HenFlingRunner henFlingRunner = new HenFlingRunner();
    ScaleGestureDetector scaleDetector;
    HenScaleListener scaleListener = new HenScaleListener();

    public ScalableImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        bitmap = Utils.getAvatar(getResources(), (int) IMAGE_WIDTH);
        detector = new GestureDetectorCompat(context, henGestureListener);
        scroller = new OverScroller(context);
        scaleDetector = new ScaleGestureDetector(context, scaleListener);
    }

    public float getCurrentScale() {
        return currentScale;
    }

    public void setCurrentScale(float currentScale) {
        this.currentScale = currentScale;
        invalidate();
    }

    private ObjectAnimator getScaleAnimator() {
        if (scaleAnimator == null) {
            scaleAnimator = ObjectAnimator.ofFloat(this, "currentScale", 0);
        }
        scaleAnimator.setFloatValues(smallScale, bigScale);
        return scaleAnimator;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean result = scaleDetector.onTouchEvent(event);
        if (!scaleDetector.isInProgress()) {
            result = detector.onTouchEvent(event);
        }
        return result;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        originalOffsetX = (getWidth() - bitmap.getWidth()) / 2f;//默认图片居中显示 X坐标点
        originalOffsetY = (getHeight() - bitmap.getHeight()) / 2f;//默认图片居中显示 Y坐标点

        if ((float) bitmap.getWidth() / bitmap.getHeight() > (float) getWidth() / getHeight()) {
            smallScale = (float) getWidth() / bitmap.getWidth();
            bigScale = (float) getHeight() / bitmap.getHeight() * OVER_SCALE_FACTOR;
            Log.e("ScalableImageView","smallScale = true ");
            Log.e("ScalableImageView","smallScale >>>> "+smallScale);
            Log.e("ScalableImageView","bigScale >>>> "+bigScale);
            Log.e("ScalableImageView","bitmap.getWidth() >>>> "+bitmap.getWidth());
            Log.e("ScalableImageView","bitmap.getHeight() >>>> "+bitmap.getHeight());
            Log.e("ScalableImageView","View.getWidth() >>>> "+getWidth());
            Log.e("ScalableImageView","View.getHeight() >>>> "+getHeight());
        } else {
            Log.e("ScalableImageView","smallScale = false ");
            Log.e("ScalableImageView","smallScale >>>> "+smallScale);
            Log.e("ScalableImageView","bigScale >>>> "+bigScale);
            smallScale = (float) getHeight() / bitmap.getHeight();
            bigScale = (float) getWidth() / bitmap.getWidth() * OVER_SCALE_FACTOR;
        }
        currentScale = smallScale;


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e("ScalableImageView","smallScale >>>> "+smallScale);
        Log.e("ScalableImageView","bigScale >>>> "+bigScale);
        float scaleFraction = (currentScale - smallScale) / (bigScale - smallScale);
        canvas.translate(offsetX * scaleFraction, offsetY * scaleFraction);
        canvas.scale(currentScale, currentScale, getWidth() / 2f, getHeight() / 2f);
        canvas.drawBitmap(bitmap, originalOffsetX, originalOffsetY, paint);
    }

    //修正边界值，不能超过边界值
    private void fixOffsets() {
        offsetX = Math.min(offsetX, (bitmap.getWidth() * bigScale - getWidth()) / 2);//右边界，最大不能超过参数2这个值,所以使用使用min函数取最小值（真正边界值）
        offsetX = Math.max(offsetX, - (bitmap.getWidth() * bigScale - getWidth()) / 2);//左边界，最小不能超过参数2这个值,所以使用使用max函数取最大值（真正边界值）
        offsetY = Math.min(offsetY, (bitmap.getHeight() * bigScale - getHeight()) / 2);//下边界，最大值不能超过参数2的值
        offsetY = Math.max(offsetY, - (bitmap.getHeight() * bigScale - getHeight()) / 2);//上边界，最小不能超过参数2这个值
    }

    private class HenGestureListener implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {//onSingleTapUp 只会在双击的的第一次回调。
            return false;
        }

        //实际上是手指 触发 的onMove事件就会触发 onScroll 事件

        /**
         *滚动事件，当在触摸屏上迅速的移动，会产生onScroll。由ACTION_MOVE产生
         * @param downEvent    第1个ACTION_DOWN MotionEvent
         * @param currentEvent  最后一个ACTION_MOVE MotionEvent
         * @param distanceX     距离上次产生onScroll事件后，X抽移动的距离
         * @param distanceY     距离上次产生onScroll事件后，Y抽移动的距离
         * @return
         */
        @Override
        public boolean onScroll(MotionEvent downEvent, MotionEvent currentEvent, float distanceX, float distanceY) {
            if (big) {//滑动没有用到动画，所以这里需要主动invalidate刷新
                offsetX -= distanceX;//是为了让 手指滑动方向与图片偏移方向相反，符合直觉的拖动效果。
                offsetY -= distanceY;//是为了让 手指滑动方向与图片偏移方向相反，符合直觉的拖动效果。
                fixOffsets();
                invalidate();
            }
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {

        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (big) {
                //圆点定义在 整个view的中心，图片的中心就是模拟运动点中心
                scroller.fling((int) offsetX, (int) offsetY, (int) velocityX, (int) velocityY,
                        - (int) ((bitmap.getWidth() * bigScale - getWidth()) / 2), //x最左边位置   也就是边界值
                        (int) ((bitmap.getWidth() * bigScale - getWidth()) / 2),//x 最右边位置       也就是边界值
                        - (int) ((bitmap.getHeight() * bigScale - getHeight()) / 2),//y 最上边位置   也就是边界值
                        (int) ((bitmap.getHeight() * bigScale - getHeight()) / 2));//y 最下边位置     也就是边界值

                postOnAnimation(henFlingRunner);
            }
            return false;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {//onSingleTapConfirmed 在发生双击时，会回调两次
            return false;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            big = !big;
            if (big) {
                //双击放大点，不偏移   （bigScale / smallScale 是图片放大倍数）
                offsetX = (e.getX() - getWidth() / 2f) - (e.getX() - getWidth() / 2f)* bigScale / smallScale;
                offsetY = (e.getY() - getHeight() / 2f) - (e.getY() - getHeight() / 2f)* bigScale / smallScale;
//                offsetX = (e.getX() - getWidth() / 2f) * (1 - bigScale / smallScale);
//                offsetY = (e.getY() - getHeight() / 2f) * (1 - bigScale / smallScale);
                offsetX = (e.getX()-getWidth()/2f) -(e.getX()-getWidth()/2f)*bigScale/smallScale;
                //解决点击放大，原地放缩。放大的点还在原地
                offsetX = (e.getX() - getWidth() / 2f) * (1 - bigScale / smallScale);
                offsetY = (e.getY() - getHeight() / 2f) * (1 - bigScale / smallScale);
                fixOffsets();
                getScaleAnimator().start();
            } else {
                getScaleAnimator().reverse();
            }
            return false;
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            return false;
        }
    }

    private class HenFlingRunner implements Runnable {
        @Override
        public void run() {
            //scroller 只是一个计算器，当调用这个方法computeScrollOffset，就相当于掐表，
            //然后通过 scroller.getCurrX()拿到掐表后的位置，然后更新 offsetX，然后再通过invalidate更新显示view
            if (scroller.computeScrollOffset()) {
                offsetX = scroller.getCurrX();
                offsetY = scroller.getCurrY();
                invalidate();
                postOnAnimation(this);
            }
        }
    }

    private class HenScaleListener implements ScaleGestureDetector.OnScaleGestureListener {
        float initialScale;

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            currentScale = initialScale * detector.getScaleFactor();
            invalidate();
            return false;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            initialScale = currentScale;
            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {

        }
    }
}
