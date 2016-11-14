package com.example.lenovo.testhangwang.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class StringUtil {


	/**
	 *    RGB32ת�Ҷ�
	 * @param srcImgData   ����RGB32���
	 * @param nWidth
	 * @param nHeight
	 * @param pbGrayData    ����Ҷ����
	 */
	public static void getGrayDataFromRgb32(int[] srcImgData,int nWidth,int nHeight,byte[] pbGrayData)
	{
		Bitmap srcColor = Bitmap.createBitmap(srcImgData,nWidth, nHeight, Bitmap.Config.ARGB_8888);
		int w=srcColor.getWidth(),h=srcColor.getHeight();
		int[] pix = new int[w * h];
		srcColor.getPixels(pix, 0, w, 0, 0, w, h);

		int alpha=0xFF<<24;
		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w; j++) {
				// ������ص���ɫ
				int color = pix[w * i + j];
				int red = ((color & 0x00FF0000) >> 16);
				int green = ((color & 0x0000FF00) >> 8);
				int blue = color & 0x000000FF;
				color = (red + green + blue)/3;
				color = alpha | (color << 16) | (color << 8) | color;
				pbGrayData[w * i + j] = (byte)color;
			}
		}
		pix = null;
		if(!srcColor.isRecycled())
		{
			srcColor.recycle();
		}
		System.gc();
		System.gc();

	}



	//����ͼƬ������ֵ
	public static int calculateInSampleSize(BitmapFactory.Options options,int reqWidth, int reqHeight) {
	    final int height = options.outHeight;
	    final int width = options.outWidth;
	    int inSampleSize = 1;

	    if (height > reqHeight || width > reqWidth) {
	             final int heightRatio = Math.round((float) height/ (float) reqHeight);
	             final int widthRatio = Math.round((float) width / (float) reqWidth);
	             inSampleSize = heightRatio > widthRatio ? heightRatio : widthRatio;
	    }
	        return inSampleSize;
	}

	// ���·�����ͼƬ��ѹ��������bitmap������ʾ
	public static Bitmap getSmallBitmap(String filePath) {
	        final BitmapFactory.Options options = new BitmapFactory.Options();
	        options.inJustDecodeBounds = true;
	        BitmapFactory.decodeFile(filePath, options);
//	        if(options.outWidth > 2048){

	        	int tempWidth = options.outWidth;
	        	int tempHeight = options.outHeight;
//	        	if((float)tempHeight/(float)tempWidth == 4f/3f){
	        		while(tempWidth > 1024 || tempHeight > 1024){
	        			tempWidth/=2;
	        			tempHeight/=2;
	        		}
	        		options.inSampleSize = calculateInSampleSize(options, tempWidth, tempHeight);

//	        		options.inSampleSize = calculateInSampleSize(options, tempHeight*3/4, tempHeight);
//	        	}else{
//	        		 options.inJustDecodeBounds = false;
//
//	        		 return null;
////	        		 return getScaleBitmap(filePath);
//	        	}
//	        }else{
//	        	options.inSampleSize = calculateInSampleSize(options, 480, 640);
//	        }

	        // Decode bitmap with inSampleSize set
	        options.inJustDecodeBounds = false;

	        return BitmapFactory.decodeFile(filePath, options);
	    }

	public static Bitmap getSmall100Bitmap(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        options.inSampleSize = calculateInSampleSize(options, 100, 100);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(filePath, options);
    }
}
