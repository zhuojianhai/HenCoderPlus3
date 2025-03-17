package com.hencoder.a12_bitmap_drawable.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import com.hencoder.a12_bitmap_drawable.drawable.MeshDrawableView;

/**
 * 绘制一个Drawable 首要条件是要给Drawable 设置 bounds
 *
 * drawable.setBounds()
 */
public class DrawableViewCustom extends View {

    Drawable drawable;
    MeshDrawableView meshDrawableView;
    public DrawableViewCustom(Context context, AttributeSet attrs) {
        super(context, attrs);
        drawable = new ColorDrawable(Color.parseColor("#fdeabc"));
        meshDrawableView = new MeshDrawableView();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制底色
        drawable.setBounds(0,0,getWidth(),getHeight());
        drawable.draw(canvas);

        //绘制 x-y 线组成的方格
        meshDrawableView.setBounds(0,0,getWidth(),getHeight());
        meshDrawableView.draw(canvas);
    }
}
