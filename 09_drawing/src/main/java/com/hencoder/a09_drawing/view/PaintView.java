package com.hencoder.a09_drawing.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

public class PaintView extends View {
	Paint mPaint;


	public PaintView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	}


	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		mPaint.reset();
		mPaint.setColor(Color.RED);
//		mPaint.setAlpha(255);
//		mPaint.setStyle(Paint.Style.FILL);//�������
//		mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
		mPaint.setStyle(Paint.Style.STROKE);//���
		mPaint.setStrokeWidth(50);
//		mPaint.setStrokeCap(Paint.Cap.BUTT);//û��
		mPaint.setStrokeCap(Paint.Cap.ROUND);//Բ��
//		mPaint.setStrokeCap(Paint.Cap.SQUARE);//����
		
//		mPaint.setStrokeJoin(Paint.Join.MITER);//���
//		mPaint.setStrokeJoin(Paint.Join.ROUND);//Բ��
//		mPaint.setStrokeJoin(Paint.Join.BEVEL);//ֱ��
		
//		canvas.drawCircle(100, 100, 100, mPaint);
		
		
		//����1
		Path path = new Path();
		path.moveTo(100, 100);
		path.lineTo(300, 100);
		path.lineTo(100, 300);
		mPaint.setStrokeJoin(Paint.Join.MITER);
		canvas.drawPath(path, mPaint);
		
		path.moveTo(100, 400);
		path.lineTo(300, 500);
		path.lineTo(100, 700);
		mPaint.setStrokeJoin(Paint.Join.ROUND);
		canvas.drawPath(path, mPaint);
//		
		path.moveTo(100, 800);
		path.lineTo(300, 800);
		path.lineTo(100, 1100);
		mPaint.setStrokeJoin(Paint.Join.BEVEL);
		canvas.drawPath(path, mPaint);
	}
	
	

}
