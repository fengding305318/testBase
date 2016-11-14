package com.example.lenovo.testhangwang.choosephotos;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.lenovo.testhangwang.R;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lenovo on 2016/4/27.
 */
public class ChoosePhotosActivity extends Activity {
    List<ImageBucket> dataList;
    List<ImageItem> dataSortList;
    GridView gridView;
    ImageBucketAdapter adapter;//自定义的适配
    ImageItemAdapter sortAdapter;

    AlbumHelper helper;
    public static final String EXTRA_IMAGE_LIST = "imagelist";
    public static final String EXTRA_IMAGE_Sort_LIST = "imageSortlist";
    private int REQUSETCODE_SELECTPHOTO_2 = 2;
    public static Bitmap bimap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_image_bucket);
        helper = AlbumHelper.getHelper();
        helper.init(getApplicationContext());

        dataSortList = (List<ImageItem>) getIntent().getSerializableExtra(
                EXTRA_IMAGE_Sort_LIST);

        if(dataSortList != null)
        {
            initSortListView();
        }
        else
        {
            initData();
            initView();
        }
    }

    /**
     * 初始化数�?
     */
    private void initData() {
        dataList = helper.getImagesBucketList(false);
        bimap= BitmapFactory.decodeResource(
                getResources(),
                R.drawable.icon_addpic_unfocused);
    }

    private void initSortListView()
    {
        gridView = (GridView) findViewById(R.id.gridview);
        sortAdapter = new ImageItemAdapter(ChoosePhotosActivity.this, dataSortList);
        gridView.setAdapter(sortAdapter);
    }

    /**
     * 初始化view视图
     */
    private void initView() {
        gridView = (GridView) findViewById(R.id.gridview);
        adapter = new ImageBucketAdapter(ChoosePhotosActivity.this, dataList);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

				/*
				Intent intent = new Intent(ChoosePhotosActivity.this,
						ImageGridActivity.class);
				intent.putExtra(ChoosePhotosActivity.EXTRA_IMAGE_LIST,
						(Serializable) dataList.get(position).imageList);
				startActivityForResult(intent, 100);
				finish();
				*/
				/*
				Intent intent=new Intent();
	            intent.setClass(ChoosePhotosActivity.this, MainActivity.class);
	            intent.putExtra(ChoosePhotosActivity.EXTRA_IMAGE_LIST,
						(Serializable) dataList.get(position).imageList);
	            startActivityForResult(intent, REQUSETCODE_SELECTPHOTO_2);
	            ChoosePhotosActivity.this.finish();
	            */

                Intent intent = new Intent();
                intent.putExtra(ChoosePhotosActivity.EXTRA_IMAGE_LIST,
                        (Serializable) dataList.get(position).imageList);
                setResult(10, intent);// ����ش���ֵ,�����һ��Code,������ַ��ص����
                ChoosePhotosActivity.this.finish();

            }

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode!=Activity.RESULT_OK){
            return;
        }

        switch (requestCode) {

            case 100:
                setResult(Activity.RESULT_OK);
                finish();
                break;

            default:
                break;
        }
    }
}
