package com.example.lenovo.testhangwang.comparephotos;

import android.app.FragmentManager;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.lenovo.testhangwang.R;
import com.example.lenovo.testhangwang.faceRec.FaceCoreHelper;
import com.example.lenovo.testhangwang.faceRec.HWFaceClient;

public class MainActivity extends FragmentActivity implements View.OnClickListener {

    private android.support.v4.app.FragmentManager fragManger;
    private Button btnSelectPic;
    private Button btnPicCompare;
    private PhotoCompareFragment photoCmpFrag;
    public static int compareTime = 3;
    public static float recogValue = 0.6f;
    private Handler handler = new Handler();//1
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragManger = getSupportFragmentManager();

        btnSelectPic = (Button) findViewById(R.id.btn_idcard_comp);
        btnSelectPic.setOnClickListener(this);
        btnPicCompare = (Button) findViewById(R.id.btn_pics_comp);
        btnPicCompare.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            //设置定位阈值
            case R.id.loc_settings:
                return true;
            //设置识别阈值
            case R.id.recog_settings:
                return true;
            //设置时间
            case R.id.settings:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    Runnable clientThread = new Runnable() {
        @Override
        public void run() {
            int Result = HWFaceClient.InitFaceClient("182.92.162.37", 8888);
            if (Result == -1) {
                Toast.makeText(MainActivity.this, "服务器连接失败", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "服务器连接成功", Toast.LENGTH_SHORT).show();
            }
        }
    };
    @Override
    public void onClick(View arg0) {
        FragmentTransaction transAction = fragManger.beginTransaction();
        switch (arg0.getId()) {
            case R.id.btn_idcard_comp:
                handler.post(clientThread);

                break;
            case R.id.btn_pics_comp:
                if(HWFaceClient.GetKeyCode() == HWFaceClient.HW_OK)
                {
                    if(HWFaceClient.bpKeyCode != null)
                    {
                        //初始化核心
                        int initResult = FaceCoreHelper.HwInitFace(HWFaceClient.bpKeyCode);

                        if(initResult == HWFaceClient.HW_OK)
                        {
                            btnPicCompare.setBackgroundColor(getResources().getColor(R.color.grid_dark_background));
                            btnSelectPic.setBackgroundColor(getResources().getColor(R.color.cornsilk));
                            btnPicCompare.setEnabled(false);
                            btnSelectPic.setEnabled(true);

                            if(photoCmpFrag == null){
                                photoCmpFrag = new PhotoCompareFragment();
                            }
                            transAction.replace(R.id.container, photoCmpFrag);
                            transAction.commitAllowingStateLoss();
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this, "核心初始化失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this, "核心获取秘钥失败", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(MainActivity.this, "核心获取秘钥失败", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
    public void displayStr(String str){
        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        FaceCoreHelper.HwReleaseFace();

        HWFaceClient.ReleaseFaceClient();
    }

}
