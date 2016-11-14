package com.honaf.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.honaf.R;
import com.honaf.adapter.MyGridViewAdapter.ImgTextWrapper;
import com.honaf.entity.AdvInfo;



public class LoginImageListAdapter extends BaseAdapter{

	private Context context;
	private List<AdvInfo> image;
	private LayoutInflater inflater;
	public LoginImageListAdapter(Context context, List<AdvInfo> image) {
		super();
		this.context = context;
		this.image = image;
		inflater=LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return Integer.MAX_VALUE;
	}

	@Override
	public Object getItem(int arg0) {
		return image.get(arg0%image.size());
		
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}
	@Override
	public View getView(int arg0, View view, ViewGroup arg2) {
		
//		ImageView vi=new ImageView(context);
//	
//		vi.setAdjustViewBounds(true);
//		vi.setMinimumWidth(320);
//		vi.setPadding(2, 0,2,0);
////		vi.setBackgroundResource(image.get(arg0%image.size()));
//		vi.setBackgroundResource(image.get(arg0%image.size()).getImagesrc());
//		return vi;
		ImgTextWrapper wrapper;
		if(view==null){
			wrapper=new ImgTextWrapper();
//			LayoutInflater  inflater=LayoutInflater.from(context);
			view=inflater.inflate(R.layout.text, null);
			view.setTag(wrapper);
			view.setPadding(5, 5, 5, 5);
		}else{
			wrapper=(ImgTextWrapper)view.getTag();
		}
		wrapper.imageView=(ImageView)view.findViewById(R.id.imageView1);
		wrapper.imageView.setBackgroundResource(image.get(arg0%image.size()).getImagesrc());
	
		return view;
	}
	class ImgTextWrapper{
		ImageView imageView;
	}

}
