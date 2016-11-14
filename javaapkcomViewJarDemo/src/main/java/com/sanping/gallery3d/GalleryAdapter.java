package com.sanping.gallery3d;

import com.example.androidviewjardemo.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery.LayoutParams;
import android.widget.ImageView;

/**
 * 高仿效果中3D图片浏览的适配器
 * @author Administrator
 *
 */
public class GalleryAdapter extends BaseAdapter {
	
	private Context context;
	
	public GalleryAdapter(Context context) {
		this.context = context;
	}

	@Override
	public int getCount() {
		return 50;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView iv = null;
		if(convertView == null) {
			iv = new ImageView(context);
		} else {
			iv = (ImageView) convertView;
		}
		if(position%3==0){
			iv.setImageDrawable(context.getResources().getDrawable(R.drawable.beautiful));
		}else if(position%3==1){
			iv.setImageDrawable(context.getResources().getDrawable(R.drawable.android));
		}else if(position%3==2){
			iv.setImageDrawable(context.getResources().getDrawable(R.drawable.froyo));
		}
		iv.setLayoutParams(new LayoutParams(160, 240));
		return iv;
	}

}
