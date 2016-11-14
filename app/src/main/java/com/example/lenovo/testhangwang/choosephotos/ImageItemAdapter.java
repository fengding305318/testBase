package com.example.lenovo.testhangwang.choosephotos;

import android.app.Activity;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lenovo.testhangwang.R;

import java.util.List;

/**
 * Created by lenovo on 2016/4/27.
 */
public class ImageItemAdapter extends BaseAdapter{
    final String TAG = getClass().getSimpleName();

    Activity act;
    List<ImageItem> dataList;
    BitmapCache cache;
    BitmapCache.ImageCallback callback = new BitmapCache.ImageCallback() {
        @Override
        public void imageLoad(ImageView imageView, Bitmap bitmap,
                              Object... params) {
            if (imageView != null && bitmap != null) {
                String url = (String) params[0];
                if (url != null && url.equals((String) imageView.getTag())) {
                    ((ImageView) imageView).setImageBitmap(bitmap);
                } else {
                    Log.e(TAG, "callback, bmp not match");
                }
            } else {
                Log.e(TAG, "callback, bmp null");
            }
        }
    };

    public ImageItemAdapter(Activity act, List<ImageItem> list) {
        this.act = act;
        dataList = list;
        cache = new BitmapCache();
    }
    @Override
    public int getCount() {
        int count = 0;
        if (dataList != null) {
            count = dataList.size();
        }
        return count;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    class Holder {
        private ImageView iv;
        private ImageView selected;
        private TextView name;
        private TextView count;
    }
    public String getFileName(String pathandname){

        int start=pathandname.lastIndexOf("/");
        //int end=pathandname.lastIndexOf(".");
        //if(start!=-1 && end!=-1){
        if(start!=-1){
            return pathandname.substring(start+1,pathandname.length());
        }else{
            return null;
        }

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int arg0, View arg1, ViewGroup parent) {
        Holder holder;
        if (arg1 == null) {
            holder = new Holder();
            arg1 = View.inflate(act, R.layout.item_image_bucket, null);
            holder.iv = (ImageView) arg1.findViewById(R.id.image);
            holder.selected = (ImageView) arg1.findViewById(R.id.isselected);
            holder.name = (TextView) arg1.findViewById(R.id.name);
            holder.count = (TextView) arg1.findViewById(R.id.count);
            arg1.setTag(holder);
        } else {
            holder = (Holder) arg1.getTag();
        }
        ImageItem item = dataList.get(arg0);
        holder.count.setText("分数:" + item.score);
        holder.name.setText("名称:" + getFileName(item.imagePath));
        holder.selected.setVisibility(View.GONE);
        if (item != null) {
            String thumbPath = item.thumbnailPath;
            String sourcePath = item.imagePath;

            holder.iv.setTag(sourcePath);

            cache.displayBmp(holder.iv, thumbPath, sourcePath, callback);
        } else {
            holder.iv.setImageBitmap(null);
        }
        return arg1;
    }


}
