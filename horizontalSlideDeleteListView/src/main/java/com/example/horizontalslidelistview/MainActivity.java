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
		mDataList.add("²Ü¡¡²Ù");
		mDataList.add("Áõ¡¡±¸");
		mDataList.add("Ëï¡¡È¨");
		mDataList.add("»ª¡¡Ù¢");
		mDataList.add("Ë¾ÂíÜ²");
		mDataList.add("¹Ø¡¡Óğ");
		mDataList.add("¸Ê¡¡Äş");
		mDataList.add("ÂÀ¡¡²¼");
		mDataList.add("ÏÄºîª");
		mDataList.add("ÕÅ¡¡·É");
		mDataList.add("ÂÀ¡¡ÃÉ");
		mDataList.add("õõ¡¡²õ");
		mDataList.add("ÕÅ¡¡ÁÉ");
		mDataList.add("Öî¸ğÁÁ");
		mDataList.add("»Æ¡¡¸Ç");
		mDataList.add("Ô¬¡¡ÉÜ");
		mDataList.add("Ğí¡¡ñÒ");
		mDataList.add("ÕÔ¡¡ÔÆ");
		mDataList.add("ÖÜ¡¡è¤");
		mDataList.add("¹ù¡¡¼Î");
		mDataList.add("Âí¡¡³¬");
		mDataList.add("´ó¡¡ÇÇ");
		mDataList.add("¶­¡¡×¿");
		mDataList.add("Õç¡¡¼§");
		mDataList.add("»ÆÔÂÓ¢");
		mDataList.add("Â½¡¡Ñ·");
		mDataList.add("¼Ö¡¡Ú¼");
	}

}
