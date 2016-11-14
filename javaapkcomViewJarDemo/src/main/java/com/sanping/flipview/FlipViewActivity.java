package com.sanping.flipview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.androidviewjardemo.MainActivity;
import com.example.androidviewjardemo.R;

public class FlipViewActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_flip_view);
	}
	public void mStartActivity(Class<?> cls){
		Intent i = new Intent(FlipViewActivity.this,cls);
		startActivity(i);
		overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	}
	public void buttonOnclick(View v){
		switch (v.getId()) {
		case R.id.txtBtn:
			mStartActivity(FlipTextViewActivity.class);
			break;
		case R.id.buttonBtn:
			mStartActivity(FlipButtonActivity.class);
			break;
		case R.id.xmlBtn:
			mStartActivity(FlipTextViewXmlActivity.class);
			break;
		default:
			break;
		}
	}
}
