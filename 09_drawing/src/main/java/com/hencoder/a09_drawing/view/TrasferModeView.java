package com.hencoder.a09_drawing.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.hencoder.a09_drawing.R;
import com.hencoder.a09_drawing.Utils;

/**
 * author zjh
 * date 2025/3/14 09:58
 * desc
 */
public class TrasferModeView extends View {

    private static final int WIDTH = (int) Utils.dp2px(300);
    private static final int PADDING = (int) Utils.dp2px(40);
    private static final int BORDER_WIDTH = (int) Utils.dp2px(10);

    Bitmap bitmap;
    Bitmap bitmapSmal;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
//    Xfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC);
//    Xfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
    Xfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP);
    RectF cut = new RectF();//离屏缓冲绘制的区域
    RectF border = new RectF();

    public TrasferModeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        bitmap = getTargetBitmap(WIDTH);
        bitmapSmal = getTargetBitmap(WIDTH/4);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
      int measureWidth =   resolveSize(WIDTH,widthMeasureSpec);
      int measureHeight = resolveSize(WIDTH,heightMeasureSpec);
      setMeasuredDimension(measureWidth,measureHeight+PADDING);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        cut.set(PADDING,PADDING,PADDING+WIDTH,PADDING+WIDTH);
        border.set(PADDING-BORDER_WIDTH,PADDING-BORDER_WIDTH,PADDING + WIDTH + BORDER_WIDTH, PADDING + WIDTH + BORDER_WIDTH);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawOval(border, paint);

        int saved = canvas.saveLayer(cut,paint);
        canvas.drawOval(cut,paint);
        paint.setXfermode(xfermode);
        canvas.drawBitmap(bitmap,PADDING,PADDING,paint);
        paint.setXfermode(null);
        canvas.restoreToCount(saved);
        canvas.drawBitmap(bitmapSmal,getWidth()/2-bitmapSmal.getWidth()/2,0,paint);//图片居中绘制
    }


    private Bitmap getTargetBitmap(int targetWidht){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.drawable.cherry,options);
        options.inJustDecodeBounds = false;

        options.inDensity = options.outWidth;
        options.inTargetDensity = targetWidht;
        return BitmapFactory.decodeResource(getResources(),R.drawable.cherry,options);
    }
}
