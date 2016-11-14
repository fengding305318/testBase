package com.sanping.usestyle;

import com.example.androidviewjardemo.R;
import com.example.androidviewjardemo.R.id;
import com.example.androidviewjardemo.R.layout;
import com.example.androidviewjardemo.R.styleable;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


/*
 * xml 中定义了一个自定义的mybutton，有自定义的属性添加
 */
public class UsrStyleActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_usr_style);
	}

	/*
	 * 这个方法是style中定义的点击事件方法，也省去了控件去监听的繁琐
	 */
	public void dialogShow(View v){
		switch (v.getId()) {
		case R.id.btn1:
			Toast.makeText(this, "this1", 200).show();
			break;
			
		case R.id.btn2:
			Toast.makeText(this, "this2", 200).show();
			break;
		default:
			break;
		}
	}
}
