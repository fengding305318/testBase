package com.example.androidviewjardemo;

import com.example.androidviewjardemo.R;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ExpandableActivity extends Activity {
	ExpandableListView mListview;
	//设置组视图的图片
    int[] logos = new int[] { R.drawable.ic_launcher, R.drawable.ic_launcher,R.drawable.ic_launcher};
    //设置组视图的显示文字
    private String[] generalsTypes = new String[] { "魏", "蜀", "吴" };
    //子视图显示文字
    private String[][] generals = new String[][] {
            { "夏侯惇", "甄姬", "许褚", "郭嘉", "司马懿", "杨修" },
            { "马超", "张飞", "刘备", "诸葛亮", "黄月英", "赵云" },
            { "吕蒙", "陆逊", "孙权", "周瑜", "孙尚香" }
    };
    //子视图图片
    public int[][] generallogos = new int[][] {
            { R.drawable.ic_launcher, R.drawable.ic_launcher,
                    R.drawable.ic_launcher, R.drawable.ic_launcher,
                    R.drawable.ic_launcher, R.drawable.ic_launcher },
            { R.drawable.ic_launcher, R.drawable.ic_launcher,
                    R.drawable.ic_launcher, R.drawable.ic_launcher,
                    R.drawable.ic_launcher, R.drawable.ic_launcher },
            { R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher,
                    R.drawable.ic_launcher, R.drawable.ic_launcher } };
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_expandablelistview);
		mListview = (ExpandableListView)findViewById(R.id.list);
		 /* 隐藏默认箭头显示 */  
		mListview.setGroupIndicator(null);  
        /* 隐藏垂直滚动条 */  
		mListview.setVerticalScrollBarEnabled(false);  
		setListener();
	}

	public void setListener(){
		mListview.setAdapter(new MyAdapter());
		mListview.setOnGroupClickListener(new OnGroupClickListener() {
			
			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				// TODO Auto-generated method stub
				Toast.makeText(ExpandableActivity.this, groupPosition+"", 500).show();
				return false;
			}
		});
		mListview.setOnChildClickListener(new OnChildClickListener() {
			
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				Toast.makeText(
						ExpandableActivity.this,
                        "你点击了" + new MyAdapter().getChild(groupPosition, childPosition),
                        Toast.LENGTH_SHORT).show();
				return false;
			}
		});
	}
	
	class MyAdapter extends BaseExpandableListAdapter{

		@Override
		public int getGroupCount() {
			// TODO Auto-generated method stub
			return generalsTypes.length;
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			// TODO Auto-generated method stub
			return generals[groupPosition].length;
		}

		@Override
		public Object getGroup(int groupPosition) {
			// TODO Auto-generated method stub
			return generalsTypes[groupPosition];
		}

		@Override
		public Object getChild(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return generals[groupPosition][childPosition];
		}

		@Override
		public long getGroupId(int groupPosition) {
			// TODO Auto-generated method stub
			return groupPosition;
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return childPosition;
		}

		@Override
		public boolean hasStableIds() {
			// TODO Auto-generated method stub
			return true;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			LinearLayout ll = new LinearLayout(
					ExpandableActivity.this);
            ll.setOrientation(0);
            ImageView logo = new ImageView(ExpandableActivity.this);
            logo.setImageResource(logos[groupPosition]);
            logo.setPadding(50, 0, 0, 0);
            ll.addView(logo);
            TextView textView = new TextView(ExpandableActivity.this);
            textView.setTextColor(Color.BLACK);
            textView.setText(getGroup(groupPosition).toString());
            ll.addView(textView);

            return ll;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			LinearLayout ll = new LinearLayout(
                    ExpandableActivity.this);
            ll.setOrientation(0);
            ImageView generallogo = new ImageView(
            		ExpandableActivity.this);
            generallogo
                    .setImageResource(generallogos[groupPosition][childPosition]);
            ll.addView(generallogo);
            TextView textView = new TextView(ExpandableActivity.this);
            textView.setText(generals[groupPosition][childPosition]);
            ll.addView(textView);
            return ll;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return true;
		}
		
	}
}
