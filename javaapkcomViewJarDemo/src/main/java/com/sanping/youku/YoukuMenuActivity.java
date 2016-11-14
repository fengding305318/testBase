package com.sanping.youku;

import com.example.androidviewjardemo.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

public class YoukuMenuActivity extends Activity implements OnClickListener{
	
	private Button btn;
	private RelativeLayout r1,r2;
	private ImageButton home,menu;
	//记录两个层次是否出现
	private boolean r1Vis = false,r2Vis = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_youku_menu);
		init();
	}
	
	private void init(){
		btn = (Button)findViewById(R.id.btn);
		r1 = (RelativeLayout)findViewById(R.id.rl_level1);
		r2 = (RelativeLayout)findViewById(R.id.rl_level2);
		
		home = (ImageButton)findViewById(R.id.ib_home);
		menu = (ImageButton)findViewById(R.id.ib_menu);
		
//		r1.setVisibility(View.GONE);
//		r2.setVisibility(View.GONE);
		
		btn.setOnClickListener(this);
		home.setOnClickListener(this);
		menu.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		//判断是否正在动画过程中
		if(Utils.isRunningAnimation()) return;
		if(v == btn){
			if(r1Vis){
				long timeSet = 0;
				if(r2Vis){
					//后面的参数是延时的作用，每一个viewgroup动画时间约为200毫秒，
					//设置延时，就能使多个view分层次的显示
					Utils.startInRotateAnimation(r2, 0);
					timeSet = 200;
				}
				Utils.startInRotateAnimation(r1, timeSet);
				r1Vis = false;
				r2Vis = false;
			}else{
				Utils.startOutRotateAnimation(r1, 0);
				Utils.startOutRotateAnimation(r2, 200);
				r1Vis = true;
				r2Vis = true;
			}
		}else if(v == home){
			if(r2Vis){
				Utils.startInRotateAnimation(r2, 0);
			}else{
				Utils.startOutRotateAnimation(r2, 0);
			}
			r2Vis = !r2Vis;
		}else if(v == menu){
			Utils.startInRotateAnimation(r2, 0);
			r2Vis = false;
		}
	}
}
