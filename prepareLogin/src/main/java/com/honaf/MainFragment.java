package com.honaf;

import java.util.ArrayList;
import java.util.List;


import com.honaf.adapter.MyGridViewAdapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;

public class MainFragment extends Fragment {
	//声明属性
	public GridView gridView;  //用来放图片
	private LayoutInflater inflater;
	IndexActivity guide;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		this.inflater = inflater;
		View view = inflater.inflate(R.layout.main, container, false);
		gridView = (GridView)view.findViewById(R.id.gv);
		//设置gridView的adapter
		gridView.setAdapter(new MyAutoAdapter(guide, getData()));
		return view;
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (guide == null) {
			guide = (IndexActivity) this.getActivity();
		}
	
	}
	public MainFragment(){
		super();
	}
	/**
	 * 拿到数据（从web端 读取）
	 * 
	 * @return
	 */
	private List<Integer> getData() {
		List<Integer> list = new ArrayList<Integer>();
		
		list.add(R.drawable.ad1);
		list.add(R.drawable.ad2);
		list.add(R.drawable.ad3);
		
		list.add(R.drawable.ad1);
		list.add(R.drawable.ad2);
		list.add(R.drawable.ad3);
		
		list.add(R.drawable.ad1);
		list.add(R.drawable.ad2);
		list.add(R.drawable.ad3);
		
		list.add(R.drawable.ad1);
		list.add(R.drawable.ad2);
		list.add(R.drawable.ad3);
		
		list.add(R.drawable.ad1);
		list.add(R.drawable.ad2);
		list.add(R.drawable.ad3);
		
		return list;
	}
	/**
	 * 继承自定义gridView的adapter
	 * 
	 * @author Administrator
	 * 
	 */
	class MyAutoAdapter extends MyGridViewAdapter {

		public MyAutoAdapter(Context context, List<Integer> list) {
			super(context, list);
		}
	}

}
