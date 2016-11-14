package com.honaf.adapter;

import java.util.List;

import com.honaf.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyGridViewAdapter extends BaseAdapter {

	public Context context;
	public List<Integer> list;

	@Override
	public int getCount() {
		return list.size();
	}

	public MyGridViewAdapter(Context context, List<Integer> list) {
		super();
		this.context = context;
		this.list = list;
	}
	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}
	@Override
	public View getView(int arg0, View view, ViewGroup arg2) {
		ImgTextWrapper wrapper;
		if(view==null){
			wrapper=new ImgTextWrapper();
			LayoutInflater  inflater=LayoutInflater.from(context);
			view=inflater.inflate(R.layout.item, null);
			view.setTag(wrapper);
			view.setPadding(1, 1, 1, 1);
		}else{
			wrapper=(ImgTextWrapper)view.getTag();
		}
		wrapper.imageView=(ImageView)view.findViewById(R.id.imageView1);
		wrapper.imageView.setImageResource(list.get(arg0));
		wrapper.textView=(TextView)view.findViewById(R.id.textView1);
		wrapper.textView.setText("hello");
		return view;
	}
	class ImgTextWrapper{
		ImageView imageView;
		TextView textView;
	}
	

}
