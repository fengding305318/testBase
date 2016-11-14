package com.example.androidviewjardemo;

import com.sanping.curlpage.CurlPage;
import com.sanping.curlpage.CurlView;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

public class CurlPageActivity extends Activity{
	private CurlView mCurlView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_curlpage);
		mCurlView = (CurlView) findViewById(R.id.curl);
		//图片的加载，可以看做一个adapter
		mCurlView.setPageProvider(new PageProvider());
		//判断屏幕高宽的改变监听
		mCurlView.setSizeChangedObserver(new SizeChangedObserver());
		//设置当前页
		mCurlView.setCurrentIndex(0);
		mCurlView.setBackgroundColor(Color.RED);
	}
	@Override
	public void onPause() {
		super.onPause();
		mCurlView.onPause();
	}

	@Override
	public void onResume() {
		super.onResume();
		mCurlView.onResume();
	}
	
	private class PageProvider implements CurlView.PageProvider{
		int[] mBitmapIds = { R.drawable.popup_img_number_1, R.drawable.popup_img_number_2,
				R.drawable.popup_img_number_3, R.drawable.popup_img_number_4
				, R.drawable.popup_img_number_5, R.drawable.popup_img_number_6 
				,R.drawable.cp};

		@Override
		public int getPageCount() {
			return 7;
		}
		
		//这是一个bitmap转化方法
		private Bitmap loadBitmap(int width, int height, int index) {
			Bitmap b = Bitmap.createBitmap(width, height,
					Bitmap.Config.ARGB_8888);
			b.eraseColor(Color.GRAY);
			Canvas c = new Canvas(b);
			Drawable d = getResources().getDrawable(mBitmapIds[index]);

			int margin = 7;
			int border = 3;
			Rect r = new Rect(margin, margin, width - margin, height - margin);

			int imageWidth = r.width() - (border * 2);
			int imageHeight = imageWidth * d.getIntrinsicHeight()
					/ d.getIntrinsicWidth();
			if (imageHeight > r.height() - (border * 2)) {
				imageHeight = r.height() - (border * 2);
				imageWidth = imageHeight * d.getIntrinsicWidth()
						/ d.getIntrinsicHeight();
			}

			r.left += ((r.width() - imageWidth) / 2) - border;
			r.right = r.left + imageWidth + border + border;
			r.top += ((r.height() - imageHeight) / 2) - border;
			r.bottom = r.top + imageHeight + border + border;

			Paint p = new Paint();
			p.setColor(Color.YELLOW);
			c.drawRect(r, p);
			r.left += border;
			r.right -= border;
			r.top += border;
			r.bottom -= border;

			d.setBounds(r);
			d.draw(c);

			return b;
		}

		@Override
		public void updatePage(CurlPage page, int width, int height, int index) {
			// TODO Auto-generated method stub
			//这里是图片的一个转化，如果直接调用资源图片，容易内存溢出
			Bitmap bitmap = loadBitmap(width, height, index);
			//设置前一页或者后一页的图片，后一页的图片是翻转的,调用reverseBitmap方法反转过来
			page.setTexture(bitmap, CurlPage.SIDE_FRONT);
			page.setTexture(reverseBitmap(bitmap, 0), CurlPage.SIDE_BACK);
			
			//设置背面的背景，这个背景色会掩盖图片，所以基本不用，而到loadBitmap方法去设置
//			page.setColor(Color.GREEN, CurlPage.SIDE_FRONT);
//			page.setColor(Color.GREEN, CurlPage.SIDE_BACK);
			switch (index) {
			case 0:
				break;

			default:
				break;
			}
		}
		
	}
	
	/**
	 * CurlView size changed observer.
	 */
	private class SizeChangedObserver implements CurlView.SizeChangedObserver {
		@Override
		public void onSizeChanged(int w, int h) {
			if (w > h) {
				//设置显示模式，一页还两页
				mCurlView.setViewMode(CurlView.SHOW_TWO_PAGES);
//				mCurlView.setMargins(.1f, .05f, .1f, .05f);
			} else {
				mCurlView.setViewMode(CurlView.SHOW_ONE_PAGE);
//				mCurlView.setMargins(.1f, .1f, .1f, .1f);
			}
		}
	}
	
	/** 
     * 图片反转 
     *  
     * @param bm 
     * @param flag 
     *            0为水平反转，1为垂直反转 
     * @return 
     */  
    public Bitmap reverseBitmap(Bitmap bmp, int flag) {  
        float[] floats = null;  
        switch (flag) {  
        case 0: // 水平反转  
            floats = new float[] { -1f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 1f };  
            break;  
        case 1: // 垂直反转  
            floats = new float[] { 1f, 0f, 0f, 0f, -1f, 0f, 0f, 0f, 1f };  
            break;  
        }  
  
        if (floats != null) {  
            Matrix matrix = new Matrix();  
            matrix.setValues(floats);  
            return Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);  
        }  
  
        return null;  
    } 
    
    /** 
     * 图片旋转 
     *  
     * @param bmp 
     *            要旋转的图片 
     * @param degree 
     *            图片旋转的角度，负值为逆时针旋转，正值为顺时针旋转 
     * @return 
     */  
    public static Bitmap rotateBitmap(Bitmap bmp, float degree) {  
        Matrix matrix = new Matrix();  
        matrix.postRotate(degree);  
        return Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);  
    } 
    
    /** 
     * 图片缩放 
     *  
     * @param bm 
     * @param w 
     *            缩小或放大成的宽 
     * @param h 
     *            缩小或放大成的高 
     * @return 
     */  
    public static Bitmap resizeBitmap(Bitmap bm, int w, int h) {  
        Bitmap BitmapOrg = bm;  
  
        int width = BitmapOrg.getWidth();  
        int height = BitmapOrg.getHeight();  
  
        float scaleWidth = ((float) w) / width;  
        float scaleHeight = ((float) h) / height;  
  
        Matrix matrix = new Matrix();  
        matrix.postScale(scaleWidth, scaleHeight);  
        return Bitmap.createBitmap(BitmapOrg, 0, 0, width, height, matrix, true);  
    }  
}
