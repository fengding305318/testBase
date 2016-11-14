package com.honaf;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.honaf.adapter.LoginImageListAdapter;
import com.honaf.entity.AdvInfo;


import android.app.ProgressDialog;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;



import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 处理登录页面
 * 
 * @author Administrator
 * 
 */
public class LoginActivity extends Fragment {
	// 声明属性
	private LayoutInflater inflater;
	private ImageView close;
	IndexActivity guide;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		this.inflater = inflater;

		View view = inflater.inflate(R.layout.login, container, false);
		initialView(view);
		initialData();
		Log.i("honaf", "list.size"+adapterImage.size());
		initListener();
		setAdapter();
		return view;
	}

	private Gallery imagList;// 登录页面可以滚动的图片
	
	private List<AdvInfo> adapterImage = new ArrayList<AdvInfo>();// 适配器交接集合
	Timer timer = new Timer();
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (guide == null) {
			guide = (IndexActivity) this.getActivity();
		}
	}

	private void initListener() {
		imagList.setOnItemClickListener(new MyOnItemClick());
		close.setOnClickListener(new MyCloseOnClick());
		
	}
	class MyCloseOnClick implements OnClickListener{

		@Override
		public void onClick(View v) {
			timer.cancel();
			imagList.setVisibility(View.INVISIBLE);
			close.setVisibility(View.INVISIBLE);
		}
		
	}
	int itemid=0;
	int num=0;
	class MyOnItemClick  implements OnItemClickListener{
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			Log.i("position", "position"+position);
			if(position>=adapterImage.size()*num&&position<adapterImage.size()*(num+1)){ 
				itemid=position-adapterImage.size()*num;
				if(position==adapterImage.size()*(num+1)-1){
					num++;
				}
				
			}
			Toast.makeText(guide,""+adapterImage.get(itemid).getAdid(), 2000).show();
		
			Intent intent=new Intent(guide,AdvActivity.class);
			startActivity(intent);
			
			
			
		}
		
	}

	/**
	 * 初始化控件
	 */
	private void initialView(View v) {
		// Gallery图片
		this.imagList = (Gallery) v.findViewById(R.id.loginImageList);
		this.close=(ImageView)v.findViewById(R.id.close);
		
		

	}

	/**
	 * 绑定Adapter
	 */
	private void setAdapter() {
		imagList.setAdapter(new LoginImageListAdapter(guide, adapterImage));
		imagList.setSelection(0);
		
	}
	
	/**
	 * 初始化数据
	 */
	private void initialData() {
		Intent intent = guide.getIntent();
		Bundle bundle = intent.getExtras();
		if (bundle != null) {
			String mes = bundle.getString("mes");
			if (mes != null) {
				Toast.makeText(guide, mes, 4000).show();
			}
		}
//		images = new int[] { R.drawable.ad1, R.drawable.ad2, R.drawable.ad3,
//				};
		AdvInfo a1=new AdvInfo("1",R.drawable.ad1,"1.1");
		AdvInfo a2=new AdvInfo("2",R.drawable.ad2,"2.1");
		AdvInfo a3=new AdvInfo("3",R.drawable.ad3,"3.1");
		adapterImage.add(a1);
		adapterImage.add(a2);
		adapterImage.add(a3);
//		for (int i = 0; i < images.length; i++) {
//			adapterImage.add(images[i]);
//		}
		timer.schedule(task, 1000, 5000);
		
	}

	/**
	 * 设置Gallery图片自动滚动 定时器
	 */
	TimerTask task = new TimerTask() {
		@Override
		public void run() {
			imagList.onFling(null, null, -900, 0);
			
		}
	};

}
