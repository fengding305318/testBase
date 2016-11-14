package com.tech9.emaoer.fruitgame;

import android.app.Activity;
import android.os.Bundle;


public class FruitFrameActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        MyGameView view= new MyGameView(this);
        setContentView(view);
    }
}