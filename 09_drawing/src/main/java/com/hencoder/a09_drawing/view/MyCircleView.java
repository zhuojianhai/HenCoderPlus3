package com.hencoder.a09_drawing.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.hencoder.a09_drawing.R;
import com.hencoder.a09_drawing.Utils;

/**
 * TODO: document your custom view class.
 */
public class MyCircleView extends View {
    private int RADIUS = (int) Utils.dp2px(100f);
    private int PADDING = (int) Utils.dp2px(100f);

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private String TAG = "MyCircleView";

    Bitmap bitmap;
    public MyCircleView(Context context) {
        super(context);
        init(null, 0);
    }

    public MyCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public MyCircleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        paint.setStrokeWidth(10);
        bitmap = getAvatar(200);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int size = (PADDING+RADIUS)* 2;//测量出view的大小
//        int size = 0;//测量出view的大小

        ViewGroup.LayoutParams layoutParams = getLayoutParams();
       int expectWidth =  layoutParams.width;
        int expectHeight = layoutParams.height;
        Log.e(TAG, "onMeasure: expectWidth"+expectWidth );
        Log.e(TAG, "onMeasure: expectHeight "+expectHeight );

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

        setMeasuredDimension(width,height);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        canvas.save();
        canvas.translate(100,100);
        canvas.rotate(45);
        canvas.drawRect(0,0,200,300,paint);
        canvas.restore();


        canvas.save();
        canvas.rotate(45);
        canvas.translate(400,300);
        canvas.drawRect(0,0,100,100,paint);
        canvas.restore();


        canvas.save();
        canvas.translate(400,800);
        canvas.rotate(45);
        canvas.scale(2,2);
        canvas.drawRect(0,0,100,100,paint);
        canvas.restore();

        canvas.save();
        canvas.rotate(45,200+bitmap.getWidth()/2,bitmap.getHeight()/2);
        canvas.translate(200,0);
        canvas.drawBitmap(bitmap,0,0,paint);
        canvas.restore();

    }
    private Bitmap getAvatar(int targetWidth){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(),R.drawable.avatar_rengwuxian,options);
        options.inJustDecodeBounds = false;
        options.inDensity = options.outWidth;
        options.inTargetDensity = targetWidth;
        return  BitmapFactory.decodeResource(getResources(),R.drawable.avatar_rengwuxian,options);
    }

}