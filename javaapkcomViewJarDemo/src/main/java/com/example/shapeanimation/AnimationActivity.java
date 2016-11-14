package com.example.shapeanimation;

import android.os.Bundle;
import android.app.Activity;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.androidviewjardemo.R;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;

public class AnimationActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_animation);
		LinearLayout container = (LinearLayout) findViewById(R.id.container);
		final MyAnimationView animView = new MyAnimationView(this);
		container.addView(animView);

		Button starter = (Button) findViewById(R.id.startButton);
		starter.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				animView.startAnimation();
			}
		});
	}

	public class MyAnimationView extends View implements
			ValueAnimator.AnimatorUpdateListener {

		public final ArrayList<ShapeHolder> balls = new ArrayList<ShapeHolder>();
		AnimatorSet animation = null;
		private float mDensity;

		public MyAnimationView(Context context) {
			super(context);

			mDensity = getContext().getResources().getDisplayMetrics().density;

			ShapeHolder ball0 = addBall(50f, 50f);
			ShapeHolder ball1 = addBall(150f, 70f);
			ShapeHolder ball2 = addBall(250f, 90f);
			ShapeHolder ball3 = addBall(350f, 110f);
		}

		/*
		 * 构建动画ObjectAnimator
		 * 第一个参数表示运动的shapeHolder
		 * 参数“y”表示垂直方向运动，“x”表示水平方向运动，其他字符串无效
		 * 后面的两个float参数表示从哪里到哪里的位置的变化
		 * */
		private void createAnimation() {
			if (animation == null) {
				ObjectAnimator anim1 = ObjectAnimator.ofFloat(balls.get(0),
						"y",0f , getHeight() - balls.get(0).getHeight())
						.setDuration(500);
				
				ObjectAnimator anim2 = anim1.clone();
				anim2.setTarget(balls.get(1));
				anim1.addUpdateListener(this);

				ShapeHolder ball2 = balls.get(2);
				ObjectAnimator animDown = ObjectAnimator.ofFloat(ball2, "y",
						0f, getHeight() - ball2.getHeight()).setDuration(500);
				//设置球3下落加速器
				animDown.setInterpolator(new AccelerateInterpolator());
				ObjectAnimator animUp = ObjectAnimator.ofFloat(ball2, "y",
						getHeight() - ball2.getHeight(), 0f).setDuration(500);
				//设置球3上升减速
				animUp.setInterpolator(new DecelerateInterpolator());
				
				// 顺序播放效果，参数个数可变
				AnimatorSet s1 = new AnimatorSet();
				s1.playSequentially(animDown, animUp);
				
				animDown.addUpdateListener(this);
				animUp.addUpdateListener(this);
				
				AnimatorSet s2 = (AnimatorSet) s1.clone();
				s2.setTarget(balls.get(3));

				animation = new AnimatorSet();
				animation.playTogether(anim1, anim2, s1);// 并行
				animation.playSequentially(s1, s2);// 串行
			}
		}

		/*
		 * 构造一个shapeHolder需要ShapeDrawable，ShapeDrawable必须要有一个shape类来构造
		 * ovalshape是椭圆模型，也可以用rectshape，长方形模型
		 * 
		 * */
		private ShapeHolder addBall(float x, float y) {
			//ovalshape 是一个椭圆形 的一个类
			OvalShape circle = new OvalShape();
			
			//设置模型的大小
			circle.resize(50f * mDensity, 50f * mDensity);
			
			ShapeDrawable drawable = new ShapeDrawable(circle);
			ShapeHolder shapeHolder = new ShapeHolder(drawable);
			
			//设置模型的位置
			shapeHolder.setX(x - 25f);
			shapeHolder.setY(y - 25f);
			
			//设置模型的前景色
			int color = Color.BLUE;
			
			Paint paint = drawable.getPaint();
			//设置模型的后景色
			int darkColor = Color.RED;
			
			//设置前景色以及后景色的区域
			RadialGradient gradient = new RadialGradient(37.5f, 12.5f, 50f,
					color, darkColor, Shader.TileMode.CLAMP);
			paint.setShader(gradient);
			shapeHolder.setPaint(paint);
			balls.add(shapeHolder);
			return shapeHolder;
		}

		@Override
		protected void onDraw(Canvas canvas) {
			for (int i = 0; i < balls.size(); ++i) {
				ShapeHolder shapeHolder = balls.get(i);
				canvas.save();
				canvas.translate(shapeHolder.getX(), shapeHolder.getY());
				shapeHolder.getShape().draw(canvas);
				canvas.restore();
			}
		}

		@Override
		public void onAnimationUpdate(ValueAnimator animation) {
			invalidate();
		}

		public void startAnimation() {
			createAnimation();
			animation.start();
		}
	}
}
