package com.Browser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class MainMenuActivity extends Activity implements OnClickListener {
	ImageButton imageButton_Baidu;
	ImageButton imageButton_Jindong;
	ImageButton imageButton_78;
	ImageButton imageButton_Taobao;
	ImageButton imageButton_Sina;
	ImageButton imageButton_NBA;
	ImageButton imageButton_58;
	ImageButton imageButton_Tengxun;
	ImageButton imageButton_Fanke;
	ImageButton imageButton_Zhenai;
	ImageButton imageButton_Renren;

	Bundle bundle = new Bundle();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.mainmenu);
		imageButton_Baidu = (ImageButton) this
				.findViewById(R.id.imageButton_Baidu);
		imageButton_Jindong = (ImageButton) this
				.findViewById(R.id.imageButton_Jindong);
		imageButton_78 = (ImageButton) this.findViewById(R.id.imageButton_78);
		imageButton_Taobao = (ImageButton) this
				.findViewById(R.id.imageButton_TaoBao);
		imageButton_Sina = (ImageButton) this
				.findViewById(R.id.imageButton_Sina);
		imageButton_NBA = (ImageButton) this.findViewById(R.id.imageButton_NBA);
		imageButton_58 = (ImageButton) this.findViewById(R.id.imageButton_58);
		imageButton_Tengxun = (ImageButton) this
				.findViewById(R.id.imageButton_Tengxun);
		imageButton_Fanke = (ImageButton) this
				.findViewById(R.id.imageButton_Fanke);
		imageButton_Renren = (ImageButton) this
				.findViewById(R.id.imageButton_Renren);
		imageButton_Zhenai = (ImageButton) this
				.findViewById(R.id.imageButton_Zhenai);

		setOnClick();

	}

	public void setOnClick() {
		imageButton_Baidu.setOnClickListener(this);
		imageButton_78.setOnClickListener(this);
		imageButton_Baidu.setOnClickListener(this);
		imageButton_Taobao.setOnClickListener(this);
		imageButton_Sina.setOnClickListener(this);
		imageButton_NBA.setOnClickListener(this);
		imageButton_58.setOnClickListener(this);
		imageButton_Tengxun.setOnClickListener(this);
		imageButton_Fanke.setOnClickListener(this);
		imageButton_Renren.setOnClickListener(this);
		imageButton_Zhenai.setOnClickListener(this);

	}

	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.imageButton_58) {
			Intent intent = new Intent(this, Web.class);
			bundle.putString("web", "58");
			intent.putExtras(bundle);
			this.startActivity(intent);
		}
		if (id == R.id.imageButton_78) {
			Intent intent = new Intent(this, Web.class);
			bundle.putString("web", "78");
			intent.putExtras(bundle);
			this.startActivity(intent);
		}
		if (id == R.id.imageButton_Baidu) {
			Intent intent = new Intent(this, Web.class);
			bundle.putString("web", "baidu");
			intent.putExtras(bundle);
			this.startActivity(intent);
		}
		if (id == R.id.imageButton_Fanke) {
			Intent intent = new Intent(this, Web.class);
			bundle.putString("web", "fanke");
			intent.putExtras(bundle);
			this.startActivity(intent);
		}
		if (id == R.id.imageButton_Jindong) {
			Intent intent = new Intent(this, Web.class);
			bundle.putString("web", "jindong");
			intent.putExtras(bundle);
			this.startActivity(intent);
		}
		if (id == R.id.imageButton_NBA) {
			Intent intent = new Intent(this, Web.class);
			bundle.putString("web", "nba");
			intent.putExtras(bundle);
			this.startActivity(intent);
		}
		if (id == R.id.imageButton_Renren) {
			Intent intent = new Intent(this, Web.class);
			bundle.putString("web", "renren");
			intent.putExtras(bundle);
			this.startActivity(intent);
		}
		if (id == R.id.imageButton_Sina) {
			Intent intent = new Intent(this, Web.class);
			bundle.putString("web", "sina");
			intent.putExtras(bundle);
			this.startActivity(intent);
		}
		if (id == R.id.imageButton_TaoBao) {
			Intent intent = new Intent(this, Web.class);
			bundle.putString("web", "taobao");
			intent.putExtras(bundle);
			this.startActivity(intent);
		}
		if (id == R.id.imageButton_Tengxun) {
			Intent intent = new Intent(this, Web.class);
			bundle.putString("web", "tentxun");
			intent.putExtras(bundle);
			this.startActivity(intent);
		}
		if (id == R.id.imageButton_Zhenai) {
			Intent intent = new Intent(this, Web.class);
			bundle.putString("web", "zhenai");
			intent.putExtras(bundle);
			this.startActivity(intent);
		}

	}

}
