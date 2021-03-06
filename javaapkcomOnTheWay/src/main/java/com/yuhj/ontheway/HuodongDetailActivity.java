package com.yuhj.ontheway;

import com.yuhj.ontheway.activity.BaseActivity;
import com.zdp.aseo.content.AseoZdpAseo;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * @name HuodongDetailActivity
 * @Descripation 活动详情界面<br>
 * @author 禹慧军
 * @date 2014-10-25
 * @version 1.0
 */
public class HuodongDetailActivity extends BaseActivity {
	Handler handler;
	WebView webView;
	TextView textView;
	ProgressBar progressBar;
	private String url;
	private String name;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		url = getIntent().getStringExtra("url");
		name = getIntent().getStringExtra("name");
		LinearLayout rootViewLayout = new LinearLayout(this);
		rootViewLayout.setOrientation(LinearLayout.VERTICAL);
		View view = LayoutInflater.from(getApplicationContext()).inflate(
				R.layout.activity_huodong_detail, null);
		// 生成水平进度条
		TextView title = (TextView) view.findViewById(R.id.huodong_main_title);
		AseoZdpAseo.initType(this, AseoZdpAseo.INSERT_TYPE);
		// ~~~ 设置数据
		title.setText(name);
		rootViewLayout.addView(view);
		progressBar = new ProgressBar(this, null,
				android.R.attr.progressBarStyleHorizontal);
		webView = new WebView(this);
		rootViewLayout.addView(progressBar);
		rootViewLayout.addView(webView);
		setContentView(rootViewLayout);
		webView.getSettings().setAllowFileAccess(true);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.loadUrl(url);
		webView.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageFinished(WebView view, String url) {
				// TODO Auto-generated method stub
				super.onPageFinished(view, url);
				// 页面下载完毕,却不代表页面渲染完毕显示出来
				// WebChromeClient中progress==100时也是一样
				if (webView.getContentHeight() != 0) {
					// 这个时候网页才显示
				}
			}

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				// 自身加载新链接,不做外部跳转
				view.loadUrl(url);
				return true;
			}

		});

		webView.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				// TODO Auto-generated method stub
				super.onProgressChanged(view, newProgress);
				// 这里将textView换成你的progress来设置进度
				// if (newProgress == 0) {
				// textView.setVisibility(View.VISIBLE);
				// progressBar.setVisibility(View.VISIBLE);
				// }
				progressBar.setProgress(newProgress);
				progressBar.postInvalidate();
				// if (newProgress == 100) {
				// textView.setVisibility(View.GONE);
				// progressBar.setVisibility(View.GONE);
				// }
			}
		});
	}

}