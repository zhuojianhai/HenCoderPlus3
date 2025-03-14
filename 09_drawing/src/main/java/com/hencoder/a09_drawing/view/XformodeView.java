package com.hencoder.a09_drawing.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.hencoder.a09_drawing.R;
import com.hencoder.a09_drawing.Utils;

/**
 * author zjh
 * date 2025/3/14 11:08
 * desc Xfermode 一些列学习
 *
 *
 */
public class XformodeView extends View {

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    Xfermode xformode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);

    RectF bounds = new RectF();

    public XformodeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        bounds.set(Utils.dp2px(150),Utils.dp2px(50),Utils.dp2px(300),Utils.dp2px(200));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int restoreCount = canvas.saveLayer(bounds, paint);
        //画圆
        paint.setColor(Color.parseColor("#D81B60"));
        canvas.drawOval(Utils.dp2px(200),Utils.dp2px(50),Utils.dp2px(300),Utils.dp2px(150),paint);
        //画矩形
        paint.setXfermode(xformode);
        paint.setColor(Color.parseColor("#2197F3"));
        canvas.drawRect(Utils.dp2px(150),Utils.dp2px(100),Utils.dp2px(250),Utils.dp2px(200),paint);
        paint.setXfermode(null);
        canvas.restoreToCount(restoreCount);
    }
}
