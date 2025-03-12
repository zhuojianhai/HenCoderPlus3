package com.hencoder.a20_drag_nestedscroll.view.drag;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.customview.widget.ViewDragHelper;

/**
 * 使用场景：用户拖拽viewgroup当中的某个View
 *
 * 需要应用在自定义 ViewGroup 中调用 ViewDragHelper.shouldInterceptTouchEvent() 和 processTouchEvent()，
 * 程序会自动开启拖拽 拖拽的原理是实时修改被拖拽的子 View 的 mLeft, mTop, mRight,mBottom 值
 */
public class DragHelperGridView extends ViewGroup {
    private static final int COLUMNS = 2;
    private static final int ROWS = 3;

    ViewDragHelper dragHelper;

    public DragHelperGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        dragHelper = ViewDragHelper.create(this, new DragCallback());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int specWidth = MeasureSpec.getSize(widthMeasureSpec);
        int specHeight = MeasureSpec.getSize(heightMeasureSpec);
        int childWidth = specWidth / COLUMNS;
        int childHeight = specHeight / ROWS;

        measureChildren(MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY));

        setMeasuredDimension(specWidth, specHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        int childLeft;
        int childTop;
        int childWidth = getWidth() / COLUMNS;
        int childHeight = getHeight() / ROWS;
        for (int index = 0; index < count; index++) {
            View child = getChildAt(index);
            childLeft = index % 2 * childWidth;
            childTop = index / 2 * childHeight;
            child.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return dragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        dragHelper.processTouchEvent(event);
        return true;
    }

    // 调用这个方法postInvalidateOnAnimation()，然后重新computeScroll，让其处理view的滑动，将view重置原先位置
    @Override
    public void computeScroll() {
        if (dragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    private class DragCallback extends ViewDragHelper.Callback {
        float capturedLeft;
        float capturedTop;

        @Override
        public boolean tryCaptureView(@NonNull View child, int pointerId) {
            return true;
        }

        @Override
        public void onViewDragStateChanged(int state) {
            if (state == ViewDragHelper.STATE_IDLE) {
                View capturedView = dragHelper.getCapturedView();
                if (capturedView != null) {
                    capturedView.setElevation(capturedView.getElevation() - 1);
                }
            }
        }

        //横向轨道
        @Override
        public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
            return left;
        }

        //纵向轨道
        @Override
        public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
            return top;
        }

        @Override
        public void onViewCaptured(@NonNull View capturedChild, int activePointerId) {//当view被抓住的时候
            capturedChild.setElevation(getElevation() + 1);//view的z轴的高度+1
            capturedLeft = capturedChild.getLeft();//记录 被拖起view的 坐标值 方便松手时候，重置view位置
            capturedTop = capturedChild.getTop();//记录 被拖起view的 坐标值  方便松手时候，重置view位置
        }

        @Override
        public void onViewPositionChanged(@NonNull View changedView, int left, int top, int dx, int dy) {//当view位置发生改变时候回调
        }

        @Override
        public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {//当view被放下时候，事件被回调
            dragHelper.settleCapturedViewAt((int) capturedLeft, (int) capturedTop);//将拖起的view 放回原先的位置
            postInvalidateOnAnimation();
        }
    }
}
