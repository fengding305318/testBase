package com.sanping.progreebutton;

import com.example.androidviewjardemo.R;
import com.example.androidviewjardemo.R.id;
import com.example.androidviewjardemo.R.layout;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;

public class ButtonToBeProgressActivity extends Activity implements OnClickListener{
	
	CircularProgressButton circularButton1,circularButton2,
	circularButton3,circularButton4,circularButton5,circularButton6;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_button_to_be_progress);
		circularButton1 = (CircularProgressButton)findViewById(R.id.circularButton1);
		circularButton2 = (CircularProgressButton)findViewById(R.id.circularButton2);
		circularButton3 = (CircularProgressButton)findViewById(R.id.circularButton3);
		circularButton4 = (CircularProgressButton)findViewById(R.id.circularButton4);
		circularButton5 = (CircularProgressButton)findViewById(R.id.circularButton5);
		circularButton6 = (CircularProgressButton)findViewById(R.id.circularButton6);
		circularButton1.setIndeterminateProgressMode(true);
		circularButton2.setIndeterminateProgressMode(true);
		//这里设置为false是为了让他按进度显示
		circularButton3.setIndeterminateProgressMode(false);
		circularButton4.setIndeterminateProgressMode(true);
		circularButton5.setIndeterminateProgressMode(true);
		circularButton6.setIndeterminateProgressMode(true);
		circularButton1.setOnClickListener(this);
		circularButton2.setOnClickListener(this);
		circularButton3.setOnClickListener(this);
		circularButton4.setOnClickListener(this);
		circularButton5.setOnClickListener(this);
		circularButton6.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		//进度条设置为100表示成功，-1表示为失败
		if(v == circularButton1){
			if (circularButton1.getProgress() == 0) {
                circularButton1.setProgress(50);
            } else if (circularButton1.getProgress() == 100) {
                circularButton1.setProgress(0);
            } else {
                circularButton1.setProgress(100);
            }
		}else if(v == circularButton2){
			if (circularButton2.getProgress() == 0) {
                circularButton2.setProgress(50);
            } else if (circularButton2.getProgress() == -1) {
                circularButton2.setProgress(0);
            } else {
                circularButton2.setProgress(-1);
            }
		}else if(v == circularButton3){
			if (circularButton3.getProgress() == 0) {
                simulateSuccessProgress(circularButton3);
            } else {
                circularButton3.setProgress(0);
            }
		}else if(v == circularButton4){
			if (circularButton4.getProgress() == 0) {
                circularButton4.setProgress(100);
            } else {
                circularButton4.setProgress(0);
            }
		}else if(v == circularButton5){
			if (circularButton5.getProgress() == 0) {
                circularButton5.setProgress(50);
            } else if (circularButton5.getProgress() == 100) {
                circularButton5.setProgress(0);
            } else {
                circularButton5.setProgress(100);
            }
		}else if(v == circularButton6){
			if (circularButton6.getProgress() == 0) {
                circularButton6.setProgress(50);
            } else if (circularButton6.getProgress() == 100) {
                circularButton6.setProgress(0);
            } else {
                circularButton6.setProgress(100);
            }
		}
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	private void simulateSuccessProgress(final CircularProgressButton button) {
        ValueAnimator widthAnimation = ValueAnimator.ofInt(1, 100);
        widthAnimation.setDuration(1500);
        widthAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        widthAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer value = (Integer) animation.getAnimatedValue();
                button.setProgress(value);
            }
        });
        widthAnimation.start();
    }
}
