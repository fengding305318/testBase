package com.sanping.pulldoor;

import com.example.androidviewjardemo.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;

public class PullDoorActivity extends Activity {
	private TextView tvHint;
	private PullDoorView pullDoorView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pull_door);
		tvHint   = (TextView)this.findViewById(R.id.tv_hint);
		pullDoorView = (PullDoorView)this.findViewById(R.id.pullDoorView);
		
		//设置textview的闪烁
		Animation ani = new AlphaAnimation(0f,1f);
		ani.setDuration(1500);
		ani.setRepeatMode(Animation.REVERSE);
		ani.setRepeatCount(Animation.INFINITE);
		tvHint.startAnimation(ani);
		
		pullDoorView.setBgImage(R.drawable.beautiful);
	}

}
