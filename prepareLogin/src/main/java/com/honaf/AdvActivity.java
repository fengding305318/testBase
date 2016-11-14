package com.honaf;


import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class AdvActivity extends Activity {
	private WebView wv;
	private ProgressDialog pdialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.adv);
		wv = (WebView) findViewById(R.id.wv);
//		wv=new WebView(this);
		wv.getSettings().setDefaultTextEncodingName("UTF-8");
		// 设置不显示滚动条
		wv.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
		// 开启javascript设置，否则WebView不执行js脚本
		wv.getSettings().setJavaScriptEnabled(true);
		loadurl();
		
	}
	public void loadurl(){
//		wv.setVisibility(View.VISIBLE);
		wv.setWebViewClient(new MyWebViewClient());
//		wv.setWebChromeClient(new MyWebChromeClient());
		// 加载进度
		pdialog = new ProgressDialog(getApplicationContext());
		// 加载本地网页
//		wv.loadUrl("file:///android_asset/webview.html");
		// 加载Intent网页
		wv.loadUrl("http://www.baidu.com");
		
	}
	/*
	 * 
	 * 页面中的链接，如果希望点击链接继续在当前browser中响应，
	 * 而不是新开Android的系统browser中响应该链接，
	 * 必须覆盖 webview的WebViewClient对象。
	 */
	final class MyWebViewClient extends WebViewClient {
		/* 页面加载时有loading提示 */
		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			pdialog = ProgressDialog.show(AdvActivity.this, null, "loading...",
					true, true);
			super.onPageStarted(view, url, favicon);
		}

		/* 页面加载完关闭loading提示 */
		@Override
		public void onPageFinished(WebView view, String url) {
			pdialog.dismiss();
			super.onPageFinished(view, url);
		
		}

		/* 页面加载失败后的错误提示 */
		@Override
		public void onReceivedError(WebView view, int errorCode,
				String description, String failingUrl) {
			pdialog.dismiss();
			Toast.makeText(getApplicationContext(), "load fail .. " + failingUrl
					+ description, Toast.LENGTH_SHORT);
			super.onReceivedError(view, errorCode, description, failingUrl);
		}
	}

}
