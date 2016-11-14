package com.example.androidviewjardemo;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;

/*
 * 异步加载图片类
 * 这个方法具体还没看懂... 嗖嘎，擦
 * */
public class ImageLoaderActivity extends Activity {
	
	private GridView grid;
	
	String[] imageUrls = {"http://192.168.88.51/images/customized/ihotel_hd_05/chi/category/category_3_14.png?ts=1416824669"
			,"http://192.168.88.51/images/customized/ihotel_hd_05/chi/category/mulpic_36_2466_698348.jpg?ts=1416824790"
			,"http://192.168.88.51/images/customized/ihotel_hd_05/chi/category/assocpic_36_714_502394.jpg?ts=1416824830"
			,"http://192.168.88.51/images/customized/ihotel_hd_05/chi/category/assocpic_36_715_600133.jpg?ts=1416824892"
			,"http://192.168.88.51/images/customized/ihotel_hd_05/chi/category/assocpic_36_716_691226.jpg?ts=1416824901"
			,"http://192.168.88.51/images/customized/ihotel_hd_05/chi/category/assocpic_36_717_169252.jpg?ts=1416824912"
			,"http://192.168.88.51/images/customized/ihotel_hd_05/chi/category/assocpic_36_718_990631.jpg?ts=1416824923"
			,"http://192.168.88.51/images/customized/ihotel_hd_05/chi/category/assocpic_36_719_408234.jpg?ts=1416824934"
			,"http://192.168.88.51/images/customized/ihotel_hd_05/chi/category/category_1_13_453144.png?ts=1416824970"
			,"http://192.168.88.51/images/customized/ihotel_hd_05/chi/category/mulpic_36_2466_698348.jpg?ts=1416824790"
			,"http://192.168.88.51/images/customized/ihotel_hd_05/chi/category/mulpic_36_2466_698348.jpg?ts=1416824790"
			,"http://192.168.88.51/images/customized/ihotel_hd_05/chi/category/category_3_14.png?ts=1416824669"
			,"http://192.168.88.51/images/customized/ihotel_hd_05/chi/category/mulpic_36_2466_698348.jpg?ts=1416824790"
			,"http://192.168.88.51/images/customized/ihotel_hd_05/chi/category/assocpic_36_714_502394.jpg?ts=1416824830"
			,"http://192.168.88.51/images/customized/ihotel_hd_05/chi/category/assocpic_36_715_600133.jpg?ts=1416824892"
			,"http://192.168.88.51/images/customized/ihotel_hd_05/chi/category/assocpic_36_716_691226.jpg?ts=1416824901"
			,"http://192.168.88.51/images/customized/ihotel_hd_05/chi/category/assocpic_36_717_169252.jpg?ts=1416824912"
			,"http://192.168.88.51/images/customized/ihotel_hd_05/chi/category/assocpic_36_718_990631.jpg?ts=1416824923"
			,"http://192.168.88.51/images/customized/ihotel_hd_05/chi/category/assocpic_36_719_408234.jpg?ts=1416824934"
			,"http://192.168.88.51/images/customized/ihotel_hd_05/chi/category/category_1_13_453144.png?ts=1416824970"
			,"http://192.168.88.51/images/customized/ihotel_hd_05/chi/category/mulpic_36_2466_698348.jpg?ts=1416824790"
			,"http://192.168.88.51/images/customized/ihotel_hd_05/chi/category/mulpic_36_2466_698348.jpg?ts=1416824790"
			};
	DisplayImageOptions options;

	public void initImageLoader(Context context) {
		// This configuration tuning is custom. You can tune every option, you may tune some of them,
		// or you can create default configuration by
		//  ImageLoaderConfiguration.createDefault(this);
		// method.
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.diskCacheFileNameGenerator(new Md5FileNameGenerator())
				.diskCacheSize(50 * 1024 * 1024) // 50 Mb
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.writeDebugLogs() // Remove for release app
				.build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_loader);
		initImageLoader(this);
		grid = (GridView)findViewById(R.id.grid);
		
		options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.p1)
		.showImageForEmptyUri(R.drawable.p2)
		.showImageOnFail(R.drawable.ic_launcher)
		.cacheInMemory(true)
		.cacheOnDisk(true)
		.considerExifParams(true)
		.bitmapConfig(Bitmap.Config.RGB_565)
		.build();
		
		grid.setAdapter(new ImageAdapter());
		
	}
	
	public class ImageAdapter extends BaseAdapter {

		private LayoutInflater inflater;

		ImageAdapter() {
			inflater = LayoutInflater.from(ImageLoaderActivity.this);
		}

		@Override
		public int getCount() {
			return imageUrls.length;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final ViewHolder holder;
			View view = convertView;
			if (view == null) {
				view = inflater.inflate(R.layout.item, parent, false);
				holder = new ViewHolder();
				assert view != null;
				holder.imageView = (ImageView) view.findViewById(R.id.image);
				holder.progressBar = (ProgressBar) view.findViewById(R.id.progress);
				view.setTag(holder);
			} else {
				holder = (ViewHolder) view.getTag();
			}

			ImageLoader.getInstance()
					.displayImage(imageUrls[position], holder.imageView, options, new SimpleImageLoadingListener() {
						@Override
						public void onLoadingStarted(String imageUri, View view) {
							holder.progressBar.setProgress(0);
							holder.progressBar.setVisibility(View.VISIBLE);
						}

						@Override
						public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
							holder.progressBar.setVisibility(View.GONE);
						}

						@Override
						public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
							holder.progressBar.setVisibility(View.GONE);
						}
					}, new ImageLoadingProgressListener() {
						@Override
						public void onProgressUpdate(String imageUri, View view, int current, int total) {
							holder.progressBar.setProgress(Math.round(100.0f * current / total));
						}
					});

			return view;
		}
	}

	static class ViewHolder {
		ImageView imageView;
		ProgressBar progressBar;
	}
}
