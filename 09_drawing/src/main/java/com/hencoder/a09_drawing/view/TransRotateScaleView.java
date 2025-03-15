package com.hencoder.a09_drawing.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.hencoder.a09_drawing.R;

public class TransRotateScaleView extends View {

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    Bitmap myBitamp;

    public TransRotateScaleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        myBitamp = getAvataBitmap(300);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();
        canvas.translate(100+myBitamp.getWidth()/2,100);
        canvas.scale(2,2,myBitamp.getWidth()/2,myBitamp.getHeight()/2);
        canvas.rotate(45,myBitamp.getWidth()/2,myBitamp.getHeight()/2);
        canvas.clipRect(0,0,myBitamp.getWidth()+100 ,myBitamp.getHeight()/2);
        canvas.drawBitmap(myBitamp,0,0,paint);
        canvas.restore();

        canvas.save();
        canvas.translate(100+myBitamp.getWidth()/2,100+myBitamp.getHeight());
        canvas.rotate(30,myBitamp.getWidth()/2,myBitamp.getHeight()/2);
        canvas.clipRect(0,0,myBitamp.getWidth()+100,myBitamp.getHeight()/2*3);
        canvas.drawBitmap(myBitamp,0,myBitamp.getHeight(),paint);
        canvas.restore();


    }

    private Bitmap getAvataBitmap(int targetWitdh){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.drawable.avatar_rengwuxian,options);
        options.inJustDecodeBounds = false;
        options.inDensity = options.outWidth;
        options.inTargetDensity = targetWitdh;
       return  BitmapFactory.decodeResource(getResources(),R.drawable.avatar_rengwuxian,options);


    }
}
