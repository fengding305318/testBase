package com.example.androidviewjardemo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.SlidingDrawer;
import android.widget.TextView;

@SuppressWarnings("deprecation")
public class SlidingDrawerDemo extends Activity {
	private SlidingDrawer mDrawer;
	private ImageButton imbg;
	private Boolean flag = false;
	private TextView tv;

	/*
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_slidingdrawer);

		imbg = (ImageButton) findViewById(R.id.handle);
		mDrawer = (SlidingDrawer) findViewById(R.id.slidingdrawer);
		tv = (TextView) findViewById(R.id.tv);

		mDrawer.setOnDrawerOpenListener(new SlidingDrawer.OnDrawerOpenListener() {
			@Override
			public void onDrawerOpened() {
				flag = true;
				imbg.setImageResource(R.drawable.dot_minus);
			}

		});

		mDrawer.setOnDrawerCloseListener(new SlidingDrawer.OnDrawerCloseListener() {
			@Override
			public void onDrawerClosed() {
				flag = false;
				imbg.setImageResource(R.drawable.dot_plus);
			}

		});

		mDrawer.setOnDrawerScrollListener(new SlidingDrawer.OnDrawerScrollListener() {
			@Override
			public void onScrollEnded() {
				tv.setText("结束拖动");
			}

			@Override
			public void onScrollStarted() {
				tv.setText("开始拖动");
			}

		});

	}
}
