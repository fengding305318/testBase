package com.hanvon.choosephotos;

import java.io.Serializable;
import java.util.List;

import com.hanvon.comparephotos.MainActivity;
import com.hanvon.comparephotos.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class ChoosePhotosActivity extends Activity {
	List<ImageBucket> dataList;
	List<ImageItem> dataSortList;
	GridView gridView;
	ImageBucketAdapter adapter;// 鑷畾涔夌殑閫傞厤鍣?
	ImageItemAdapter sortAdapter;
	
	AlbumHelper helper;
	public static final String EXTRA_IMAGE_LIST = "imagelist";
	public static final String EXTRA_IMAGE_Sort_LIST = "imageSortlist";
	private int REQUSETCODE_SELECTPHOTO_2 = 2;
	public static Bitmap bimap;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_image_bucket);
		helper = AlbumHelper.getHelper();
		helper.init(getApplicationContext());
		
		dataSortList = (List<ImageItem>) getIntent().getSerializableExtra(
				EXTRA_IMAGE_Sort_LIST);
		
		if(dataSortList != null)
		{
			initSortListView();
		}
		else
		{
			initData();
		    initView();
		}
	}

	/**
	 * 鍒濆鍖栨暟鎹?
	 */
	private void initData() {
		dataList = helper.getImagesBucketList(false);	
		bimap=BitmapFactory.decodeResource(
				getResources(),
				R.drawable.icon_addpic_unfocused);
	}
	
	private void initSortListView()
	{
		gridView = (GridView) findViewById(R.id.gridview);
		sortAdapter = new ImageItemAdapter(ChoosePhotosActivity.this, dataSortList);
		gridView.setAdapter(sortAdapter);
	}

	/**
	 * 鍒濆鍖杤iew瑙嗗浘
	 */
	private void initView() {
		gridView = (GridView) findViewById(R.id.gridview);
		adapter = new ImageBucketAdapter(ChoosePhotosActivity.this, dataList);
		gridView.setAdapter(adapter);

		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				/*
				Intent intent = new Intent(ChoosePhotosActivity.this,
						ImageGridActivity.class);
				intent.putExtra(ChoosePhotosActivity.EXTRA_IMAGE_LIST,
						(Serializable) dataList.get(position).imageList);
				startActivityForResult(intent, 100);
				finish();
				*/
				/*
				Intent intent=new Intent();   
	            intent.setClass(ChoosePhotosActivity.this, MainActivity.class);   
	            intent.putExtra(ChoosePhotosActivity.EXTRA_IMAGE_LIST,
						(Serializable) dataList.get(position).imageList);
	            startActivityForResult(intent, REQUSETCODE_SELECTPHOTO_2);
	            ChoosePhotosActivity.this.finish(); 
	            */
				
				Intent intent = new Intent(); 
				intent.putExtra(ChoosePhotosActivity.EXTRA_IMAGE_LIST,
						(Serializable) dataList.get(position).imageList);
                setResult(10, intent);// 放入回传的值,并添加一个Code,方便区分返回的数据 
                ChoosePhotosActivity.this.finish(); 

			}
			
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode!=Activity.RESULT_OK){
			return;
		}
		
		switch (requestCode) {
		
		case 100:
			setResult(Activity.RESULT_OK);
			finish();
			break;

		default:
			break;
		}
	}
}
