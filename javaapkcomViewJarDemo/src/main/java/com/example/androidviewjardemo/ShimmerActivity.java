package com.example.androidviewjardemo;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.shanping.shimmer.Shimmer;
import com.shanping.shimmer.ShimmerButton;
import com.shanping.shimmer.ShimmerTextView;

public class ShimmerActivity extends Activity {
	ShimmerTextView stv;
	ShimmerButton btn;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shimmer);
		
		stv = (ShimmerTextView)findViewById(R.id.shimmer_tv);
		//设置闪烁颜色
		stv.setShimmerColor(Color.GREEN);
		
		Shimmer s = new Shimmer();
		//设置闪烁次数，不设置为无限循环
		s.setRepeatCount(3);
		//设置闪烁一次的时长，默认3000毫秒
		s.setDuration(6000);
		//设置每次开始闪烁时的延时，默认不延时
		s.setStartDelay(3000);
		//设置从左至右闪烁0，从右至左闪烁1
		s.setDirection(1);
		//闪烁监听
		s.setAnimatorListener(new AnimatorListener() {
			@Override
			public void onAnimationStart(Animator animation) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void onAnimationRepeat(Animator animation) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void onAnimationEnd(Animator animation) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void onAnimationCancel(Animator animation) {
				// TODO Auto-generated method stub
			}
		});
		s.start(stv);
		
		btn = (ShimmerButton)findViewById(R.id.btn);
		btn.setShimmerColor(Color.RED);
		s.start(btn);
		
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				return;
			}
		});
		
	}


}
