package com.hencoder.a09_drawing.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.hencoder.a09_drawing.Utils;

import androidx.annotation.Nullable;

public class PieChart extends View {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private static final float RADIUS = Utils.dp2px(150);
    private static final float PULLED_LENGTH = Utils.dp2px(20);//偏移量
    RectF bounds = new RectF();
    int[] COLORS = {Color.parseColor("#448AFF"),
            Color.parseColor("#D81B60"),
            Color.parseColor("#43A047"),
            Color.parseColor("#FDD835")};
    int[] ANGLES = {60, 100, 120, 80};
    private static final int PULLED_INDEX = 2;

    public PieChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        bounds.set(getWidth() / 2 - RADIUS, getHeight() / 2 - RADIUS,
                getWidth() / 2 + RADIUS, getHeight() / 2 + RADIUS);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int size = (int) ((RADIUS)* 2);//给出测量出view的默认大小
//        int size = 0;//测量出view的大小

        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        int expectWidth =  layoutParams.width;
        int expectHeight = layoutParams.height;

        //结合父布局的要求，最终确定自己的大小
        int specWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        int specWidthSize = MeasureSpec.getSize(widthMeasureSpec);
        int width = expectWidth;
        if (specWidthMode == MeasureSpec.EXACTLY){
            width = specWidthSize;
        }else if (specWidthMode == MeasureSpec.AT_MOST){
            if (size>specWidthSize){
                width = specWidthSize;
            }else{
                width = size;
            }
        }else if (specWidthMode==MeasureSpec.UNSPECIFIED){
            width = size;
        }


        int specHeightMode = MeasureSpec.getMode(heightMeasureSpec);
        int specHeightSize = MeasureSpec.getSize(heightMeasureSpec);

        int height = expectHeight;
        if (specHeightMode==MeasureSpec.EXACTLY){
            height = specHeightSize;
        }else if (specHeightMode==MeasureSpec.AT_MOST){
            if (size>specHeightSize){
                height = specHeightSize;
            }else {
                height = size;
            }
        }else if (specHeightMode == MeasureSpec.UNSPECIFIED){
            height = size;
        }

        setMeasuredDimension(width, (int) (height + PULLED_LENGTH*2));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int currentAngle = 0;
        for (int i = 0; i < COLORS.length; i++) {
            paint.setColor(COLORS[i]);
            if (i == PULLED_INDEX) {
                canvas.save();
                canvas.translate((float) Math.cos(Math.toRadians(currentAngle + ANGLES[i] / 2)) * PULLED_LENGTH,
                        (float) Math.sin(Math.toRadians(currentAngle + ANGLES[i] / 2)) * PULLED_LENGTH);
                canvas.drawArc(bounds, currentAngle, ANGLES[i], true, paint);
                canvas.restore();
            }else{
                canvas.drawArc(bounds, currentAngle, ANGLES[i], true, paint);
            }
            currentAngle += ANGLES[i];
        }
    }
}
