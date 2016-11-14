package com.example.horizontalslidelistview;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class MainActivity extends Activity {

	private ListView mListView;

	private List<String> mDataList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initData();

		mListView = (ListView) findViewById(R.id.listview);
		HorizontalSlideAdapter adapter = new HorizontalSlideAdapter(this,
				mDataList);
		mListView.setAdapter(adapter);
	}

	private void initData() {
		mDataList = new ArrayList<String>();
		mDataList.add("�ܡ���");
		mDataList.add("������");
		mDataList.add("�Ȩ");
		mDataList.add("����٢");
		mDataList.add("˾��ܲ");
		mDataList.add("�ء���");
		mDataList.add("�ʡ���");
		mDataList.add("������");
		mDataList.add("�ĺ");
		mDataList.add("�š���");
		mDataList.add("������");
		mDataList.add("������");
		mDataList.add("�š���");
		mDataList.add("�����");
		mDataList.add("�ơ���");
		mDataList.add("Ԭ����");
		mDataList.add("����");
		mDataList.add("�ԡ���");
		mDataList.add("�ܡ��");
		mDataList.add("������");
		mDataList.add("����");
		mDataList.add("����");
		mDataList.add("����׿");
		mDataList.add("�硡��");
		mDataList.add("����Ӣ");
		mDataList.add("½��ѷ");
		mDataList.add("�֡�ڼ");
	}

}
