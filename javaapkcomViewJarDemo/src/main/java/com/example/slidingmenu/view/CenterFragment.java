package com.example.slidingmenu.view;



import com.example.androidviewjardemo.R;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

@SuppressLint("NewApi")
public class CenterFragment extends Fragment implements OnClickListener{
	@SuppressLint("NewApi")
	Button btn1;
	Button btn2;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.center_view, null);
		btn1 = (Button) view.findViewById(R.id.leftBtn);
		btn2 = (Button) view.findViewById(R.id.rightBtn);
		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
		return view;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v == btn1){
			((SlidMenuActivity) getActivity()).showLeft();
		}else if(v == btn2)
			((SlidMenuActivity) getActivity()).showRight();
	}
	
}
