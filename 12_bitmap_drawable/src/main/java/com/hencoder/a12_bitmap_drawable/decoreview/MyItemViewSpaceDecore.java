package com.hencoder.a12_bitmap_drawable.decoreview;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 自定义 ItemDecoration 实现间距
 * 如果我们只想给 RecyclerView 的 ItemView 之间增加间距，可以只重写 getItemOffsets() 方法。
 */
public class MyItemViewSpaceDecore extends RecyclerView.ItemDecoration {
   private  int space;

    public int getSpace() {
        return space;
    }

    public void setSpace(int space) {
        this.space = space;
    }


    public MyItemViewSpaceDecore() {
        super();
    }
    public MyItemViewSpaceDecore(int space) {
        super();
        this.space = space;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.left = space;
        outRect.right = space;
        outRect.bottom =space;
        if (parent.getChildAdapterPosition(view)==0){ // 如果是第一个 item，增加顶部间距
            outRect.top = space;
        }

    }
}
