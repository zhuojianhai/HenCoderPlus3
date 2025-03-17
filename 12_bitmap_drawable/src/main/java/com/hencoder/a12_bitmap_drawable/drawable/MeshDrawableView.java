package com.hencoder.a12_bitmap_drawable.drawable;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import com.hencoder.a12_bitmap_drawable.Utils;

/**
 * Drawable 是一个绘制规则
 */
public class MeshDrawableView extends Drawable {

    private static final float INERVAL = Utils.dp2px(80);

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    {
        paint.setColor(Color.parseColor("#1E88E5"));
        paint.setStrokeWidth(Utils.dp2px(5));
    }

    @Override
    public void draw(Canvas canvas) {
        Rect bounds = getBounds();

        //绘制竖线
        for (int x=0;x<bounds.right;x+=INERVAL){
            canvas.drawLine(x,bounds.top,x,bounds.bottom,paint);
        }

        //绘制的横线
        for (int y=0;y<bounds.bottom;y+=INERVAL){
            canvas.drawLine(bounds.left,y,bounds.right,y,paint);
        }
    }

    @Override
    public void setAlpha(int alpha) {
        paint.setAlpha(alpha);

    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        paint.setColorFilter(colorFilter);

    }

    @Override
    public int getOpacity() {
        int alpha = paint.getAlpha();
        switch (alpha){
            case 0:
                return  PixelFormat.TRANSPARENT;//全透明
            case 255:
                return PixelFormat.OPAQUE; //不透明
            default:
                return PixelFormat.TRANSLUCENT;//半透明
        }
    }
}
