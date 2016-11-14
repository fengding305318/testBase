package com.example.horizontalslidelistview;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class HorizontalSlideAdapter extends ArrayAdapter<String> {

	/** ��Ļ��� */
	private int mScreenWidth;

	/** ɾ����ť�¼� */
	private DeleteButtonOnclickImpl mDelOnclickImpl;
	/** HorizontalScrollView���һ����¼� */
	private ScrollViewScrollImpl mScrollImpl;

	/** ���ֲ���,��̬��HorizontalScrollView�е�TextView��Ȱ��������� */
	private LinearLayout.LayoutParams mParams;

	/** ��¼������ɾ����ť��itemView */
	public HorizontalScrollView mScrollView;

	/** touch�¼�����,����Ѿ��л�����ɾ����ť��itemView,��������һ����(down,move,up)��onTouch���� */
	public boolean mLockOnTouch = false;

	public HorizontalSlideAdapter(Context context, List<String> objects) {
		super(context, 0, objects);
		// �㵽��Ļ���
		Display defaultDisplay = ((Activity) context).getWindowManager()
				.getDefaultDisplay();
		DisplayMetrics metrics = new DisplayMetrics();
		defaultDisplay.getMetrics(metrics);
		mScreenWidth = metrics.widthPixels;
		mParams = new LinearLayout.LayoutParams(mScreenWidth,
				LinearLayout.LayoutParams.MATCH_PARENT);
		// ��ʼ��ɾ����ť�¼���item�����¼�
		mDelOnclickImpl = new DeleteButtonOnclickImpl();
		mScrollImpl = new ScrollViewScrollImpl();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = View.inflate(getContext(),
					R.layout.item_horizontal_slide_listview, null);
			holder.scrollView = (HorizontalScrollView) convertView;
			holder.scrollView.setOnTouchListener(mScrollImpl);
			holder.infoTextView = (TextView) convertView
					.findViewById(R.id.item_text);
			// ����item����Ϊfill_parent��
			holder.infoTextView.setLayoutParams(mParams);
			holder.deleteButton = (Button) convertView
					.findViewById(R.id.item_delete);
			holder.deleteButton.setOnClickListener(mDelOnclickImpl);
		//	holder.infoTextView.setOnClickListener(mDelOnclickImpl);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.position = position;
		holder.deleteButton.setTag(holder);
		holder.infoTextView.setText(getItem(position));
		holder.scrollView.scrollTo(0, 0);
		return convertView;
	}

	static class ViewHolder {
		private HorizontalScrollView scrollView;
		private TextView infoTextView;
		private Button deleteButton;
		private int position;
	}

	/** HorizontalScrollView�Ļ����¼� */
	private class ScrollViewScrollImpl implements OnTouchListener {
		/** ��¼��ʼʱ������ */
		private float startX = 0;
		private TextView lastView;

		@SuppressLint("ClickableViewAccessibility")
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				// ����л���ɾ����ť��itemView,����������ȥ������������touch����,�������ڸ������dispatchTouchEvent�н���
				if (mScrollView != null) {
					scrollView(mScrollView, HorizontalScrollView.FOCUS_LEFT);
					mScrollView = null;
					mLockOnTouch = true;
					return true;
				}
				startX = event.getX();
				break;
			case MotionEvent.ACTION_UP:
				HorizontalScrollView view = (HorizontalScrollView) v;
				// ���������>50������,����ʾ��ɾ����ť
				if (startX > event.getX() + 50) {
					startX = 0;// ��Ϊ����һ���¼��������,��ֹ����,��ԭstartXֵ
					scrollView(view, HorizontalScrollView.FOCUS_RIGHT);
					mScrollView = view;
				} else {
					scrollView(view, HorizontalScrollView.FOCUS_LEFT);
					ViewHolder holder = (ViewHolder) v.getTag();
					if(lastView!=null){
						lastView.setTextColor(Color.RED);
					}
					lastView=holder.infoTextView;
					holder.infoTextView.setTextColor(Color.BLACK);
				}
				break;
			}
			return false;
		}
	}

	/** HorizontalScrollView���һ��� */
	public void scrollView(final HorizontalScrollView view, final int parameter) {
		view.post(new Runnable() {
			@Override
			public void run() {
				view.pageScroll(parameter);
			}
		});
	}

	/** ɾ���¼� */
	private class DeleteButtonOnclickImpl implements OnClickListener {
		@Override
		public void onClick(View v) {
			final ViewHolder holder = (ViewHolder) v.getTag();
			if(v.getId()==R.id.item_delete){
			Toast.makeText(getContext(), "ɾ����" + holder.position + "��",
					Toast.LENGTH_SHORT).show();
			Animation animation = AnimationUtils.loadAnimation(getContext(),
					R.anim.anim_item_delete);
			holder.scrollView.startAnimation(animation);
			animation.setAnimationListener(new AnimationListener() {
				@Override
				public void onAnimationStart(Animation animation) {
				}

				@Override
				public void onAnimationRepeat(Animation animation) {
				}

				@Override
				public void onAnimationEnd(Animation animation) {
					remove(getItem(holder.position));
				}
			});
			}
			else{
				
			}

		}
	}
}
