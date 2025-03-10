package com.hencoder.a15_scalableimageview.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.OverScroller;

import androidx.annotation.Nullable;
import androidx.core.view.GestureDetectorCompat;

import com.hencoder.a15_scalableimageview.Utils;

/**
 * author zjh
 * date 2025/3/9 21:03
 * desc
 * <p>
 * <p>
 * onScroll() 是 GestureDetector.OnGestureListener 接口的方法，它在手指 拖动（scroll） 时被回调，适用于 滑动视图、拖拽 UI 元素等场景。
 * <p>
 * 1. distanceX 和 distanceY 代表的含义
 * 从官方注释来看：
 * <p>
 * distanceX 和 distanceY 并不是 e1 和 e2 之间的总位移，而是 自上次回调以来手指滑动的距离。
 * <p>
 * 假设 onScroll() 被 多次调用，则：
 * <p>
 * distanceX = 前一次回调的 e2.getX() - 当前 e2.getX()
 * distanceY = 前一次回调的 e2.getY() - 当前 e2.getY()
 * 这意味着：
 * <p>
 * 手指向右滑 (e2.getX() 增大) → distanceX 为负
 * 手指向左滑 (e2.getX() 减小) → distanceX 为正
 * 手指向下滑 (e2.getY() 增大) → distanceY 为负
 * 手指向上滑 (e2.getY() 减小) → distanceY 为正
 * 2. distanceX 和 distanceY 的计算方式
 * 假设 onScroll() 被连续调用：
 * <p>
 * 复制
 * 编辑
 * 第一帧: e1(100, 200) → e2(120, 220)  → distanceX = -20, distanceY = -20
 * 第二帧: e1(100, 200) → e2(150, 250)  → distanceX = -30, distanceY = -30
 * 所以，distanceX 表示的是手指在 X 轴上的相对移动量，而不是 e1 和 e2 之间的总距离。
 * <p>
 * 3. 为什么 distanceX 是反向的？
 * 在 onScroll() 里：
 * <p>
 * java
 * 复制
 * 编辑
 *
 * @Override public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
 * offsetX -= distanceX;
 * offsetY -= distanceY;
 * invalidate();
 * return true;
 * }
 * 由于 distanceX = 前一次 e2.getX()- 当前e2.getX()`，
 * 所以：
 * <p>
 * 手指右滑 (distanceX < 0)，offsetX -= distanceX，相当于 offsetX += |distanceX|，使图片向右移动
 * 手指左滑 (distanceX > 0)，offsetX -= distanceX，使图片向左移动
 * 手指下滑 (distanceY < 0)，offsetY -= distanceY，使图片向下移动
 * 手指上滑 (distanceY > 0)，offsetY -= distanceY，使图片向上移动
 * 4. 总结
 * distanceX 是手指的滑动方向，与实际视图的移动方向相反，所以 offsetX -= distanceX 用于修正方向。
 * onScroll() 可能被多次调用，每次提供的是相对于上次回调的滑动增量，而不是总位移。
 * 这就是为什么 拖拽图片时要 offsetX -= distanceX;，确保手指滑动的方向与图片移动的方向相反，符合直觉！ ✅
 */
public class ScableImageViewDemo extends View {
    private static final float IMAGE_WIDTH = Utils.dpToPixel(300);
    private static final float OVER_SCALE_FACTOR = 1.5f; //额外的放缩系数，这样图片放大状态才能滑动

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Bitmap bitmap;

    float originalOffsetX;
    float originalOffsetY;
    float offsetX;
    float offsetY;

    float smallScale;
    float bigScale;

//    float scaleFraction = 0f;

    float currentScale;

    GestureDetectorCompat gestureDetector;
    ObjectAnimator scaleAnimator;
    OverScroller scroller;

    HenGestureListener henGestureListener = new HenGestureListener();

    ScaleGestureDetector scaleDetector;
    HenScaleListener scaleListener = new HenScaleListener();

    boolean big = false; //是否大图

    public ScableImageViewDemo(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        bitmap = Utils.getAvatar(getResources(), (int) IMAGE_WIDTH);
        gestureDetector = new GestureDetectorCompat(context, henGestureListener);
        scroller = new OverScroller(context);
        scaleDetector = new ScaleGestureDetector(context,scaleListener);

    }


//    public float getScaleFraction() {
//        return scaleFraction;
//    }
//
//    public void setScaleFraction(float scaleFraction) {
//        this.scaleFraction = scaleFraction;
//        invalidate();
//    }


    public float getCurrentScale() {
        return currentScale;
    }

    public void setCurrentScale(float currentScale) {
        this.currentScale = currentScale;
        invalidate();
    }

    private ObjectAnimator getScaleAnimator(){
        if (scaleAnimator == null){
            scaleAnimator = ObjectAnimator.ofFloat(this,"currentScale",0f);
        }
        scaleAnimator.setFloatValues(smallScale,bigScale);
        return scaleAnimator;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        originalOffsetX = (getWidth()-bitmap.getWidth())/2f;
        originalOffsetY = (getHeight()-bitmap.getHeight())/2f;

        if ((float) bitmap.getWidth() / bitmap.getHeight() > (float) getWidth() / getHeight()) {
            smallScale = (float) getWidth() / bitmap.getWidth();
            bigScale = (float) getHeight() / bitmap.getHeight() * OVER_SCALE_FACTOR;
        } else {
            smallScale = (float) getHeight() / bitmap.getHeight();
            bigScale = (float) getWidth() / bitmap.getWidth() * OVER_SCALE_FACTOR;
        }

        currentScale = smallScale;
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//       float currentScale =  big?bigScale:smallScale;
//        canvas.scale(currentScale,currentScale,getWidth()/2f,getHeight()/2f);

        float scale = smallScale + (bigScale-smallScale)*scaleFraction;
        canvas.translate(offsetX,offsetY);
        canvas.scale(scale,scale,getWidth()/2f,getHeight()/2f);
        canvas.drawBitmap(bitmap,originalOffsetX,originalOffsetY,paint);
    }

    private class HenGestureListener implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {

            big = !big;
            if (big){
                getScaleAnimator().start();
            }else{
                getScaleAnimator().reverse();
            }
//            invalidate(); 不需要自己主动去刷新了，因为刷新通过属性动画set属性里有invalidate来处理了
            return false;
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if (big){

            }
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {

        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (big){//放大状态才能滑动

            }
            return false;
        }
    }

    private class HenScaleListener implements ScaleGestureDetector.OnScaleGestureListener {

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            invalidate();
            return false;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            return false;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {

        }
    }
}
