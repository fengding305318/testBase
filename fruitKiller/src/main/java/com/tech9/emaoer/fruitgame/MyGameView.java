package com.tech9.emaoer.fruitgame;

import java.util.ArrayList;
import java.util.Random;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

public class MyGameView extends MySurfaceView {

	private Context mContext;
	private ArrayList<PointF> mTrack;
	private final static int POINT_LIMIT = 5;
	private Paint mPaint;
	private int scroe=0;
	//此变量用于修改刀光的颜色
	private int mBladeColor = 0xFFFFFFFF;
	//用于容纳精灵
	private ArrayList<Spirite> mSpirits;
	private ArrayList<Spirite> mBooms;
	//计算下次生成精灵的时间
	private long mNextTime = 0L;
	private long mNextTimeBoom = 0L;
	//显示右上角时间
	private long mTimeCount;
	public int count = 15;
	//声明播放器
	private MediaPlayer mPlayer;
	private SoundPool mSoundPool;
	private int mExplodeSoundId;
	//背景
	private Drawable mBackground;

	public MyGameView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub

		mContext = context;
		mPaint = new Paint();
		mBackground = mContext.getResources().getDrawable(R.drawable.game_background);
		mTrack = new ArrayList<PointF>();
		//实例化容纳精灵的列表，请自行做好管理精灵的工作
		mSpirits = new ArrayList<Spirite>();
		mBooms = new ArrayList<Spirite>();
		//初始化播放器
		mPlayer=MediaPlayer.create(context, R.raw.music_menu);
		mPlayer.setLooping(true);

		mSoundPool=new SoundPool(5,AudioManager.STREAM_MUSIC,100);
		mExplodeSoundId=mSoundPool.load(context,R.raw.bomb_explode,1);
	}

	//专用于绘制屏幕
	@Override
	protected void myDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.myDraw(canvas);
		//用户画背景
		drawBackground(canvas);

		//到了计算好的时间就生成精灵
		if(mNextTime<System.currentTimeMillis()){
			generateSpirit();
			mSoundPool.play(mExplodeSoundId, 1, 1, 1, 0, 1);
			nextGenTime();
		}
		if(mNextTimeBoom<System.currentTimeMillis()){
			drawBoom();
			nextGenTimeBoom();
		}


		drawTime(canvas);
		checkSpirites();
		drawSpirites(canvas);
		//画刀光
		drawBlade(canvas);
		isHit();
		isHitBomb();
		drawScore(canvas);
	}

	//下一次生成精灵的时间
	private void nextGenTime(){
		mNextTime = System.currentTimeMillis();
		Random r = new Random();
		int interval = 1000+r.nextInt(100);
		mNextTime += interval;
	}
	private void nextGenTimeBoom(){
		mNextTimeBoom = System.currentTimeMillis()+1000;
		Random r = new Random();
		int interval = 3000+r.nextInt(2000);
		mNextTimeBoom += interval;
	}


	//生成精灵，并添加到精灵管理列表
	private void generateSpirit(){
		//请修改此方法，使精灵从更多方向抛出
		Spirite spirite = new Spirite(mContext);

		spirite.loadBitmap(R.drawable.ic_launcher);

		Random rand=new Random();
		int randNum=1+rand.nextInt(5);
		int cakeId;
		switch(randNum){
		case 1:
			cakeId=R.drawable.orange;
			spirite.loadBitmap(cakeId);
			spirite.setmType(cakeId);
			//t_num=randNum;
			break;
		case 2:
			cakeId=R.drawable.papaya;
			spirite.loadBitmap(cakeId);
			spirite.setmType(cakeId);
			//t_num=randNum;
			break;
		case 3:
			cakeId=R.drawable.peach;
			spirite.loadBitmap(cakeId);
			spirite.setmType(cakeId);
			//t_num=randNum;
			break;
		case 4:
			cakeId=R.drawable.watermellon;
			spirite.loadBitmap(cakeId);
			spirite.setmType(cakeId);
			//t_num=randNum;
			break;
		case 5:
			cakeId=R.drawable.strawberry;
			spirite.loadBitmap(cakeId);
			spirite.setmType(cakeId);
			//t_num=randNum;
			break;
		}




		Random r = new Random();

		spirite.mCoord.x = 50 + r.nextInt(300);
		spirite.mCoord.y = 480;

		spirite.mV.x = 5+r.nextInt(5);
		spirite.mV.y = -(30 + (r.nextInt(20)+10));
		/*mSpirits.get(0).setmType(mType);*/
		mSpirits.add(spirite);

	}
	//画炸弹
	private void drawBoom(){
		Spirite spirite= new Spirite(mContext);
		spirite.loadBitmap(R.drawable.boom);
		Random r = new Random();

		spirite.mCoord.x = 50 + r.nextInt(300);
		spirite.mCoord.y = 480;
		spirite.mV.x = 5+r.nextInt(5);
		spirite.mV.y = -(30 + (r.nextInt(20)+10));
		/*mSpirits.get(0).setmType(mType);*/
		mBooms.add(spirite);
	}

	//开始和停止背景音乐
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		super.surfaceCreated(holder);
		mPlayer.start();

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		super.surfaceDestroyed(holder);
		mPlayer.stop();
		mSoundPool.release();
	}



	//画精灵到画布上
	private void drawSpirites(Canvas canvas){
		for(int i=0;i<mSpirits.size();i++){
			mSpirits.get(i).draw(canvas);
		}
		for(int i=0;i<mBooms.size();i++){
			mBooms.get(i).draw(canvas);
		}
	}

	//检查精灵是否还在屏幕内，不在屏幕内则移除
	private void checkSpirites(){
		for(int i=0; i<mSpirits.size(); i++ ){
			if(isSpiriteValidate(i)){
				mSpirits.remove(i);
				i -=1;
			}
		}
	}

	//具体检查精灵是否在屏幕内的方法
	private boolean isSpiriteValidate(int i){
		PointF coord = mSpirits.get(i).mCoord;
		if(coord.x<-mSpirits.get(i).mDimention.x  || coord.x>800 || coord.y>480){
			return true;
		}

		return false;
	}

	private void drawScore(Canvas canvas){
		Paint paint=new Paint();
		paint.setColor(Color.WHITE);
		paint.setTextSize(30);
		canvas.drawText("Score:"+scroe, 50, 50, paint);
	}



	private void isHit(){
		synchronized (mTrack) {
			for(int i=0;i<mTrack.size();i++){
				for(int z=0;z<mSpirits.size();z++){
					if(mTrack.get(i).x>mSpirits.get(z).mCoord.x&&mTrack.get(i).x<mSpirits.get(z).mCoord.x+mSpirits.get(z).mDimention.x){
						if(mTrack.get(i).y>mSpirits.get(z).mCoord.y&&mTrack.get(i).y<mSpirits.get(z).mCoord.y+mSpirits.get(z).mDimention.y){


									switch(mSpirits.get(z).getmType()){
									case R.drawable.orange:
										initCutCake(R.drawable.orangep1, R.drawable.orangep2, z);
										scroe+=10;
										break;
									case R.drawable.papaya:
										initCutCake(R.drawable.papayap1, R.drawable.papayap2, z);
										scroe+=10;
										break;
									case R.drawable.peach:
										initCutCake(R.drawable.peachp1, R.drawable.peachp2, z);
										scroe+=1;
										break;
									case R.drawable.strawberry:
										initCutCake(R.drawable.strawberryp1, R.drawable.strawberryp2, z);
										scroe+=1;
										break;
									case R.drawable.watermellon:
										initCutCake(R.drawable.watermellonp1, R.drawable.watermellonp2, z);
										scroe+=1;
										break;
									}
						}
					}
				}
			}
		}
	}


	private void isHitBomb(){
		synchronized (mTrack) {
		for(int i=0;i<mTrack.size();i++){
			for(int z=0;z<mBooms.size();z++){
				if(mTrack.get(i).x>mBooms.get(z).mCoord.x && mTrack.get(i).x<mBooms.get(z).mCoord.x+mBooms.get(z).mDimention.x){
					if(mTrack.get(i).y>mBooms.get(z).mCoord.y && mTrack.get(i).y<mBooms.get(z).mCoord.y+mBooms.get(z).mDimention.y){
						if(scroe >= 10){
							scroe -= 10;
						}else{
							scroe = 0;
						}
						mBooms.remove(z);
						mSpirits.removeAll(mSpirits);
					}
					}
			}
		}
		}
		}

	private void initCutCake(int id1, int id2, int z) {
		Spirite spirite_left = new Spirite(mContext);
		Spirite spirite_right = new Spirite(mContext);
		spirite_left.loadBitmap(id1);
		spirite_left.mCoord.x=mSpirits.get(z).mCoord.x+60;
		spirite_left.mCoord.y=mSpirits.get(z).mCoord.y;
		mSpirits.add(spirite_left);

		spirite_right.loadBitmap(id2);
		spirite_right.mCoord.x=mSpirits.get(z).mCoord.x-60;
		spirite_right.mCoord.y=mSpirits.get(z).mCoord.y;
		mSpirits.add(spirite_right);
		mSpirits.remove(z);
	}


	//请记得修改此画背景的方法，使背景更漂亮
	private void drawBackground(Canvas canvas){
		canvas.drawColor(0xFF000000);
		int width = 800;
		int height = 480;
		mBackground.setBounds(0, 0, width, height);

		mBackground.draw(canvas);
	}

	//画刀光到画布上,有能力的小组可自行改进刀光
	private void drawBlade(Canvas canvas){
		mPaint.setColor(0xFFFFFFFF);
		synchronized(mTrack){
		Path path = new Path();
		Float startX, startY;
		Float controlX,controlY;
		Float endX, endY;

		int strokeWidth = 1;
		mPaint.setStyle(Style.STROKE);

			if(mTrack.size()>1){
				endX =  mTrack.get(0).x;
				endY =  mTrack.get(0).y;

				for(int i=0;i<mTrack.size()-1;i++){
					startX = endX;
					startY = endY;
					controlX = mTrack.get(i).x;
					controlY = mTrack.get(i).y;
					endX = (controlX + mTrack.get(i+1).x)/2;
					endY = (controlY + mTrack.get(i+1).y)/2;
					path.moveTo(startX, startY);
					path.quadTo(controlX, controlY, endX, endY);
					mPaint.setColor(mBladeColor);
					mPaint.setStrokeWidth(strokeWidth++);
					canvas.drawPath(path, mPaint);

					path.reset();

				}

				startX = endX;
				startY = endY;
				endX = mTrack.get(mTrack.size()-1).x;
				endY = mTrack.get(mTrack.size()-1).y;
				path.moveTo(startX, startY);
				path.lineTo(endX, endY);
				mPaint.setStrokeWidth(strokeWidth++);
				mPaint.setColor(mBladeColor);
				canvas.drawPath(path, mPaint);

				mTrack.remove(0);

			}
		}
	}

	//右上角时间和页面判断跳转
	private void drawTime(Canvas canvas){
		if(System.currentTimeMillis()-mTimeCount>1000){
			mTimeCount = System.currentTimeMillis();
			count--;
			if(count == 2){
				Intent intent = new Intent(mContext, GameStopActivity.class);
				intent.putExtra("Score", ""+ scroe);
				mContext.startActivity(intent);
			}
		}

			Paint paint = new Paint();
			paint.setColor(Color.WHITE);
			paint.setTextSize(30);

			canvas.drawText("Time:"+count, 650, 50, paint);

	}

	//屏幕点击事件的响应方法
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub

		if(event.getAction()==MotionEvent.ACTION_DOWN){
			handleActionDown(event);

		}else if(event.getAction()==MotionEvent.ACTION_MOVE){
			handleActionMove(event);
		}else if(event.getAction()==MotionEvent.ACTION_UP){
			handleActionUp();
		}

		return true;
	}

	//手指按下的响应方法
	private void handleActionDown(MotionEvent event){
		PointF point = new PointF(event.getX(),event.getY());

		synchronized(mTrack){
			mTrack.add(point);
		}
	}

	//手指拖动的响应方法
	private void handleActionMove(MotionEvent event){
		PointF point = new PointF(event.getX(),event.getY());

		synchronized(mTrack){
			if(mTrack.size()>=POINT_LIMIT){
				mTrack.remove(0);
			}
			mTrack.add(point);
		}
	}

	//手指抬起的响应方法
	private void handleActionUp(){
		synchronized(mTrack){
			mTrack.clear();
		}
	}
}
