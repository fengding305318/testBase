package com.example.lenovo.testhangwang.comparephotos;

import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.Toast;

public class MyApplication extends Application {
	public static int screenWidth;
	public static int screenHeight;
	public static Context mContext;
	@Override
	public void onCreate() {
		super.onCreate();

		mContext = getApplicationContext();

		WindowManager windowMg = (WindowManager) getSystemService(WINDOW_SERVICE);
		DisplayMetrics dm = new DisplayMetrics();
		windowMg.getDefaultDisplay().getMetrics(dm);
		screenWidth = dm.widthPixels;
		screenHeight = dm.heightPixels;

		//��ʼ������
//		int initResult = FaceCoreHelper.HwInitFaceEngine();
	}

	public static void displayStr(String str){
		Toast.makeText(mContext, str, Toast.LENGTH_SHORT).show();
	}
}
