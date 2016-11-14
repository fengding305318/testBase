package org.taptwo.android.widget;

import org.taptwo.android.widget.CircleFlowIndicator;
import org.taptwo.android.widget.ViewFlow;
import org.taptwo.android.widget.ViewFlow.ViewClickListener;
import org.taptwo.android.widget.ViewFlow.ViewLazyInitializeListener;
import org.taptwo.android.widget.ViewFlow.ViewSwitchListener;

import com.example.androidviewjardemo.R;

import android.os.Bundle;
import android.os.SystemClock;
import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

public class MainFlipActivity extends Activity {
	private ViewFlow viewFlow;

	/**
	 * Called when the activity is first created. 监听和其他的监听一样
	 * */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_flip);

		viewFlow = (ViewFlow) findViewById(R.id.viewflow);

		// 后面的int参数表示焦点停留在哪个位置，1表示第2个位置
		viewFlow.setAdapter(new ImageAdapter(this), 1);
		
		// 设置那种圆点的选择效果
		CircleFlowIndicator indic = (CircleFlowIndicator) findViewById(R.id.viewflowindic);
		viewFlow.setFlowIndicator(indic);

		// 得到当前的位置
		viewFlow.getSelectedItemPosition();

		// 得到当前选择的view
		viewFlow.getSelectedView();

		// 监听item选择
		viewFlow.setOnViewSwitchListener(new ViewSwitchListener() {

			@Override
			public void onSwitched(View view, final int position) {
				// TODO Auto-generated method stub
				runOnUiThread(new Runnable() {
					public void run() {
						Toast.makeText(MainFlipActivity.this, position + "SELECTION",
								200).show();
					}
				});
			}
		});

		// 自定义监听点击的接口
		viewFlow.setOnViewClickListener(new ViewClickListener() {

			@Override
			public void onClicked(final int position) {
				// TODO Auto-generated method stub
				runOnUiThread(new Runnable() {
					public void run() {
						Toast.makeText(MainFlipActivity.this, position + "CLICK",
								200).show();
					}
				});
			}
		});
	}

	/*
	 * If your min SDK version is < 8 you need to trigger the
	 * onConfigurationChanged in ViewFlow manually, like this
	 */
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		viewFlow.onConfigurationChanged(newConfig);
	}
}
