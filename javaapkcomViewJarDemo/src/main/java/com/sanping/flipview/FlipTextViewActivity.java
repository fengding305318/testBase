package com.sanping.flipview;

import com.example.androidviewjardemo.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class FlipTextViewActivity extends Activity {

  protected FlipViewController flipView;

  /**
   * Called when the activity is first created.
   */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    //参数int代表折叠方向。  0 表示纵向 ，1表示横向。没有该参数表示默认纵向
    flipView = new FlipViewController(this,0);
    flipView.setAnimationBitmapFormat(Config.ARGB_8888);

    flipView.setAdapter(new BaseAdapter() {
      @Override
      public int getCount() {
        return 10;
      }

      @Override
      public Object getItem(int position) {
        return position;
      }

      @Override
      public long getItemId(int position) {
        return position;
      }

      @Override
      public View getView(int position, View convertView, ViewGroup parent) {
        NumberTextView view;
        if (convertView == null) {
          final Context context = parent.getContext();
          view = new NumberTextView(context, position);
          view.setTextSize(100);
        } else {
          view = (NumberTextView) convertView;
          view.setNumber(position);
        }

        return view;
      }
    });

    setContentView(flipView);
  }

  @Override
  protected void onResume() {
    super.onResume();
    flipView.onResume();
  }

  @Override
  protected void onPause() {
    super.onPause();
    flipView.onPause();
  }
}
