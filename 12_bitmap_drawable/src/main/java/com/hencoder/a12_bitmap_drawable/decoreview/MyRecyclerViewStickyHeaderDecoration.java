package com.hencoder.a12_bitmap_drawable.decoreview;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 自定义 ItemDecoration 实现索引标题（如通讯录）
 * 在 onDrawOver() 方法中，我们可以实现固定索引标题，如通讯录的A-Z 索引吸顶效果。
 */
public class MyRecyclerViewStickyHeaderDecoration extends RecyclerView.ItemDecoration {
    private Paint paint;
    private int headerHeight = 80;

    public MyRecyclerViewStickyHeaderDecoration() {
        super();
        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setTextSize(40);
    }

    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        int childCount = parent.getChildCount();
        for (int i=0;i<childCount;i++){
            View child = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(child);

            if (position % 5 == 0) { // 每 5 个 Item 显示一次标题
                int left = parent.getPaddingLeft();
                int top = child.getTop() - headerHeight;
                int right = parent.getWidth() - parent.getPaddingRight();
                int bottom = child.getTop();

                paint.setColor(Color.BLUE);
                // 画背景
                c.drawRect(left, top, right, bottom, paint);

                paint.setColor(Color.WHITE);
                // 画文字
                c.drawText("Header " + position, left + 20, bottom - 20, paint);
            }
        }
    }
}
