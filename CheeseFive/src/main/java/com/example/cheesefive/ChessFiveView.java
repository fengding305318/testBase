package com.example.cheesefive;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Picture;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class ChessFiveView extends View implements OnTouchListener{
	int maxRows=12;
	int maxColx=10;
	int x0=10;
	int y0=100;
	int D=76;
	Bitmap start,stop;

	int chessX,chessY;
	int chessType=1;           //1��ʾ����,2��ʾ����
    int chess[][]=new int[maxRows][maxColx];//0��ʾ�հ�,1��ʾ����,2��ʾ����
    int winner=0;
    List<StepInfo> lstStep=new ArrayList<StepInfo>();

	public ChessFiveView(Context context) {
		super(context);

		setOnTouchListener(this);
		start=BitmapFactory.decodeResource(getResources(),R.drawable.start1);
		stop=BitmapFactory.decodeResource(getResources(), R.drawable.stop2);




	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);

		Paint paint1=new Paint();
		paint1.setColor(Color.BLACK);


		for(int i=0;i<maxRows;i++){
			canvas.drawLine(x0, y0+i*D,x0+(maxColx-1)*D ,y0+i*D, paint1);

		}
		for(int i=0;i<maxColx;i++){
			canvas.drawLine(x0+i*D, y0, x0+i*D, y0+(maxRows-1)*D, paint1);

		}

		canvas.drawBitmap(stop, 500, 1000, paint1);
		paint1.setColor(Color.CYAN);
		paint1.setTextSize(50);
		canvas.drawText("�ҵ�������", 250, 80, paint1);


		/*if(chessType==1){
		paint1.setColor(Color.BLACK);
		}else {
			paint1.setColor(Color.WHITE);
		}
		canvas.drawCircle(x0+chessX*D, y0+chessY*D, 20,paint1);
		//canvas.drawCircle(x0+chessX*D, y0+chessY*D, 20,paint1);
		chessType=chessType==1?2:1;

		*/
		for(int i=0;i<maxRows;i++){
			for(int j=0;j<maxColx;j++){
				if(chess[i][j]>0){//�ж������Ƿ�ռ��
					if(chess[i][j]==1){
						paint1.setColor(Color.BLACK);
					}else {
						paint1.setColor(Color.WHITE);
					}
					canvas.drawCircle(x0+j*D, y0+i*D, 20,paint1);


				}
			}
		}


		if(winner>0){
			paint1.setColor(Color.RED);
			paint1.setTextSize(70);
			String s=winner==1?"����Ӯ��":"����Ӯ��";
		    canvas.drawText(s, 100, 300, paint1);
		}
		//��ʾ��Ҫ����
		paint1.setTextSize(50);
		paint1.setColor(Color.BLUE);
		canvas.drawText("����", 300, 1000, paint1);

		canvas.drawBitmap(start, 100, 1000, paint1);//��ʼ��Ϸ


	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
	    float x=(float)event.getX();
	    float y=(float)event.getY();
	    //�жϻ���
	    if(x>=300&&x<=450&&y>=1000&&y<=1100){
	    	goBack();
	    	return false;//����onTouch()������ִ��

	    }
	    //��ʼ��Ϸ
	    if(x>=100&&x<200&&y>=1000&&y<=1100){
	    	rePlay();
	    	return false;

	    }

	    System.out.println(chessX);
	    System.out.println(chessY);
	    chessX=(int)((x-x0)/D+0.5);//�������һ��,��ȡ��һ��,��ʾ��
	    chessY=(int)((y-y0)/D+0.5);//��ʾ��
	    //�ж��Ƿ�Խ��
	    if(chessX<0||chessX>=maxColx||chessY<0||chessY>=maxRows){
	          return false;//����OnTouch������ִ��

	    }

	   if(chess[chessY][chessX]>0){ //�ж������ϸ�λ���Ƿ��Ѿ���ռ��
	    	return false;//����OnTouch������ִ��
	    }
	    chess[chessY][chessX]=chessType; //�ö�Ӧ������ռ�ݸ�λ��

	    StepInfo step=new StepInfo(chessY,chessX,chessType);
	    lstStep.add(step);
	    //������������
	    chessType=chessType==1?2:1;
	    checkWinner();//����ʤ�����ж�
	    postInvalidate();  //�ڸı�������֮���������»��ƽ���

	    return false;

	}
	public void goBack(){
		//1.��lstStep������ȡ���������һ��Ԫ��
		//2.���λ���к��е���Ϣ,������Ӧλ�õ�chess[row][col]��ֵ��Ϊ0
		//3.���»�ͼ,��lstStep���������Ԫ��ȥ��

		int n=lstStep.size();
		if(n<=0){
			return ;//�˳�goBack()������ִ��

		}
		StepInfo step=lstStep.get(n-1);
		int row=step.getRow();
		int col=step.getCol();
		chessType=step.getChessType();

		chess[row][col]=0;
		postInvalidate();//���»�ͼ
		lstStep.remove(n-1);

	}
	public void rePlay(){
		for(int i=0;i<maxRows;i++){
			for(int j=0;j<maxColx;j++){
				chess[i][j]=0;
			}
		}
		lstStep.clear();
		chessType=1;
		winner=0;
		//���»�ͼ
		postInvalidate();
	}


	private void checkWinner() {
		// ���ϵ��£������� ��������
		for(int i=0;i<maxRows;i++)
			for(int j=0;j<maxColx;j++){
				if(chess[i][j] == 0)
				continue; //�������䲻��ִ�У�����ִ����һ��ѭ��

				//�Ը�λ��Ϊ��׼������4������ �� ���£����ϣ�����
				//�ж��Ƿ������ͬ���͵�����


				// ���ҷ���
				int count=1;
				for(int k=1;k<=4 && (j+k<maxColx);k++){
					if(chess[i][j+k] != chess[i][j]) break;//������������ʱ��,��������ո�Ͳ�������,������ǰ��ѭ��
					else
						count++;
				}
				if(count >= 5){
					winner = chess[i][j];
					return ;//����checkWinner()������ִ��

				}
				// ���·���
				count=1;
				for(int k=1;k<=4 && (i+k<maxRows);k++){
					if(chess[i+k][j] != chess[i][j]) break;
					else
						count++;
				}
				if(count >= 5){
					winner = chess[i][j];
					return ;//����checkWinner()������ִ��

				}

				//б��
				count=1;
				for(int k=1;k<=4 && (j+k<maxColx) && (i-k>0);k++){
					if(chess[i-k][j+k] != chess[i][j]) break;
					else
						count++;
				}
				if(count >= 5){
					winner = chess[i][j];
					return ;//����checkWinner()������ִ��

				}



				//б��
				count=1;
				for(int k=1;k<=4 && (j+k<maxColx) && (i+k<maxRows);k++){
					if(chess[i+k][j+k] != chess[i][j]) break;
					else
						count++;
				}
				if(count >= 5){
					winner = chess[i][j];
					return ;//����checkWinner()������ִ��

				}



			}
	}
}
