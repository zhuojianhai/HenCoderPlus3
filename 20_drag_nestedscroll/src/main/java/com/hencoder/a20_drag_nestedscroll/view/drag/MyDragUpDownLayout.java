package com.hencoder.a20_drag_nestedscroll.view.drag;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.customview.widget.ViewDragHelper;

import com.hencoder.a20_drag_nestedscroll.R;

/**
 * author zjh
 * date 2025/3/12 15:43
 * desc
 * 自定义 FrameLayout，实现了上下拖拽 childView（ID 为 R.id.childv）的功能，
 * 并且在松手时会自动回弹到顶部或底部。
 * 它基于 ViewDragHelper 来实现拖拽逻辑
 */
public class MyDragUpDownLayout extends FrameLayout {
    View view;
    ViewDragHelper dragHelper;
    MyDragCallback dragCallback = new MyDragCallback();
    ViewConfiguration viewConfiguration;

    public MyDragUpDownLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        dragHelper = ViewDragHelper.create(this,dragCallback);
        viewConfiguration = ViewConfiguration.get(context);
    }


    //在布局加载完成后，找到 childView 并赋值给 view。
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        view = findViewById(R.id.childv);
    }

    //通过 dragHelper.shouldInterceptTouchEvent(ev) 让 ViewDragHelper 判断是否需要拦截事件。
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return dragHelper.shouldInterceptTouchEvent(ev);
    }

    //通过 dragHelper.processTouchEvent(event) 交给 ViewDragHelper 处理触摸事件。
    @Override
    public boolean onTouchEvent(MotionEvent event) {
         dragHelper.processTouchEvent(event);
         return true;
    }

    //continueSettling(true)：如果 ViewDragHelper 需要继续滚动，就调用 postInvalidateOnAnimation() 进行重绘。
    @Override
    public void computeScroll() {
        super.computeScroll();
        if (dragHelper.continueSettling(true)){
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    class MyDragCallback extends ViewDragHelper.Callback{


        //只允许 childView 被拖拽。
        @Override
        public boolean tryCaptureView(@NonNull View child, int pointerId) {
            return child==view;
        }

        @Override
        public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
            return top;
        }

        /**
         * 判断惯性滑动
         * Math.abs(yvel) > viewConfiguration.getScaledMinimumFlingVelocity()：判断是否是快速滑动。
         * 如果是快速滑动，yvel > 0（向下）则滑到底部，否则滑到顶部。
         * 判断当前位置
         * 如果 childView 的 top 小于 FrameLayout 一半，则滑到顶部。
         * 否则滑到底部。
         * postInvalidateOnAnimation() 触发重绘，确保动画执行。
         * @param releasedChild
         * @param xvel
         * @param yvel
         */
        @Override
        public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);

//            判断是否是快速滑动。
            if (Math.abs(yvel)>viewConfiguration.getScaledMinimumFlingVelocity()){
                // 快速滑动
                if (yvel>0){
                    dragHelper.settleCapturedViewAt(0,getHeight()-releasedChild.getHeight());
                }else{
                    dragHelper.settleCapturedViewAt(0,0);
                }
            }else{
                // 缓慢拖动，判断位置
                if (releasedChild.getTop() < getHeight() - releasedChild.getBottom()) {
                    dragHelper.settleCapturedViewAt(0, 0);
                } else {
                    dragHelper.settleCapturedViewAt(0, getHeight() - releasedChild.getHeight());
                }
            }
            postInvalidateOnAnimation();
        }
    }
}
