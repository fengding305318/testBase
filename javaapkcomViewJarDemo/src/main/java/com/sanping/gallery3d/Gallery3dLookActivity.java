package com.sanping.gallery3d;

import com.example.androidviewjardemo.R;

import android.app.Activity;
import android.os.Bundle;

/**
 * 3D图片浏览效果的主页面
 * @author Administrator
 *
 */
public class Gallery3dLookActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_3dlook_gallery);
		initView();
	}
	
	public void initView() {
	CustomGallery mCustomGallery = (CustomGallery) findViewById(R.id.custom_gallery);
		
		GalleryAdapter mAdapter = new GalleryAdapter(this);
		mCustomGallery.setAdapter(mAdapter);
		
	}
}
