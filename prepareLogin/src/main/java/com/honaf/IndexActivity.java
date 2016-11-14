package com.honaf;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;

import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * 首页(底部框架可重用的跳转方式)
 * 
 * @author Administrator
 * 
 */
public class IndexActivity extends FragmentActivity{

	private LinearLayout home_img_bn_Layout, style_img_bn_layout, cam_img_bn_layout, shopping_img_bn_layout, show_img_bn_layout; // 底部选项
	public LinearLayout linear;
	public int id = 1;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// 设置无标题（尽量在前面设置）
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.applicationintroduce);
		// 初始化控件
		this.findViews();
		// 初始化监听
		this.setListener();
		home_img_bn_Layout.setSelected(true);
		style_img_bn_layout.setSelected(false);
		cam_img_bn_layout.setSelected(false);
		shopping_img_bn_layout.setSelected(false);
		show_img_bn_layout.setSelected(false);
		currentId = R.id.bottom_home_layout_ly;
		changeUI(currentId);

	}

	private void changeUI(int checkid) {

		updateContent(checkid);
	}

	

	private String currentContentFragmentTag = null;

	private int currentId;
	private MainFragment opportunityIndex;

	private LoginActivity contactsIndex;

	public void updateContent(int id) {

		try {
			Fragment fragment = null;
			String tag = null;
			final FragmentManager fm = getSupportFragmentManager();
			final FragmentTransaction tr = fm.beginTransaction();
			if (currentContentFragmentTag != null) {
				final Fragment currentFragment = fm.findFragmentByTag(currentContentFragmentTag);
				if (currentFragment != null) {
					tr.hide(currentFragment);// 将当前的Frament隐藏到后台去
				}
			}
			switch (id) {
			case R.id.bottom_home_layout_ly:
				tag = MainFragment.class.getSimpleName();
				final Fragment opprotunity = fm.findFragmentByTag(tag);
				if (opprotunity != null) {
					fragment = opprotunity;
				} else {
					fragment = new MainFragment();
				}
				opportunityIndex = (MainFragment) fragment;
				break;
			case R.id.bottom_style_layout_ly:
				tag = LoginActivity.class.getSimpleName();
				final Fragment contacts = fm.findFragmentByTag(tag);
				if (contacts != null) {
					fragment = contacts;
				} else {
					fragment = new LoginActivity();
				}

				contactsIndex = (LoginActivity) fragment;
				break;
			
//			default:
//				tag = OpportunityIndex.class.getSimpleName();
//				final Fragment opprotunity2 = fm.findFragmentByTag(tag);
//				if (opprotunity2 != null) {
//					fragment = opprotunity2;
//				} else {
//					fragment = new OpportunityIndex();
//				}
//				opportunityIndex = (OpportunityIndex) fragment;
//				break;
			}
			if (fragment != null && fragment.isAdded()) {
				tr.show(fragment);// 显示要显示的frament
			} else {
				tr.add(R.id.fragment, fragment, tag);// 如果没添加，就添加进去并且会显示出来
			}
			tr.commitAllowingStateLoss();
			currentContentFragmentTag = tag;
			currentId = id;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 初始化控件
	 */
	public void findViews() {
//		linear = (LinearLayout) findViewById(R.id.linear1);
		home_img_bn_Layout = (LinearLayout) findViewById(R.id.bottom_home_layout_ly);
		style_img_bn_layout = (LinearLayout) findViewById(R.id.bottom_style_layout_ly);
		cam_img_bn_layout = (LinearLayout) findViewById(R.id.bottom_cam_layout_ly);
		shopping_img_bn_layout = (LinearLayout) findViewById(R.id.bottom_shopping_layout_ly);
		show_img_bn_layout = (LinearLayout) findViewById(R.id.bottom_show_layout_ly);
	}

	/**
	 * 初始化监听
	 */
	public void setListener() {
		home_img_bn_Layout.setOnClickListener(clickListener_home);
		style_img_bn_layout.setOnClickListener(clickListener_style);
		cam_img_bn_layout.setOnClickListener(clickListener_cam);
		shopping_img_bn_layout.setOnClickListener(clickListener_shopping);
		show_img_bn_layout.setOnClickListener(clickListener_show);
	}

	/**
	 * Home监听
	 */
	private OnClickListener clickListener_home = new OnClickListener() {
		@Override
		public void onClick(View v) {
			id = 1;
			home_img_bn_Layout.setSelected(true);
			style_img_bn_layout.setSelected(false);
			cam_img_bn_layout.setSelected(false);
			shopping_img_bn_layout.setSelected(false);
			show_img_bn_layout.setSelected(false);
			// linear.removeAllViews();
			// Intent intent = new Intent(IndexActivity.this,
			// MainActivity.class);
			// View view = IndexActivity.this.getLocalActivityManager()
			// .startActivity("suibian", intent).getDecorView();
			// linear.addView(view);
			changeUI(v.getId());
		}
	};
	/**
	 * 登录、注册监听
	 */
	private OnClickListener clickListener_style = new OnClickListener() {
		@Override
		public void onClick(View v) {
			id = 2;
			home_img_bn_Layout.setSelected(false);
			style_img_bn_layout.setSelected(true);
			cam_img_bn_layout.setSelected(false);
			shopping_img_bn_layout.setSelected(false);
			show_img_bn_layout.setSelected(false);
			changeUI(v.getId());
			// linear.removeAllViews();
			// Intent intent = new Intent(IndexActivity.this,
			// LoginActivity.class);
			// View view = IndexActivity.this.getLocalActivityManager()
			// .startActivity("suibian", intent).getDecorView();
			// linear.addView(view);
		}
	};
	/**
	 * 首页监听
	 */
	private OnClickListener clickListener_cam = new OnClickListener() {

		@Override
		public void onClick(View v) {
			id = 3;
			home_img_bn_Layout.setSelected(false);
			style_img_bn_layout.setSelected(false);
			cam_img_bn_layout.setSelected(true);
			shopping_img_bn_layout.setSelected(false);
			show_img_bn_layout.setSelected(false);
//			changeUI(v.getId());
		}
	};
	/**
	 * 购物监听
	 */
	private OnClickListener clickListener_shopping = new OnClickListener() {

		@Override
		public void onClick(View v) {
			id = 4;
			home_img_bn_Layout.setSelected(false);
			style_img_bn_layout.setSelected(false);
			cam_img_bn_layout.setSelected(false);
			shopping_img_bn_layout.setSelected(true);
			show_img_bn_layout.setSelected(false);
			toastInfo("购物");
//			changeUI(v.getId());
		}
	};
	/**
	 * 商品展示监听
	 */
	private OnClickListener clickListener_show = new OnClickListener() {

		@Override
		public void onClick(View v) {
			id = 5;
			home_img_bn_Layout.setSelected(false);
			style_img_bn_layout.setSelected(false);
			cam_img_bn_layout.setSelected(false);
			shopping_img_bn_layout.setSelected(false);
			show_img_bn_layout.setSelected(true);
//			changeUI(v.getId());

		}
	};

	/**
	 * 自定义输出
	 * 
	 * @param string
	 */
	private void toastInfo(String string) {
		Toast.makeText(IndexActivity.this, string, Toast.LENGTH_SHORT).show();
	}

	// // 手势检测器
	// GestureDetector gestureDetector;
	// // Bitmap试图资源
	// // 记录当前的缩放比
	// float currentScale = 1;
	// // 位图的宽和高
	// int width, height;
	// // matrix控制缩放的类
	// Matrix matrix = new Matrix();
	//
	// @Override
	// public boolean onTouchEvent(MotionEvent event) {
	// // System.out.println(event);
	// // 将触屏时间交给手势检测器处理
	// return gestureDetector.onTouchEvent(event);
	// // return false;
	// }
	//
	// @Override
	// public boolean onDown(MotionEvent e) {
	// // TODO Auto-generated method stub
	// return false;
	// }
	//
	// @Override
	// public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
	// float velocityY) {
	// // 如果速度大于4000 则等于4000
	// System.out.println(velocityX);
	// // velocityX = velocityX > 4000 ? 4000 : velocityX;
	// // velocityX = velocityX < -4000 ? -4000 : velocityX;
	// // Toast.makeText(this, "honaf", 1000).show();
	// if(velocityX>2000){
	// if(id==1){
	// return true;
	// }
	// id--;
	// if(id==1){
	// home_img_bn_Layout.setSelected(true);
	// style_img_bn_layout.setSelected(false);
	// cam_img_bn_layout.setSelected(false);
	// shopping_img_bn_layout.setSelected(false);
	// show_img_bn_layout.setSelected(false);
	// linear.removeAllViews();
	// Intent intent = new Intent(IndexActivity.this, MainActivity.class);
	// View view = IndexActivity.this.getLocalActivityManager()
	// .startActivity("suibian", intent).getDecorView();
	// linear.addView(view);
	// }
	//
	//
	// }
	// if(velocityX<-2000){
	// if(id==5){
	// return false;
	// }
	// id++;
	// if(id==2){
	// home_img_bn_Layout.setSelected(false);
	// style_img_bn_layout.setSelected(true);
	// cam_img_bn_layout.setSelected(false);
	// shopping_img_bn_layout.setSelected(false);
	// show_img_bn_layout.setSelected(false);
	//
	// linear.removeAllViews();
	// Intent intent = new Intent(IndexActivity.this, LoginActivity.class);
	// View view = IndexActivity.this.getLocalActivityManager()
	// .startActivity("suibian", intent).getDecorView();
	// linear.addView(view);
	// }
	// // if(id==3){
	// // home_img_bn_Layout.setSelected(false);
	// // style_img_bn_layout.setSelected(false);
	// // cam_img_bn_layout.setSelected(true);
	// // shopping_img_bn_layout.setSelected(false);
	// // show_img_bn_layout.setSelected(false);
	// //
	// // linear.removeAllViews();
	// // Intent intent = new Intent(IndexActivity.this, LoginActivity.class);
	// // View view = IndexActivity.this.getLocalActivityManager()
	// // .startActivity("suibian", intent).getDecorView();
	// // linear.addView(view);
	// // }
	// // if(id==4){
	// // home_img_bn_Layout.setSelected(false);
	// // style_img_bn_layout.setSelected(false);
	// // cam_img_bn_layout.setSelected(false);
	// // shopping_img_bn_layout.setSelected(true);
	// // show_img_bn_layout.setSelected(false);
	// //
	// // linear.removeAllViews();
	// // Intent intent = new Intent(IndexActivity.this, LoginActivity.class);
	// // View view = IndexActivity.this.getLocalActivityManager()
	// // .startActivity("suibian", intent).getDecorView();
	// // linear.addView(view);
	// // }
	// }
	// // 根据velocity来控制缩放比例>0则放大
	// // currentScale += currentScale * velocityX / 4000.0f;
	// // // 保证currentScale不为0
	// // currentScale = currentScale > 0.01 ? currentScale : 0.01f;
	// // // 重置
	// // matrix.reset();
	// // // 设置x,y方向上面的缩放比例
	// // matrix.setScale(currentScale, currentScale, 160, 200);
	// // BitmapDrawable tmp = (BitmapDrawable) imageView.getDrawable();
	// // 判断当前的Bitmap是否回收
	// // if (!tmp.getBitmap().isRecycled()) {
	// // tmp.getBitmap().recycle();
	// // }
	// // Bitmap bitmap2 = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
	// // bitmap.getHeight(), matrix, true);
	// // imageView.setImageBitmap(bitmap2);
	// return true;
	// }
	//
	// @Override
	// public void onLongPress(MotionEvent e) {
	// // TODO Auto-generated method stub
	// }
	//
	// @Override
	// public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
	// float distanceY) {
	// // TODO Auto-generated method stub
	// return false;
	// }
	//
	// @Override
	// public void onShowPress(MotionEvent e) {
	// // TODO Auto-generated method stub
	// }
	//
	// @Override
	// public boolean onSingleTapUp(MotionEvent e) {
	// return false;
	// }
	//
	// @Override
	// public boolean dispatchTouchEvent(MotionEvent ev) {
	// if (this.gestureDetector.onTouchEvent(ev))
	// return true;
	// else
	// return super.dispatchTouchEvent(ev);
	// }

}