package com.example.androidviewjardemo;



import com.sunlighttech.ihotel.qr.InitElement;
import com.sunlighttech.ihotel.qr.util.OkCallbacks;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

/*二维码扫描
 * 添加包qrZxing
 * 添加文件arm64-v8a,armeabi,armeabi-v7a,mips,mips64,x86,x86_64
 * 添加声音文件bee.ogg,以及图片
 * 添加各种权限
 * */
public class SaoMiaoActivity extends Activity implements OkCallbacks{
	//capture_containter显示整个画面的Layout
	private RelativeLayout mContainer = null;
	//二维码区域
	private RelativeLayout mCropLayout = null;
	private Button quxiaoButton;
	private InitElement initE;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_saomiao);
		initView();
	}

	private void initView() {
		mContainer = (RelativeLayout) findViewById(R.id.capture_containter);
		mCropLayout = (RelativeLayout) findViewById(R.id.capture_crop_layout);
		quxiaoButton = (Button) findViewById(R.id.quxiao);
		ImageView mQrLineView = (ImageView) findViewById(R.id.capture_scan_line);
		TranslateAnimation mAnimation = new TranslateAnimation(
				TranslateAnimation.ABSOLUTE, 0f, TranslateAnimation.ABSOLUTE,
				0f, TranslateAnimation.RELATIVE_TO_PARENT, 0f,
				TranslateAnimation.RELATIVE_TO_PARENT, 0.9f);
		mAnimation.setDuration(1500);
		mAnimation.setRepeatCount(-1);
		mAnimation.setRepeatMode(Animation.REVERSE);
		mAnimation.setInterpolator(new LinearInterpolator());
		mQrLineView.setAnimation(mAnimation);
		quxiaoButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
	}
	private void initAction(){
		SurfaceView surfaceView = (SurfaceView) findViewById(R.id.capture_preview);
		AssetFileDescriptor file = getResources().openRawResourceFd(R.raw.beep);
		initE = new InitElement(this, mContainer, mCropLayout,file);
		initE.initSurfaceHolder(surfaceView);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		initAction();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		initE.actPause();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		initE.actDestory();
	}

	@Override
	public void isOk(String data) {
		// TODO Auto-generated method stub
        Toast.makeText(this, "扫描结果："+data, 500).show();
	}
}
