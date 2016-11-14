package com.example.lenovo.testhangwang.comparephotos;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.lenovo.testhangwang.R;
import com.example.lenovo.testhangwang.choosephotos.ChoosePhotosActivity;
import com.example.lenovo.testhangwang.choosephotos.ImageItem;
import com.example.lenovo.testhangwang.faceRec.FaceCoreHelper;
import com.example.lenovo.testhangwang.faceRec.HWFaceClient;
import com.example.lenovo.testhangwang.utils.StringUtil;

import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2016/4/27.
 */
public class PhotoCompareFragment extends Fragment implements View.OnClickListener {
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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
				tv_result.setText("比对得分：" + score);
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
                                    tv_result.setText("缺少单张图片的模板");
                                }
                            }
                        }
                    }
                }
                if(result)
                {
                    QuickSort(dataList);
                    MyApplication.displayStr("排序中..");

                    List<ImageItem> dataSortList = new ArrayList<ImageItem>();;

				/*小核心分数从0开始最相似
				for(int i = dataList.size() - 1; i >= 0; i--)
				{
					if(dataList.get(i).pFeature != null)
					{
						dataSortList.add(dataList.get(i));
					}
				}
				*/
                    //大核心分数100分值
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
                    tv_result.setText("图片集未提取到模板");
                }

                break;
            case R.id.img_photo1:
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQUSETCODE_SELECTPHOTO_1);

                //tv_result.setText("比对得分：" + 0);
                break;
            case R.id.img_photo2:
                //Intent intent2 = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                //startActivityForResult(intent2, REQUSETCODE_SELECTPHOTO_2);

                Intent intent2 = new Intent(getActivity(), ChoosePhotosActivity.class);
                startActivityForResult(intent2, REQUSETCODE_SELECTPHOTO_2);

                //tv_result.setText("比对得分：" + 0);
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
                        MyApplication.displayStr("未发现图片");
                        img2.setImageResource(R.drawable.icon_addpic_unfocused);
                    }
                }
                else
                {
                    MyApplication.displayStr("请选择图片集");
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

    //定义一个Handler
    private Handler mHandler = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what) {
                case STOP:
                    PB_OrderProgress.setVisibility(View.INVISIBLE);
                    tv_result.setText("有 " + success + " 张图片提取模板成功");
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
                    tv_result.setText("开始提取图片集模板...");
                    PB_OrderProgress.setMax(Progress);
                    PB_OrderProgress.setProgress(0);
                    break;
            }
        }
    };

    /**
     * 保存文件
     *
     * @param fileName 文件名
     * @param contentd  文件内容
     * @throws Exception
     */
    public void saveLog(String fileName, String contentd) throws Exception {

        // 由于页面输入的都是文本信息，所以当文件名不是以.txt后缀名结尾时，自动加上.txt后缀
        if (!fileName.endsWith(".txt")) {
            fileName = fileName + ".txt";
        }

        byte[] buf = fileName.getBytes("iso8859-1");
        fileName = new String(buf,"utf-8");
        FileOutputStream fos = new FileOutputStream(fileName, true);
        fos.write(contentd.getBytes());
        fos.close();
    }

    //快速排序
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

            // 换位后不能将low加1,防止跳位
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
                // 有low < high，可将high向前推一位
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
                    MyApplication.displayStr("图像获取出错");
                    return;
                }
//				bmpImage = StringUtil.getScaleBitmap(imgPath);
                photoManage(img1,bmpImage1,1);

            }else if(requestCode == REQUSETCODE_SELECTPHOTO_2){
                bmpImage2 = StringUtil.getSmallBitmap(imgPath);
                if(bmpImage2 == null){
                    MyApplication.displayStr("图像获取出错");
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
            MyApplication.displayStr("获取图片失败");
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
        int[] pixels01 = new int[nWidth01 * nHeight01]; //通过位图的大小创建像素点数组
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

        String strTime = "定位：" + getDetectTime + " 特征：" + getFeaturTime;

        return strTime;
    }

    private Bitmap addBitmap(Bitmap[] bitmaps) {
        int width = 210;
        int height = 210;
        Bitmap result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

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
        int[] pixels01 = new int[nWidth01 * nHeight01]; //通过位图的大小创建像素点数组
        bmp.getPixels(pixels01, 0, nWidth01, 0, 0, nWidth01, nHeight01);
        StringUtil.getGrayDataFromRgb32(pixels01, nWidth01, nHeight01, referenceFeature);

        int[] detectNum = new int[1];
        detectNum[0] = 1;
        int[] facePos = new int[(HWFaceClient.HW_FACEINFO_LEN * HWFaceClient.HW_FACEPOS_LEN) * detectNum[0]];

        int detectResult = FaceCoreHelper.HwDetectMultiFaceAndEyeEx(referenceFeature, nWidth01, nHeight01, facePos, detectNum);//(nWidth01 + 3)/4 * 4
        if(detectResult == HWFaceClient.HW_OK){
            MyApplication.displayStr("定位成功");

            getLocalPicFeature(referenceFeature,nWidth01, nHeight01,facePos,tag);

            img.setImageBitmap(bmp);
        }else if(nWidth01 < 320){
            MyApplication.displayStr("图片太小，请放大处理后再试");
            img.setImageResource(R.drawable.icon_addpic_unfocused);
        }else{
            img.setImageResource(R.drawable.icon_addpic_unfocused);
            MyApplication.displayStr("定位不成功，请检查图片");
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
