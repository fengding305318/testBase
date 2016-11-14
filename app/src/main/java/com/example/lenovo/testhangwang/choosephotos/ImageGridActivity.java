package com.example.lenovo.testhangwang.choosephotos;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.example.lenovo.testhangwang.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Created by lenovo on 2016/4/27.
 */
public class ImageGridActivity extends Activity {
    public static final String EXTRA_IMAGE_LIST = "imagelist";

    List<ImageItem> dataList;
    GridView gridView;
    ImageGridAdapter adapter;
    AlbumHelper helper;
    Button bt;

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    Toast.makeText(ImageGridActivity.this, "最多选择9张图片", 400).show();
                    break;

                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_image_grid);
        helper = AlbumHelper.getHelper();
        helper.init(getApplicationContext());

        dataList = (List<ImageItem>) getIntent().getSerializableExtra(
                EXTRA_IMAGE_LIST);

        initView();
        bt = (Button) findViewById(R.id.bt);
        bt.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                ArrayList<String> list = new ArrayList<String>();
                Collection<String> c = adapter.map.values();
                Iterator<String> it = c.iterator();
                for (; it.hasNext(); ) {
                    list.add(it.next());
                }

                if (Bimp.act_bool) {
                    setResult(Activity.RESULT_OK);
                    Bimp.act_bool = false;
                }
                for (int i = 0; i < list.size(); i++) {
                    if (Bimp.bmp.size() < 9) {
                        try {
                            Bitmap bm = Bimp.revitionImageSize(list.get(i));
                            Bimp.bmp.add(bm);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                finish();
            }

        });
    }

    private void initView() {
        gridView = (GridView) findViewById(R.id.gridview);
        gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        adapter = new ImageGridAdapter(ImageGridActivity.this, dataList,
                mHandler);
        gridView.setAdapter(adapter);
        adapter.setTextCallback(new ImageGridAdapter.TextCallback() {
            public void onListen(int count) {
                bt.setText("完成" + "(" + count + ")");
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                adapter.notifyDataSetChanged();
            }

        });

    }
}
