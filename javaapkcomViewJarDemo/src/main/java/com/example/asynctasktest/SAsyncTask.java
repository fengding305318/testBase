package com.example.asynctasktest;

import android.os.AsyncTask;
import android.widget.ProgressBar;
import android.widget.TextView;

public class SAsyncTask extends AsyncTask<Integer, Integer, String>{
	TextView tv;
	ProgressBar pb;
	public SAsyncTask(TextView tv,ProgressBar pb){
		this.tv = tv;
		this.pb = pb;
	}
	
	//该方法运行在UI线程当中,并且运行在UI线程当中 可以对UI空间进行设置  
	//最先开始运行该方法，然后到doInBackground方法去
	    @Override  
	    protected void onPreExecute() {  
	        tv.setText("开始执行异步线程");  
	        pb.setMax(20);
	    } 
	    
	/**  
     * 这里的Integer参数对应AsyncTask中的第一个参数   
     * 这里的String返回值对应AsyncTask的第三个参数  
     * 该方法并不运行在UI线程当中，主要用于异步操作，所有在该方法中不能对UI当中的空间进行设置和修改  
     * 但是可以调用publishProgress方法触发onProgressUpdate对UI进行操作  
     */  
	@Override
	protected String doInBackground(Integer... params) {
		for (int i = 0; i < 20; i++) {
			try {
				Thread.sleep(1000);
				//调用该方法，促发重写的onProgressUpdate函数
				publishProgress(i);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		//这里结束后，将结果返回到onPostExecute方法中
		return params.length+"返回结果";
	}
	/**  
     * 这里的String参数对应AsyncTask中的第三个参数（也就是接收doInBackground的返回值）  
     * 在doInBackground方法执行结束之后在运行，并且运行在UI线程当中 可以对UI空间进行设置  
     */ 
	@Override
	protected void onPostExecute(String result) {
		tv.setText("执行结束"+result);
	}
	
	
    
    /**  
     * 这里的Intege参数对应AsyncTask中的第二个参数  
     * 在doInBackground方法当中，，每次调用publishProgress方法都会触发onProgressUpdate执行  
     * onProgressUpdate是在UI线程中执行，所有可以对UI空间进行操作  
     */  
    @Override  
    protected void onProgressUpdate(Integer... values) {  
        int vlaue = values[0];  
        tv.setText(vlaue+"");
        pb.setProgress(vlaue);  
    } 

}
