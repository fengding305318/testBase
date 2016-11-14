/*
Copyright 2012 Aphid Mobile

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at
 
   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package com.sanping.flipview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class FlipButtonActivity extends Activity {

  private FlipViewController flipView;

  /**
   * Called when the activity is first created.
   */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  //参数int代表折叠方向。  0 表示纵向 ，1表示横向。没有该参数表示默认纵向
    flipView = new FlipViewController(this,1);
    //使用RGB_565可以减少在大屏幕设备峰值内存使用量，但它是由你来选择最佳的位图格式
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
        NumberButton button;
        if (convertView == null) {
          final Context context = parent.getContext();
          button = new NumberButton(context, position);
          button.setTextSize(200);
        } else {
          button = (NumberButton) convertView;
          button.setNumber(position);
        }

        return button;
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
