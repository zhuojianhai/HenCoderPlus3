package com.hencoder.a13_layout.view;

import static android.view.View.MeasureSpec.AT_MOST;
import static android.view.View.MeasureSpec.UNSPECIFIED;
import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * author zjh
 * date 2025/3/8 17:04
 * desc
 */
public class MyTagLayout extends ViewGroup {
    public MyTagLayout(Context context) {
        super(context);
    }

    public MyTagLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();

        for (int i =0;i<childCount;i++){
            View child = getChildAt(i);
            LayoutParams lp = child.getLayoutParams();
            int slefWidthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
            int selfWidthSpecSize = MeasureSpec.getSize(widthMeasureSpec);

            switch (lp.width){//获取子view 的layout__xx
                case MATCH_PARENT:
                    if (slefWidthSpecMode==MeasureSpec.EXACTLY ||slefWidthSpecMode== AT_MOST){
                        childWidthSpec = MeasureSpec.makeMeasureSpec(selfWidthSpecSize-usedWidth,MeasureSpec.EXACTLY)
                    }else{
                        chidWidthSpec = MeasureSpec.makeMeasureSpec(0,MeasureSpec.UNSPECIFIED);
                    }
                    break;
                case WRAP_CONTENT:
                    if (slefWidthSpecMode==MeasureSpec.EXACTLY ||slefWidthSpecMode== AT_MOST){
                        childWidthSpec = MeasureSpec.makeMeasureSpec(selfWidthSpecSize-usedWidth, AT_MOST);
                    }else{
                        chidWidthSpec = MeasureSpec.makeMeasureSpec(0,MeasureSpec.UNSPECIFIED);
                    }
                    break;
                case UNSPECIFIED:
                    childWidthSpec = MeasureSpec.makeMeasureSpec(lp.width,MeasureSpec.EXACTLY);
                    break;

            }
            child.measure();
            setMeasuredDimension(0,0);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(),attrs);
    }
}
