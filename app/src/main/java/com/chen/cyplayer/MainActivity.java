package com.chen.cyplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.chen.cyplayer.bean.CyTimeInfoBean;
import com.chen.cyplayer.listener.CyOnCompleteListener;
import com.chen.cyplayer.listener.CyOnLoadListener;
import com.chen.cyplayer.listener.CyOnParparedListener;
import com.chen.cyplayer.listener.CyOnTimeInfoListener;
import com.chen.cyplayer.log.MyLog;
import com.chen.cyplayer.player.CyPlayer;
import com.chen.cyplayer.util.CyTimeUtil;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private CyPlayer cyPlayer;
    private TextView tvTime;
    private TextView tvVolum;
    private SeekBar seekVolume;
    private SeekBar seekSeek;
    private boolean isSeekBar = false;
    private int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvTime = findViewById(R.id.tvTime);
        tvVolum = findViewById(R.id.tvVolum);
        seekVolume = findViewById(R.id.seekVolume);
        seekSeek = findViewById(R.id.seekSeek);

        cyPlayer = CyPlayer.getInstance();
        cyPlayer.setCyOnParparedListener(new CyOnParparedListener() {
            @Override
            public void onParpared() {
                boolean b = Looper.getMainLooper() == Looper.myLooper();
                MyLog.d("准备好了，可以开始播放声音了！  "+b);
                cyPlayer.start();
            }
        });
        cyPlayer.setCyOnLoadListener(new CyOnLoadListener() {
            @Override
            public void onLoad(boolean load) {
                boolean b = Looper.getMainLooper() == Looper.myLooper();
                if (load){
                    MyLog.d("加载中...  "+b);
                }else {
                    MyLog.d("播放中...  "+b);
                }
            }
        });
        cyPlayer.setCyOnTimeInfoListener(new CyOnTimeInfoListener() {
            @Override
            public void timeInfo(CyTimeInfoBean timeInfoBean) {

                if (!isSeekBar){
                    seekSeek.setProgress(timeInfoBean.getCurrentTime() * 100 / timeInfoBean.getTotalTime()  );
                }

                tvTime.setText(CyTimeUtil.secdsToDateFormat(timeInfoBean.getCurrentTime()) + "/" + CyTimeUtil.secdsToDateFormat(timeInfoBean.getTotalTime()));
            }
        });

        cyPlayer.setCyOnCompleteListener(new CyOnCompleteListener() {
            @Override
            public void onComplete() {
                MyLog.d("播放完成");
            }
        });

        seekSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (cyPlayer.getDuration() > 0 && fromUser){
                    position = cyPlayer.getDuration() * progress / 100;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                isSeekBar = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                isSeekBar = false;
                cyPlayer.seek(position);
            }
        });

        seekVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                cyPlayer.setVolume(progress);
                tvVolum.setText("音量:" + cyPlayer.getVolumePercent()+"");
                MyLog.d("volmue: "+cyPlayer.getVolumePercent());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    public void begin(View view) {

        cyPlayer.setSource("http://mpge.5nd.com/2015/2015-11-26/69708/1.mp3");
//        cyPlayer.setSource("/mnt/sdcard/tencent/QQfile_recv/芙蓉锦铺.mp3");
        cyPlayer.parpared();


    }

    public void pause(View view) {
        cyPlayer.pause();
    }

    public void resume(View view) {
        cyPlayer.resume();
    }

    public void stop(View view) {
        cyPlayer.stop();
    }

    public void seek(View view) {
        cyPlayer.seek(210);
    }

    public void next(View view) {
        cyPlayer.playNext("/mnt/sdcard/tencent/QQfile_recv/芙蓉锦铺.mp3");
    }

    public void volume(View view) {
        cyPlayer.setVolume(0);

    }
}
