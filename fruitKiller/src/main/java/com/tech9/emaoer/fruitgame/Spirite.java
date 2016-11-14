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
	//Y方向加速度
	private final static float ACCLERATIONY = 3.5F;
	//X方向加速度
	private final static float ACCLERATIONX = 0F;
	//精灵的坐标
	public PointF mCoord;
	//精灵的速度
	public PointF mV;
	//精灵的长宽
	public PointF mDimention;
	//画笔，可做特殊效果
	private Paint mPaint;
	//精灵的类型
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
	
	//导入该精灵的图片
	public void loadBitmap(int id){
		mBitmap = BitmapFactory.decodeResource(mContext.getResources(), id);
		mDimention.x = mBitmap.getWidth();
		mDimention.y = mBitmap.getHeight();
	}


	//把精灵画到画布上
	public void draw(Canvas canvas){
		canvas.drawBitmap(mBitmap, mCoord.x, mCoord.y, mPaint);
		
		move();
	}
	
	//计算精灵的移动
	public void move(){
		mCoord.x += mV.x;
		mCoord.y += mV.y;
		
		mV.x += ACCLERATIONX;		
		mV.y += ACCLERATIONY;		
	}
}
