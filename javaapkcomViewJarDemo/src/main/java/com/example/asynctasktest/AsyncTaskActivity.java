package com.example.asynctasktest;

import com.example.androidviewjardemo.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class AsyncTaskActivity extends Activity {
	private Button button;  
    private ProgressBar progressBar;  
    private TextView textView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_asynctask);
		button = (Button)findViewById(R.id.button03);  
        progressBar = (ProgressBar)findViewById(R.id.progressBar02);  
        textView = (TextView)findViewById(R.id.textView01);
        button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SAsyncTask asyncTask = new SAsyncTask(textView, progressBar);  
                asyncTask.execute(); 
			}
		});
	}
}
