package com.hencoder.a09_drawing.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.hencoder.a09_drawing.R;
import com.hencoder.a09_drawing.Utils;

/**
 * author zjh
 * date 2025/3/13 10:29
 * desc    从xml中获取 默认的宽高
 */
public class MyPieChatView extends View {

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    int defaultRadius = (int) Utils.dp2px(150);
    int defaultPulledLength = (int) Utils.dp2px(20);//偏移量

    RectF bounds = new RectF();

    private static final int PULLED_INDEX = 2;

    int [] angles = {60,100,120,80};

    int[] COLORS = {
            Color.parseColor("#448AFF"),
            Color.parseColor("#D81B60"),
            Color.parseColor("#43A047"),
            Color.parseColor("#FDD835")

    };



    public MyPieChatView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MyPieChatView);
        defaultRadius = ta.getDimensionPixelSize(R.styleable.MyPieChatView_defalutRadius,defaultRadius);
        defaultPulledLength = ta.getDimensionPixelSize(R.styleable.MyPieChatView_defaultPulledLength,defaultPulledLength);
        ta.recycle();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        bounds.set(getWidth()/2-defaultRadius,getHeight()/2-defaultRadius,getWidth()/2+defaultRadius,getHeight()/2+defaultRadius);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width;
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY){
            width = widthSize;
        }else if (widthMode == MeasureSpec.AT_MOST){
            width = Math.min(defaultRadius*2,widthSize);
        }else{
            width = defaultRadius*2;
        }
        int height;
        int heightMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(widthMeasureSpec);
        if (heightMode == MeasureSpec.EXACTLY){
            height = heightSize;
        }else if (heightMode == MeasureSpec.AT_MOST){
            height = Math.min(defaultRadius*2,heightSize);
        }else{
            height = defaultRadius*2;
        }

        setMeasuredDimension(width,height);//保存测量出来的view宽高
    }

    /**
     * 偏移量，本质是三角函数的运用
     *
     * 已知斜边和角度
     *  对边 = sinX * Length
     *  邻边 =  cosX * length
     *  注意的是 这里的X 是弧度值，需要使用Math.toRadians函数将角度转弧度
     *
     * @param canvas
     */

    @Override
    protected void onDraw(Canvas canvas) {
        int currentAngle = 0;
        for (int i=0;i<COLORS.length;i++){
            paint.setColor(COLORS[i]);
            if (i==PULLED_INDEX){
                canvas.save();
                float v = (float) Math.toRadians(currentAngle + angles[2] / 2);//角度转弧度
//                float v = (float) Math.toRadians(currentAngle + angles[2]);//角度转弧度
                float cos = (float) Math.cos(v);
                float transX = (float) (cos * defaultPulledLength);

                double sin = Math.sin(v);
                float transY = (float) (sin*defaultPulledLength);

                canvas.translate(transX,transY);
                canvas.drawArc(bounds,currentAngle,angles[i],true,paint);
                canvas.restore();
            }else{
                canvas.drawArc(bounds,currentAngle,angles[i],true,paint);
            }
            currentAngle+=angles[i];
        }

    }
}
