package com.honaf;

import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;

public class Gestor extends SimpleOnGestureListener{

	/**
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @param args 设定文件
	 * @return void 返回类型
	 * @throws
	 */

	public interface OnGestureListener {
		// Touch down时触发, e为down时的MotionEvent
		boolean onDown(MotionEvent e);

		// 在Touch down之后一定时间（115ms）触发，e为down时的MotionEvent
		void onShowPress(MotionEvent e);

		// Touch up时触发，e为up时的MotionEvent
		boolean onSingleTapUp(MotionEvent e);

		// 滑动时触发，e1为down时的MotionEvent，e2为move时的MotionEvent
		boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
				float distanceY);

		// 在Touch down之后一定时间（500ms）触发，e为down时的MotionEvent
		void onLongPress(MotionEvent e);

		// 滑动一段距离，up时触发，e1为down时的MotionEvent，e2为up时的MotionEvent
		boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY);
	}

	public interface OnDoubleTapListener {
		// 完成一次单击，并确定没有二击事件后触发（300ms），e为down时的MotionEvent
		boolean onSingleTapConfirmed(MotionEvent e);

		// 第二次单击down时触发，e为第一次down时的MotionEvent
		boolean onDoubleTap(MotionEvent e);

		// 第二次单击down,move和up时都触发，e为不同时机下的MotionEvent
		boolean onDoubleTapEvent(MotionEvent e);
	}

}
