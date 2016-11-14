package com.Browser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Web extends Activity {

	WebView webView = null;
	Bundle bundle = new Bundle();
	public static Web instance = null;

	// Intent intent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.web);
		instance = this;
		this.webView = (WebView) this.findViewById(R.id.webkitWebView1);
		WebSettings settings=this.webView.getSettings();
		settings.setJavaScriptEnabled(true);//‘ –Ì‘À––JavaScript
		this.webView.setWebViewClient(new WebViewClient(){

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				return false;
			}

		});
		Intent intent = this.getIntent();
		bundle = intent.getExtras();

		new Thread() {

			@Override
			public void run() {

				String web = bundle.getString("web");
				Log.d("web", web);
				if (web.equals("baidu")) {
					instance.webView.loadUrl("http://www.baidu.com/");
				}
				if (web.equals("jindong")) {
					instance.webView
							.loadUrl("http://m.360buy.com/");
				}
				if (web.equals("78")) {
					instance.webView.loadUrl("http://www.78.cn/");
				}
				if (web.equals("taobao")) {
					instance.webView.loadUrl("http://www.taobao.com/");
				}
				if (web.equals("sina")) {
					instance.webView.loadUrl("http://www.sina.com.cn/");
				}
				if (web.equals("nba")) {
					instance.webView.loadUrl("http://china.nba.com/");
				}
				if (web.equals("58")) {
					instance.webView.loadUrl("http://ty.58.com/");
				}
				if (web.equals("tentxun")) {
					instance.webView.loadUrl("http://www.qq.com/");
				}
				if (web.equals("fanke")) {
					instance.webView
							.loadUrl("http://www.vancl.com/?source=bdzqbtd56a1cce0ea3fe76");
				}
				if (web.equals("renren")) {
					instance.webView.loadUrl("http://www.renren.com/");
				}
				if (web.equals("zhenai")) {
					instance.webView
							.loadUrl("http://album.zhenai.com/login/login.jsp");
				}

			}

		}.start();


	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
			webView.goBack();
	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
	}

}
