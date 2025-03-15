package com.hencoder.a09_drawing.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.hencoder.a09_drawing.R;

public class ClipView extends View {
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    Bitmap bitmap;

    public ClipView(Context context, AttributeSet attrs) {
        super(context, attrs);
        bitmap = getAvata(300);
    }

    Matrix matrix = new Matrix();
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        canvas.save();
//        canvas.translate(200,bitmap.getHeight());
//        canvas.rotate(45,200+bitmap.getWidth()/2,bitmap.getHeight()/2);
//        canvas.drawBitmap(bitmap,200,bitmap.getHeight(),paint);
//        canvas.restore();

//        canvas.save();
//        canvas.translate(200,0);
//        canvas.rotate(45,200+bitmap.getWidth()/2,bitmap.getHeight()/2);
//        canvas.drawBitmap(bitmap,200,0,paint);
//        canvas.restore();

//     下面2种代码的效果是一致的
        canvas.save();
        canvas.rotate(45,200+bitmap.getWidth()/2,bitmap.getHeight()/2);
        canvas.translate(200,0);
        canvas.drawBitmap(bitmap,0,0,paint);
        canvas.restore();


        matrix.reset();
        matrix.postTranslate(200,0);
        matrix.postRotate(45,200+bitmap.getWidth()/2,bitmap.getHeight()/2);
        canvas.drawBitmap(bitmap,matrix,paint);

    }

    private Bitmap getAvata(int targetWidth){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.drawable.avatar_rengwuxian,options);
        options.inDensity = options.outWidth;
        options.inTargetDensity = targetWidth;
        options.inJustDecodeBounds=false;

        return BitmapFactory.decodeResource(getResources(),R.drawable.avatar_rengwuxian,options);

    }
}
