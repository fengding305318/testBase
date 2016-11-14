package com.sanping.centeropenact;

import com.example.androidviewjardemo.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Activity2 extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Preparing the 2 images to be split
        ActivitySplitAnimationUtil.prepareAnimation(this);
        setContentView(R.layout.activity_2);
        ActivitySplitAnimationUtil.animate(this, 1000);
    }
    
    @Override
    protected void onStop() {
        // If we're currently running the entrance animation - cancel it
        ActivitySplitAnimationUtil.cancel();

        super.onStop();    //To change body of overridden methods use File | Settings | File Templates.
    }

}
