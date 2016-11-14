package com.sanping.draglistview;


import java.util.ArrayList;

import com.example.androidviewjardemo.R;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class DragListviewActivity extends Activity implements OnClickListener{
	private DynamicListView mListView;
	private MyAdapter myAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_drag_listview);
		mListView = (DynamicListView)findViewById(R.id.listview);
//		mListView.setDivider(null);
		myAdapter = new MyAdapter(this,getItems());
		
		//这是一个动画的效果，
		AlphaInAnimationAdapter animAdapter = new AlphaInAnimationAdapter(myAdapter);
		animAdapter.setAbsListView(mListView);
		mListView.setAdapter(animAdapter);
		
		
		Button alphaButton = (Button)findViewById(R.id.alphaButton);
		Button leftButton = (Button)findViewById(R.id.leftButton);
		Button rightButton = (Button)findViewById(R.id.rightButton);
		Button bottomButton = (Button)findViewById(R.id.bottomButton);
		Button bottomRightButton = (Button)findViewById(R.id.bottomRightButton);
		Button scaleButton = (Button)findViewById(R.id.scaleButton);
		
		alphaButton.setOnClickListener(this);
		leftButton.setOnClickListener(this);
		rightButton.setOnClickListener(this);
		bottomButton.setOnClickListener(this);
		bottomRightButton.setOnClickListener(this);
		scaleButton.setOnClickListener(this);
	}
	
	
	private void setAlphaAdapter() {
		AnimationAdapter animAdapter = new AlphaInAnimationAdapter(myAdapter);
		animAdapter.setAbsListView(mListView);
		mListView.setAdapter(animAdapter);
	}

	private void setLeftAdapter() {
		AnimationAdapter animAdapter = new SwingLeftInAnimationAdapter(myAdapter);
		animAdapter.setAbsListView(mListView);
		mListView.setAdapter(animAdapter);
	}

	private void setRightAdapter() {
		AnimationAdapter animAdapter = new SwingRightInAnimationAdapter(myAdapter);
		animAdapter.setAbsListView(mListView);
		mListView.setAdapter(animAdapter);
	}

	private void setBottomAdapter() {
		AnimationAdapter animAdapter = new SwingBottomInAnimationAdapter(myAdapter);
		animAdapter.setAbsListView(mListView);
		mListView.setAdapter(animAdapter);
	}

	private void setBottomRightAdapter() {
		AnimationAdapter animAdapter = new SwingBottomInAnimationAdapter(new SwingRightInAnimationAdapter(myAdapter));
		animAdapter.setAbsListView(mListView);
		mListView.setAdapter(animAdapter);
	}

	private void setScaleAdapter() {
		AnimationAdapter animAdapter = new ScaleInAnimationAdapter(myAdapter);
		animAdapter.setAbsListView(mListView);
		mListView.setAdapter(animAdapter);
	}
	
	
	public ArrayList<Integer> getItems() {
		ArrayList<Integer> items = new ArrayList<Integer>();
		for (int i = 0; i < 1000; i++) {
			items.add(i);
		}
		return items;
	}
	
	public class MyAdapter extends ArrayAdapter<Integer>{
		ArrayList<Integer> items;
		public MyAdapter(Context context, ArrayList<Integer> items) {
			super(items);
			this.items = items;
		}
		@Override
		public long getItemId(int position) {
			return getItem(position).hashCode();
		}

		@Override
		public boolean hasStableIds() {
			return true;
		}
		
		//注意：这个地方使用的是getItem（）方法，不然的话，会使整个item重新排列，而得不到效果
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			TextView tv = new TextView(DragListviewActivity.this);
			tv.setText("这 是 第 "+getItem(position)+" 行");
			tv.setTextSize(30);
			return tv;
		}
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.alphaButton:
			setAlphaAdapter();
			break;
		case R.id.leftButton:
			setLeftAdapter();
			break;
		case R.id.rightButton:
			setRightAdapter();
			break;
		case R.id.bottomButton:
			setBottomAdapter();
			break;
		case R.id.bottomRightButton:
			setBottomRightAdapter();
			break;
		case R.id.scaleButton:
			setScaleAdapter();
			break;

		default:
			break;
		}
	}
}
