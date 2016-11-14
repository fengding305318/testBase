package com.sanping.circlemenu;

/*
 * Copyright 2013 Csaba Szugyiczki
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.example.androidviewjardemo.R;
import com.sanping.circlemenu.CircleLayout.OnItemClickListener;
import com.sanping.circlemenu.CircleLayout.OnItemSelectedListener;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 圆形旋转菜单(自定义Layout+ImageView)
 * @author Administrator
 *
 */
public class CircleMenuMainActivity extends Activity implements OnItemSelectedListener, OnItemClickListener{
	private TextView selectedTextView;
	private CircleLayout circleMenu;
	private CircleImageView circleImageview1;
	private CircleImageView circleImageview2;
	private CircleImageView circleImageview3;
	private CircleImageView circleImageview4;
	private CircleImageView circleImageview5;
	private CircleImageView circleImageview6;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_custom_circleimage_main);
		initView();
	}
	
	public void initView() {
		circleMenu = (CircleLayout)findViewById(R.id.main_circle_layout);
		circleImageview1 = (CircleImageView)findViewById(R.id.main_facebook_image);
		circleImageview1.setName("FaceBook");
		circleImageview2 = (CircleImageView)findViewById(R.id.main_myspace_image);
		circleImageview2.setName("myspace");
		circleImageview3 = (CircleImageView)findViewById(R.id.main_google_image);
		circleImageview3.setName("google");
		circleImageview4 = (CircleImageView)findViewById(R.id.main_linkedin_image);
		circleImageview4.setName("linkedin");
		circleImageview5 = (CircleImageView)findViewById(R.id.main_twitter_image);
		circleImageview5.setName("twitter");
		circleImageview6 = (CircleImageView)findViewById(R.id.main_wordpress_image);
		circleImageview6.setName("wordpress");
		
		selectedTextView = (TextView)findViewById(R.id.main_selected_textView);
		selectedTextView.setText(((CircleImageView)circleMenu.getSelectedItem()).getName());
		
		circleMenu.setOnItemSelectedListener(this);
		circleMenu.setOnItemClickListener(this);
	}

	
	@Override
	public void onItemSelected(View view, int position, long id, String name) {		
		selectedTextView.setText(name+position);
	}

	@Override
	public void onItemClick(View view, int position, long id, String name) {
		Toast.makeText(getApplicationContext(),name+position, Toast.LENGTH_SHORT).show();
	}

}
