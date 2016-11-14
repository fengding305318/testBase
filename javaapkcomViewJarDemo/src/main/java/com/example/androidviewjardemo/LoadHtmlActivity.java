package com.example.androidviewjardemo;

import android.os.Bundle;
import android.app.Activity;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

//you can also link to a website. Example:
//webView.loadUrl("www.google.com");
//I have included web permissions in the AndroidManifest.xml

//不知道要不要加<uses-permission android:name="android.permission.INTERNET" />权限
public class LoadHtmlActivity extends Activity {
	WebView webView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_load_html);
        webView = (WebView)findViewById(R.id.fullscreen_content);
        webView.setWebViewClient(new MyWebViewClient());//辅助处理各种通知、请求事件的，
        webView.loadUrl("file:///android_asset/www/index.html");
        webView.setWebChromeClient(new WebChromeClient());//辅助webview处理js的对话框，网站图标，网站title，加载进度等
        WebSettings webSettings = webView.getSettings();
        //设置允许提交
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabasePath("/data/data/"+this.getPackageName()+"/databases/");
	}

	@Override
    public void onBackPressed()
    {
        if(webView.canGoBack())
            webView.goBack();
        else
            super.onBackPressed();
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

	public class MyWebViewClient extends WebViewClient {
	    @Override
	    public boolean shouldOverrideUrlLoading(WebView view, String url) {
	        view.loadUrl(url);
	        return true;
	    }
	}
}
