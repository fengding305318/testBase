package com.example.slidingmenu.view;

import com.example.androidviewjardemo.R;
import com.example.slidingmenu.view.SlidingMenu;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.view.Menu;

public class SlidMenuActivity extends Activity {
	SlidingMenu slidMenu;
	LeftFragment leftFragment;
	RightFragment rightFragment;
	CenterFragment viewPageFragment;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_slid_menu);
		slidMenu = (SlidingMenu)findViewById(R.id.slidMenu);
		
		slidMenu.setRightView(getLayoutInflater().inflate(
				R.layout.right_frame, null));
		slidMenu.setLeftView(getLayoutInflater().inflate(
				R.layout.left_frame, null));
		slidMenu.setCenterView(getLayoutInflater().inflate(
				R.layout.center_frame, null));
		
		FragmentTransaction t = getFragmentManager().beginTransaction();
		leftFragment = new LeftFragment();
		t.replace(R.id.left_frame, leftFragment);

		rightFragment = new RightFragment();
		t.replace(R.id.right_frame, rightFragment);

		viewPageFragment = new CenterFragment();
		t.replace(R.id.center_frame, viewPageFragment);
		t.commit();
		
	}
	public void setCanSlid(boolean left,boolean right){
		slidMenu.setCanSliding(left, right);
	}
	
	public void showLeft() {
		slidMenu.showLeftView();
	}

	public void showRight() {
		slidMenu.showRightView();
	}

}
