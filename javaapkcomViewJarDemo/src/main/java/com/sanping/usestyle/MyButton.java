package com.sanping.usestyle;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.Button;

import com.example.androidviewjardemo.R;

public class MyButton extends Button{

	public MyButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initAttribute(context, attrs);
	}
	
	private void initAttribute(Context context, AttributeSet attrs){
		TypedArray attr = getTypedArray(context, attrs, R.styleable.MyButton);
        if (attr == null) {
            return;
        }
        String text = attr.getString(R.styleable.MyButton_cp_string);
        int reference = attr.getResourceId(R.styleable.MyButton_cp_reference,0);
        int color = attr.getColor(R.styleable.MyButton_cp_color, 0);
        float dis = attr.getDimension(R.styleable.MyButton_cp_dimension, 0);
        this.setText(text);
        this.setTextColor(color);
        this.setTextSize(dis);
        this.setBackgroundResource(reference);
	}
	protected TypedArray getTypedArray(Context context, AttributeSet attributeSet, int[] attr) {
        return context.obtainStyledAttributes(attributeSet, attr, 0, 0);
    }
}
