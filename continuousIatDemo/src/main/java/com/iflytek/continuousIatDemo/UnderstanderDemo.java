package com.iflytek.continuousIatDemo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.TextUnderstander;
import com.iflytek.cloud.TextUnderstanderListener;
import com.iflytek.cloud.UnderstanderResult;

/**
 * Created by lenovo on 2016/3/24.
 */
public class UnderstanderDemo extends Activity{
    private static String TAG = MainActivity.class.getSimpleName();
    //语义理解对象（文本到语义）
    private TextUnderstander mTextUnderstander;
    private Toast mToast;
    private String text;

    @SuppressLint("ShowToast")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SpeechUtility.createUtility(UnderstanderDemo.this, "appid=" + "565fe584");
        mTextUnderstander = TextUnderstander.createTextUnderstander(UnderstanderDemo.this, mTextUdrInitListener);
        mToast = Toast.makeText(UnderstanderDemo.this, "", Toast.LENGTH_SHORT);
        setParam();
        if (mTextUnderstander.isUnderstanding()) {
            mTextUnderstander.cancel();
            showTip("取消");
        } else {
            text = getIntent().getStringExtra("text");
            mTextUnderstander.understandText(text, mTextUnderstanderListener);
        }
    }


    /**
     * 初始化监听器（文本到语义）
     */
    private InitListener mTextUdrInitListener = new InitListener() {

        @Override
        public void onInit(int code) {
            Log.d(TAG, "textUnderstanderListener init() code = " + code);
            if (code != ErrorCode.SUCCESS) {
                showTip("初始化失败,错误码："+code);
            }
        }
    };

    /**
     * 文本回调理解
     */
    private TextUnderstanderListener mTextUnderstanderListener = new TextUnderstanderListener() {
        @Override
        public void onResult(UnderstanderResult understanderResult) {
            if (null != understanderResult) {
                String text = understanderResult.getResultString();
                String str = JsonParser.pareIatCalculateResult(text);
                Intent intent = new Intent(UnderstanderDemo.this, TtSpeaking.class);
                intent.putExtra("text", str);
                startActivity(intent);
            } else {
                showTip("识别结果不正确。");
            }
        }

        @Override
        public void onError(SpeechError speechError) {

        }
    };
    private void showTip(final String str) {
        mToast.setText(str);
        mToast.show();
    }

    private void setParam() {

    }
}
