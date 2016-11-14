package com.example.pulltofresh;

import java.util.ArrayList;
import java.util.List;

import com.example.androidviewjardemo.R;
import com.example.pulltofresh.MyListView.OnRefreshListener;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;

public class PullFreshActivity extends Activity {
	private List<String> list;  
    private MyListView lv;  
    private LvAdapter adapter;  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pull_fresh);
		
		lv = (MyListView) findViewById(R.id.lv);  
        list = new ArrayList<String>();  
        list.add("我");  
        adapter = new LvAdapter(list, this);  
        lv.setAdapter(adapter);  
        
        lv.setonRefreshListener(new OnRefreshListener() {  
        	  
            @Override  
            public void onRefresh() {  
                new AsyncTask<Void, Void, Void>() {  
                    protected Void doInBackground(Void... params) {  
                        try {  
                            Thread.sleep(1000);  
                        } catch (Exception e) {  
                            e.printStackTrace();  
                        }  
                        list.add("刷新后添加的内容"+list.size());  
                        return null;  
                    }  
  
                    @Override  
                    protected void onPostExecute(Void result) {  
                        adapter.notifyDataSetChanged();  
                        lv.onRefreshComplete();  
                    }  
                }.execute(null, null, null);  
            }  
        });  
    }  
}
