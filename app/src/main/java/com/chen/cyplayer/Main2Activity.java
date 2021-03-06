package com.chen.cyplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaFormat;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.chen.cyplayer.camera.CyCameraView;
import com.chen.cyplayer.encodec.CyBaseMediaEncoder;
import com.chen.cyplayer.encodec.CyMediaEncodec;
import com.chen.cyplayer.log.MyLog;
import com.chen.cyplayer.util.DisplayUtil;

public class Main2Activity extends AppCompatActivity {
    private CyCameraView cyCameraView;
    private Button button;
    private CyMediaEncodec cyMediaEncodec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        cyCameraView = findViewById(R.id.surfaceView);
        button = findViewById(R.id.button);
        initView();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void initView(){



    }

    private void record(int samplerate, int channels){
        if(cyMediaEncodec == null)
        {
            cyMediaEncodec = new CyMediaEncodec(Main2Activity.this, cyCameraView.getTextureId());
            cyMediaEncodec.initEncodec(cyCameraView.getEglContext(),
                    Environment.getExternalStorageDirectory().getAbsolutePath() + "/测试视频.mp4",720, 1280, samplerate, channels);
            cyMediaEncodec.setOnMediaInfoListener(new CyBaseMediaEncoder.OnMediaInfoListener() {
                @Override
                public void onMediaTime(int times) {
                    Log.d("chen188669", "time is : " + times);
                }
            });
            cyMediaEncodec.startRecord();
        }
        else
        {
            cyMediaEncodec.stopRecord();
            button.setText("开始录制");
            cyMediaEncodec = null;
        }
    }
}
