package com.hencoder.a10_text_and_transformation.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * author zjh
 * date 2025/3/14 17:49
 * desc
 */
public class CenterTextView extends View {
    private Paint paint;
    private String text = "Hello World";
    Rect rect = new Rect();
    public CenterTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        paint.setTextSize(50);
        paint.setTextAlign(Paint.Align.LEFT);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        rect.set(0,0,w,h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float baseline = rect.centerY() - (fontMetrics.top + fontMetrics.bottom) / 2;
        float textWidth = paint.measureText(text);
        float x = rect.centerX() - textWidth / 2;
        paint.setColor(Color.WHITE);
        canvas.drawText(text, x, baseline, paint);

    }
}
