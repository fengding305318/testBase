package com.tech9.emaoer.fruitgame;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class StartGameActivity extends Activity {
	
	private final static int CONFIRM_DIALOG = 1;
	
	 @Override
	public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.gamestart);
	        initConfirmDialogButton();
	        initCancleDialogButton();
	      
	    }
	 private void initConfirmDialogButton()
	    {
	    	ImageView btn = (ImageView) findViewById(R.id.imageView_start);
	    	btn.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					jump();
				}
			});
	    }    
	    private void jump(){
	    	Intent intent=new Intent(this,FruitFrameActivity.class);
	    
	    	startActivity(intent);
	    }

	private void initCancleDialogButton()
	{
		ImageView btn = (ImageView) findViewById(R.id.imageView_cancle);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showDialog(CONFIRM_DIALOG);
				
			}
		});
	}
		@Override
		protected Dialog onCreateDialog(int id) {
			// TODO Auto-generated method stub
			if(id == CONFIRM_DIALOG)
			{
				return showConfirm();
			}
			return super.onCreateDialog(id);
		}
		
		private Dialog showConfirm()
		{
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			
			builder.setTitle("你确定要退出本游戏？");
			builder.setIcon(R.drawable.watermelon);
			builder.setMessage("点击OK将退出游戏,点击cancle将返回开始界面");
			builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					finish();
				}
			});
			builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					removeDialog(CONFIRM_DIALOG);
				}
			});
			
			return builder.create();
		}
		







}
