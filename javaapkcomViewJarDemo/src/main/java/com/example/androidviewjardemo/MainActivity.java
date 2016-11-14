package com.example.androidviewjardemo;

import org.taptwo.android.widget.MainFlipActivity;

import com.example.asynctasktest.AsyncTaskActivity;
import com.example.mytextview.MyTextViewActivity;
import com.example.pulltofresh.PullFreshActivity;
import com.example.shapeanimation.AnimationActivity;
import com.example.slidingmenu.view.SlidMenuActivity;
import com.example.sortlistview.IndexableListViewActivity;
import com.sanping.centeropenact.Activity1;
import com.sanping.circlemenu.CircleMenuMainActivity;
import com.sanping.deletelistview.DeleteListViewMainActivity;
import com.sanping.dialog.DialogActivity;
import com.sanping.draglistview.DragListviewActivity;
import com.sanping.flipview.FlipViewActivity;
import com.sanping.gallery3d.Gallery3dLookActivity;
import com.sanping.jumptext.JumpTextActivity;
import com.sanping.notifyanim.NotifyAnimActivity;
import com.sanping.progreebutton.ButtonToBeProgressActivity;
import com.sanping.progresswithnum.ProgressWithNumActivity;
import com.sanping.pulldoor.PullDoorActivity;
import com.sanping.seachfly.SearchFlyActivity;
import com.sanping.shimmerjumptext.ShimmerJumpTextActivity;
import com.sanping.taobao.TaobaoMenuActivity;
import com.sanping.usestyle.UsrStyleActivity;
import com.sanping.wheel.WheelMainActivity;
import com.sanping.youku.YoukuMenuActivity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

public class MainActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}
	public void mStartActivity(Class<?> cls){
		Intent i = new Intent(MainActivity.this,cls);
		startActivity(i);
		overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	}
	public void buttonOnclick(View v){
		switch (v.getId()) {
		case R.id.shimmerBtn:mStartActivity(ShimmerActivity.class);break;
		case R.id.slidingDrawerBtn:mStartActivity(SlidingDrawerDemo.class);break;
		case R.id.listviewFreshBtn:mStartActivity(PullFreshActivity.class);break;
		case R.id.curlPageBtn:mStartActivity(CurlPageActivity.class);break;
		case R.id.qrZxingBtn:mStartActivity(SaoMiaoActivity.class);break;
		case R.id.shapeMoveAnimBtn:mStartActivity(AnimationActivity.class);break;
		case R.id.asyncTaskBtn:mStartActivity(AsyncTaskActivity.class);break;
		case R.id.asyncLoadImage:mStartActivity(ImageLoaderActivity.class);break;
		case R.id.rotate3dAnim:mStartActivity(Rotate3dAnimActivity.class);break;
		case R.id.indexListBtn:mStartActivity(IndexableListViewActivity.class);break;
		case R.id.slidMenuBtn:mStartActivity(SlidMenuActivity.class);break;
		case R.id.viewSlipBtn:mStartActivity(MainFlipActivity.class);break;
		case R.id.myViewBtn:mStartActivity(MyTextViewActivity.class);break;
		case R.id.loadHtml5Btn:mStartActivity(LoadHtmlActivity.class);break;
		case R.id.pullDoorBtn:mStartActivity(PullDoorActivity.class);break;
		case R.id.lensFocusBtn:mStartActivity(LensFocusActivity.class);break;
		case R.id.yingbiRotateBtn:mStartActivity(RotateActivity.class);break;
		case R.id.centerOpenActBtn:mStartActivity(Activity1.class);break;
		case R.id.taobaoMenuBtn:mStartActivity(TaobaoMenuActivity.class);break;
		case R.id.youkuMenuBtn:mStartActivity(YoukuMenuActivity.class);break;
		case R.id.galleryLookBtn:mStartActivity(Gallery3dLookActivity.class);break;
		case R.id.wheelBtn:mStartActivity(WheelMainActivity.class);break;
		case R.id.seachFlyBtn:mStartActivity(SearchFlyActivity.class);break;
		case R.id.circleMenuBtn:mStartActivity(CircleMenuMainActivity.class);break;
		case R.id.deleteListViewBtn:mStartActivity(DeleteListViewMainActivity.class);break;
		case R.id.dragListviewBtn:mStartActivity(DragListviewActivity.class);break;
		case R.id.buttonToProgress:mStartActivity(ButtonToBeProgressActivity.class);break;
		case R.id.dialogAnimBtn:mStartActivity(DialogActivity.class);break;
		case R.id.jumpTextBtn:mStartActivity(JumpTextActivity.class);break;
		case R.id.notifycationBtn:mStartActivity(NotifyAnimActivity.class);break;
		case R.id.expandableListviewBtn:mStartActivity(ExpandableActivity.class);break;
		case R.id.useStyleBtn:mStartActivity(UsrStyleActivity.class);break;
		case R.id.flipviewBtn:mStartActivity(FlipViewActivity.class);break;
		case R.id.shimmerJumpTextBtn:mStartActivity(ShimmerJumpTextActivity.class);break;
		case R.id.progressWithNumBtn:mStartActivity(ProgressWithNumActivity.class);break;
		default:
			break;
		}
	}
}
