package com.tech9.emaoer.fruitgame;

import android.content.Context;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;

public class Spirite {
	
	private Context mContext;
	private Bitmap mBitmap;
	//Y������ٶ�
	private final static float ACCLERATIONY = 3.5F;
	//X������ٶ�
	private final static float ACCLERATIONX = 0F;
	//���������
	public PointF mCoord;
	//������ٶ�
	public PointF mV;
	//����ĳ���
	public PointF mDimention;
	//���ʣ���������Ч��
	private Paint mPaint;
	//���������
	private int mType;
	
	public int getmType() {
		return mType;
	}

	public void setmType(int mType) {
		this.mType = mType;
	}
	
	public Spirite(Context context){
		mContext = context;
		mCoord = new PointF();
		mV = new PointF();
		mDimention = new PointF();
	}
	
	//����þ����ͼƬ
	public void loadBitmap(int id){
		mBitmap = BitmapFactory.decodeResource(mContext.getResources(), id);
		mDimention.x = mBitmap.getWidth();
		mDimention.y = mBitmap.getHeight();
	}


	//�Ѿ��黭��������
	public void draw(Canvas canvas){
		canvas.drawBitmap(mBitmap, mCoord.x, mCoord.y, mPaint);
		
		move();
	}
	
	//���㾫����ƶ�
	public void move(){
		mCoord.x += mV.x;
		mCoord.y += mV.y;
		
		mV.x += ACCLERATIONX;		
		mV.y += ACCLERATIONY;		
	}
}
