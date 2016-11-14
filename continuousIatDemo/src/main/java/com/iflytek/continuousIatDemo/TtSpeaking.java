package com.iflytek.continuousIatDemo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.SynthesizerListener;
import com.iflytek.continuousIat.R;

/**
 * Created by lenovo on 2016/3/24.
 */
public class TtSpeaking extends Activity{
    private SpeechSynthesizer mTts;
    private String voicer = "xiaoyan";
    //缓冲进度
    private int mPercentForBuffering = 0;
    //播放进度
    private int mPercentForPlaying = 0;
    private Toast mToast;

    @SuppressLint("ShowToast")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tt_speaking);
        SpeechUtility.createUtility(TtSpeaking.this, "appid=" + "565fe584");
        mTts = SpeechSynthesizer.createSynthesizer(TtSpeaking.this, mTtsInitListener);
        setParam();
        String text = getIntent().getStringExtra("text");
        mTts.startSpeaking(text, mTtsListener);


        mToast = Toast.makeText(TtSpeaking.this, "", Toast.LENGTH_SHORT);
    }
    private void setParam() {
        mTts.setParameter(SpeechConstant.PARAMS, null);
        mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
        mTts.setParameter(SpeechConstant.VOICE_NAME, voicer);
        mTts.setParameter(SpeechConstant.SPEED, "50");
        mTts.setParameter(SpeechConstant.PITCH, "50");
        mTts.setParameter(SpeechConstant.VOLUME, "50");
        mTts.setParameter(SpeechConstant.STREAM_TYPE, "3");
        mTts.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "false");
        //mTts.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
        mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, Environment.getExternalStorageDirectory() + "/msc/tts.wav");
    }

    private SynthesizerListener mTtsListener = new SynthesizerListener() {
        @Override
        public void onSpeakBegin() {
            showTip("开始播放");
        }

        @Override
        public void onBufferProgress(int percent, int beginPos, int endPos, String info) {
            //合成进度
            mPercentForBuffering = percent;
            showTip(String.format("缓冲进度为%d%%,播放进度为%d%%", mPercentForBuffering, mPercentForPlaying));
        }
        //暂停播放时就回调到这里了
        @Override
        public void onSpeakPaused() {
            showTip("暂停播放");//播放暂停后就会来到这儿，。。。。。。这里容易出现bug
        }
        @Override
        public void onSpeakResumed() {
            showTip("继续播放");
        }
        //正在播放时就回调到这里了
        @Override
        public void onSpeakProgress(int i, int i1, int i2) {
            mPercentForPlaying = i;
            showTip(String.format("缓冲进度为%d%%,播放进度为%d%%", mPercentForBuffering, mPercentForPlaying));
        }

        @Override
        public void onCompleted(SpeechError speechError) {
            if (speechError == null) {
                showTip("播放完成");
            }else if (speechError != null) {
                showTip(speechError.getPlainDescription(true));
            }
            Intent intent = new Intent(TtSpeaking.this, MainActivity.class);
            startActivity(intent);
        }
        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {

        }
    };

    private InitListener mTtsInitListener = new InitListener() {
        @Override
        public void onInit(int code) {
            Log.i("TAG", "InitListener init() code = " + code);
            if (code != ErrorCode.SUCCESS) {
                showTip("初始化失败，错误码：" + code);
            } else {
                //初始化成功，之后可以调用startSpeaking方法
                //注：有的开发者在onCreate方法中创建完合成对象后马上就调用startSpeaking进行合成
                //正确的做法是将onCreate中的startSpeaking调用移至之这里
            }
        }
    };
    private void showTip(final String str) {
        mToast.setText(str);
        mToast.show();
    }
}
