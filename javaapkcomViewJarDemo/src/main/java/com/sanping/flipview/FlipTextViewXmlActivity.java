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

import com.example.androidviewjardemo.R;

import android.content.Context;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * @author Paul Burke paulburke.co
 */
public class FlipTextViewXmlActivity extends FlipTextViewActivity {

  protected FlipViewController flipView;

  /**
   * Called when the activity is first created.
   */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.xml_layout);

    /*
     * 这里本来可以通过xml的stybleable设置折叠方向的，但是我为了打包删除了样式，所以只有默认的纵向了
     * 欲知更多详细代码， 可以运行开源项目里面的viewflip折叠
     */
    flipView = (FlipViewController) findViewById(R.id.flipView);
    flipView.setAnimationBitmapFormat(Config.RGB_565);

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
          view.setTextSize(200);
        } else {
          view = (NumberTextView) convertView;
          view.setNumber(position);
        }

        return view;
      }
    });
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
