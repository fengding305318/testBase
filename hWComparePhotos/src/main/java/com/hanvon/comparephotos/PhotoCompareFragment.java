package com.hanvon.comparephotos;

import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.hanvon.choosephotos.ChoosePhotosActivity;
import com.hanvon.choosephotos.ImageItem;
import com.hanvon.utils.StringUtil;
import com.hanvon.utils.YuvDataUtil;
import com.hanvon.faceRec.FaceCoreHelper;
import com.hanvon.faceRec.HWFaceClient;

import android.app.Application;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Bitmap.Config;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class PhotoCompareFragment extends Fragment implements OnClickListener{

	private int REQUSETCODE_SELECTPHOTO_1 = 1;
	private int REQUSETCODE_SELECTPHOTO_2 = 2;
	private TextView tv_result;
	private Bitmap bmpImage1,bmpImage2;
	private ImageView img1,img2;
	private byte[] pRefFeature1;
	private byte[] pRefFeature2;
	private ProgressBar PB_OrderProgress;
	
	public static final String EXTRA_IMAGE_LIST = "imagelist";
	public static final String EXTRA_IMAGE_Sort_LIST = "imageSortlist";
	List<ImageItem> dataList;
	private String strResultTime = "";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.frag_photo_compare, null);
		view.findViewById(R.id.btn_start).setOnClickListener(this);
		view.findViewById(R.id.btn_getFeature).setOnClickListener(this);
		img1 = (ImageView) view.findViewById(R.id.img_photo1);
		img1.setOnClickListener(this);
		img2 = (ImageView) view.findViewById(R.id.img_photo2);
		img2.setOnClickListener(this);
		tv_result = (TextView) view.findViewById(R.id.tv_result);
		PB_OrderProgress = (ProgressBar) view.findViewById(R.id.progressBar1);
		PB_OrderProgress.setVisibility(View.INVISIBLE);
		
		return view;
	}

	@Override
	public void onClick(View arg0) {
		switch(arg0.getId()){
		case R.id.btn_start:
			
			float[] frvalue = new float[1];
			
			/*
			if(pRefFeature1 != null && pRefFeature1.length > 0 && pRefFeature2 != null && pRefFeature2.length > 0){
				
				FaceCoreHelper.HwCompareFeature(pRefFeature1, pRefFeature2, frvalue);
				FaceCoreHelper.HwCompareSecondFeature(pRefFeature1, pRefFeature2, frvalue2);
				int score = frvalue[0] + frvalue2[0];
				tv_result.setText("�ȶԵ÷֣�" + score);
			}
			*/
			boolean result = false;
			if(dataList != null)
			{
				if(dataList.size() > 0)
				{
					for(int i = 0; i <dataList.size(); i++)
					{
						final ImageItem item = dataList.get(i);
						if(item.pFeature != null && item.pFeature.length > 0)
						{
							if(pRefFeature1 != null && pRefFeature1.length > 0)
							{
								FaceCoreHelper.HwCompareFeature(pRefFeature1, item.pFeature, frvalue);
								float score = frvalue[0];
								item.score = (int)(score * 100);
								dataList.set(i, item);
								result = true;
							}
							else
							{
								tv_result.setText("ȱ�ٵ���ͼƬ��ģ��");
							}
						}
					}
				}
			}
			if(result)
			{
				QuickSort(dataList);
				MyApplication.displayStr("������..");
				
				List<ImageItem> dataSortList = new ArrayList<ImageItem>();;
				
				/*С���ķ�����0��ʼ������
				for(int i = dataList.size() - 1; i >= 0; i--)
				{
					if(dataList.get(i).pFeature != null)
					{
						dataSortList.add(dataList.get(i));
					}
				}
				*/
				//����ķ���100��ֵ
				for(int i = 0; i < dataList.size(); i++)
				{
					if(dataList.get(i).pFeature != null)
					{
						dataSortList.add(dataList.get(i));
					}
				}
				
				Intent intent2 = new Intent(getActivity(), ChoosePhotosActivity.class);
				intent2.putExtra(ChoosePhotosActivity.EXTRA_IMAGE_Sort_LIST,
						(Serializable) dataSortList);
				startActivity(intent2);
			}
			else
			{
				tv_result.setText("ͼƬ��δ��ȡ��ģ��");
			}
			
			break;
		case R.id.img_photo1:
			Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			startActivityForResult(intent, REQUSETCODE_SELECTPHOTO_1);
			
			//tv_result.setText("�ȶԵ÷֣�" + 0);
			break;
		case R.id.img_photo2:
			//Intent intent2 = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			//startActivityForResult(intent2, REQUSETCODE_SELECTPHOTO_2);
			
			Intent intent2 = new Intent(getActivity(), ChoosePhotosActivity.class);
			startActivityForResult(intent2, REQUSETCODE_SELECTPHOTO_2);
			
			//tv_result.setText("�ȶԵ÷֣�" + 0);
			break;
		case R.id.btn_getFeature:
			iCount = 0;
		    success = 0;
			if(dataList != null)
			{
				if(dataList.size() > 0)
				{
					Progress = dataList.size();
					Message msg = new Message(); 
                    msg.what = START;  
                    mHandler.sendMessage(msg);
					Thread mThread = new Thread(new Runnable() {  
	                    public void run() {
	                    	
		                    
							for(int i = 0; i < dataList.size(); i++)
							{
								final ImageItem item = dataList.get(i);
								
								String strPath = item.imagePath;
								Bitmap bmpImage = null;
								bmpImage = StringUtil.getSmallBitmap(strPath);
								
								strResultTime = "";
								strResultTime = getFeature(bmpImage, item);
								strResultTime = String.format("%s,%s\r\n",strPath,strResultTime);
								if(item.score == 1)
									success++;
								dataList.set(i, item); 

								Message msgNext = new Message();
								msgNext.what = NEXT;  
                                mHandler.sendMessage(msgNext);
							}

							Message msgStop = new Message();
							msgStop.what = STOP;  
		                    mHandler.sendMessage(msgStop);
	                    }
					});
					mThread.start();  
					
				}
				else
				{
					MyApplication.displayStr("δ����ͼƬ");
					img2.setImageResource(R.drawable.icon_addpic_unfocused);
				}
			}
			else
			{
				MyApplication.displayStr("��ѡ��ͼƬ��");
				img2.setImageResource(R.drawable.icon_addpic_unfocused);
			}
			
			break;
		}
	}
	
	protected static final int STOP = 0x10000;  
    protected static final int NEXT = 0x10001; 
    protected static final int START = 0x1002;
    private int iCount = 0;
    private int success = 0;
    private int Progress = 0;
    private String strLogPath = Environment.getExternalStorageDirectory().getAbsolutePath();
	
	//����һ��Handler  
    private Handler mHandler = new Handler(){  
        public void handleMessage(Message msg){  
            switch (msg.what) {  
            case STOP:  
            	PB_OrderProgress.setVisibility(View.INVISIBLE);
                tv_result.setText("�� " + success + " ��ͼƬ��ȡģ��ɹ�");
                break;  
            case NEXT:  
                PB_OrderProgress.setProgress(iCount++);  
                try {
					saveLog(strLogPath + "/FaceLog.txt", strResultTime);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                break;
            case START:
            	PB_OrderProgress.setVisibility(View.VISIBLE);
            	tv_result.setText("��ʼ��ȡͼƬ��ģ��...");
				PB_OrderProgress.setMax(Progress);
				PB_OrderProgress.setProgress(0);
				break;
            }  
        }  
    }; 
    
    /** 
     * �����ļ� 
     *  
     * @param fileName �ļ��� 
     * @param content  �ļ����� 
     * @throws Exception 
     */
    public void saveLog(String fileName, String contentd) throws Exception { 

    	 // ����ҳ������Ķ����ı���Ϣ�����Ե��ļ���������.txt��׺����βʱ���Զ�����.txt��׺ 
        if (!fileName.endsWith(".txt")) { 
            fileName = fileName + ".txt"; 
        } 
          
        byte[] buf = fileName.getBytes("iso8859-1"); 
        fileName = new String(buf,"utf-8"); 
        FileOutputStream fos = new FileOutputStream(fileName, true); 
        fos.write(contentd.getBytes()); 
        fos.close();
    }
	
	//��������
    public static void QuickSort(List<ImageItem> items)
    {
        RecQuickSort(items, 0, items.size() - 1);
    }
    
    private static void RecQuickSort(List<ImageItem> items, int low, int high)
    {
        if (low < high)
        {
            int i = Partition(items, low, high);
            RecQuickSort(items, low, i - 1);
            RecQuickSort(items, i + 1, high);
        }
    }
    
    private static int Partition(List<ImageItem> items, int low, int high)
    {
        int tmp = (items.get(low)).score;
        ImageItem tmpstruct = items.get(low);
        while (low < high)
        {
            while (low < high && (items.get(high)).score <= tmp)
                high--;

            // ��λ���ܽ�low��1,��ֹ��λ  
            if (low < high)
            {
            	//items.get(low) = items.get(high);
            	items.set(low, items.get(high));
            }

            while (low < high && (items.get(low)).score >= tmp)
                low++;

            if (low < high)
            {
                //items[high] = items[low];
            	items.set(high, items.get(low));
                // ��low < high���ɽ�high��ǰ��һλ  
                high--;
            }
        }
        //items[low] = tmpstruct;
        items.set(low, tmpstruct);

        return low;
    }
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(getActivity().RESULT_OK == resultCode ){
			
			Uri photosUri = data.getData();
			Cursor cursor = getActivity().getContentResolver().query(photosUri, null, null, null, null);
			cursor.moveToFirst();
			String imgPath = cursor.getString(1);
			cursor.close();
			
			if(REQUSETCODE_SELECTPHOTO_1 == requestCode){
//				bmpImage1 = BitmapFactory.decodeFile(imgPath);
				bmpImage1 = StringUtil.getSmallBitmap(imgPath);
				if(bmpImage1 == null){
					MyApplication.displayStr("ͼ���ȡ����");
					return;
				}
//				bmpImage = StringUtil.getScaleBitmap(imgPath);
				photoManage(img1,bmpImage1,1);
				
			}else if(requestCode == REQUSETCODE_SELECTPHOTO_2){
				bmpImage2 = StringUtil.getSmallBitmap(imgPath);
				if(bmpImage2 == null){
					MyApplication.displayStr("ͼ���ȡ����");
					return;
				}
				photoManage(img2,bmpImage2,2);
			}
		}
		else if(resultCode == 10)
		{
			if(requestCode == REQUSETCODE_SELECTPHOTO_2){
				dataList = (List<ImageItem>) data.getSerializableExtra(
						EXTRA_IMAGE_LIST);
				
				Bitmap[] bmpImages = new Bitmap[4];
				
				for(int i = 0; i <dataList.size(); i++)
				{
					if(i < bmpImages.length)
					{
						final ImageItem item = dataList.get(i);
						String strPath = item.imagePath;
						Bitmap bmpImage = null;
						bmpImage = StringUtil.getSmall100Bitmap(strPath);
						bmpImages[i] = bmpImage;
					}
					else
					{
						break;
					}
					
					//getFeature()

					//dataList.set(i, item);
				}
				Bitmap bmps = addBitmap(bmpImages);
				img2.setImageBitmap(bmps);
			}
		}
		else{
			MyApplication.displayStr("��ȡͼƬʧ��");
		}
	}
	
	private String getFeature(Bitmap bmp, ImageItem item)
	{
		int result = 0;
		long timeBegin;
		long timeEnd;
		long getDetectTime = 0;
		long getFeaturTime = 0;
		
		int nWidth01 = bmp.getWidth();
		int nHeight01 = bmp.getHeight();
		byte[] referenceFeature = new byte[nWidth01 * nHeight01];
		int[] pixels01 = new int[nWidth01 * nHeight01]; //ͨ��λͼ�Ĵ�С�������ص�����
		bmp.getPixels(pixels01, 0, nWidth01, 0, 0, nWidth01, nHeight01);
		StringUtil.getGrayDataFromRgb32(pixels01, nWidth01, nHeight01, referenceFeature);
		
		int[] detectNum = new int[1];
		detectNum[0] = 1;
		int[] facePos = new int[(HWFaceClient.HW_FACEINFO_LEN * HWFaceClient.HW_FACEPOS_LEN) * detectNum[0]];
		
		timeBegin = System.nanoTime();
		int detectResult = FaceCoreHelper.HwDetectMultiFaceAndEyeEx(referenceFeature, nWidth01, nHeight01, facePos, detectNum);//(nWidth01 + 3)/4 * 4
		timeEnd = System.nanoTime();
		getDetectTime = (timeEnd - timeBegin)/1000000;
		
		if(detectResult == HWFaceClient.HW_OK){
			int[] featureLength = new int[1];
			FaceCoreHelper.HwGetFeatureSize(featureLength);
			int featureSize = featureLength[0];
			item.pFeature = new byte[featureSize];
			
			timeBegin = System.nanoTime();
			result = FaceCoreHelper.HwGetFaceFeatureEx(referenceFeature, nWidth01, nHeight01, facePos, item.pFeature);
			timeEnd = System.nanoTime();
			getFeaturTime = (timeEnd - timeBegin)/1000000;
			
			if(result == HWFaceClient.HW_OK)
				item.score = 1;
			else
			{
				item.pFeature = null;
				item.score = -1;
			}
		}
		
		String strTime = "��λ��" + getDetectTime + " ������" + getFeaturTime;
		
		return strTime;
	}
	
	private Bitmap addBitmap(Bitmap[] bitmaps) {
		int width = 210;
		int height = 210;
		Bitmap result = Bitmap.createBitmap(width, height, Config.ARGB_8888); 
		
		Canvas canvas = new Canvas(result);
		
		for(int i = 0 ; i < 4; i++)
		{
			if(bitmaps[i] != null)
			{
				if(i == 0)
				{
					canvas.drawBitmap(bitmaps[i], new Rect(0,0,bitmaps[i].getWidth(),bitmaps[i].getHeight()), new Rect(0,0,100,100), null);
				}
				else if(i == 1)
				{
					canvas.drawBitmap(bitmaps[i], new Rect(0,0,bitmaps[i].getWidth(),bitmaps[i].getHeight()), new Rect(110,0,210,100), null);
				}
				else if(i == 2)
				{
					canvas.drawBitmap(bitmaps[i], new Rect(0,0,bitmaps[i].getWidth(),bitmaps[i].getHeight()), new Rect(0,110,100,210), null);
				}
				else
				{
					canvas.drawBitmap(bitmaps[i], new Rect(0,0,bitmaps[i].getWidth(),bitmaps[i].getHeight()), new Rect(110,110,210,210), null);
				}
			}
		}
		return result;
   }

	
	private void photoManage(ImageView img,Bitmap bmp,int tag){
		
		
		int nWidth01 = bmp.getWidth();
		int nHeight01 = bmp.getHeight();
		byte[] referenceFeature = new byte[nWidth01 * nHeight01];
		int[] pixels01 = new int[nWidth01 * nHeight01]; //ͨ��λͼ�Ĵ�С�������ص�����
		bmp.getPixels(pixels01, 0, nWidth01, 0, 0, nWidth01, nHeight01);
		StringUtil.getGrayDataFromRgb32(pixels01, nWidth01, nHeight01, referenceFeature);

		int[] detectNum = new int[1];
		detectNum[0] = 1;
		int[] facePos = new int[(HWFaceClient.HW_FACEINFO_LEN * HWFaceClient.HW_FACEPOS_LEN) * detectNum[0]];

		int detectResult = FaceCoreHelper.HwDetectMultiFaceAndEyeEx(referenceFeature, nWidth01, nHeight01, facePos, detectNum);//(nWidth01 + 3)/4 * 4
		if(detectResult == HWFaceClient.HW_OK){
			MyApplication.displayStr("��λ�ɹ�");
			
			getLocalPicFeature(referenceFeature,nWidth01, nHeight01,facePos,tag);
			
			img.setImageBitmap(bmp);
		}else if(nWidth01 < 320){
			MyApplication.displayStr("ͼƬ̫С����Ŵ��������");
			img.setImageResource(R.drawable.icon_addpic_unfocused);
		}else{
			img.setImageResource(R.drawable.icon_addpic_unfocused);
			MyApplication.displayStr("��λ���ɹ�������ͼƬ");
			pRefFeature1 = null;
			pRefFeature2 = null;
		}
	}
	
	private void getLocalPicFeature(byte[] referenceFeature, int width, int height, int[] facePos,int index){
		int[] featureLength = new int[1];
		FaceCoreHelper.HwGetFeatureSize(featureLength);
		int featureSize = featureLength[0];
		if(index == 1){
			pRefFeature1 = new byte[featureSize];
			FaceCoreHelper.HwGetFaceFeatureEx(referenceFeature, width, height, facePos, pRefFeature1);
		}else if(2 == index){
			pRefFeature2 = new byte[featureSize];
			FaceCoreHelper.HwGetFaceFeatureEx(referenceFeature, width, height, facePos, pRefFeature2);
		}
	}
	
}
