package com.sanping.jumptext;


import com.example.androidviewjardemo.R;

import android.os.Bundle;
import android.app.Activity;
import android.widget.TextView;

public class JumpTextActivity extends Activity {
	private JumpingBeans jumpingBeans1, jumpingBeans2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jump_text);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		 final TextView textView1 = (TextView) findViewById(R.id.txt1);
	        jumpingBeans1 = JumpingBeans.with(textView1)
	                .appendJumpingDots()
	                .build();

	        // Note that, even though we access textView2's text when starting to work on
	        // the animation builder, we don't alter it in any way, so we're ok
	        final TextView textView2 = (TextView) findViewById(R.id.txt2);
	        jumpingBeans2 = JumpingBeans.with(textView2)
	                .makeTextJump(0, textView2.getText().toString().indexOf("好"))
	                .setIsWave(false)
	                .setLoopDuration(1000)
	                .build();
	}
	
	@Override
    protected void onPause() {
        super.onPause();
        jumpingBeans1.stopJumping();
        jumpingBeans2.stopJumping();
    }

}
