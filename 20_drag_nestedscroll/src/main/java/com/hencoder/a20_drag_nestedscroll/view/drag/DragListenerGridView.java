package com.hencoder.a20_drag_nestedscroll.view.drag;

import android.content.Context;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 使用场景：
 * 用户的拖起-->放下操作，重在内容的移动。可以附加拖拽数据
 *
 * 1、不需要写自定义view，使用startDrag  startDragAndDrop 手动开启拖拽
 * 2、拖拽原理是创造出一个（半透明）图像在屏幕最上层，用户拖拽图像移动
 */
public class DragListenerGridView extends ViewGroup {
    private static final int COLUMNS = 2;
    private static final int ROWS = 3;

    ViewConfiguration viewConfiguration;
    OnDragListener dragListener = new HenDragListener();
    View draggedView;
    List<View> orderedChildren = new ArrayList<>();

    public DragListenerGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        viewConfiguration = ViewConfiguration.get(context);
        setChildrenDrawingOrderEnabled(true);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            orderedChildren.add(child); // 初始化位置
            child.setOnLongClickListener(new OnLongClickListener() {//view 长按开启拖拽
                @Override
                public boolean onLongClick(View v) {
                    draggedView = v;
                    //参数1、ClipData data  不是随时能获取的数据，只有在 DragEvent.ACTION_DROP才能拿的到
                    //参数3、myLocalState 本地数据，随时都能拿的到的数据
                    v.startDrag(null, new DragShadowBuilder(v), v, 0);
                    return false;
                }
            });

            //其中一个view拖拽时候，所有view都会收到拖拽事件回调
            child.setOnDragListener(dragListener);//给每个view设置拖拽事件监听
        }
    }

    @Override
    public boolean onDragEvent(DragEvent event) {//除了使用 OnDragListener，自定义view 也可以重写 onDragEvent事件，
        return super.onDragEvent(event);
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
            child.layout(0, 0, childWidth, childHeight);
            child.setTranslationX(childLeft);
            child.setTranslationY(childTop);
        }
    }

    private class HenDragListener implements OnDragListener {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED://拖拽开始
                    if (event.getLocalState() == v) {//正因为，一个view被拖拽起来后，所有view都会收到拖拽事件
                        v.setVisibility(INVISIBLE);
                    }
                    break;
                case DragEvent.ACTION_DRAG_ENTERED://拖拽进入目标范围
                    if (event.getLocalState() != v) {//进入的不是我自己view的范围时候
                        sort(v);
                    }
                    break;
                case DragEvent.ACTION_DRAG_EXITED://拖拽离开范围
                    break;
                case DragEvent.ACTION_DRAG_ENDED://拖拽结束
                    if (event.getLocalState() == v) {
                        v.setVisibility(VISIBLE);
                    }
                    break;
                case DragEvent.ACTION_DROP:// 只有在这里才能获取 ClipData 数据
                    break;
            }
            return true;
        }
    }

    /**
     *
     * @param targetView
     */
    private void sort(View targetView) {
        int draggedIndex = -1;
        int targetIndex = -1;
        for (int i = 0; i < getChildCount(); i++) {
            View child = orderedChildren.get(i);
            if (targetView == child) {
                targetIndex = i;
            } else if (draggedView == child) {
                draggedIndex = i;
            }
        }
        if (targetIndex < draggedIndex) {
            orderedChildren.remove(draggedIndex);
            orderedChildren.add(targetIndex, draggedView);
        } else if (targetIndex > draggedIndex) {
            orderedChildren.remove(draggedIndex);
            orderedChildren.add(targetIndex, draggedView);
        }
        int childLeft;
        int childTop;
        int childWidth = getWidth() / COLUMNS;
        int childHeight = getHeight() / ROWS;
        for (int index = 0; index < getChildCount(); index++) {
            View child = orderedChildren.get(index);
            childLeft = index % 2 * childWidth;
            childTop = index / 2 * childHeight;
            child.animate()
                    .translationX(childLeft)
                    .translationY(childTop)
                    .setDuration(150);
        }
    }
}
