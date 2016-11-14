package com.sanping.shimmerjumptext;

import com.example.androidviewjardemo.R;

import android.os.Bundle;
import android.app.Activity;

public class ShimmerJumpTextActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shimmer_jump_text);

        TitanicTextView tv = (TitanicTextView) findViewById(R.id.my_text_view);

        // set fancy typeface
        //设置的一种字体，不是必要的代码
        tv.setTypeface(Typefaces.get(this, "Satisfy-Regular.ttf"));

        // start animation
        new Titanic().start(tv);
	}

	

}
