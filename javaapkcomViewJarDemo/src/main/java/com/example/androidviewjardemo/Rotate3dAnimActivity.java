package com.example.androidviewjardemo;

import com.example.listviewanim.Rotate3dAnimation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class Rotate3dAnimActivity extends Activity{
	ImageView iv;
	AnimatorSet set;
	Rotate3dAnimation r3d;
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_3d_rotate);
		iv = (ImageView)findViewById(R.id.iv);
		/*第一个参数：从何角度开始旋转
		 * 2：最终停留的角度
		 * 3：旋转的x位置
		 * 4：旋转的y位置
		 * 5：对于3D旋转中的深度，0就是平面旋转了
		 * 6：true从初始位置到最终位置运动，false则从设置的最终位置移动到初始位置*/
		r3d= new Rotate3dAnimation(0, 360, 300
				, 400, 1000,true);
		iv.setOnClickListener(new OnClickListener() {
			
			@TargetApi(Build.VERSION_CODES.HONEYCOMB)
			@SuppressLint("NewApi")
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//表示2种不同的旋转
				int a = 0;
				if(a==0){
					//这个方法，会使旋转到设置的深度值之后，再从那个位置旋转到初始值上
					r3d.setmDuration(3000);//这里是设置jar包中的mDuration参数
					r3d.startAnim(iv);
				}else if(a == 1){
					//这里是一般动画的启动方法，这里旋转的话，会停留在设置的深度值上
					r3d.setDuration(3000);
					iv.startAnimation(r3d);
				}
			}
		});
	}
	
}
